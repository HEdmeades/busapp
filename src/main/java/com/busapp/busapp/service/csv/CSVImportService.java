package com.busapp.busapp.service.csv;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class CSVImportService {

    public <T> List<T> readCSV(Class<T> klass, File file)  {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        ColumnPositionMappingStrategy<T> mappingStrategy = new ColumnPositionMappingStrategy<T>();
        mappingStrategy.setType(klass);

        CsvToBean<T> parser  = new CsvToBeanBuilder<T>(reader)
                .withSeparator(',')
                .withMappingStrategy(mappingStrategy)
                .withSkipLines(1)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        return parser.parse();
    }
}
