package com.vcmais.proofOfConcept.strategy.factory;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import com.vcmais.proofOfConcept.strategy.annotation.StringTransformImplementation;
import com.vcmais.proofOfConcept.strategy.strategies.StringTransform;

@Component
@ApplicationScope
public class StringTransformStrategyFactory extends AnnotatedStrategyFactory<StringTransformImplementation, StringTransform> {

	@Override
	public Class<StringTransformImplementation> strategyAnnotation() {
		return StringTransformImplementation.class;
	}

	@Override
	public Class<StringTransform> strategyInterface() {
		return StringTransform.class;
	}
	
}
