package com.lotuas.common.scan.exception;

public class ResourceConvertException extends RuntimeException {
    public ResourceConvertException() {
        super("Resource Convert Exception!!!");
    }

    public ResourceConvertException(String message) {
        super(message);
    }
}
