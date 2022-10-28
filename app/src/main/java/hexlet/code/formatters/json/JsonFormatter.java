package hexlet.code.formatters.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JsonFormatter {
    private static ObjectMapper mapper = new ObjectMapper();

    public static String format(Map<String, Object> map) throws JsonProcessingException {
        String jsonResult = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(map);
        return jsonResult;

    }
}
