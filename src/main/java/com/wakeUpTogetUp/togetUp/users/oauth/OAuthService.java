package com.wakeUpTogetUp.togetUp.users.oauth;

import com.wakeUpTogetUp.togetUp.users.LoginType;
import com.wakeUpTogetUp.togetUp.users.model.User;
import com.wakeUpTogetUp.togetUp.utils.JwtService;
import com.wakeUpTogetUp.togetUp.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//import static com.wakeUpTogetUp.togetUp.users.oauth.ProviderType.GOOGLE;



@Service
@RequiredArgsConstructor
public class OAuthService {
    private final GoogleOauth googleOauth;
    private final HttpServletResponse response;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    public String request(LoginType socialLoginType) throws IOException {
        String redirectURL;
        switch (socialLoginType) {
            case GOOGLE: {
                //각 소셜 로그인을 요청하면 소셜로그인 페이지로 리다이렉트 해주는 프로세스이다.
                redirectURL = googleOauth.getOauthRedirectURL();

            }
            break;
            default: {
                throw new IllegalArgumentException("알 수 없는 소셜 로그인 형식입니다.");
            }

        }

      //  response.sendRedirect(redirectURL);
        return redirectURL;
    }

    public GetSocialOAuthRes oAuthLogin(LoginType socialLoginType, String code) throws IOException {

        switch (socialLoginType) {
            case GOOGLE: {
                //구글로 일회성 코드를 보내 액세스 토큰이 담긴 응답객체를 받아옴
                ResponseEntity<String> accessTokenResponse = googleOauth.requestAccessToken(code);
                //응답 객체가 JSON형식으로 되어 있으므로, 이를 deserialization해서 자바 객체에 담을 것이다.
                GoogleOAuthToken oAuthToken = googleOauth.getAccessToken(accessTokenResponse);

                //액세스 토큰을 다시 구글로 보내 구글에 저장된 사용자 정보가 담긴 응답 객체를 받아온다.
                ResponseEntity<String> userInfoResponse = googleOauth.requestUserInfo(oAuthToken);
                //다시 JSON 형식의 응답 객체를 자바 객체로 역직렬화한다.
                GoogleUser googleUser = googleOauth.getUserInfo(userInfoResponse);

                String user_email = googleUser.getEmail();
                String user_name=googleUser.getName();
                System.out.println("here4"+user_email);

                //우리 서버의 db와 대조하여 해당 user가 존재하는 지 확인한다.
                int user_num= userRepository.countByEmail(user_email);//accountProvider.getUserNum(user_id);

                if(user_num!=0){
                //서버에 user가 존재하면 앞으로 회원 인가 처리를 위한 jwtToken을 발급한다.
                    // TODO : userId 가져와서 넣기
                String jwtToken = jwtService.generateAccessToken(9, secretKey, expiredTimeMs);//"dfdda";//jwtService.createJwt(user_num, user_id);
                //액세스 토큰과 jwtToken, 이외 정보들이 담긴 자바 객체를 다시 전송한다.

                GetSocialOAuthRes getSocialOAuthRes = new GetSocialOAuthRes(jwtToken, user_name, oAuthToken.getToken_type());
                return getSocialOAuthRes;
                }
                else //서버에 user가 존재하지 않으면 회원가입을 한다.
                {
                      User user =googleUser.toEntity();
                      userRepository.save(user);
                      // TODO : 토큰 고정됨?
                      System.out.println("여기!"+user);
                      String jwtToken = "dfdda";//jwtService.createJwt(user_num, user_id);
                      GetSocialOAuthRes getSocialOAuthRes = new GetSocialOAuthRes(jwtToken, user_name,  oAuthToken.getToken_type());
                      return getSocialOAuthRes;
                        //throw new BaseException(BaseResponseStatus.ACCOUNT_DOESNT_EXISTS);
                }
            }
            default: {
                throw new IllegalArgumentException("알 수 없는 소셜 로그인 형식입니다.");
            }

        }
    }
}
