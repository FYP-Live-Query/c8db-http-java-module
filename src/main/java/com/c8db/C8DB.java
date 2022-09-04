package com.c8db;

import com.arangodb.velocypack.VPackSlice;
import com.c8db.http.C8DBHTTPConnection;
import com.c8db.http.HTTPRequest;
import com.c8db.http.HTTPResponse;

import javax.security.auth.login.CredentialException;
import java.io.IOException;

public class C8DB {

    // create connection - for this class
    // Authentication - for another class

    private final C8DBHTTPConnection c8DbhttpConnection; // create connection pool
    private static C8DB db;

    private C8DB(C8DBHTTPConnection c8DbhttpConnection) {
        this.c8DbhttpConnection = c8DbhttpConnection;
    }

    public static class Builder {
        private String hostName;
        private int port = 0;
        private String apiKey;
        public Builder(){

        }
        public C8DB.Builder port(int port){
            this.port = port;
            return this;
        }

        public C8DB.Builder hostName(String hostName){
            this.hostName = hostName;
            return this;
        }

        public C8DB.Builder apiKey(String apiKey){
            this.apiKey = apiKey;
            return this;
        }

        public C8DB build() throws CredentialException {
            if(port <= 0) {
                port = 443;
            }

            C8DBHTTPConnection connection = new C8DBHTTPConnection.Builder()
                    .hostName(hostName)
                    .port(port)
                    .apiKey(apiKey)
                    .build();

            return new C8DB(connection);
        }
    }

    public VPackSlice execute(HTTPRequest request) throws IOException {
        HTTPResponse response = c8DbhttpConnection.execute(request);
        return response.responseBody();
    }


    // TODO : this method should activated after writing custom JSON parser
//    public com.c8db.http.http.HTTPResponse.ResponseBody execute(com.c8db.http.http.HTTPRequest request) throws IOException {
//        com.c8db.http.http.HTTPResponse response = dbhttpConnection.execute(request);
//        return response.parseJSON();
//    }
}
