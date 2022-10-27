package hexlet.code;

import java.util.Map;

public class Formatter {
    public static String format(Map<String, Object> map, String format) {
        StringBuilder result = new StringBuilder();
        if (format.equals("stylish")) {
            result.append("{\n");
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                final int whitespaces = 4;
                result.append(" ".repeat(whitespaces));
                result.append(key);
                result.append(value);
                result.append("\n");
            }
            result.append("}");
        }
        return result.toString();
    }
}
