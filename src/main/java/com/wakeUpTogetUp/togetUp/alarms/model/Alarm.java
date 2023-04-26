package com.wakeUpTogetUp.togetUp.alarms.model;

import com.wakeUpTogetUp.togetUp.users.model.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Table(name = "alarm")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert          // insert 시 값이 null인 필드 제외
public class Alarm {
    @Builder
    public Alarm(User user, String name, String icon, String sound, int volume, Boolean isVibrate, Boolean isRoutineOn, int snoozeInterval, int snoozeCnt, String startHour, String startMinute, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday, Boolean sunday) {
        this.user = user;
        this.name = name;
        this.icon = icon;
        this.sound = sound;
        this.volume = volume;
        this.isVibrate = isVibrate;
        this.isRoutineOn = isRoutineOn;
        this.snoozeInterval = snoozeInterval;
        this.snoozeCnt = snoozeCnt;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    private String name;
    private String icon;
    private String sound;
    private int volume;
    private Boolean isVibrate;
    private Boolean isRoutineOn;
    private int snoozeInterval;
    private int snoozeCnt;
    private String startHour;
    private String startMinute;
    private Boolean monday;
    private Boolean tuesday;
    private Boolean wednesday;
    private Boolean thursday;
    private Boolean friday;
    private Boolean saturday;
    private Boolean sunday;
    private boolean isActivated;
}