package cz.muni.fi.pa165.mvc.formatters;

import org.springframework.format.Formatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateTimeFormatter implements Formatter<LocalDate> {
    private final DateTimeFormatter formatter;

    public LocalDateTimeFormatter(String pattern) {
        formatter = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public LocalDate parse(String text, Locale locale) {
        if ("now".equals(text))
            return LocalDate.now();
        return LocalDate.parse(text, formatter);
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return formatter.format(object);
    }
}
