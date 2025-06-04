package com.concertticketing.userapi.common.validator;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.concertticketing.userapi.common.validation.ValueOfEnum;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, String> {
    private Set<String> enumValues;

    @Override
    public void initialize(ValueOfEnum constraintAnnotation) {
        enumValues = Arrays.stream(constraintAnnotation.enumClass().getEnumConstants())
            .map(e -> e.name().toLowerCase())
            .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null && enumValues.contains(s.toLowerCase());
    }
}
