package github.io.im2back.challenger.service;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import github.io.im2back.challenger.model.carteira.Carteira;
import github.io.im2back.challenger.model.transacao.Transacao;
import github.io.im2back.challenger.model.util.ValidacaoException;
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
	
	public boolean autorizarTransacao(Carteira carteiraPagante, BigDecimal amount) {
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> authorizationResponse =	restTemplate.
				getForEntity("https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6", Map.class);
	
	if (authorizationResponse.getStatusCode() == HttpStatus.OK ) {
		String message = (String) authorizationResponse.getBody().get("message");
		return "Autorizado".equalsIgnoreCase(message);
	}else return false;
	}
	
	public boolean validarTransacao(Carteira carteiraPagante, BigDecimal amount) {
		if (!autorizarTransacao(carteiraPagante,amount)) {
			throw new ValidacaoException("Transacao não autorizada");
		}
		else return true;
	}
}
	
