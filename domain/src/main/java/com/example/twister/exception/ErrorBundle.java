package com.example.twister.exception;

public interface ErrorBundle {
    Exception getException();

    String getErrorMessage();
}