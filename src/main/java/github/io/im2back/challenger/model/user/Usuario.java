package github.io.im2back.challenger.model.user;


import github.io.im2back.challenger.model.carteira.Carteira;
import github.io.im2back.challenger.model.user.dtos.DadosCadastroUsuarioRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@EqualsAndHashCode(of ="id")
@NoArgsConstructor
@Table(name = "tb_user")
public class Usuario {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo")
	private TipodeUsuario tipoDeUsuario;
	
	@Column(name = "nome")
	private String nomeCompleto; 
	private String cpf;
	private String email; 
	private String senha;
	
	
	@JoinColumn(name="carteira_id")
	@OneToOne(cascade = CascadeType.ALL)
	private Carteira carteira = new Carteira();
	
	public Usuario(TipodeUsuario tipoDeUsuario, String nomeCompleto, String cpf, String email, String senha) {
		super();
		this.tipoDeUsuario = tipoDeUsuario;
		this.nomeCompleto = nomeCompleto;
		this.cpf = cpf;
		this.email = email;
		this.senha = senha;
		
	}
	
	public Usuario (DadosCadastroUsuarioRequest dados) {
		this.tipoDeUsuario = dados.tipoDeUsuario();
		this.nomeCompleto = dados.nomeCompleto();
		this.cpf = dados.cpf();
		this.email = dados.email();
		this.senha = dados.senha();
	}
	
	
	
	
}
