import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ResponseBodyDeserializer extends StdDeserializer<HTTPResponse.ResponseBody> {

    protected ResponseBodyDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public HTTPResponse.ResponseBody deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        HTTPResponse.ResponseBody responseBody = new HTTPResponse.ResponseBody();

        while(!jsonParser.isClosed()){
            JsonToken token = jsonParser.nextToken();

            if(JsonToken.FIELD_NAME.equals(token)){
                String fieldName = jsonParser.getCurrentName();
//                System.out.println(fieldName);

                token = jsonParser.nextToken();

                if("code".equals(fieldName)){
//                    responseBody.setCode(jsonParser.getValueAsInt());
                    System.out.println(jsonParser.getValueAsInt());
                } else if ("error".equals(fieldName)){
//                    responseBody.setError(jsonParser.getBooleanValue());
                    System.out.println(jsonParser.getBooleanValue());
                } else if ("result".equals(fieldName)){
                    Iterator<ArrayList> itr = jsonParser.readValuesAs(ArrayList.class);
                    List<String> ans = itr.next();
                    responseBody.setResult(ans);
                }else if ("errorMessage".equals(fieldName)){
                    responseBody.setErrorMessage(jsonParser.getValueAsString());
                }else if ("errorNum".equals(fieldName)){
                    responseBody.setErrorNum(jsonParser.getValueAsInt());
                }
            }

        }
        return responseBody;
    }
}