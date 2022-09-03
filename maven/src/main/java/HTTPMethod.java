public enum HTTPMethod {

    DELETE(0),
    GET(1),
    POST(2),
    PUT(3),
    HEAD(4),
    PATCH(5),
    OPTIONS(6),
    VSTREAM_CRED(7),
    VSTREAM_REGISTER(8),
    VSTREAM_STATUS(9),
    ILLEGAL(10);

    public int getType() {
        return type;
    }

    private final int type;

    private HTTPMethod(int type){
        this.type = type;
    }
}
