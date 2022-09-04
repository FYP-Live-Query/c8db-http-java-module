package com.c8db.http;

import com.c8db.util.ResponseBodyDeserializer;
import com.arangodb.velocypack.VPackSlice;
import com.arangodb.velocystream.Response;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.List;

public class HTTPResponse {

    private Response response;
    private VPackSlice body;
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
        this.body = response.getBody();
        SimpleModule module =
                new SimpleModule("com.c8db.http.Util.ResponseBodyDeserializer",
                        new Version(1, 0, 0, null, null, null));

        module.addDeserializer(ResponseBody.class, new ResponseBodyDeserializer(ResponseBody.class));

        objectMapper.registerModule(module);
    }

    public String parseJSONtoString() throws JsonProcessingException {
        // TODO : Writing a custom JSON parser
        // return objectMapper.readValue(response.getBody().toString(),ResponseBody.class);
        return body.toString();
    }

    public VPackSlice responseBody() throws JsonProcessingException {
        return body;
    }

}
