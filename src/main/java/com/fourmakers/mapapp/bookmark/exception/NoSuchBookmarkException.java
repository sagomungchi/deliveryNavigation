package com.fourmakers.mapapp.bookmark.exception;

public class NoSuchBookmarkException extends RuntimeException{
    public NoSuchBookmarkException() {
        super();
    }

    public NoSuchBookmarkException(String message) {
        super(message);
    }
}
