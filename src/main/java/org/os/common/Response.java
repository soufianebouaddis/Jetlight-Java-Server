package org.os.common;

public class Response<T> {
    String statusCode;
    String message;
    T Data;

    public Response(){

    }

    public Response(String statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        Data = data;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }
}
