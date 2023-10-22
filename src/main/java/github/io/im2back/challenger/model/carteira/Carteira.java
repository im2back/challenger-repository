package github.io.im2back.challenger.model.carteira;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Entity
@Getter
@Table(name ="tb_carteira")
@AllArgsConstructor
@NoArgsConstructor
public class Carteira{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private BigDecimal saldo = new BigDecimal(0);
	
	public void receber(BigDecimal quantia) {
		 this.saldo = this.saldo.add(quantia);
	}
	
	 public void transferir(BigDecimal quantia) {
	        this.saldo = this.saldo.subtract(quantia);
	      
	    }

}
