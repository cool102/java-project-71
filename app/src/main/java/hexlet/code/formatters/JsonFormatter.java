package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class JsonFormatter {
    private static ObjectMapper mapper = new ObjectMapper();
    public static String format(List<Map<String, Object>> differs) throws JsonProcessingException {
        String jsonResult = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(differs);
        return jsonResult;

    }
}
