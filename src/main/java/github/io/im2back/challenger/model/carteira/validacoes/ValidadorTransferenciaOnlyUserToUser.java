package github.io.im2back.challenger.model.carteira.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import github.io.im2back.challenger.model.transacao.TransacaoDTORequest;
import github.io.im2back.challenger.model.user.TipodeUsuario;
import github.io.im2back.challenger.model.util.ValidacaoException;
import github.io.im2back.challenger.repositories.UsuarioRepository;

@Component
public class ValidadorTransferenciaOnlyUserToUser implements ValidadorCarteira {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	//Usuários podem enviar dinheiro (efetuar transferência) para lojistas e entre usuários.
	//Lojistas só recebem transferências, não enviam dinheiro para ninguém.
	public void validar(TransacaoDTORequest dados) {
		var usuarioPagante = usuarioRepository.findById(dados.idPagante()).get();
		
		TipodeUsuario tipoUsuarioPagante = usuarioPagante.getTipoDeUsuario();
		
		
		if(tipoUsuarioPagante == TipodeUsuario.LOJISTA ) {
			throw new ValidacaoException ("Usuarios do tipo Lojista não pode enviar pagamentos, apenas receber!");
		}
	}

}
