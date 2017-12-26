package com.vcmais.proofOfConcept.strategy;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vcmais.proofOfConcept.strategy.factory.StringTransformStrategyFactory;
import com.vcmais.proofOfConcept.strategy.strategies.StringTransform;

@RestController
@RequestMapping("/strategy")
public class ExecuteStrategyController {
	protected static final String APPLICATION_JSON = MediaType.APPLICATION_JSON_UTF8_VALUE;
	
	@Autowired
	private StringTransformStrategyFactory strategyFactory;
	
	@GetMapping(value = "/{strategy:\\w+}", produces = APPLICATION_JSON)
	public ResponseEntity<StringResponse> get(@PathVariable(name = "strategy") @Valid @NotEmpty String strategy, @RequestParam("s") @Valid @NotEmpty String string) throws IllegalAccessException {
		
		// Pega a implementação pelo nome enviado na URL
		StringTransform transform = strategyFactory.getImplementation(strategy);
		
		// E simplesmente chama :)
		String result = transform.transform(string);
		
		StringResponse response = new StringResponse(result);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	public static class StringResponse {
		private String message;
		
		public StringResponse(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
}
