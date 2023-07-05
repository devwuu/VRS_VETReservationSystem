package com.web.vt.domain.common.enums;

import jakarta.persistence.AttributeConverter;

import java.util.EnumSet;
import java.util.NoSuchElementException;

public class UsageStatusConverter implements AttributeConverter<UsageStatus, String> {

    @Override
    public String convertToDatabaseColumn(UsageStatus attribute) {
        return attribute.code();
    }

    @Override
    public UsageStatus convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(UsageStatus.class).stream()
                .filter(e->e.code().equals(dbData))
                .findAny()
                .orElseThrow(()-> new NoSuchElementException("NOT EXIST STATUS CODE"));
    }
}
