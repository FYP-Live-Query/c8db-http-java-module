
# Macrometa C8DB HTTP Access

This is a simple java interface for accessing HTTP API of c8db in macrometa GDN.




## Usage/Examples

Create C8DB object with host name, port and API key. This will create a connection to the database.
(jwt and pw authentication will support in future release)

```java
C8DB db = new C8DB.Builder()
                .hostName("www.example.com")
                .port(443)
                .apiKey("apikey-xxxxxxxxx")
                .build();

```
Then create a HTTPEndPoint as follows and add HTTPEndpoint to the HTTPRequest with specifying request method as follows.

```java
HTTPEndPoint endPoint = new HTTPEndPoint("/_api/key/AccessPortal/database?full=true");

HTTPRequest request = new HTTPRequest.Builder()
                .RequestType(HTTPMethod.GET)
                .EndPoint(endPoint)
                .build();
```
Then execute request against C8DB object that is created. This will return a response body of type VPackSlice.
```java
VPackSlice responseBody = db.execute(request);
```
To access values in json use VPackSlice API as follows.
```java
int intValueInJSONObject = responseBody.get("field-name-in-json-response").getAsInt();
```

