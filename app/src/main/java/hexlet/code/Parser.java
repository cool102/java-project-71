package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.util.Map;

public class Parser {
    public static Map<String, Object> parse(String content, String filePath) throws JsonProcessingException {
        ObjectMapper mapper = Parser.getParser(filePath);
        return mapper.readValue(content, Map.class);
    }
    public static ObjectMapper getParser(String filePath) {
        if (filePath.endsWith(".json")) {
            return new ObjectMapper();
        }
        if (filePath.endsWith(".yml") || filePath.endsWith(".yaml")) {
            return new YAMLMapper();
        } else {
            throw new RuntimeException(String.format("Invalid file path: %s", filePath));
        }

    }

}
