package com.web.vt.domain.common.enums;

import jakarta.persistence.AttributeConverter;

import java.util.EnumSet;
import java.util.NoSuchElementException;

public class GenderConverter implements AttributeConverter<Gender, String> {
    @Override
    public String convertToDatabaseColumn(Gender attribute) {
        return attribute.code();
    }

    @Override
    public Gender convertToEntityAttribute(String dbData) {

        return EnumSet.allOf(Gender.class)
                .stream()
                .filter(e -> e.code().equals(dbData))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("NOT EXIST GENDER"));
    }
}
