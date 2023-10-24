package github.io.im2back.challenger.model.validacoes;

import github.io.im2back.challenger.model.transacao.TransacaoDTORequest;

public interface ValidadorCarteira {
	
	 void validar (TransacaoDTORequest dados);
}
