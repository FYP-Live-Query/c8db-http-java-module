package com.c8db.util;

import com.c8db.http.HTTPResponse;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

public class ResponseBodyDeserializer extends StdDeserializer<HTTPResponse.ResponseBody> {

    public ResponseBodyDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public HTTPResponse.ResponseBody deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {

        HTTPResponse.ResponseBody responseBody = new HTTPResponse.ResponseBody();
        Stack<JsonToken> tokenStack = new Stack<>();
        Stack<Hashtable> hashtableStack = new Stack<>();
        Stack<ArrayList> listStack = new Stack<>();
        Hashtable<String,Object> hashtable = new Hashtable<>();
        ArrayList<Object> list = new ArrayList<>();
        JsonToken jsonToken = jsonParser.nextToken();
        String key;

        // TODO : Refactor custom JSON parser
        while(!jsonParser.isClosed()){
            if(JsonToken.START_OBJECT.equals(jsonToken)){
                tokenStack.push(jsonToken);

                hashtable = new Hashtable<>();
                System.out.print("{");
                jsonToken = jsonParser.nextToken();
                continue;
            }
            if(JsonToken.START_ARRAY.equals(jsonToken)){
                tokenStack.push(jsonToken);

                list = new ArrayList<>();
                System.out.print("[");
                jsonToken = jsonParser.nextToken();
                continue;
            }
            if(JsonToken.END_OBJECT.equals(jsonToken)){
                tokenStack.pop();
                System.out.print("}");
                if(tokenStack.empty()){
                    jsonToken = jsonParser.nextToken();
                    continue;
                }
                if(tokenStack.peek().equals(JsonToken.START_ARRAY)){
                    // adds to the list on the top stack of listStack
                    System.out.print(",");
                }else{
                    // adds to the hash table in top of stack of hashtableStack
                    System.out.print(",");
                }

                jsonToken = jsonParser.nextToken();
                continue;
            }
            if(JsonToken.END_ARRAY.equals(jsonToken)){
                tokenStack.pop();

                list = new ArrayList<>();
                System.out.print("]");
                if(tokenStack.peek().equals(JsonToken.START_ARRAY)){
                    // adds to the list on the top stack of listStack
                    System.out.print(",");
                }else{
                    // adds to the hash table in top of stack of hashtableStack
                    System.out.print(",");
                }
                jsonToken = jsonParser.nextToken();
                continue;
            }
            if(!JsonToken.FIELD_NAME.equals(jsonToken)){
                String value = jsonParser.getValueAsString();
                jsonToken = jsonParser.nextToken();
                if(
                        JsonToken.START_OBJECT.equals(jsonToken) ||
                        JsonToken.START_ARRAY.equals(jsonToken) ||
                        JsonToken.END_ARRAY.equals(jsonToken) ||
                        JsonToken.END_OBJECT.equals(jsonToken)
                ){
                    System.out.print("\"" +value + "\"");
                    continue;
                }
                System.out.print("\"" +value + "\",");
                continue;
            }
            if(JsonToken.FIELD_NAME.equals(jsonToken)){
                key = jsonParser.getCurrentName();
                System.out.print(key + " : " );
                jsonToken = jsonParser.nextToken();
                if(
                        JsonToken.START_OBJECT.equals(jsonToken) ||
                        JsonToken.START_ARRAY.equals(jsonToken) ||
                        JsonToken.END_ARRAY.equals(jsonToken) ||
                        JsonToken.END_OBJECT.equals(jsonToken)
                ){
                    continue;
                }
            }
        }

        return responseBody;
    }
}