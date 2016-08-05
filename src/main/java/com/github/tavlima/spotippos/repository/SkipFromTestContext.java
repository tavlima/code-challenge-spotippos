package com.github.tavlima.spotippos.repository;

import java.lang.annotation.*;

/**
 * Created by thiago on 8/5/16.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SkipFromTestContext {
}
