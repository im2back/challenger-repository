package github.io.im2back.challenger.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github.io.im2back.challenger.infra.util.CarteiraTransacaoCarteiras;
import github.io.im2back.challenger.model.carteira.Carteira;
import github.io.im2back.challenger.model.carteira.validacoes.ValidadorCarteira;
import github.io.im2back.challenger.model.transacao.TipoDaTransacao;
import github.io.im2back.challenger.model.transacao.Transacao;
import github.io.im2back.challenger.model.transacao.dtos.TransacaoDTORequest;
import github.io.im2back.challenger.model.transacao.dtos.TransacaoDTOResponse;
import github.io.im2back.challenger.model.transacao.dtos.TransacaoEstornoDTOResponse;
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
	
	@Autowired
	private NotificationService notificationService;

	public Object enviarDinheiro(TransacaoDTORequest dados) {
		validadores.forEach(v -> v.validar(dados));
		CarteiraTransacaoCarteiras carteiraTransacaoCarteiras = recuperarEIniciarTransferencia(dados);
		
		/* antes de finalizar e salvar a operação de transferencia na database eu consulto um serviço externo */
		if (transacaoService.autorizarTransacao(carteiraTransacaoCarteiras.getCarteiraPagante(), dados.amount()) == true) {
				Transacao trans = respostaPositivaFinalizarTransferencia(carteiraTransacaoCarteiras, dados);	
						String msgNotificacao = enviarNotificacao(dados, "Transação realizada com sucesso", "transferência recebida com sucesso");
							return new TransacaoDTOResponse(trans.getId(), carteiraTransacaoCarteiras.getCarteiraPagante().getId(),
									carteiraTransacaoCarteiras.getCarteiraRecebedor().getId(), dados.amount(), msgNotificacao );
		} else {
			Transacao trans = inconsistencia(carteiraTransacaoCarteiras.getCarteiraPagante(),carteiraTransacaoCarteiras.getCarteiraRecebedor(), dados.amount());
				String msgNotificacao = enviarNotificacao(dados, "transferência falhou","falha no recebimento");
						return new TransacaoEstornoDTOResponse(trans.getId(), "Falha na operação",carteiraTransacaoCarteiras.getCarteiraPagante().getId(),
						carteiraTransacaoCarteiras.getCarteiraRecebedor().getId(), dados.amount(),msgNotificacao);
		}
	}

	private Transacao inconsistencia(Carteira pagante, Carteira recebedor, BigDecimal amount) {
		// desfaz a operação que está intransiente
		recebedor.transferir(amount);
		pagante.receber(amount);

		// após desfazer a operação eu instancio um objeto Transacao para ser armazenado
		Transacao trans = new Transacao(amount, recebedor, pagante, TipoDaTransacao.CANCELADA_ESTORNADA);
		transacaoService.save(trans);

		return trans;
	}

	private CarteiraTransacaoCarteiras recuperarEIniciarTransferencia(TransacaoDTORequest dados) {

		// Localizo o usuario
		var usuarioPagante = usuarioService.findById(dados.idPagante());
		var usuarioRecebedor = usuarioService.findById(dados.idRecebedor());

		// Localizo e recupero a carteira dos usuarios envolvidos
		var carteiraPagante = repository.findById(usuarioPagante.getCarteira().getId()).get();
		var carteiraRecebedor = repository.findById(usuarioRecebedor.getCarteira().getId()).get();

		// Realizo as operações
		carteiraPagante.transferir(dados.amount());
		carteiraRecebedor.receber(dados.amount());

		CarteiraTransacaoCarteiras carteiraTransacaoPair = new CarteiraTransacaoCarteiras(carteiraPagante, carteiraRecebedor);

		return carteiraTransacaoPair;
	}

	private String enviarNotificacao(TransacaoDTORequest dados, String msgPagante,String msgRecebedor) {
		var usuarioPagante = usuarioService.findById(dados.idPagante());
		var usuarioRecebedor = usuarioService.findById(dados.idRecebedor());
		
		String retorno = notificationService.enviarNotificacao(usuarioPagante, msgPagante);
		 notificationService.enviarNotificacao(usuarioRecebedor, msgRecebedor);
		 return retorno;
	}
	
	private Transacao respostaPositivaFinalizarTransferencia(CarteiraTransacaoCarteiras carteiraTransacaoPair,TransacaoDTORequest dados) {
		/*Se o autorizador externo der o aval eu salvo a operação no banco de dados*/
		repository.saveAll(Arrays.asList(carteiraTransacaoPair.getCarteiraPagante(),carteiraTransacaoPair.getCarteiraRecebedor()));

		/* salvo um registro da transação no banco de dados caso essa codicional seja acionada*/
		Transacao trans = new Transacao(dados.amount(), carteiraTransacaoPair.getCarteiraPagante(),carteiraTransacaoPair.getCarteiraRecebedor(), TipoDaTransacao.FINALIZADA_CONCLUIDA);
		transacaoService.save(trans);
		
		return trans;
	}
	
	
}


