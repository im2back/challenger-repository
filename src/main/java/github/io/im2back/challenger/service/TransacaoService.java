package github.io.im2back.challenger.service;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import github.io.im2back.challenger.model.carteira.Carteira;
import github.io.im2back.challenger.model.transacao.Transacao;
import github.io.im2back.challenger.repositories.TransacaoRepository;

@Service
public class TransacaoService {
	@Autowired
	private TransacaoRepository repository;

	@Autowired
	private RestTemplate restTemplate;

	public void save(Transacao transacao) {

		repository.save(transacao);

	}

	// Client Closed Request 499 ---> https://run.mocky.io/v3/ab9e31c0-3f16-4367-b319-c346a21b41ec
	// Autorizado 200 ---> https://run.mocky.io/v3/e8785181-c5dc-44cc-9dfa-1d77aa9bf478
	public boolean autorizarTransacao(Carteira carteiraPagante, BigDecimal amount) {
		try {
			@SuppressWarnings("rawtypes")
			ResponseEntity<Map> authorizationResponse = restTemplate
					.getForEntity("https://run.mocky.io/v3/e8785181-c5dc-44cc-9dfa-1d77aa9bf478", Map.class);

			if (authorizationResponse.getStatusCode() == HttpStatus.OK) {
				String message = (String) authorizationResponse.getBody().get("message");
				return "Autorizado".equalsIgnoreCase(message);
			} 
			else {
				return false;
			}
			
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
				return false;
		}

	}

}
