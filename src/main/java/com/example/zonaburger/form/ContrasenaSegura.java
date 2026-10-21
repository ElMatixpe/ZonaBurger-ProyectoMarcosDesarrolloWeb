package com.example.zonaburger.form;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/* Valida que la contraseña sea segura: mínimo 8 caracteres, una mayúscula,*/
/*una minuscula, un numero y un signo especial*/
@Documented
@Constraint(validatedBy = ContrasenaSeguraValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)

public @interface ContrasenaSegura {

    String MENSAJE = "La contraseña debe tener mínimo 8 caracteres, una mayúscula, "
            + "una minúscula, un número y un signo especial (sin espacios).";

    String message() default MENSAJE;

    boolean opcional() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
