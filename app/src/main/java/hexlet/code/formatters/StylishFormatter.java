package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public class StylishFormatter {
    public static String format(List<Map<String, Object>> differs) {
        StringBuilder result = new StringBuilder();

        result.append("{\r\n");
        for (Map<String, Object> map : differs) {
            final int whitespaces = 4;
            final int whitespacesForUnchanged = 6;
            Object differType = map.get("differType");
            switch ((String) differType) {
                case "unchanged":
                    result.append(" ".repeat(whitespacesForUnchanged));
                    result.append(map.get("key"));
                    result.append(": ");
                    result.append(map.get("value"));
                    result.append("\r\n");
                    break;
                case "updated":
                    result.append(" ".repeat(whitespaces));
                    result.append("- " + map.get("key"));
                    result.append(": ");
                    result.append(map.get("oldValue"));
                    result.append("\r\n");
                    result.append(" ".repeat(whitespaces));
                    result.append("+ " + map.get("key"));
                    result.append(": ");
                    result.append(map.get("newValue"));
                    result.append("\r\n");
                    break;
                case "removed":
                    result.append(" ".repeat(whitespaces));
                    result.append("- " + map.get("key"));
                    result.append(": ");
                    result.append(map.get("value"));
                    result.append("\r\n");
                    break;
                case "added":
                    result.append(" ".repeat(whitespaces));
                    result.append("+ " + map.get("key"));
                    result.append(": ");
                    result.append(map.get("value"));
                    result.append("\r\n");
                    break;
                default:
                    throw new IllegalArgumentException("Unknown differ type: " + differType);
            }
        }
        result.append("}");
        return result.toString();

    }
}
