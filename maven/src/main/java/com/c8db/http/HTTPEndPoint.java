package com.c8db.http;

public class HTTPEndPoint {

    // Immutable endpoint

    private String endPoint;

    public HTTPEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }
}
