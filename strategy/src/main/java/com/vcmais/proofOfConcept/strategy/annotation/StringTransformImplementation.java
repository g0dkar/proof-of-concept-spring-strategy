package com.vcmais.proofOfConcept.strategy.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.vcmais.proofOfConcept.strategy.strategies.StringTransform;

/**
 * Classes annotated with this are implementations of {@link StringTransform}
 *
 */
@Qualifier
@Documented
@Retention(RUNTIME)
@Component
public @interface StringTransformImplementation {
	String value() default "";
}
