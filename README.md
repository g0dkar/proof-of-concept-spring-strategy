# One to implement a Strategy with Spring Boot

This project is a Proof-of-Concept project showcasing a way of implementing the Strategy design pattern using Spring's own dependency injection system to build the concrete strategy *if and only if* and *when* needed.

>**Update as of 2020:** My knowledge about Spring Boot has grown and you can simply inject a `List<StrategyInterface>` and have all your strategies implement a `getName()` that you can use to select which to execute. This code is being kept as _one way_ of having the same.

# Test it out
Get the project, run `StrategyApplication.java` and test it out:
- http://localhost:8080/strategy/upper?s=HaDouKen
- http://localhost:8080/strategy/lower?s=HaDouKen
- http://localhost:8080/strategy/camel?s=HaDouKen

# How to use this?
1. Create an `@Annotation` for your strategy implementations

```
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
```

2. Create a interface for your strategy implementations
```
public interface StringTransform {
	default String transform(String input) {
		throw new UnsupportedOperationException();
	}
}
```

3. Implement your strategy
```
@StringTransformImplementation("upper")
public class UppercaseStringTransformStrategy implements StringTransform {
	@Override
	public String transform(String input) {
		return input.toUpperCase();
	}
}

@StringTransformImplementation("lower")
public class LowercaseStringTransformStrategy implements StringTransform {
	@Override
	public String transform(String input) {
		return input.toLowerCase();
	}
}

@StringTransformImplementation("camel")
public class CamelCaseStringTransformStrategy implements StringTransform {
	@Override
	public String transform(String input) {
		return input.substring(0, 1).toLowerCase() + input.substring(1);
	}
}
```

4. Create a factory for your strategy implementations:
```
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
```

5. Inject your factory:
```
@Autowired
private StringTransformStrategyFactory strategyFactory;
```

6. Ask for an implementation by its name:
```
StringTransform upperTransform = strategyFactory.getImplementation("upper");
StringTransform lowerTransform = strategyFactory.getImplementation("lower");
StringTransform camelTransform = strategyFactory.getImplementation("camel");
```

# PRO-Tips!
1. Add which strategy you want on your request URL:
```
@GetMapping(value = "/{strategy:\\w+}", produces = APPLICATION_JSON)
public ResponseEntity<?> execute(@PathVariable(name = "strategy") String strategy, @RequestParam("s") @Valid @NotEmpty String string) throws IllegalAccessException {
	StringTransform transform = strategyFactory.getImplementation(strategy);
	String result = transform.transform(string);
	
	// do the rest of the stuff
}
```

2. Kinda, sorta, _"you won't tell the difference"_ use an `enum` as your strategy name:
```
public enum StringTransformationTypes {
	NONE, UPPER, LOWER, CAMEL;
}

@StringTransformImplementation("UPPER")
public class UppercaseStringTransformStrategy implements StringTransform { ... }

StringTransform upperTransform = strategyFactory.getImplementation(StringTransformationTypes.UPPER.name());
```
