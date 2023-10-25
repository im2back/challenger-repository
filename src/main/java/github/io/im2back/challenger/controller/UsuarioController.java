package github.io.im2back.challenger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import github.io.im2back.challenger.model.user.Usuario;
import github.io.im2back.challenger.model.user.dtos.DadosCadastroUsuarioRequest;
import github.io.im2back.challenger.model.user.dtos.DadosCadastroUsuarioResponse;
import github.io.im2back.challenger.service.UsuarioService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping(value = "usuarios")
public class UsuarioController {
	
	@Autowired	private UsuarioService service;

	@PostMapping("/cadastrar")
	@Transactional
	ResponseEntity<DadosCadastroUsuarioResponse> insertNewUser(@RequestBody DadosCadastroUsuarioRequest dados,UriComponentsBuilder uriBuilder){
		 DadosCadastroUsuarioResponse response =  service.saveNewUser(dados);
		 
		 var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(response.id()).toUri();
		
		return ResponseEntity.created(uri).body(response);
		
	}
	
	@GetMapping(value = "/{id}")
	ResponseEntity<DadosCadastroUsuarioResponse> listarUsuario(@PathVariable Long id){
		Usuario user = service.findById(id);
		var response = new DadosCadastroUsuarioResponse(user.getId(), user.getTipoDeUsuario(), user.getNomeCompleto(), user.getCpf(), user.getEmail(),
				user.getCarteira().getId());
		return ResponseEntity.ok(response);
	}
}
