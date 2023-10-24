package github.io.im2back.challenger.model.transacao.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public record TransacaoDTORequest(
		@NotNull
		Long idPagante,
		@NotNull
		Long idRecebedor,
		@NotNull
		BigDecimal amount
		) {

}
