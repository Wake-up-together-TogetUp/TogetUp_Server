package com.wakeUpTogetUp.togetUp.users;

import com.wakeUpTogetUp.togetUp.config.BaseException;
import com.wakeUpTogetUp.togetUp.config.BaseResponse;
import com.wakeUpTogetUp.togetUp.config.BaseResponseStatus;
import com.wakeUpTogetUp.togetUp.users.oauth.GetSocialOAuthRes;
import com.wakeUpTogetUp.togetUp.users.oauth.OAuthService;
import com.wakeUpTogetUp.togetUp.users.oauth.SocialLoginType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Controller
@RequiredArgsConstructor
@RequestMapping("/app/users")
public class UserController  {

    private final UserService userService;
    private final OAuthService oAuthService;


    @ResponseBody
    @PostMapping("/new") //
    public BaseResponse<BaseResponseStatus> join(@RequestBody UserForm form) {
        try {

            userService.createUser(form);
            return new BaseResponse(BaseResponseStatus.SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 유저 소셜 로그인으로 리다이렉트 해주는 url
     * [GET] /accounts/auth
     * @return void
     */
   // @NoAuth
    @GetMapping("/auth/{socialLoginType}") //GOOGLE이 들어올 것이다.
    @ResponseBody
    public BaseResponse<String> socialLoginRedirect(@PathVariable(name="socialLoginType") String SocialLoginPath) throws IOException {
        SocialLoginType socialLoginType= SocialLoginType.valueOf(SocialLoginPath.toUpperCase());
        String  redirectURL= oAuthService.request(socialLoginType);

        return new BaseResponse<>(BaseResponseStatus.SUCCESS,redirectURL);
    }
    /**
     * Social Login API Server 요청에 의한 callback 을 처리
     * @param socialLoginPath (GOOGLE, FACEBOOK, NAVER, KAKAO)
     * @param code API Server 로부터 넘어오는 code
     * @return SNS Login 요청 결과로 받은 Json 형태의 java 객체 (access_token, jwt_token, user_num 등)
     */


    @ResponseBody
    @GetMapping(value = "/auth/{socialLoginType}/callback")
    public BaseResponse<GetSocialOAuthRes> callback (
            @PathVariable(name = "socialLoginType") String socialLoginPath,
            @RequestParam(name = "code") String code)throws IOException,BaseException{
        System.out.println(">> 소셜 로그인 API 서버로부터 받은 code :"+ code);
        SocialLoginType socialLoginType= SocialLoginType.valueOf(socialLoginPath.toUpperCase());
        GetSocialOAuthRes getSocialOAuthRes=oAuthService.oAuthLogin(socialLoginType,code);

        return new BaseResponse<>(BaseResponseStatus.SUCCESS,getSocialOAuthRes);
    }






}





