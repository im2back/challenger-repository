package github.io.im2back.challenger.model.user.dtos;

import github.io.im2back.challenger.model.user.TipodeUsuario;


public record DadosCadastroUsuarioResponse(
		Long id,
		TipodeUsuario tipoDeUsuario,	
		String nomeCompleto,	
		String cpf,	
		String email,
		Long numeroDaCarteira
		) {
	
	public DadosCadastroUsuarioResponse(DadosCadastroUsuarioRequest dados, Long id,Long numeroCarteira) {
		this(id,dados.tipoDeUsuario(),dados.nomeCompleto(),dados.cpf(),dados.email(),numeroCarteira);
	}


}
