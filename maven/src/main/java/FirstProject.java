import com.arangodb.*;
import com.arangodb.entity.BaseDocument;
import com.arangodb.internal.ArangoDefaults;
import com.arangodb.internal.http.HttpConnection;
import com.arangodb.internal.net.HostDescription;
import com.arangodb.mapping.ArangoJack;
import com.arangodb.velocystream.Request;
import com.arangodb.velocystream.RequestType;
import com.arangodb.velocystream.Response;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

public class FirstProject {
    public static void main(String[] args) throws IOException {
        // Connection
//        ArangoDB arangoDB = new ArangoDB.Builder()
//                .serializer(new ArangoJack())
//                .build();
//
//        // Creating a database
//        ArangoDatabase db = arangoDB.db(DbName.of("mydb"));
//        System.out.println("Creating database...");
//        db.create();
//
//        // Creating a collection
//        ArangoCollection collection = db.collection("firstCollection");
//        System.out.println("Creating collection...");
//        collection.create();
//
//        // Creating a document
//        String key = "myKey";
//        BaseDocument doc = new BaseDocument(key);
//        doc.addAttribute("a", "Foo");
//        doc.addAttribute("b", 42);
//        System.out.println("Inserting document...");
//        collection.insertDocument(doc);
//
//        // Read a document
//        {
//            System.out.println("Reading document...");
//            BaseDocument readDocument = collection.getDocument(key, BaseDocument.class);
//            System.out.println("Key: " + readDocument.getKey());
//            System.out.println("Attribute a: " + readDocument.getAttribute("a"));
//            System.out.println("Attribute b: " + readDocument.getAttribute("b"));
//        }
//
//        // Read a document as Jackson JsonNode
//        {
//            System.out.println("Reading document as Jackson JsonNode...");
//            JsonNode jsonNode = collection.getDocument(key, ObjectNode.class);
//            System.out.println("Key: " + jsonNode.get("_key").textValue());
//            System.out.println("Attribute a: " + jsonNode.get("a").textValue());
//            System.out.println("Attribute b: " + jsonNode.get("b").intValue());
//        }
//
//        // Update a document
//        {
//            doc.addAttribute("c", "Bar");
//            System.out.println("Updating document ...");
//            collection.updateDocument(key, doc);
//        }
//
//        // Read the document again
//        {
//            System.out.println("Reading updated document ...");
//            BaseDocument updatedDocument = collection.getDocument(key, BaseDocument.class);
//            System.out.println("Key: " + updatedDocument.getKey());
//            System.out.println("Attribute a: " + updatedDocument.getAttribute("a"));
//            System.out.println("Attribute b: " + updatedDocument.getAttribute("b"));
//            System.out.println("Attribute c: " + updatedDocument.getAttribute("c"));
//        }
//
//        // Delete a document
//        {
//            System.out.println("Deleting document ...");
//            collection.deleteDocument(key);
//        }
//
//        // Execute AQL queries
//        {
//            for (int i = 0; i < 10; i++) {
//                BaseDocument value = new BaseDocument(String.valueOf(i));
//                value.addAttribute("name", "Homer");
//                collection.insertDocument(value);
//            }
//
//            String query = "FOR t IN firstCollection FILTER t.name == @name RETURN t";
//            Map<String, Object> bindVars = Collections.singletonMap("name", "Homer");
//            System.out.println("Executing read query ...");
//            ArangoCursor<BaseDocument> cursor = db.query(query, bindVars, null, BaseDocument.class);
//            cursor.forEach(aDocument -> System.out.println("Key: " + aDocument.getKey()));
//        }
//
//        // Delete a document with AQL
//        {
//            String query = "FOR t IN firstCollection FILTER t.name == @name "
//                    + "REMOVE t IN firstCollection LET removed = OLD RETURN removed";
//            Map<String, Object> bindVars = Collections.singletonMap("name", "Homer");
//            System.out.println("Executing delete query ...");
//            ArangoCursor<BaseDocument> cursor = db.query(query, bindVars, null, BaseDocument.class);
//            cursor.forEach(aDocument -> System.out.println("Removed document " + aDocument.getKey()));
//        }
//        CloseableHttpClient httpclient = HttpClients.createDefault();
//        HttpGet httpget = new HttpGet("https://api-varden-4f0f3c4f.paas.macrometa.io:0/_db/_system/_api/database");
//        httpget.setHeader("accept", "application/json");
//        httpget.setHeader("Authorization"," Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjEuNjYxOTI2NjY5MjU4OTYxNWUrNiwiZXhwIjoxNjYxOTY5ODY5LCJpc3MiOiJtYWNyb21ldGEiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJyb290Iiwic3ViIjoibWFkdTE0MF9nbWFpbC5jb20iLCJ0ZW5hbnQiOiJtYWR1MTQwX2dtYWlsLmNvbSJ9.dVQccQomvpT2VktQJtvvuLKrdeART38Ek4Y6V5tzrB4=");
//        CloseableHttpResponse response = httpclient.execute(httpget);

//        HttpConnection arangoHttpConnection = new HttpConnection.Builder()
//                .useSsl(true)
//                .host(new HostDescription("api-varden-4f0f3c4f.paas.macrometa.io", 443))
//                .serializationUtil(new ArangoJack())
//                .build();
//        arangoHttpConnection.setJwt("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjEuNjYyMDg2Njk1NTg0OTUwN2UrNiwiZXhwIjoxNjYyMTI5ODk1LCJpc3MiOiJtYWNyb21ldGEiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJyb290Iiwic3ViIjoibWFkdTE0MF9nbWFpbC5jb20iLCJ0ZW5hbnQiOiJtYWR1MTQwX2dtYWlsLmNvbSJ9.ydj-G2MBEVbDchbtYUijFb4uJ_APeZibkCidF-WMlVc=");
//        System.out.println(arangoHttpConnection.toString());
//        Request req = new Request(DbName.SYSTEM, RequestType.GET,"/_api/environments");

//        while(true) {
//            Response res = arangoHttpConnection.execute(req);
//
//            System.out.println(res.getBody().toString());
//            try {
////                res.wait();
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        DB db = new DB.Builder().hostName("api-varden-4f0f3c4f.paas.macrometa.io").port(443).build();

        HTTPEndPoint endPoint = new HTTPEndPoint("/_fabric/_system/_api/collection?excludeSystem=true");

        HTTPRequest request = new HTTPRequest.Builder()
                .RequestType(HTTPMethod.GET)
                .EndPoint(endPoint)
                .build();

        HTTPResponse.ResponseBody responseBody = db.execute(request);

        Iterator itr = responseBody.getResult().stream().iterator();
        while(itr.hasNext())
            System.out.println(itr.next());

//        arangoDB.shutdown();
    }
}
