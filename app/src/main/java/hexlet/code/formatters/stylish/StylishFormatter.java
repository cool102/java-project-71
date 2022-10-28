package hexlet.code.formatters.stylish;

import java.util.Map;

public class StylishFormatter {
    public static String format(Map<String, Object> map) {
        StringBuilder result = new StringBuilder();

        result.append("{\n");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            final int whitespaces = 4;
            result.append(" ".repeat(whitespaces));
            result.append(key);
            result.append(":");
            result.append(value);
            result.append("\n");
        }
        result.append("}");

        return result.toString();

    }
}
