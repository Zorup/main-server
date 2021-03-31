package com.example.socialpost.common.exception;

public class HUserNotFoundException extends RuntimeException{
    private static final long serialVersionUID = -7125888902456916908L;

    public HUserNotFoundException(String msg, Throwable t) {
        super(msg,t);
    }
    public HUserNotFoundException(String msg) {
        super(msg);
    }
    public HUserNotFoundException() {
        super();
    }
}