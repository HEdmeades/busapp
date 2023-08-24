package com.busapp.busapp.objects;

import com.busapp.busapp.enums.StopId;
import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@NoArgsConstructor
public class Trip implements Serializable {

    public Trip(Tap firstAction) {
        this.startTime = firstAction.getTimeOfTap();
        this.fromStopId = firstAction.getStopId();
        this.companyId = firstAction.getCompanyId();
        this.busId = firstAction.getBusId();
        this.pan = firstAction.getPan();
    }

    public Trip(Tap firstAction, Tap secondAction) {
        this(firstAction);

        this.endTime = secondAction.getTimeOfTap();
        this.toStopId = secondAction.getStopId();
    }

    public enum Status {
        COMPLETED,
        INCOMPLETE,
        CANCELLED,
    }

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private StopId fromStopId;
    private StopId toStopId;
    private BigDecimal chargeAmount;
    private String companyId;
    private String busId;
    private String pan;
    private Status status;

    public String getDurationSeconds(){
        if(this.getEndTime() ==  null){
            return Long.toString(ChronoUnit.SECONDS.between(this.getStartTime(), this.getStartTime().toLocalDate().atTime(LocalTime.MAX)));
        }

        return Long.toString(ChronoUnit.SECONDS.between(this.getStartTime(), this.getEndTime()));
    }

}
