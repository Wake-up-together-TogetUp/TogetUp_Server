package com.wakeUpTogetUp.togetUp.routine;

import com.wakeUpTogetUp.togetUp.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.routine.dto.request.PostRoutineReq;
import com.wakeUpTogetUp.togetUp.routine.dto.response.RoutineRes;
import com.wakeUpTogetUp.togetUp.routine.model.Routine;
import com.wakeUpTogetUp.togetUp.utils.mapper.EntityDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoutineService {
    private final RoutineRepository routineRepository;

    // 루틴 생성
    @Transactional
    public RoutineRes createRoutine(PostRoutineReq postRoutineReq, Alarm alarmCreated) {
        Routine routine = Routine.builder()
                .alarm(alarmCreated)
//                .color(postRoutineReq.getColor())
                .estimatedTime(postRoutineReq.getEstimatedTime())
                .icon(postRoutineReq.getIcon())
                .name(postRoutineReq.getName())
                .routineOrder(postRoutineReq.getRoutineOrder())
                .build();
        routineRepository.save(routine);

        Routine routineCreated = routineRepository.save(routine);
        RoutineRes postRoutineRes = EntityDtoMapper.INSTANCE.toRoutineRes(routineCreated);

        return postRoutineRes;
    }

    // 알람 id로 루틴 리스트 삭제
    @Transactional
    public void deleteRoutinesByAlarmId(int alarmId){
        routineRepository.deleteAllByAlarmId(alarmId);
    }
}
