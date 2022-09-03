import java.io.IOException;

public class DB {

    // create connection - for this class
    // Authentication - for another class

    private final DBHTTPConnection dbhttpConnection; // create connection pool
    private static DB db;

    private DB(DBHTTPConnection dbhttpConnection) {
        this.dbhttpConnection = dbhttpConnection;
    }

    public static class Builder {
        private String hostName;
        private int port = 0;
        public Builder(){

        }
        public DB.Builder port(int port){
            this.port = port;
            return this;
        }

        public DB.Builder hostName(String hostName){
            this.hostName = hostName;
            return this;
        }

        public DB build(){
            if(port <= 0) {
                port = 443;
            }

            DBHTTPConnection connection = new DBHTTPConnection.Builder()
                    .hostName(hostName)
                    .port(port)
                    .build();
            connection.setJwt("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjEuNjYyMTMzNzIxNDM5MzUyN2UrNiwiZXhwIjoxNjYyMTc2OTIxLCJpc3MiOiJtYWNyb21ldGEiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJyb290Iiwic3ViIjoibWFkdTE0MF9nbWFpbC5jb20iLCJ0ZW5hbnQiOiJtYWR1MTQwX2dtYWlsLmNvbSJ9.MLHpg0FaWtSdn1JC6ORavdZ0A83jCSvPfnmR3jIgbCU="); // hard code for now
            return new DB(connection);
        }
    }

    public HTTPResponse.ResponseBody execute(HTTPRequest request) throws IOException {
        HTTPResponse response = dbhttpConnection.execute(request);
        return response.parseJSON();
    }
}
