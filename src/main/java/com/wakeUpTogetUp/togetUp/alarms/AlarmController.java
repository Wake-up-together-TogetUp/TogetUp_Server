package com.wakeUpTogetUp.togetUp.alarms;

import com.wakeUpTogetUp.togetUp.alarms.model.PostAlarmReq;
import com.wakeUpTogetUp.togetUp.common.BaseResponse;
import com.wakeUpTogetUp.togetUp.common.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/app/users")
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmService alarmService;

//    사용자 알람, 루틴 목록 불러오기
//    @GetMapping("{userId}/alarms")
//    public BaseResponse<List<GetAlarmsRes>> GetAlarmsByUserId(@PathVariable int userId){
//        List<GetAlarmsRes> getAlarmsResList = alarmService.getAlarmsByUserId(userId);
//
//        return new BaseResponse<List<GetAlarmsRes>>(BaseResponseStatus.SUCCESS, getAlarmsResList);
//    }

//    알람 1개 불러오기

//    알람 생성(알람, 알람-루틴 매핑)
    @PostMapping("/{userId}/alarms")
    public BaseResponse createAlarm(
            @PathVariable("userId") int userId,
            @RequestBody @Valid PostAlarmReq postAlarmReq
    ){
        //TODO : jwt 정보와 일치하는지 확인하기
        int createdAlarmId = alarmService.createAlarm(userId, postAlarmReq);

        return new BaseResponse(BaseResponseStatus.SUCCESS, createdAlarmId);
    }

//    알람 수정
//    알람 삭제
}