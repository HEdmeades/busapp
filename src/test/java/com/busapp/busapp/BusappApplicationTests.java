package com.busapp.busapp;

import com.busapp.busapp.enums.StopId;
import com.busapp.busapp.objects.Tap;
import com.busapp.busapp.objects.Trip;
import com.busapp.busapp.service.busRideFacade.BusRideService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BusappApplicationTests {

    @Autowired
    private BusRideService busRideService;

    @Test
    void contextLoads() {
    }

    @Test
    void testFullExampleIncludingExport() {
        ClassLoader classLoader = getClass().getClassLoader();

        busRideService.processAndExportTripsFile(new File(classLoader.getResource("testFiles/taps.csv").getFile()));
    }

    @Test
    void testTapsCSVExampleFile() {
        ClassLoader classLoader = getClass().getClassLoader();

        List<Trip> trips = busRideService.processTripFile(new File(classLoader.getResource("testFiles/taps.csv").getFile()));

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
    void testCompletedTripStop1toStop2() {
        List<Tap> taps = new ArrayList<>();

        Tap tapOn = new Tap();
        tapOn.setId(1);
        tapOn.setTapType(Tap.TapType.ON);
        tapOn.setStopId(StopId.Stop1);
        tapOn.setBusId("Bus1");
        tapOn.setCompanyId("Company1");
        tapOn.setPan("5500005555555559");
        tapOn.setTimeOfTap(LocalDateTime.of(2023, 8, 24, 10, 30));
        taps.add(tapOn);

        Tap tapOff = new Tap();
        tapOff.setId(2);
        tapOff.setTapType(Tap.TapType.OFF);
        tapOff.setStopId(StopId.Stop2);
        tapOff.setBusId("Bus1");
        tapOff.setCompanyId("Company1");
        tapOff.setPan("5500005555555559");
        tapOff.setTimeOfTap(LocalDateTime.of(2023, 8, 24, 10, 50));
        taps.add(tapOff);

        List<Trip> trips = busRideService.calculateTrips(taps);

        assert trips.size() == 1;

        Trip trip = trips.get(0);
        assert trip.getStatus().equals(Trip.Status.COMPLETED);
        assert trip.getStartTime().isBefore(trip.getEndTime());
        assert trip.getChargeAmount().equals(new BigDecimal("3.25"));
    }

    @Test
    void testCompletedTripStop1toStop3() {
        List<Tap> taps = new ArrayList<>();

        Tap tapOn = new Tap();
        tapOn.setId(1);
        tapOn.setTapType(Tap.TapType.ON);
        tapOn.setStopId(StopId.Stop1);
        tapOn.setBusId("Bus1");
        tapOn.setCompanyId("Company1");
        tapOn.setPan("5500005555555559");
        tapOn.setTimeOfTap(LocalDateTime.of(2023, 8, 24, 10, 30));
        taps.add(tapOn);

        Tap tapOff = new Tap();
        tapOff.setId(2);
        tapOff.setTapType(Tap.TapType.OFF);
        tapOff.setStopId(StopId.Stop3);
        tapOff.setBusId("Bus1");
        tapOff.setCompanyId("Company1");
        tapOff.setPan("5500005555555559");
        tapOff.setTimeOfTap(LocalDateTime.of(2023, 8, 24, 10, 50));
        taps.add(tapOff);

        List<Trip> trips = busRideService.calculateTrips(taps);

        assert trips.size() == 1;

        Trip trip = trips.get(0);
        assert trip.getStatus().equals(Trip.Status.COMPLETED);
        assert trip.getStartTime().isBefore(trip.getEndTime());
        assert trip.getChargeAmount().equals(new BigDecimal("7.30"));
    }

    @Test
    void testCompletedTripStop2toStop3() {
        List<Tap> taps = new ArrayList<>();

        Tap tapOn = new Tap();
        tapOn.setId(1);
        tapOn.setTapType(Tap.TapType.ON);
        tapOn.setStopId(StopId.Stop2);
        tapOn.setBusId("Bus1");
        tapOn.setCompanyId("Company1");
        tapOn.setPan("5500005555555559");
        tapOn.setTimeOfTap(LocalDateTime.of(2023, 8, 24, 10, 30));
        taps.add(tapOn);

        Tap tapOff = new Tap();
        tapOff.setId(2);
        tapOff.setTapType(Tap.TapType.OFF);
        tapOff.setStopId(StopId.Stop3);
        tapOff.setBusId("Bus1");
        tapOff.setCompanyId("Company1");
        tapOff.setPan("5500005555555559");
        tapOff.setTimeOfTap(LocalDateTime.of(2023, 8, 24, 10, 50));
        taps.add(tapOff);

        List<Trip> trips = busRideService.calculateTrips(taps);

        assert trips.size() == 1;

        Trip trip = trips.get(0);
        assert trip.getStatus().equals(Trip.Status.COMPLETED);
        assert trip.getStartTime().isBefore(trip.getEndTime());
        assert trip.getChargeAmount().equals(new BigDecimal("5.50"));
    }

    @Test
    void testCancelledTrip() {
        List<Tap> taps = new ArrayList<>();

        Tap tapOn = new Tap();
        tapOn.setId(1);
        tapOn.setTapType(Tap.TapType.ON);
        tapOn.setStopId(StopId.Stop2);
        tapOn.setBusId("Bus1");
        tapOn.setCompanyId("Company1");
        tapOn.setPan("5500005555555559");
        tapOn.setTimeOfTap(LocalDateTime.of(2023, 8, 24, 10, 30));
        taps.add(tapOn);

        Tap tapOff = new Tap();
        tapOff.setId(2);
        tapOff.setTapType(Tap.TapType.OFF);
        tapOff.setStopId(StopId.Stop2);
        tapOff.setBusId("Bus1");
        tapOff.setCompanyId("Company1");
        tapOff.setPan("5500005555555559");
        tapOff.setTimeOfTap(LocalDateTime.of(2023, 8, 24, 10, 50));
        taps.add(tapOff);

        List<Trip> trips = busRideService.calculateTrips(taps);

        assert trips.size() == 1;

        Trip trip = trips.get(0);
        assert trip.getStatus().equals(Trip.Status.CANCELLED);
        assert trip.getStartTime().isBefore(trip.getEndTime());
        assert trip.getChargeAmount().equals(new BigDecimal("0"));
    }

    @Test
    void testIncompleteTripStop1() {
        List<Tap> taps = new ArrayList<>();

        Tap tapOn = new Tap();
        tapOn.setId(1);
        tapOn.setTapType(Tap.TapType.ON);
        tapOn.setStopId(StopId.Stop1);
        tapOn.setBusId("Bus1");
        tapOn.setCompanyId("Company1");
        tapOn.setPan("5500005555555559");
        tapOn.setTimeOfTap(LocalDateTime.of(2023, 8, 24, 10, 30));
        taps.add(tapOn);

        List<Trip> trips = busRideService.calculateTrips(taps);

        assert trips.size() == 1;

        Trip trip = trips.get(0);
        assert trip.getStatus().equals(Trip.Status.INCOMPLETE);
        assert trip.getEndTime() == null;
        assert trip.getChargeAmount().equals(new BigDecimal("7.30"));
    }

    @Test
    void testIncompleteTripStop2() {
        List<Tap> taps = new ArrayList<>();

        Tap tapOn = new Tap();
        tapOn.setId(1);
        tapOn.setTapType(Tap.TapType.ON);
        tapOn.setStopId(StopId.Stop2);
        tapOn.setBusId("Bus1");
        tapOn.setCompanyId("Company1");
        tapOn.setPan("5500005555555559");
        tapOn.setTimeOfTap(LocalDateTime.of(2023, 8, 24, 10, 30));
        taps.add(tapOn);

        List<Trip> trips = busRideService.calculateTrips(taps);

        assert trips.size() == 1;

        Trip trip = trips.get(0);
        assert trip.getStatus().equals(Trip.Status.INCOMPLETE);
        assert trip.getEndTime() == null;
        assert trip.getChargeAmount().equals(new BigDecimal("5.50"));
    }

    @Test
    void testIncompleteTripStop3() {
        List<Tap> taps = new ArrayList<>();

        Tap tapOn = new Tap();
        tapOn.setId(1);
        tapOn.setTapType(Tap.TapType.ON);
        tapOn.setStopId(StopId.Stop3);
        tapOn.setBusId("Bus1");
        tapOn.setCompanyId("Company1");
        tapOn.setPan("5500005555555559");
        tapOn.setTimeOfTap(LocalDateTime.of(2023, 8, 24, 10, 30));
        taps.add(tapOn);

        List<Trip> trips = busRideService.calculateTrips(taps);

        assert trips.size() == 1;

        Trip trip = trips.get(0);
        assert trip.getStatus().equals(Trip.Status.INCOMPLETE);
        assert trip.getEndTime() == null;
        assert trip.getChargeAmount().equals(new BigDecimal("7.30"));
    }

}
