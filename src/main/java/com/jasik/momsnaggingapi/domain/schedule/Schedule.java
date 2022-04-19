package com.jasik.momsnaggingapi.domain.schedule;

import com.jasik.momsnaggingapi.domain.common.BaseTime;
import com.jasik.momsnaggingapi.infra.config.AppConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Schedule extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long originalId;

    @Column(columnDefinition = "int default 0")
    private int seqNumber;

    @Column(columnDefinition = "int default 0")
    private int goalCount;

    @Column(columnDefinition = "int default 0")
    private int doneCount;

    @Column(nullable = false)
    private String title;

    private String scheduleTime;

    @Column(nullable = false)
    private LocalDate scheduleDate;

    private LocalDateTime alarmTime;
//    private LocalDateTime routineEndDate;

    @Column(columnDefinition = "boolean default null", name = "is_done")
    private boolean done;
//    @Column(columnDefinition = "boolean default false")
//    private boolean isTemplate;
    @Column(columnDefinition = "boolean default false", name = "is_mon")
    private boolean mon;
    @Column(columnDefinition = "boolean default false", name = "is_tue")
    private boolean tue;
    @Column(columnDefinition = "boolean default false", name = "is_wed")
    private boolean wed;
    @Column(columnDefinition = "boolean default false", name = "is_thu")
    private boolean thu;
    @Column(columnDefinition = "boolean default false", name = "is_fri")
    private boolean fri;
    @Column(columnDefinition = "boolean default false", name = "is_sat")
    private boolean sat;
    @Column(columnDefinition = "boolean default false", name = "is_sun")
    private boolean sun;

    @Builder
    public Schedule(Long userId, Long originalId, int seqNumber, int goalCount, int doneCount, String title,
                    String scheduleTime, LocalDate scheduleDate, LocalDateTime alarmTime, boolean done,
//                    LocalDateTime routineEndDate, boolean isTemplate,
                    boolean mon, boolean tue, boolean wed, boolean thu, boolean fri, boolean sat, boolean sun) {
        this.userId = userId;
        this.originalId = originalId;
        this.seqNumber = seqNumber;
        this.goalCount = goalCount;
        this.doneCount = doneCount;
        this.title = title;
        this.scheduleTime = scheduleTime;
        this.scheduleDate = scheduleDate;
        this.alarmTime = alarmTime;
//        this.routineEndDate = routineEndDate;
        this.done = done;
//        this.isTemplate = isTemplate;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
        this.sun = sun;
    }

    @Schema(description = "스케줄 생성 시 요청 클래스")
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ScheduleRequest {

        @Schema(description = "스케줄 이름", defaultValue = "술 마시기")
        @NotNull
        private String title;

        @Schema(description = "n회 습관일 경우 목표 횟수", defaultValue = "0")
        private int goalCount;

        @Schema(description = "스케줄 수행 시간", defaultValue = "아무때나")
        private String scheduleTime;

        @Schema(description = "스케줄 수행 일자")
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate scheduleDate;

        @Schema(description = "스케줄 알람 시간")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime alarmTime;

        @Schema(description = "월요일 반복 여부", defaultValue = "false")
        private boolean mon;

        @Schema(description = "화요일 반복 여부", defaultValue = "false")
        private boolean tue;

        @Schema(description = "수요일 반복 여부", defaultValue = "false")
        private boolean wed;

        @Schema(description = "목요일 반복 여부", defaultValue = "false")
        private boolean thu;

        @Schema(description = "금요일 반복 여부", defaultValue = "false")
        private boolean fri;

        @Schema(description = "토요일 반복 여부", defaultValue = "false")
        private boolean sat;

        @Schema(description = "일요일 반복 여부", defaultValue = "false")
        private boolean sun;

        @Schema(description = "수행 완료 여부", defaultValue = "false", allowableValues = {"true", "false", "null"})
        private boolean done;
    }

    @Schema(description = "단일 스케줄 조회 시 응답 클래스")
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ScheduleResponse {
        @Schema(description = "스케줄 ID", defaultValue = "8f886d50-70ff-11ea-b498-02dd0a2dce82")
        private Long id;

        @Schema(description = "n회 습관의 수행 목표 수", defaultValue = "0")
        private int goalCount;

        @Schema(description = "n회 습관의 수행 완료 수", defaultValue = "0")
        private int doneCount;

        @Schema(description = "스케줄 이름", defaultValue = "술 마시기")
        private String title;

        @Schema(description = "스케줄 수행 시간", defaultValue = "아무때나")
        private String scheduleTime;

        @Schema(description = "스케줄 수행 일자")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate scheduleDate;

        @Schema(description = "스케줄 알람 시간")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime alarmTime;

        @Schema(description = "스케줄 수행 여부", defaultValue = "false")
        private boolean done;

        @Schema(description = "월요일 반복 여부", defaultValue = "false")
        private boolean mon;

        @Schema(description = "화요일 반복 여부", defaultValue = "false")
        private boolean tue;

        @Schema(description = "수요일 반복 여부", defaultValue = "false")
        private boolean wed;

        @Schema(description = "목요일 반복 여부", defaultValue = "false")
        private boolean thu;

        @Schema(description = "금요일 반복 여부", defaultValue = "false")
        private boolean fri;

        @Schema(description = "토요일 반복 여부", defaultValue = "false")
        private boolean sat;

        @Schema(description = "일요일 반복 여부", defaultValue = "false")
        private boolean sun;

        @Schema(description = "스케줄 유형(할일/습관)", defaultValue = "todo")   // originalId 있으면 routine
        private String scheduleType;
    }

    @Schema(description = "스케줄 리스트 조회 시 응답 클래스")
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SchedulesResponse {

        @Schema(description = "스케줄 ID", defaultValue = "8f886d50-70ff-11ea-b498-02dd0a2dce82")
        private Long id;

        @Schema(description = "스케줄 정렬 순서", defaultValue = "0")
        private int seqNumber;

        @Schema(description = "스케줄 이름", defaultValue = "술 마시기")
        private String title;

        @Schema(description = "스케줄 수행 시간", defaultValue = "아무때나")
        private String scheduleTime;

        @Schema(description = "수행 완료 여부", defaultValue = "false", allowableValues = {"true", "false", "null"})
        private boolean isDone;

        @Schema(description = "스케줄 유형(할일/습관)", defaultValue = "todo")        // originalId 있으면 routine
        private String scheduleType;
    }
}