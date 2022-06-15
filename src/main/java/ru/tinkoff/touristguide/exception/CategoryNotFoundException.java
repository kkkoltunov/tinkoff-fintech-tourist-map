package ru.tinkoff.touristguide.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String string) {
        super(string);
    }
}
