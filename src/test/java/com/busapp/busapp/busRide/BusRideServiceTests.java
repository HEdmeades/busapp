package com.busapp.busapp.busRide;

import com.busapp.busapp.enums.StopId;
import com.busapp.busapp.objects.Tap;
import com.busapp.busapp.objects.Trip;
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
class BusRideServiceTests {

    @Autowired
    private BusRideService busRideService;

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

    @Test
    void testMultipleIncompleteTrips() {
        List<Tap> taps = new ArrayList<>();

        Tap tapOn1 = new Tap();
        tapOn1.setId(1);
        tapOn1.setTapType(Tap.TapType.ON);
        tapOn1.setStopId(StopId.Stop2);
        tapOn1.setBusId("Bus1");
        tapOn1.setCompanyId("Company1");
        tapOn1.setPan("5500005555555559");
        tapOn1.setTimeOfTap(LocalDateTime.of(2023, 8, 24, 10, 30));
        taps.add(tapOn1);

        Tap tapOn2 = new Tap();
        tapOn2.setId(2);
        tapOn2.setTapType(Tap.TapType.ON);
        tapOn2.setStopId(StopId.Stop3);
        tapOn2.setBusId("Bus1");
        tapOn2.setCompanyId("Company1");
        tapOn2.setPan("5500005555555559");
        tapOn2.setTimeOfTap(LocalDateTime.of(2023, 8, 24, 10, 30));
        taps.add(tapOn2);

        Tap tapOn3 = new Tap();
        tapOn3.setId(2);
        tapOn3.setTapType(Tap.TapType.ON);
        tapOn3.setStopId(StopId.Stop1);
        tapOn3.setBusId("Bus1");
        tapOn3.setCompanyId("Company1");
        tapOn3.setPan("5500005555555123");
        tapOn3.setTimeOfTap(LocalDateTime.of(2023, 8, 24, 10, 30));
        taps.add(tapOn3);

        List<Trip> trips = busRideService.calculateTrips(taps);

        assert trips.size() == 3;

        Trip trip1 = trips.get(0);
        assert trip1.getStatus().equals(Trip.Status.INCOMPLETE);
        assert trip1.getEndTime() == null;
        assert trip1.getChargeAmount().equals(new BigDecimal("7.30"));

        Trip trip2 = trips.get(1);
        assert trip2.getStatus().equals(Trip.Status.INCOMPLETE);
        assert trip2.getEndTime() == null;
        assert trip2.getChargeAmount().equals(new BigDecimal("5.50"));

        Trip trip3 = trips.get(2);
        assert trip3.getStatus().equals(Trip.Status.INCOMPLETE);
        assert trip3.getEndTime() == null;
        assert trip3.getChargeAmount().equals(new BigDecimal("7.30"));
    }
}
