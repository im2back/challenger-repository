package github.io.im2back.challenger.model.transacao.dtos;

import java.math.BigDecimal;

public record TransacaoDTOResponse(
		Long idTransacao,
		Long idCarteiraPagante,
		Long idCarteiraRecebedor,
		BigDecimal valor,
		String notificacao
		) {

}
