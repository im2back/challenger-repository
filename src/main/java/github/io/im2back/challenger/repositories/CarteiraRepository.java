package github.io.im2back.challenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import github.io.im2back.challenger.model.carteira.Carteira;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {

}
