package com.example.teatime.bd.repository.api;

public class DatabaseSearchException extends RuntimeException {
    public DatabaseSearchException(String message) {
        super(message);
    }

    public DatabaseSearchException(String message, Throwable cause) {
        super(message, cause);
    }
}
