package github.io.im2back.challenger.infra.util;

import github.io.im2back.challenger.model.carteira.Carteira;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CarteirasEnvolvidasNaTransacao {
	
	private Carteira carteiraPagante;
	private Carteira carteiraRecebedor;
	

}
