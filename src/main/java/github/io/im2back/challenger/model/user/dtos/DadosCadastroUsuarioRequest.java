package github.io.im2back.challenger.model.user.dtos;

import github.io.im2back.challenger.model.user.TipodeUsuario;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroUsuarioRequest(
		
		@NotBlank
		TipodeUsuario tipoDeUsuario,
		
		@Column(name = "nome")
		@NotBlank
		String nomeCompleto,
		
		@NotBlank
		@Column(unique = true)
		@Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")
		String cpf,
		
		@Column(unique = true)
		@NotBlank
		@Email
		String email, 
		
		@NotBlank
		String senha
		) {



}
