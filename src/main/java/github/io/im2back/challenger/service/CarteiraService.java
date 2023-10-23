package github.io.im2back.challenger.service;

import java.math.BigDecimal;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github.io.im2back.challenger.model.carteira.Carteira;
import github.io.im2back.challenger.model.transacao.Transacao;
import github.io.im2back.challenger.model.transacao.TransacaoDTOResponse;
//import github.io.im2back.challenger.model.transacao.Transacao;
import github.io.im2back.challenger.repositories.CarteiraRepository;
import github.io.im2back.challenger.repositories.TransacaoRepository;

@Service
public class CarteiraService {
	
	@Autowired
	private CarteiraRepository repository;
	
	@Autowired
	private TransacaoRepository transacaoRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public TransacaoDTOResponse enviarDinheiro(Long idPagante, Long idRecebedor, BigDecimal amount) {
		//Localizo o usuario
		var usuarioPagante = usuarioService.findById(idPagante);
		var usuarioRecebedor = usuarioService.findById(idRecebedor);
		
		//Localizo e recupero a carteira dos usuarios envolvidos
		var carteiraPagante = repository.findById(usuarioPagante.getCarteira().getId()).get();
		var carteiraRecebedor = repository.findById(usuarioRecebedor.getCarteira().getId()).get();
		
		//validações
		
		//Realizo as operações
		carteiraPagante.transferir(amount);
		carteiraRecebedor.receber(amount);
		
		//salvo a transação no banco de dados
		Transacao trans = new Transacao(amount, carteiraPagante, carteiraRecebedor);
		transacaoRepository.save(trans);
		
		//consulta o serviço externo antes de salvar a operação
		
		//chamo a operação incosistencia dependendo da resposta do serviço autorizador
		inconsistencia(carteiraPagante, carteiraRecebedor, amount);
		
		repository.saveAll(Arrays.asList(carteiraPagante,carteiraRecebedor));
		
		return new TransacaoDTOResponse(trans.getId(), carteiraPagante.getId(), carteiraRecebedor.getId(),amount);
		//envia recibo do pagamento
	}
	
	private void inconsistencia(Carteira pagante, Carteira recebedor, BigDecimal amount) {
		recebedor.transferir(amount);
		pagante.receber(amount);
		
	}

}