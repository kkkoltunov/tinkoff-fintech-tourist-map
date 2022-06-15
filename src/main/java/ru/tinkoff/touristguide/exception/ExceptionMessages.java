package ru.tinkoff.touristguide.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ExceptionMessages {

    private List<String> messages;
}
