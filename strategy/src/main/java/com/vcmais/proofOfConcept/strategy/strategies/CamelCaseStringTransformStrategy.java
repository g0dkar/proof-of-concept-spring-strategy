package com.vcmais.proofOfConcept.strategy.strategies;

import com.vcmais.proofOfConcept.strategy.annotation.StringTransformImplementation;

@StringTransformImplementation("camel")
public class CamelCaseStringTransformStrategy implements StringTransform {
	@Override
	public String transform(String input) {
		return input.substring(0, 1).toLowerCase() + input.substring(1);
	}
}
