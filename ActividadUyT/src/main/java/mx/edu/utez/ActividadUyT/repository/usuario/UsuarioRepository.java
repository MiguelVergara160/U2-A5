package mx.edu.utez.ActividadUyT.repository.usuario;

import mx.edu.utez.ActividadUyT.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
