package github.io.im2back.challenger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github.io.im2back.challenger.model.user.Usuario;
import github.io.im2back.challenger.model.user.dtos.DadosCadastroUsuarioRequest;
import github.io.im2back.challenger.model.user.dtos.DadosCadastroUsuarioResponse;
import github.io.im2back.challenger.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	public DadosCadastroUsuarioResponse saveNewUser(DadosCadastroUsuarioRequest dados) {
		Usuario usuario = new Usuario(dados);
		repository.save(usuario);
		DadosCadastroUsuarioResponse response = new DadosCadastroUsuarioResponse(dados,usuario.getId(),usuario.getCarteira().getId());	
		return response;
	}
	
	public Usuario findById(Long id) {
			Usuario user = repository.getReferenceById(id);
			return user;
	}
	
}
