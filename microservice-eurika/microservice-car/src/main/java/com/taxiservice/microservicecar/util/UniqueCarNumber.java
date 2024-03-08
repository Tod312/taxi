package com.taxiservice.microservicecar.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueCarNumberContraint.class)
public @interface UniqueCarNumber {
	
	String message() default "this car number is already taken";
	
	Class<?>[] groups() default { };
	
	Class<? extends Payload>[] payload() default { };

}
