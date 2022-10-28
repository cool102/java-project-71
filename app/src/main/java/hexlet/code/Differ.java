package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 */
public class Differ {
    public static String generate(String filePath1, String filePath2, String formatName) throws Exception {

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
        Map map1 = Parser.parse(content1);
        Map map2 = Parser.parse(content2);


        //Map<String, Object> result = new TreeMap<>();


        Set<String> keys1 = map1.keySet();
        Set<String> keys2 = map2.keySet();
        Set<String> allKeys = new TreeSet<>();

        allKeys.addAll(keys1);
        allKeys.addAll(keys2);


        Map<String, Object> resultMap = new LinkedHashMap();
        for (String key : allKeys) {
            boolean keyInResultMap = map2.containsKey(key);
            if (keyInResultMap) {  //  либо ключ был и в 1 и во 2 мапе
                // либо он появился во 2 мапе и его не было в первой мапе

                Object value2 = map2.get(key);
                Object value1 = map1.get(key);

                boolean valuesIsEqual = Objects.equals(value2, value1);
                boolean map1ContainsThisKey = keys1.contains(key);
                boolean map2ContainsThisKey = keys2.contains(key);
                if (valuesIsEqual & map1ContainsThisKey & map2ContainsThisKey) {
                    // ключ и там и тут - добавляем без знаков

                    resultMap.put("  " + key, value1);
                }
                boolean valuesAreNotEqual = !Objects.equals(value2, value1);
                if (valuesAreNotEqual & map1ContainsThisKey & map2ContainsThisKey) {
                    // ключ и там и тут - добавляем изменения

                    resultMap.put("- " + key, value1);

                    resultMap.put("+ " + key, value2);
                }
            }
            boolean keyWasNotInResultMap = !map2.containsKey(key);
            boolean map1ContainsThisKey = keys1.contains(key);
            if (keyWasNotInResultMap & map1ContainsThisKey) {
                // ключа нет тут, но был там - добавляем удаленные
                Object value1 = map1.get(key);

                resultMap.put("- " + key, value1);
            }
            boolean keyWasNotInFirstMap = !map1.containsKey(key);
            if (keyWasNotInFirstMap & keyInResultMap) {
                // ключа тут, но было там - добавляем новое
                Object value2 = map2.get(key);

                resultMap.put("+ " + key, value2);
            }
        }

        return Formatter.format(resultMap, formatName);

    }


    private static String getContent(Path path) throws IOException {
        return Files.readString(path);
    }


}

