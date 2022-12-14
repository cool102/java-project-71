package hexlet.code.formatters;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class PlainFormatter {
    public static String format(List<Map<String, Object>> differs) {
        StringBuilder sb = new StringBuilder();
        for (Map<String, Object> map : differs) {
            Object differType = map.get("differType");
            switch ((String) differType) {
                case "updated":
                    Object oldValue = map.get("oldValue");
                    oldValue = makeDecision(oldValue);
                    Object newValue = map.get("newValue");
                    newValue = makeDecision(newValue);
                    sb.append("Property '" + map.get("key") + "' was " + differType + ". "
                            + "From " + oldValue + " to " + newValue + "\n");
                    break;
                case "removed":
                    sb.append("Property '" + map.get("key") + "' was removed\n");
                    break;
                case "added":
                    Object value = map.get("value");
                    value = makeDecision(value);
                    sb.append("Property '" + map.get("key") + "' was added with value: " + value + "\n");
                case "unchanged":
                    break;
                default:
                    throw new IllegalArgumentException("Unknown differ type: " + differType);
            }

        }
        Set<String> resultLines = new TreeSet<>();
        sb.toString().lines().forEach(resultLines::add);
        return String.join("\n", resultLines);


    }

    private static Object makeDecision(Object oldValue) {
        if (oldValue != null) {
            if (oldValue instanceof String) {
                oldValue = "'" + oldValue + "'";
            }
            if (oldValue.getClass().isArray() || oldValue instanceof ArrayList
                    || oldValue instanceof LinkedHashMap) {
                oldValue = "[complex value]";
            }
        }
        return oldValue;
    }
}

