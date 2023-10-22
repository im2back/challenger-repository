package github.io.im2back.challenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import github.io.im2back.challenger.model.user.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
