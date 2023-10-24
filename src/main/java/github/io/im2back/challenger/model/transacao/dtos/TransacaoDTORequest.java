package github.io.im2back.challenger.model.transacao.dtos;

import java.math.BigDecimal;

public record TransacaoDTORequest(
		Long idPagante,
		Long idRecebedor,
		BigDecimal amount
		) {

}
