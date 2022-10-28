package hexlet.code;

import hexlet.code.formatters.plain.PlainFormatter;
import hexlet.code.formatters.stylish.StylishFormatter;

import java.util.Map;

public class Formatter {
    public static String format(Map<String, Object> map, String format) {
        if (format.equals("stylish")) {
            return StylishFormatter.format(map);
        }
        if (format.equals("plain")) {
            return PlainFormatter.format(map);
        }
        return null;

    }
}

