package github.io.im2back.challenger.model.transacao;

import java.math.BigDecimal;

import github.io.im2back.challenger.model.carteira.Carteira;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of ="id")
@Table(name = "tb_transacao" )
@Entity
public class Transacao {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private BigDecimal valor;
	
	@ManyToOne
	@JoinColumn(name="carteiraPagante_id")
	private Carteira carteiraRaiz;
	
	@ManyToOne
	@JoinColumn(name="carteiraRecebedor_id")
	private Carteira carteiraDestino;

	public Transacao(BigDecimal valor, Carteira carteiraRaiz, Carteira carteiraDestino) {
		super();
		this.valor = valor;
		this.carteiraRaiz = carteiraRaiz;
		this.carteiraDestino = carteiraDestino;
	}

	



	

}
