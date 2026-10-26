package mx.edu.utez.Proyecto1D.service.usuario;

import jakarta.transaction.Transactional;
import mx.edu.utez.Proyecto1D.controller.usuario.dto.RequestCreateUserDTO;
import mx.edu.utez.Proyecto1D.controller.usuario.dto.RequestUpdateUserDTO;
import mx.edu.utez.Proyecto1D.exception.customExceptions.CustomBadRequestException;
import mx.edu.utez.Proyecto1D.exception.customExceptions.CustomNotFoundException;
import mx.edu.utez.Proyecto1D.model.persona.Persona;
import mx.edu.utez.Proyecto1D.model.usuario.Roles;
import mx.edu.utez.Proyecto1D.model.usuario.Usuario;
import mx.edu.utez.Proyecto1D.repository.persona.PersonaRepository;
import mx.edu.utez.Proyecto1D.repository.usuario.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    // inyeccion de depedencias
    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;


    public UsuarioService(UsuarioRepository usuarioRepository, PersonaRepository personaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.personaRepository = personaRepository;
    }

    // servicios
    public List<Usuario> getAllUsers(){

        return usuarioRepository.findAll();
    }

@Transactional
    public Usuario createUser(RequestCreateUserDTO payload){
        // validaciones
        if (usuarioRepository.findByUsername(payload.getUsername()).isPresent()) {
            throw new CustomBadRequestException("El username ya esta en uso");
        }

        // crear el registro de persona
        Persona nuevaPersona = new Persona();
        nuevaPersona.setNombres(payload.getNombres());
        nuevaPersona.setPrimerApellido(payload.getPrimerApellido());
        nuevaPersona.setSegundoApellido(payload.getSegundoApellido());
        nuevaPersona.setCorreo(payload.getCorreo());
        nuevaPersona.setCurp(payload.getCurp());
        nuevaPersona.setFechaNacimiento(payload.getFechaNacimiento());

        nuevaPersona = personaRepository.save(nuevaPersona);


        //crear la instancia de usuario que vamos a guardar
        // a partir de los datos del dto
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(payload.getUsername());
        nuevoUsuario.setPassword(payload.getPassword());
        nuevoUsuario.setDescripcion(payload.getDescripcion());
        nuevoUsuario.setEnable(true);
        nuevoUsuario.setRol(
                Roles.valueOf(payload.getRol())
        );
        nuevoUsuario.setPersona(nuevaPersona);

        return usuarioRepository.save(nuevoUsuario);
    }

    public Usuario getUserById(Long id) {
        return buscarUsuario(id);
    }

    @Transactional
    public Usuario enableUser(Long id) {
        Usuario usuario = buscarUsuario(id);
        usuario.setEnable(true);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario disableUser(Long id) {
        Usuario usuario = buscarUsuario(id);
        usuario.setEnable(false);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario updateUser(Long id, RequestUpdateUserDTO payload) {
        Usuario usuario = buscarUsuario(id);

        boolean usernameEnUso = usuarioRepository.findByUsername(payload.getUsername())
                .filter(otro -> !otro.getId().equals(id))
                .isPresent();
        if (usernameEnUso) {
            throw new CustomBadRequestException("El username ya esta en uso");
        }

        usuario.setUsername(payload.getUsername());
        usuario.setDescripcion(payload.getDescripcion());

        return usuarioRepository.save(usuario);
    }

    private Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("El usuario con id " + id + " no existe"));
    }
}
