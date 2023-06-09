package com.wakeUpTogetUp.togetUp.mission;

import com.wakeUpTogetUp.togetUp.mission.model.Mission;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Integer> {
    @Query("select m from Mission m where m.isActivated = true")
    List<Mission> findAllTrue(Sort sort);
}
