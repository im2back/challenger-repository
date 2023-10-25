package github.io.im2back.challenger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import github.io.im2back.challenger.model.transacao.dtos.ListarTransacaoDTOResponse;
import github.io.im2back.challenger.service.TransacaoService;

@RestController
@RequestMapping(value = "transacoes")
public class TransacaoController {
	
	@Autowired
	private TransacaoService service;
	
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity <List<ListarTransacaoDTOResponse>> listarTransacoesDoUsuario(@PathVariable Long id){
		var	responseList = service.listarTransacoes(id);
			
		return ResponseEntity.ok(responseList);
	}
}
