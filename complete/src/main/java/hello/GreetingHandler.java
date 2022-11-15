package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class GreetingHandler {

	private static final Logger log = LoggerFactory.getLogger(Application.class);
	@Autowired
	CustomerRepository customerRepository;

	public Mono<ServerResponse> hello(ServerRequest request) {
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromValue(new Greeting("Hello, Spring!")));
	}

	public Mono<ServerResponse> customers(ServerRequest request) {
		Flux<Customer> customers = customerRepository.findByLastName("Palmer");
		customers.subscribe(System.out::println);
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(customers, Customer.class);
	}

}
