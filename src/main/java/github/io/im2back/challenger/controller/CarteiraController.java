package github.io.im2back.challenger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import github.io.im2back.challenger.model.transacao.dtos.TransacaoDTORequest;
import github.io.im2back.challenger.service.CarteiraService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "carteira")
public class CarteiraController {
	
	@Autowired
	private CarteiraService service;
	
	
	@PutMapping("/transferencia")
	@Transactional
	public ResponseEntity<Object> enviarGrana(@RequestBody @Valid TransacaoDTORequest dados){
		
		var	response = service.enviarDinheiro(dados);
			
		return ResponseEntity.ok(response);
	}
}
