package com.busapp.busapp.service.busRide;

import com.busapp.busapp.objects.Tap;
import com.busapp.busapp.objects.Trip;
import com.busapp.busapp.service.csv.CSVExportService;
import com.busapp.busapp.service.csv.CSVImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BusRideFileService {

    @Autowired
    private CSVImportService csvImportService;

    @Autowired
    private CSVExportService csvExportService;

    @Autowired
    private BusRideService busRideService;

    public void processAndExportTripsFile(File file){
        exportTripsToCSV(this.processTripFile(file));
    }

    public void exportTripsToCSV(List<Trip> trips){
        csvExportService.exportTripsToCSV(trips, "trips", LocalDateTime.now());
    }

    public List<Trip> processTripFile(File file){
        return busRideService.calculateTrips(csvImportService.readCSV(Tap.class, file));
    }


}
