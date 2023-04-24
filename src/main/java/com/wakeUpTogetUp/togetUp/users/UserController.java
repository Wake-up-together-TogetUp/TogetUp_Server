package com.wakeUpTogetUp.togetUp.users;

import com.wakeUpTogetUp.togetUp.config.BaseException;
import com.wakeUpTogetUp.togetUp.config.BaseResponse;
import com.wakeUpTogetUp.togetUp.config.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/app/users")
public class UserController  {

    private final UserService userService;


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






}





