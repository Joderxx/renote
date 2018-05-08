package top.renote.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by Joder_X on 2018/2/11.
 */
public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String modelToJson(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    public static <T> T JsonToModel(String json,Class<T> c) throws IOException {
        return (T)mapper.readValue(json,c);
    }
}
