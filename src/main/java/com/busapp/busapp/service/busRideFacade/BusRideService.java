package com.busapp.busapp.service.busRideFacade;

import com.busapp.busapp.enums.StopId;
import com.busapp.busapp.objects.Tap;
import com.busapp.busapp.objects.Trip;
import com.busapp.busapp.service.csv.CSVExportService;
import com.busapp.busapp.service.csv.CSVImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BusRideService {

    @Autowired
    private CSVImportService csvImportService;

    @Autowired
    private CSVExportService csvExportService;

    public void processAndExportTripsFile(File file){
        exportTripsToCSV(this.processTripFile(file));
    }

    public void exportTripsToCSV(List<Trip> trips){
        csvExportService.exportTripsToCSV(trips);
    }

    public List<Trip> processTripFile(File file){
        return calculateTrips(csvImportService.readCSV(Tap.class, file));
    }

    //Assuming file is a batch file processed EOD
    public List<Trip> calculateTrips(List<Tap> taps) {
        //Map of identifier to list of taps completed
        Map<String, List<Tap>> panActionMap = taps.stream().collect(Collectors.groupingBy(Tap::getIdentifier));

        List<Trip> trips = new ArrayList<>();
        for (String identifier : panActionMap.keySet()) {
            trips.addAll(this.createTripsForGroupedTaps(panActionMap.get(identifier)));
        }

        return trips;
    }

    //Calculation scenarios
    //1: 2 taps ON & OFF at same stop = CANCELLED
    //2: 1 ON action and no corresponding OFF = INCOMPLETE
    //3: 2 action ON & OFF at different stops = COMPLETE
    public List<Trip> createTripsForGroupedTaps(List<Tap> taps) {
        //Order taps first to last to make calculation easier
        taps = taps.stream().sorted(Comparator.comparing(Tap::getTimeOfTap)).collect(Collectors.toList());

        List<Trip> trips = new ArrayList<>();
        for (int i = 0; i < taps.size(); i++) {

            Tap firstAction = taps.get(i);
            Tap secondAction = null;

            if(firstAction.getTapType().equals(Tap.TapType.OFF)){
                //Edge case found in example data where there is an OFF but no corresponding ON.
                continue;
            }

            if (i + 1 < taps.size()) {
                secondAction = taps.get(i + 1);

                if (firstAction.getTapType().equals(Tap.TapType.ON) && secondAction.getTapType().equals(Tap.TapType.OFF)) {
                    //We have a full trip (might still be a cancel)
                    Trip trip = new Trip(firstAction, secondAction);

                    if (firstAction.getStopId().equals(secondAction.getStopId())) {
                        //We have a cancel
                        trip.setStatus(Trip.Status.CANCELLED);
                        trip.setChargeAmount(new BigDecimal(BigInteger.ZERO));
                    } else {
                        //We have a complete trip
                        trip.setStatus(Trip.Status.COMPLETED);
                        trip.setChargeAmount(calculateCompletedFair(firstAction.getStopId(), secondAction.getStopId()));
                    }

                    trips.add(trip);
                    i++;
                    continue;
                }
            }

            //We have an incomplete
            Trip trip = new Trip(firstAction);
            trip.setStatus(Trip.Status.INCOMPLETE);

            trip.setChargeAmount(calculateInCompleteFair(firstAction.getStopId()));
            trips.add(trip);
        }

        return trips;
    }

    public BigDecimal calculateCompletedFair(StopId fromStop, StopId toStop) {
        if (fromStop.equals(StopId.Stop1) && toStop.equals(StopId.Stop2)
                || fromStop.equals(StopId.Stop2) && toStop.equals(StopId.Stop1)) {
            return new BigDecimal("3.25");
        } else if (fromStop.equals(StopId.Stop2) && toStop.equals(StopId.Stop3)
                || fromStop.equals(StopId.Stop3) && toStop.equals(StopId.Stop2)) {
            return new BigDecimal("5.50");
        }

        return new BigDecimal("7.30");
    }

    public BigDecimal calculateInCompleteFair(StopId fromStop) {
        if (fromStop.equals(StopId.Stop3)) {
            return calculateCompletedFair(fromStop, StopId.Stop1);
        }
        return calculateCompletedFair(fromStop, StopId.Stop3);
    }

}
