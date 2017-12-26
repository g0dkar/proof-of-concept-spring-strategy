# A better way to implement a Strategy with Spring Boot

This project is a Proof-of-Concept project showcasing a most likely better way of implementing the Strategy design pattern using Spring's own dependency injection system to build the concrete strategy *if and only if* and *when* needed.

Basically:
1. Create a `@Annotation` for your strategy implementations

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

7. **PRO-TIP**: Add which strategy you want on your request URL:
```
@GetMapping(value = "/{strategy:\\w+}", produces = APPLICATION_JSON)
public ResponseEntity<StringResponse> execute(@PathVariable(name = "strategy") String strategy, @RequestParam("s") @Valid @NotEmpty String string) throws IllegalAccessException {
	StringTransform transform = strategyFactory.getImplementation(strategy);
	String result = transform.transform(string);
	
	// do the rest of the stuff
}
```
