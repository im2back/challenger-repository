package github.io.im2back.challenger.model.carteira.validacoes;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import github.io.im2back.challenger.model.transacao.dtos.TransacaoDTORequest;
import github.io.im2back.challenger.model.user.Usuario;
import github.io.im2back.challenger.model.util.ValidacaoException;
import github.io.im2back.challenger.repositories.UsuarioRepository;

@Component
public class ValidadorDeUsuarioESaldo implements ValidadorCarteira{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public void validar(TransacaoDTORequest dados) {
		
		if(usuarioRepository.findById(dados.idPagante()).isEmpty()) {
			
			throw new ValidacaoException("Revise os dados bancarios, conta inexistente.");
		}
		if(usuarioRepository.findById(dados.idRecebedor()).isEmpty() ) {
			throw new ValidacaoException("Revise os dados bancarios, conta inexistente.");
		}
		
		
		Usuario usuarioPagante = usuarioRepository.getReferenceById(dados.idPagante());
		
		BigDecimal saldoPagante = usuarioPagante.getCarteira().getSaldo();
		BigDecimal valorDaTransferencia = dados.amount();
		
		int comparacao = saldoPagante.compareTo(valorDaTransferencia);

		if(comparacao < 0) {
			throw new ValidacaoException ("Saldo insuficiente !");
		}
	}

}
