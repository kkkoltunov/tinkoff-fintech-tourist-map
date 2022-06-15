package ru.tinkoff.touristguide.exception;

public class NotValidRouteException extends RuntimeException {

    public NotValidRouteException(String string) {
        super(string);
    }
}