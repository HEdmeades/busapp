package com.busapp.busapp.csv;

import com.busapp.busapp.objects.Tap;
import com.busapp.busapp.service.csv.CSVImportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.File;
import java.util.List;

@SpringBootTest
class CSVImportTests {

	@Autowired
	private CSVImportService csvImportService;

	@Test
	void testReadCSVFile(){
		ClassLoader classLoader = getClass().getClassLoader();

		csvImportService.readCSV(String.class, new File(classLoader.getResource("testFiles/empty.csv").getFile()));
	}

	@Test
	void testReadTapsCSVFile(){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		ClassLoader classLoader = getClass().getClassLoader();

		List<Tap> taps = csvImportService.readCSV(Tap.class, new File(classLoader.getResource("testFiles/taps2.csv").getFile()));

		for (Tap tap : taps) {
			assert validator.validate(tap).isEmpty();
		}
	}

}
