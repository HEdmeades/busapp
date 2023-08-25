package com.busapp.busapp.service.busRide;

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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FairCalculationService {

    public final static BigDecimal FIRST_SECTOR_FAIR = new BigDecimal("3.25");
    public final static BigDecimal SECOND_SECTOR_FAIR = new BigDecimal("5.50");
    public final static BigDecimal FIRST_AND_SECOND_SECTOR_FAIR = new BigDecimal("7.30");

    public BigDecimal calculateCompletedFair(StopId fromStop, StopId toStop) {
        if (fromStop.equals(StopId.Stop1) && toStop.equals(StopId.Stop2)
                || fromStop.equals(StopId.Stop2) && toStop.equals(StopId.Stop1)) {
            return FIRST_SECTOR_FAIR;
        } else if (fromStop.equals(StopId.Stop2) && toStop.equals(StopId.Stop3)
                || fromStop.equals(StopId.Stop3) && toStop.equals(StopId.Stop2)) {
            return SECOND_SECTOR_FAIR;
        }

        return FIRST_AND_SECOND_SECTOR_FAIR;
    }

    public BigDecimal calculateInCompleteFair(StopId fromStop) {
        if (fromStop.equals(StopId.Stop3)) {
            return calculateCompletedFair(fromStop, StopId.Stop1);
        }
        return calculateCompletedFair(fromStop, StopId.Stop3);
    }

}
