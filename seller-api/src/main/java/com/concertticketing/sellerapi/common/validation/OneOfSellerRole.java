package com.concertticketing.sellerapi.common.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.concertticketing.sellerapi.apis.sellers.constant.SellerRole;
import com.concertticketing.sellerapi.common.validator.OneOfSellerRoleValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {OneOfSellerRoleValidator.class})
public @interface OneOfSellerRole {
    String message() default "invalid enum";

    SellerRole[] anyOf();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
