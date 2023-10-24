package github.io.im2back.challenger.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github.io.im2back.challenger.model.carteira.Carteira;
import github.io.im2back.challenger.model.carteira.validacoes.ValidadorCarteira;
import github.io.im2back.challenger.model.transacao.Transacao;
import github.io.im2back.challenger.model.transacao.TransacaoDTORequest;
import github.io.im2back.challenger.model.transacao.TransacaoDTOResponse;
import github.io.im2back.challenger.model.transacao.TransacaoEstornoDTOResponse;
import github.io.im2back.challenger.model.util.CarteiraTransacaoPair;
import github.io.im2back.challenger.repositories.CarteiraRepository;

@Service
public class CarteiraService {
	
	@Autowired
	private CarteiraRepository repository;
	
	@Autowired
	private TransacaoService transacaoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private List<ValidadorCarteira> validadores;
	
	public Object enviarDinheiro(TransacaoDTORequest dados) {
		validadores.forEach(v -> v.validar(dados));
		CarteiraTransacaoPair carteiraTransacaoPair = recuperarEIniciarTransferencia(dados);
		
		/*Para fazer -> antes de finalizar e salvar a operação de transferencia na database eu consulto um serviço externo*/
		if(transacaoService.validarTransacao(carteiraTransacaoPair.getCarteiraPagante(), dados.amount()) == true) {	
			
			repository.saveAll(Arrays.asList(carteiraTransacaoPair.getCarteiraPagante(),carteiraTransacaoPair.getCarteiraRecebedor()));	
			
			return new TransacaoDTOResponse(carteiraTransacaoPair.getTransacao().getId(),carteiraTransacaoPair.getCarteiraPagante().getId(),
					carteiraTransacaoPair.getCarteiraRecebedor().getId(),dados.amount());	
			}	 
		else  {
			//chamo a operação incosistencia e desfaço as alterações dependendo da resposta do serviço autorizador
			inconsistencia(carteiraTransacaoPair.getCarteiraPagante(),carteiraTransacaoPair.getCarteiraRecebedor(), dados.amount());
			
			return new TransacaoEstornoDTOResponse(carteiraTransacaoPair.getTransacao().getId(), "Falha na operação", carteiraTransacaoPair.getCarteiraPagante().getId(),
					
					carteiraTransacaoPair.getCarteiraRecebedor().getId(),dados.amount());
		}
		
			
	}
	
	@SuppressWarnings("unused")
	private void inconsistencia(Carteira pagante, Carteira recebedor, BigDecimal amount) {	
		//desfaz a operação
		recebedor.transferir(amount);
		pagante.receber(amount);
		
		// instancia uma transação de estorno
		Transacao trans = new Transacao(amount, recebedor, pagante);
		/*Adicioanr um melhoramento na classe Transacao. Adicionar um enum contendo o Status da transacao,
		 após isso adicionar um retorno desse tipo no metodo enviar dinheiro nesse caso se é estorno ou concluida*/
		
	}

	private CarteiraTransacaoPair recuperarEIniciarTransferencia(TransacaoDTORequest dados) {
		//Localizo o usuario
				var usuarioPagante = usuarioService.findById(dados.idPagante());
				var usuarioRecebedor = usuarioService.findById(dados.idRecebedor());
				
				//Localizo e recupero a carteira dos usuarios envolvidos
				var carteiraPagante = repository.findById(usuarioPagante.getCarteira().getId()).get();
				var carteiraRecebedor = repository.findById(usuarioRecebedor.getCarteira().getId()).get();
				
				//Realizo as operações
				carteiraPagante.transferir(dados.amount());
				carteiraRecebedor.receber(dados.amount());
				
				//salvo um registro da transação no banco de dados
				Transacao trans = new Transacao(dados.amount(), carteiraPagante, carteiraRecebedor);
				transacaoService.save(trans);
				
				CarteiraTransacaoPair carteiraTransacaoPair = new CarteiraTransacaoPair(carteiraPagante, carteiraRecebedor, trans);
				
				
				
				return carteiraTransacaoPair;
	}
}
