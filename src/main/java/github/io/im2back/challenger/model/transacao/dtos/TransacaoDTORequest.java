package github.io.im2back.challenger.model.transacao.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;

public record TransacaoDTORequest(
		@NotBlank
		Long idPagante,
		@NotBlank
		Long idRecebedor,
		@NotBlank
		BigDecimal amount
		) {

}
