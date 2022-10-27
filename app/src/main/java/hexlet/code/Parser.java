package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.util.Map;

public class Parser {
    public static Map parse(String content) throws JsonProcessingException {
        ObjectMapper mapper = Parser.getParser(content);
        return mapper.readValue(content, Map.class);
    }

    public static ObjectMapper getParser(String content) {
        if (content.contains("{")) {
            return new ObjectMapper();
        } else {
            return new YAMLMapper();
        }
    }

}
