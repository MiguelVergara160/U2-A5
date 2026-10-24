package mx.edu.utez.ActividadUyT.controller.usuario;

import mx.edu.utez.ActividadUyT.model.ticket.Ticket;
import mx.edu.utez.ActividadUyT.model.usuario.Usuario;
import mx.edu.utez.ActividadUyT.service.ticket.TicketService;
import mx.edu.utez.ActividadUyT.service.usuario.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin({"*"})
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final TicketService ticketService;

    public UsuarioController(UsuarioService usuarioService, TicketService ticketService) {
        this.usuarioService = usuarioService;
        this.ticketService = ticketService;
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Usuario> desactivarUsuario(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(
                        usuarioService.desactivarUsuario(id)
                );
    }

    @GetMapping("/{id}/tickets")
    public ResponseEntity<List<Ticket>> obtenerTicketsUsuario(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(
                        ticketService.obtenerTicketsUsuario(id)
                );
    }
}
