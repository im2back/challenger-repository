package github.io.im2back.challenger.model.transacao.dtos;

import java.math.BigDecimal;

import github.io.im2back.challenger.model.transacao.TipoDaTransacao;
import github.io.im2back.challenger.model.transacao.Transacao;

public record ListarTransacaoDTOResponse(
		Long idTransacao,
		Long idCarteiraPagante,
		Long idCarteiraRecebedor,
		BigDecimal valor,
		TipoDaTransacao tipo
		) {
	public ListarTransacaoDTOResponse(Transacao dados) {
		this(dados.getId(),dados.getCarteiraRaiz().getId(),dados.getCarteiraDestino().getId(),dados.getValor()
				,dados.getTipoDaTransacao());
	}


}
