package github.io.im2back.challenger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import github.io.im2back.challenger.model.user.dtos.DadosCadastroUsuarioRequest;
import github.io.im2back.challenger.model.user.dtos.DadosCadastroUsuarioResponse;
import github.io.im2back.challenger.service.UsuarioService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping(value = "users")
public class UsuarioController {
	
	@Autowired	private UsuarioService service;

	@PostMapping
	@Transactional
	ResponseEntity<DadosCadastroUsuarioResponse> insertNewUser(@RequestBody DadosCadastroUsuarioRequest dados,UriComponentsBuilder uriBuilder){
		 DadosCadastroUsuarioResponse response =  service.saveNewUser(dados);
		 
		 var uri = uriBuilder.path("/users/{id}").buildAndExpand(response.id()).toUri();
		
		return ResponseEntity.created(uri).body(response);
		
	}
}
