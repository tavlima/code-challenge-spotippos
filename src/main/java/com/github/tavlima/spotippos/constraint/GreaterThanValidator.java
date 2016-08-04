package com.github.tavlima.spotippos.constraint;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by thiago on 8/4/16.
 */
public class GreaterThanValidator implements ConstraintValidator<GreaterThan, Object> {

    private String errorMessage;
    private String targetFieldName;
    private String referenceFieldName;

    @Override
    public void initialize(GreaterThan constraintAnnotation) {
        this.errorMessage = constraintAnnotation.message();
        this.targetFieldName = constraintAnnotation.target();
        this.referenceFieldName = constraintAnnotation.reference();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean ret = false;

        try {
            final Object target = PropertyUtils.getProperty(value, this.targetFieldName);
            final Object reference = PropertyUtils.getProperty(value, this.referenceFieldName);

            if (Integer.class.isInstance(target) && Integer.class.isInstance(reference)) {
                ret = Integer.class.cast(target) > Integer.class.cast(reference);
            }

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            // nothing
        }

        if (! ret) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.errorMessage)
                    .addPropertyNode(this.targetFieldName)
                    .addConstraintViolation();
        }

        return ret;
    }

}
