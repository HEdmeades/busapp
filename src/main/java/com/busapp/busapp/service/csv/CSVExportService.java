package com.busapp.busapp.service.csv;

import com.busapp.busapp.objects.Trip;
import com.busapp.busapp.utils.DateTimeUtils;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVExportService {

    public final String outputFolder = "./files/out/";

    public void exportTripsToCSV(List<Trip> trips, String fileName, LocalDateTime generationDateTime){
        try {
            FileWriter streamWriter = new FileWriter(outputFolder + String.format("%s-%s.csv", fileName, generationDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss"))));
            CSVWriter write = new CSVWriter(streamWriter, CSVWriter.DEFAULT_SEPARATOR , CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
            List<String[]> lines = new ArrayList<>();
            lines.add(new String[]{
                    "Started",
                    "Finished",
                    "DurationSecs",
                    "FromStopId",
                    "ToStopId",
                    "ChargeAmount",
                    "CompanyId",
                    "BusID",
                    "PAN",
                    "Status",
            });

            for (Trip trip : trips) {
                lines.add(new String[]{
                        DateTimeUtils.LocalDateTimeToFileDateString(trip.getStartTime()),
                        trip.getEndTime() != null ? DateTimeUtils.LocalDateTimeToFileDateString(trip.getEndTime()) : null,
                        trip.getDurationSeconds(),
                        trip.getFromStopId().toString(),
                        trip.getToStopId() != null ? trip.getToStopId().toString() : null,
                        trip.getChargeAmount().toString(),
                        trip.getCompanyId(),
                        trip.getBusId(),
                        trip.getPan(),
                        trip.getStatus().toString(),
                });
            }

            write.writeAll(lines);
            streamWriter.close();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }
}
