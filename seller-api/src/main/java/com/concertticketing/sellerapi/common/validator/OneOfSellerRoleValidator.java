package com.concertticketing.sellerapi.common.validator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.concertticketing.sellerapi.apis.sellers.constant.SellerRole;
import com.concertticketing.sellerapi.common.validation.OneOfSellerRole;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OneOfSellerRoleValidator implements ConstraintValidator<OneOfSellerRole, SellerRole> {
    private Set<SellerRole> roles;

    @Override
    public void initialize(OneOfSellerRole constraint) {
        roles = new HashSet<>(Arrays.asList(constraint.anyOf()));
    }

    @Override
    public boolean isValid(SellerRole sellerRole, ConstraintValidatorContext constraintValidatorContext) {
        return sellerRole != null && roles.contains(sellerRole);
    }
}
