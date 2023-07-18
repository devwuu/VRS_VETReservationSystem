package com.web.vt.domain.common.enums;

import com.web.vt.utils.ObjectUtil;
import jakarta.persistence.AttributeConverter;

import java.util.EnumSet;
import java.util.NoSuchElementException;

public class GenderConverter implements AttributeConverter<Gender, String> {
    @Override
    public String convertToDatabaseColumn(Gender attribute) {
        return ObjectUtil.isNotEmpty(attribute) ? attribute.code() : Gender.ETC.code();
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
