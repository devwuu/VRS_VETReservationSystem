package com.web.vt.domain.common.enums;

import jakarta.persistence.AttributeConverter;

import java.util.EnumSet;
import java.util.NoSuchElementException;

public class PositionConverter implements AttributeConverter<Position, String> {
    @Override
    public String convertToDatabaseColumn(Position attribute) {
        return attribute.code();
    }

    @Override
    public Position convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(Position.class)
                .stream()
                .filter(e -> e.code().equals(dbData))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("NOT EXIST POSITION"));
    }
}
