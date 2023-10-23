package github.io.im2back.challenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import github.io.im2back.challenger.model.transacao.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

}
