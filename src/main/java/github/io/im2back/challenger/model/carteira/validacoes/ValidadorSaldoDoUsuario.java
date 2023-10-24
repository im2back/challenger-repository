package github.io.im2back.challenger.model.carteira.validacoes;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import github.io.im2back.challenger.model.transacao.TransacaoDTORequest;
import github.io.im2back.challenger.model.util.ValidacaoException;
import github.io.im2back.challenger.repositories.UsuarioRepository;

@Component
public class ValidadorSaldoDoUsuario implements ValidadorCarteira{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public void validar(TransacaoDTORequest dados) {
		var usuarioPagante = usuarioRepository.findById(dados.idPagante()).get();
		
		BigDecimal saldoPagante = usuarioPagante.getCarteira().getSaldo();
		BigDecimal valorDaTransferencia = dados.amount();
		
		int comparacao = saldoPagante.compareTo(valorDaTransferencia);

		if(comparacao < 0) {
			throw new ValidacaoException ("Saldo insuficiente !");
		}
	}

}
