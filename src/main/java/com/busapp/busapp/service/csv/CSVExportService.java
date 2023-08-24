package com.busapp.busapp.service.csv;

import com.busapp.busapp.objects.Trip;
import com.opencsv.CSVWriter;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.HeaderColumnNameMappingStrategyBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVExportService {

    public void exportTripsToCSV(List<Trip> trips){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            FileWriter streamWriter = new FileWriter("EXPORT_EXAMPLES.csv");
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
                        trip.getStartTime().toString(),
                        trip.getEndTime() != null ? trip.getEndTime().toString() : null,
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
