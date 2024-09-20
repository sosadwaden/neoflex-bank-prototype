package com.sosadwaden.deal.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<ValidEnum, Enum<?>> {

    private Class<? extends Enum<?>> enumClass;
    private String[] enumValues;

    @Override
    public void initialize(ValidEnum annotation) {
        enumClass = annotation.enumClass();
        enumValues = Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .toArray(String[]::new);
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null значения не проверяем, это должно управляться аннотацией @NotNull или другой
        }

        boolean isValid = Arrays.stream(enumValues).anyMatch(enumValue -> enumValue.equals(value.name()));

        if (!isValid) {
            String message = "Значение " + value.name() + " недопустимо. Должно быть одним из: " + Arrays.toString(enumValues);
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        }

        return isValid;
    }
}
