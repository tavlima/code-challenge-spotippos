package com.github.tavlima.spotippos.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by thiago on 8/4/16.
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = GreaterThanValidator.class)
@Documented
public @interface GreaterThan {

    String message() default "Field comparison failed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String target();

    String reference();

    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        GreaterThan[] value();
    }
}
