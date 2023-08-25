package com.busapp.busapp.objects;

import com.busapp.busapp.enums.StopId;
import com.busapp.busapp.utils.DateTimeUtils;
import com.opencsv.bean.AbstractBeanField;
import com.opencsv.bean.CsvCustomBindByPosition;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Valid
@Getter
@Setter
@NoArgsConstructor
public class Tap implements Serializable {

    public enum TapType {
        ON,
        OFF
    }

    @NotNull
    @CsvCustomBindByPosition(position = 0, converter = IntegerConverter.class)
    private Integer id;

    @NotNull
    @CsvCustomBindByPosition(position = 1, converter = LocalDateConverter.class)
    private LocalDateTime timeOfTap;

    @NotNull
    @CsvCustomBindByPosition(position = 2, converter = TapTypeConverter.class)
    private TapType tapType;

    @NotNull
    @CsvCustomBindByPosition(position = 3, converter = StopIdConverter.class)
    private StopId stopId;

    @NotBlank
    @CsvCustomBindByPosition(position = 4, converter = StringConverter.class)
    private String companyId;

    @NotBlank
    @CsvCustomBindByPosition(position = 5, converter = StringConverter.class)
    private String busId;

    @NotBlank
    @CsvCustomBindByPosition(position = 6, converter = StringConverter.class)
    private String pan;

    //Identifier groups the date (as localdate), pan, companyId and Bus id allowing us to group our taps
    public String getIdentifier() {
        return String.format("%s-%s-%s-%s", this.getTimeOfTap().toLocalDate(), this.getPan(), this.getCompanyId(), this.getBusId());
    }

    public static class LocalDateConverter extends AbstractBeanField<String, LocalDateTime> {

        @Override
        protected LocalDateTime convert(String s) throws CsvDataTypeMismatchException {
            try {
                return DateTimeUtils.fileDateStringToLocalDateTime(StringUtils.trimToNull(s));
            }
            catch (DateTimeParseException e) {
                throw new CsvDataTypeMismatchException(s, LocalDateTime.class, String.format("Could not parse - %s to a date", s));
            }
        }
    }

    public static class StringConverter extends AbstractBeanField<String, String> {
        @Override
        protected String convert(String s) {
            return StringUtils.trimToNull(s);
        }
    }

    public static class IntegerConverter extends AbstractBeanField<String, Integer> {
        @Override
        protected Integer convert(String s) {
            return Integer.valueOf(StringUtils.trimToNull(s));
        }
    }

    public static class TapTypeConverter extends AbstractBeanField<String, TapType> {
        @Override
        protected TapType convert(String s) {
            return TapType.valueOf(StringUtils.trimToNull(s));
        }
    }

    public static class StopIdConverter extends AbstractBeanField<String, StopId> {
        @Override
        protected StopId convert(String s) {
            return StopId.valueOf(StringUtils.trimToNull(s));
        }
    }

}
