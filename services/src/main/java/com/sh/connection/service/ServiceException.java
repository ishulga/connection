package com.sh.connection.service;

public class ServiceException extends Exception {

    private static final long serialVersionUID = 2988068968403229534L;

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
