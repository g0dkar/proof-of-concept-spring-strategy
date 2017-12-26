package com.vcmais.proofOfConcept.strategy.strategies;

import com.vcmais.proofOfConcept.strategy.annotation.StringTransformImplementation;

@StringTransformImplementation("upper")
public class UppercaseStringTransformStrategy implements StringTransform {
	@Override
	public String transform(String input) {
		return input.toUpperCase();
	}
}
