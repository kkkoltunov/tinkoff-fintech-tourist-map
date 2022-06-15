package ru.tinkoff.touristguide.exception;

public class SightNotFoundException extends RuntimeException {

    public SightNotFoundException(String string) {
        super(string);
    }
}
