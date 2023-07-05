package com.web.vt.domain.common.enums;

import jakarta.persistence.AttributeConverter;

import java.util.EnumSet;
import java.util.NoSuchElementException;

public class ReservationStatusConverter implements AttributeConverter<ReservationStatus, String> {
    @Override
    public String convertToDatabaseColumn(ReservationStatus attribute) {
        return attribute.code();
    }

    @Override
    public ReservationStatus convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(ReservationStatus.class)
                .stream()
                .filter(e -> e.code().equals(dbData))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("NOT EXIST RESERVATION STATUS"));
    }
}
