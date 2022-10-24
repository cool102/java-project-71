package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Differ {
    public static String generate(String filePath1, String filePath2) throws Exception {

        Path writeFilePath1 = Paths.get(filePath1);
        Path writeFilePath2 = Paths.get(filePath2);

        // Формируем путь абсолютный путь,
        Path path1 = writeFilePath1.toAbsolutePath().normalize();
        Path path2 = writeFilePath2.toAbsolutePath().normalize();

        if (!Files.exists(path1)) {
            throw new Exception("File '" + path1 + "' does not exist");
        }

        if (!Files.exists(path2)) {
            throw new Exception("File '" + path2 + "' does not exist");
        }

        //Читаю файл
        String content1 = getContent(writeFilePath1);
        String content2 = getContent(writeFilePath2);

        // сохраняю спарсенное в Мапу
        Map map1 = getData(content1);
        Map map2 = getData(content2);


        //Map<String, Object> result = new TreeMap<>();


        Set<String> keys1 = map1.keySet();
        Set<String> keys2 = map2.keySet();
        Set<String> allKeys = new TreeSet<>();
        allKeys.addAll(keys1);
        allKeys.addAll(keys2);
        StringBuilder result = new StringBuilder("{\n");
        for (String key : allKeys) {
            if (map2.containsKey(key)) {
                Object value2 = map2.get(key);
                Object value1 = map1.get(key);

                if (Objects.equals(value2, value1)) {  // добавляем без знаков
                    result.append("   " + key + ": ");
                    result.append(value1 + "\n");
                }
                if (!Objects.equals(value2, value1) & Objects.nonNull(value1) & Objects.nonNull(value2)) { // добавляем изменения
                    result.append(" - " + key + ": ");
                    result.append(value1 + "\n");
                    result.append(" - " + key + ": ");
                    result.append(value2 + "\n");
                }
            }
            if (!map2.containsKey(key)) { // добавляем удаленные
                Object value1 = map1.get(key);
                result.append(" - " + key + ": ");
                result.append(value1 + "\n");
            }
            if (!map1.containsKey(key)) { // добавляем новое
                Object value2 = map2.get(key);
                result.append(" + " + key + ": ");
                result.append(value2 + "\n");
            }
        }
        result.append("}");
        return result.toString();
    }


    private static String getContent(Path path) throws IOException {
        return Files.readString(path);
    }

    private static Map getData(String content) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, Map.class);
    }


}

