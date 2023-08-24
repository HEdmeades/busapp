package com.busapp.busapp;

import com.busapp.busapp.enums.StopId;
import com.busapp.busapp.objects.Trip;
import com.busapp.busapp.service.busRideFacade.BusRideService;
import com.busapp.busapp.service.csv.CSVExportService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CSVExportTests {

    @Autowired
    private BusRideService busRideService;

    @Autowired
    private CSVExportService csvExportService;

    @Test
    void testExportFile() {
        List<Trip> trips= new ArrayList<>();

        Trip trip1 = new Trip();
        trip1.setChargeAmount(new BigDecimal("0"));
        trip1.setStatus(Trip.Status.COMPLETED);
        trip1.setPan("TEST");
        trip1.setBusId("TEST");
        trip1.setCompanyId("TEST");
        trip1.setFromStopId(StopId.Stop1);
        trip1.setToStopId(StopId.Stop2);
        trip1.setStartTime(LocalDateTime.now());
        trip1.setEndTime(LocalDateTime.now().plusSeconds(20));
        trips.add(trip1);

        Trip trip2 = new Trip();
        trip2.setChargeAmount(new BigDecimal("0"));
        trip2.setStatus(Trip.Status.INCOMPLETE);
        trip2.setPan("TEST");
        trip2.setBusId("TEST");
        trip2.setCompanyId("TEST");
        trip2.setFromStopId(StopId.Stop1);
        trip1.setStartTime(LocalDateTime.now());
        trips.add(trip2);

        Trip trip3 = new Trip();
        trip3.setChargeAmount(new BigDecimal("0"));
        trip3.setStatus(Trip.Status.CANCELLED);
        trip3.setPan("TEST");
        trip3.setBusId("TEST");
        trip3.setCompanyId("TEST");
        trip3.setFromStopId(StopId.Stop1);
        trip3.setToStopId(StopId.Stop2);
        trip3.setStartTime(LocalDateTime.now());
        trip3.setEndTime(LocalDateTime.now().plusSeconds(20));
        trips.add(trip2);

        csvExportService.exportTripsToCSV(trips);
    }

}
