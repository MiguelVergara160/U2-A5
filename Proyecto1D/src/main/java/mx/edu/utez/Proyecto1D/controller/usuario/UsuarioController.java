package mx.edu.utez.Proyecto1D.controller.usuario;

import jakarta.validation.Valid;
import mx.edu.utez.Proyecto1D.controller.usuario.dto.RequestCreateUserDTO;
import mx.edu.utez.Proyecto1D.controller.usuario.dto.RequestUpdateUserDTO;
import mx.edu.utez.Proyecto1D.model.usuario.Usuario;
import mx.edu.utez.Proyecto1D.service.usuario.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin({"*"})
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    //servicios:
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsers() {
        return ResponseEntity
                .status(200)
                .body(
                        usuarioService.getAllUsers()
                );

    }

    @PostMapping
    public ResponseEntity<Usuario> createUser(@RequestBody @Valid RequestCreateUserDTO payload) {
        return ResponseEntity
                .status(201)
                .body(
                        usuarioService.createUser(payload)
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(
                        usuarioService.getUserById(id)
                );
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<Usuario> enableUser(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(
                        usuarioService.enableUser(id)
                );
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Usuario> disableUser(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(
                        usuarioService.disableUser(id)
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUser(
            @PathVariable Long id,
            @RequestBody @Valid RequestUpdateUserDTO payload) {
        return ResponseEntity
                .status(200)
                .body(
                        usuarioService.updateUser(id, payload)
                );
    }

}
