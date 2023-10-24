package github.io.im2back.challenger.model.carteira.validacoes;

import github.io.im2back.challenger.model.transacao.TransacaoDTORequest;

public interface ValidadorCarteira {
	
	 void validar (TransacaoDTORequest dados);
}
