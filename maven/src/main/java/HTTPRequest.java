import com.arangodb.DbName;
import com.arangodb.velocystream.Request;
import com.arangodb.velocystream.RequestType;

public class HTTPRequest {

    private Request request;

    private final static DbName DEFAULT_DATABASE_NAME = DbName.SYSTEM;

    private HTTPRequest(DbName dbName,HTTPMethod method, HTTPEndPoint endPoint){
        request = new Request(
                dbName,
                RequestType.fromType(method.getType()),
                endPoint.getEndPoint()
        );
    }

    public static class Builder {
        private DbName dbName;
        private HTTPMethod method;
        private HTTPEndPoint endPoint;

        public Builder(){

        }

        public HTTPRequest.Builder DbName(DbName dbName) {
            this.dbName = dbName;
            return this;
        }

        public HTTPRequest.Builder RequestType(HTTPMethod method) {
            this.method = method;
            return this;
        }

        public HTTPRequest.Builder EndPoint(HTTPEndPoint endPoint) {
            this.endPoint = endPoint;
            return this;
        }

        public HTTPRequest build(){
            if(dbName == null){
                dbName = DEFAULT_DATABASE_NAME;
            }
            return new HTTPRequest(dbName,method,endPoint);
        }
    }

    public Request getRequest() {
        return request;
    }

}
