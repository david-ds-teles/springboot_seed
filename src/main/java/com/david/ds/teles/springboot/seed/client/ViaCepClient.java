package com.david.ds.teles.springboot.seed.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ViaCepClient implements ZipService {
	private final WebClient webClient;

	public ViaCepClient(
		@Value("${viacep.url}") String viaCepEndpoint,
		WebClient.Builder webClientBuilder
	) {
		this.webClient = webClientBuilder.baseUrl(viaCepEndpoint).build();
	}

	public Zip fetch(String zip) {
		Mono<Zip> mono = this.webClient.get().uri("/{cep}/json/", zip).retrieve().bodyToMono(Zip.class);
		return mono.block();
	}
}
