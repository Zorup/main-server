package com.example.socialpost.common.exception;

public class AlreadyExitIdException extends RuntimeException{
    private static final long serialVersionUID = 690490588273146617L;

    public AlreadyExitIdException(String msg, Throwable t) {
        super(msg,t);
    }
    public AlreadyExitIdException(String msg) {
        super(msg);
    }
    public AlreadyExitIdException() {
        super();
    }
}
