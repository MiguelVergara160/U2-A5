package mx.edu.utez.ActividadUyT.service.usuario;

import jakarta.transaction.Transactional;
import mx.edu.utez.ActividadUyT.exception.customExceptions.CustomNotFoundException;
import mx.edu.utez.ActividadUyT.model.usuario.Usuario;
import mx.edu.utez.ActividadUyT.repository.usuario.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Usuario desactivarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("El usuario no existe"));

        usuario.setActivo(false);

        return usuarioRepository.save(usuario);
    }
}
