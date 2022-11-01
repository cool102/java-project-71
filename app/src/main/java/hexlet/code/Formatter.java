package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import hexlet.code.formatters.JsonFormatter;
import hexlet.code.formatters.PlainFormatter;
import hexlet.code.formatters.StylishFormatter;

import java.util.List;
import java.util.Map;

public class Formatter {
    public static String format(List<Map<String, Object>> differs, String format) throws JsonProcessingException {
        if (format.equals("stylish")) {
            return StylishFormatter.format(differs);
        }
        if (format.equals("plain")) {
            return PlainFormatter.format(differs);
        }
        if (format.equals("json")) {
            return JsonFormatter.format(differs);
        }
        return null;

    }
}

