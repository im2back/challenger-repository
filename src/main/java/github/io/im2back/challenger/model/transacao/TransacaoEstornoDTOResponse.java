package github.io.im2back.challenger.model.transacao;

import java.math.BigDecimal;

public record TransacaoEstornoDTOResponse(
		Long idTransacao,
		String message,
		Long idCarteiraPagante,
		Long idCarteiraRecebedor,
		BigDecimal valorEstornado
		) {
	

}
