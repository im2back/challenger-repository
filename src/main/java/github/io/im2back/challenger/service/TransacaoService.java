package github.io.im2back.challenger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github.io.im2back.challenger.model.transacao.Transacao;
import github.io.im2back.challenger.repositories.TransacaoRepository;

@Service
public class TransacaoService {
	@Autowired
	private TransacaoRepository repository;
	
	public void save(Transacao transacao) {
	
		repository.save(transacao);
		
	}
}
