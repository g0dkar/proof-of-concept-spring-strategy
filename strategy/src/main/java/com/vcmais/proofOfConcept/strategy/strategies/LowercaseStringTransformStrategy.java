package com.vcmais.proofOfConcept.strategy.strategies;

import com.vcmais.proofOfConcept.strategy.annotation.StringTransformImplementation;

@StringTransformImplementation("lower")
public class LowercaseStringTransformStrategy implements StringTransform {
	@Override
	public String transform(String input) {
		return input.toLowerCase();
	}
}
