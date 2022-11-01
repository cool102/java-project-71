package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public final class Differ {
    public static String generate(String filePath1, String filePath2, String format) throws Exception {
        List<Map<String, Object>> differ = buildDiffers(filePath1, filePath2);
        return Formatter.format(differ, format);
    }

    public static String generate(String filePath1, String filePath2) throws Exception {
        final String defaultFormat = "stylish";
        List<Map<String, Object>> differ = buildDiffers(filePath1, filePath2);
        return Formatter.format(differ, defaultFormat);
    }

    public static List<Map<String, Object>> buildDiffers(String filePath1, String filePath2) throws Exception {
        Path writeFilePath1 = Paths.get(filePath1);
        Path writeFilePath2 = Paths.get(filePath2);
        Path path1 = writeFilePath1.toAbsolutePath().normalize();
        Path path2 = writeFilePath2.toAbsolutePath().normalize();

        if (!Files.exists(path1)) {
            throw new Exception("File '" + path1 + "' does not exist");
        }
        if (!Files.exists(path2)) {
            throw new Exception("File '" + path2 + "' does not exist");
        }
        String content1 = getContent(writeFilePath1);
        String content2 = getContent(writeFilePath2);
        Map<String, Object> map1 = Parser.parse(content1, filePath1);
        Map<String, Object> map2 = Parser.parse(content2, filePath2);
        Set<String> keys1 = map1.keySet();
        Set<String> keys2 = map2.keySet();
        Set<String> allKeys = new TreeSet<>();
        allKeys.addAll(keys1);
        allKeys.addAll(keys2);

        List<Map<String, Object>> differences = new ArrayList<>();

        for (String key : allKeys) {
            Map<String, Object> innerMap = new LinkedHashMap<>();
            if (map1.containsKey(key) && map2.containsKey(key)) {
                Object value1 = map1.get(key);
                Object value2 = map2.get(key);
                if (Objects.equals(value1, value2)) {
                    innerMap.put("key", key);
                    innerMap.put("value", value1);
                    innerMap.put("differType", "unchanged");
                    differences.add(innerMap);
                } else {
                    innerMap.put("key", key);
                    innerMap.put("oldValue", value1);
                    innerMap.put("newValue", value2);
                    innerMap.put("differType", "updated");
                    differences.add(innerMap);
                }
            }
            if (map1.containsKey(key) & !map2.containsKey(key)) {
                innerMap.put("key", key);
                innerMap.put("value", map1.get(key));
                innerMap.put("differType", "removed");
                differences.add(innerMap);
            }
            if (map2.containsKey(key) & !map1.containsKey(key)) {
                innerMap.put("key", key);
                innerMap.put("value", map2.get(key));
                innerMap.put("differType", "added");
                differences.add(innerMap);
            }
        }
        return differences;
    }

    private static String getContent(Path path) throws IOException {
        return Files.readString(path);
    }

}


