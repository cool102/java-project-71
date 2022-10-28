package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import hexlet.code.formatters.json.JsonFormatter;
import hexlet.code.formatters.plain.PlainFormatter;
import hexlet.code.formatters.stylish.StylishFormatter;

import java.util.Map;

public class Formatter {
    public static String format(Map<String, Object> map, String format) throws JsonProcessingException {
        if (format.equals("stylish")) {
            return StylishFormatter.format(map);
        }
        if (format.equals("plain")) {
            return PlainFormatter.format(map);
        }
        if (format.equals("json")) {
            return JsonFormatter.format(map);
        }
        return null;

    }
}

