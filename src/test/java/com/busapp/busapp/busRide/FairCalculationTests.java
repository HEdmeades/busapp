package com.busapp.busapp.busRide;

import com.busapp.busapp.enums.StopId;
import com.busapp.busapp.objects.Tap;
import com.busapp.busapp.objects.Trip;
import com.busapp.busapp.service.busRide.BusRideService;
import com.busapp.busapp.service.busRide.FairCalculationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class FairCalculationTests {

    @Autowired
    private FairCalculationService fairCalculationService;

    @Test
    void testStop1ToStop2() {
        assert fairCalculationService.calculateCompletedFair(StopId.Stop1, StopId.Stop2).equals(FairCalculationService.FIRST_SECTOR_FAIR);
        assert fairCalculationService.calculateCompletedFair(StopId.Stop2, StopId.Stop1).equals(FairCalculationService.FIRST_SECTOR_FAIR);
    }

    @Test
    void testStop2ToStop3() {
        assert fairCalculationService.calculateCompletedFair(StopId.Stop2, StopId.Stop3).equals(FairCalculationService.SECOND_SECTOR_FAIR);
        assert fairCalculationService.calculateCompletedFair(StopId.Stop3, StopId.Stop2).equals(FairCalculationService.SECOND_SECTOR_FAIR);
    }

    @Test
    void testStop1ToStop3() {
        assert fairCalculationService.calculateCompletedFair(StopId.Stop1, StopId.Stop3).equals(FairCalculationService.FIRST_AND_SECOND_SECTOR_FAIR);
        assert fairCalculationService.calculateCompletedFair(StopId.Stop3, StopId.Stop1).equals(FairCalculationService.FIRST_AND_SECOND_SECTOR_FAIR);
    }

}
