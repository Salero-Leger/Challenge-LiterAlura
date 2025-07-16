package com.alura.literalura.Services;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    private static final String SEPARATOR = ";";

    @Override
    public String convertToDatabaseColumn(List<String> list) {
        return list != null && !list.isEmpty()
                ? String.join(SEPARATOR, list)
                : "";
    }

    @Override
    public List<String> convertToEntityAttribute(String data) {
        return (data != null && !data.isBlank())
                ? Arrays.stream(data.split(SEPARATOR)).map(String::trim).collect(Collectors.toList())
                : Collections.emptyList();
    }
}
