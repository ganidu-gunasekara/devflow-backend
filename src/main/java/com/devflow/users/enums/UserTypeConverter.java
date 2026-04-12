package com.devflow.users.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UserTypeConverter implements AttributeConverter<UserType, String> {

    @Override
    public String convertToDatabaseColumn(UserType userType) {
        if (userType == null) return null;
        return userType.name().toLowerCase();
    }

    @Override
    public UserType convertToEntityAttribute(String value) {
        if (value == null) return null;
        return UserType.valueOf(value.toUpperCase());
    }
}