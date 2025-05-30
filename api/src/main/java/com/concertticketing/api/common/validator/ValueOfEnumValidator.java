package com.concertticketing.api.common.validator;

import com.concertticketing.api.common.validation.ValueOfEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

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
