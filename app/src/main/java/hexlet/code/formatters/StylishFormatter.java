package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public class StylishFormatter {
    public static String format(List<Map<String, Object>> differs) {
        StringBuilder result = new StringBuilder();

        result.append("{\n");
        for (Map<String, Object> map : differs) {
            final int whitespaces = 2;
            final int whitespacesForUnchanged = 4;
            Object differType = map.get("differType");
            switch ((String) differType) {
                case "unchanged":
                    result.append(" ".repeat(whitespacesForUnchanged));
                    result.append(map.get("key"));
                    result.append(": ");
                    result.append(map.get("value"));
                    result.append("\n");
                    break;
                case "updated":
                    result.append(" ".repeat(whitespaces));
                    result.append("- " + map.get("key"));
                    result.append(": ");
                    result.append(map.get("oldValue"));
                    result.append("\n");
                    result.append(" ".repeat(whitespaces));
                    result.append("+ " + map.get("key"));
                    result.append(": ");
                    result.append(map.get("newValue"));
                    result.append("\n");
                    break;
                case "removed":
                    result.append(" ".repeat(whitespaces));
                    result.append("- " + map.get("key"));
                    result.append(": ");
                    result.append(map.get("value"));
                    result.append("\n");
                    break;
                case "added":
                    result.append(" ".repeat(whitespaces));
                    result.append("+ " + map.get("key"));
                    result.append(": ");
                    result.append(map.get("value"));
                    result.append("\n");
                    break;
                default:
                    throw new IllegalArgumentException("Unknown differ type: " + differType);
            }
        }
        result.append("}");
        return result.toString();

    }
}
