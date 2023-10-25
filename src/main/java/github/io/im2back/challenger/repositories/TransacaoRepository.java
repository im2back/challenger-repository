package github.io.im2back.challenger.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import github.io.im2back.challenger.model.transacao.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

	@Query("""
	 		SELECT t
			FROM Transacao t
			WHERE t.carteiraDestino.id = :id OR t.carteiraRaiz.id = :id
	
 		""")
	List<Transacao> listarTransacaoPorUsuario(Long id);
}
