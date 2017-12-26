package com.vcmais.proofOfConcept.strategy.strategies;

public interface StringTransform {
	default String transform(String input) {
		throw new UnsupportedOperationException();
	}
}
