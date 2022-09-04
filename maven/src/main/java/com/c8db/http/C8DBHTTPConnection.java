package com.c8db.http;

import com.arangodb.internal.http.HttpConnection;
import com.arangodb.internal.net.HostDescription;
import com.arangodb.mapping.ArangoJack;
import com.arangodb.util.ArangoSerialization;
import com.arangodb.velocystream.Request;
import com.arangodb.velocystream.Response;

import javax.security.auth.login.CredentialException;
import java.io.Closeable;
import java.io.IOException;

public class C8DBHTTPConnection implements Closeable {

    private final HttpConnection C8DBHttpConnection;

    private String apiKey = null;

    private C8DBHTTPConnection(HostDescription host, ArangoSerialization util, Boolean useSsl, String apiKey) throws CredentialException {
        this.C8DBHttpConnection = new HttpConnection.Builder()
                .useSsl(useSsl)
                .host(host)
                .serializationUtil(util)
                .build();
        if(apiKey == null){
            throw new CredentialException("API Key should provide in order to connect to com.c8db.http.C8DB.");
        }
        this.apiKey = apiKey;
    }

    public static class Builder {

        private int port;
        private String hostName;
        private ArangoSerialization util;
        private Boolean useSsl;
        private String apiKey = null;

        public Builder() {
        }

        public C8DBHTTPConnection.Builder serializationUtil(ArangoSerialization util) {
            this.util = util;
            return this;
        }

        public C8DBHTTPConnection.Builder useSsl(Boolean useSsl) {
            this.useSsl = useSsl;
            return this;
        }

        public C8DBHTTPConnection.Builder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public C8DBHTTPConnection.Builder port(int port) {
            this.port = port <= 0 ? port : 443;
            return this;
        }

        public C8DBHTTPConnection.Builder hostName(String hostName) {
            this.hostName = hostName;
            return this;
        }

        public C8DBHTTPConnection build() throws CredentialException {

            if (this.util == null){
                this.util = new ArangoJack();
            }

            if(this.port <= 0 || useSsl == null){
                this.port = 443;
                this.useSsl = true;
            }


            return new C8DBHTTPConnection(new HostDescription(hostName,port),this.util,this.useSsl,this.apiKey);
        }
    }

    @Override
    public String toString() {
        return "DBHTTPConnection{}";
    }

    @Override
    public void close() throws IOException {
        C8DBHttpConnection.close();
    }

    public HTTPResponse execute(HTTPRequest request) throws IOException {
        Request req = request.getRequest();
        req.putHeaderParam("Authorization","apikey " + apiKey);
        Response response = C8DBHttpConnection.execute(req);
        return new HTTPResponse(response);
    }
}
