import com.arangodb.Protocol;
import com.arangodb.internal.http.HttpConnection;
import com.arangodb.internal.net.Connection;
import com.arangodb.internal.net.HostDescription;
import com.arangodb.mapping.ArangoJack;
import com.arangodb.util.ArangoSerialization;
import com.arangodb.velocystream.Response;

import java.io.IOException;

public class DBHTTPConnection implements Connection {

    private final HttpConnection C8DBHttpConnection;

    private DBHTTPConnection(HostDescription host, ArangoSerialization util,Boolean useSsl) {
        this.C8DBHttpConnection = new HttpConnection.Builder().useSsl(useSsl).host(host)
                .serializationUtil(util)
                .build();
    }

    public static class Builder {

        private int port;
        private String hostName;
        private ArangoSerialization util;
        private Boolean useSsl;

        public Builder() {
        }

        public DBHTTPConnection.Builder serializationUtil(ArangoSerialization util) {
            this.util = util;
            return this;
        }

        public DBHTTPConnection.Builder useSsl(Boolean useSsl) {
            this.useSsl = useSsl;
            return this;
        }

        public DBHTTPConnection.Builder port(int port) {
            this.port = port <= 0 ? port : 443;
            return this;
        }

        public DBHTTPConnection.Builder hostName(String hostName) {
            this.hostName = hostName;
            return this;
        }

        public DBHTTPConnection build() {

            if (this.util == null){
                this.util = new ArangoJack();
            }

            if(this.port <= 0 || useSsl == null){
                this.port = 443;
                this.useSsl = true;
            }

            return new DBHTTPConnection(new HostDescription(hostName,port),this.util,this.useSsl);
        }
    }

    @Override
    public String toString() {
        return "DBHTTPConnection{}";
    }

    @Override
    public void setJwt(String s) {
        C8DBHttpConnection.setJwt(s);
    }

    @Override
    public void close() throws IOException {
        C8DBHttpConnection.close();
    }

    protected HTTPResponse execute(HTTPRequest request) throws IOException {
        Response response = C8DBHttpConnection.execute(request.getRequest());
        return new HTTPResponse(response);
    }
}
