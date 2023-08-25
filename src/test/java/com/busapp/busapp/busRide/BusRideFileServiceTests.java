package com.busapp.busapp.busRide;

import com.busapp.busapp.enums.StopId;
import com.busapp.busapp.objects.Tap;
import com.busapp.busapp.objects.Trip;
import com.busapp.busapp.service.busRide.BusRideFileService;
import com.busapp.busapp.service.busRide.BusRideService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BusRideFileServiceTests {

    @Autowired
    private BusRideFileService busRideFileService;

    @Test
    void contextLoads() {
    }

    @Test
    void testFullExampleIncludingExport() {
        ClassLoader classLoader = getClass().getClassLoader();

        busRideFileService.processAndExportTripsFile(new File(classLoader.getResource("testFiles/taps.csv").getFile()));
    }

    @Test
    void testTapsCSVExampleFile() {
        ClassLoader classLoader = getClass().getClassLoader();

        List<Trip> trips = busRideFileService.processTripFile(new File(classLoader.getResource("testFiles/taps.csv").getFile()));

        assert trips.size() == 3;

        Trip trip1 = trips.get(0);
        assert trip1.getStatus().equals(Trip.Status.CANCELLED);
        assert trip1.getStartTime().isBefore(trip1.getEndTime());
        assert trip1.getChargeAmount().equals(new BigDecimal("0"));

        Trip trip2 = trips.get(1);
        assert trip2.getStatus().equals(Trip.Status.INCOMPLETE);
        assert trip2.getEndTime() == null;
        assert trip2.getChargeAmount().equals(new BigDecimal("7.30"));

        Trip trip3 = trips.get(2);
        assert trip3.getStatus().equals(Trip.Status.COMPLETED);
        assert trip3.getStartTime().isBefore(trip3.getEndTime());
        assert trip3.getChargeAmount().equals(new BigDecimal("3.25"));
    }

    @Test
    void testAllScenarioTaps() {
        ClassLoader classLoader = getClass().getClassLoader();

        List<Trip> trips = busRideFileService.processTripFile(new File(classLoader.getResource("testFiles/allScenarioTest.csv").getFile()));

        assert trips.size() == 3;

        Trip trip1 = trips.get(0);
        assert trip1.getStatus().equals(Trip.Status.CANCELLED);
        assert trip1.getStartTime().isBefore(trip1.getEndTime());
        assert trip1.getChargeAmount().equals(new BigDecimal("0"));

        Trip trip2 = trips.get(1);
        assert trip2.getStatus().equals(Trip.Status.COMPLETED);
        assert trip1.getStartTime().isBefore(trip1.getEndTime());
        assert trip2.getChargeAmount().equals(new BigDecimal("3.25"));

        Trip trip3 = trips.get(2);
        assert trip3.getStatus().equals(Trip.Status.INCOMPLETE);
        assert trip3.getEndTime() == null;
        assert trip3.getChargeAmount().equals(new BigDecimal("5.50"));
    }
}
