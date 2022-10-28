package hexlet.code.formatters.plain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class PlainFormatter {

    public static boolean isDoubleWithOutSign(Set<String> keys, String key) {
        int sum = 0;
        for (String cur : keys) {
            if (cur.contains(key.trim().substring(1))) {
                sum++;
            }
        }
        return sum == 2;
    }

    public static String format(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();

        Set<String> keys = map.keySet();
        for (String key : keys) {
            if (isDoubleWithOutSign(keys, key)) {
                String preparedKey = key.trim().replace(" ", "").substring(1).trim();
                String minusKey = "- " + preparedKey;
                String plusKey = "+ " + preparedKey;
                Object val1 = map.get(minusKey);
                Object val2 = map.get(plusKey);
                Object from = val1;
                Object to = val2;

                if (val1 != null && val2 != null) {
                    if (val1.getClass().isArray() || val1 instanceof List || val1 instanceof LinkedHashMap) {
                        from = "[complex value]";
                    }
                    if (val2.getClass().isArray() || val2 instanceof List || val2 instanceof LinkedHashMap) {
                        to = "[complex value]";
                    }
                    if (val1 instanceof String) {
                        from = "'" + val1 + "'";
                    }

                    if (val2 instanceof String) {
                        to = "'" + val2 + "'";
                    }

                }

                String line = String.format("Property '%s' was updated. From %s to %s\n",
                        preparedKey.replace(":", ""), from, to);
                sb.append(line);
            }
            if (!isDoubleWithOutSign(keys, key)) {
                if (key.contains("-")) {
                    String preparedKey = key.trim().replace(" ", "").substring(1).trim();
                    String line = String.format("Property '%s' was removed\n", preparedKey.replace(":", ""));
                    sb.append(line);
                }
                if (key.contains("+")) {
                    String preparedKey = key.trim().replace(" ", "").substring(1).trim();

                    Object val = map.get(key);
                    Object value = val;
                    if (val.getClass().isArray() || val instanceof ArrayList || val instanceof LinkedHashMap) {
                        value = "[complex value]";
                    }
                    if (val instanceof String) {
                        value = "'" + val + "'";
                    }


                    String line = String.format("Property '%s' was added with value: %s\n",
                            preparedKey.replace(":", ""), value);
                    sb.append(line);
                }
            }

        }
        Set<String> resultLines = new TreeSet<>();
        sb.toString().lines().forEach(resultLines::add);
        return String.join("\n", resultLines);


    }
}

