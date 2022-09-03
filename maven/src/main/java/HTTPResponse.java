import com.arangodb.velocypack.VPackSlice;
import com.arangodb.velocystream.Response;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class HTTPResponse {

    private Response response;
    private static ObjectMapper objectMapper = new ObjectMapper();


    public static class ResponseBody{

        private int code;
        private List<String> result;
        private boolean error;
        private String errorMessage = null;
        private int errorNum;

        public int getErrorNum() {
            return errorNum;
        }


        public boolean getError() {
            return error;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public int getCode() {
            return code;
        }

        public List<String> getResult() {
            return result;
        }

        public void setErrorNum(int errorNum) {
            this.errorNum = errorNum;
        }

        public void setError(boolean error) {
            this.error = error;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public void setResult(List<String> result) throws IOException {
            this.result = result;
        }
    }

    public HTTPResponse(Response response){
        this.response = response;
        SimpleModule module =
                new SimpleModule("ResponseBodyDeserializer",
                        new Version(3, 1, 8, null, null, null));
        module.addDeserializer(ResponseBody.class, new ResponseBodyDeserializer(ResponseBody.class));
        objectMapper.registerModule(module);
    }

    public ResponseBody parseJSON() throws JsonProcessingException {
        return objectMapper.readValue(response.getBody().toString(),ResponseBody.class);
    }

}
