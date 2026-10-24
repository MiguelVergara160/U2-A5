package mx.edu.utez.ActividadUyT.controller.ticket;

import jakarta.validation.Valid;
import mx.edu.utez.ActividadUyT.controller.ticket.dto.RequestCrearTicketDTO;
import mx.edu.utez.ActividadUyT.controller.ticket.dto.RequestReasignarPrioridadDTO;
import mx.edu.utez.ActividadUyT.model.ticket.Ticket;
import mx.edu.utez.ActividadUyT.service.ticket.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin({"*"})
@RequestMapping("/ticket")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<Ticket> crearTicket(@RequestBody @Valid RequestCrearTicketDTO payload) {
        return ResponseEntity
                .status(201)
                .body(
                        ticketService.crearTicket(payload)
                );
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Ticket> actualizarEstadoTicket(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(
                        ticketService.actualizarEstadoTicket(id)
                );
    }

    @PatchMapping("/{id}/prioridad")
    public ResponseEntity<Ticket> reasignarPrioridad(
            @PathVariable Long id,
            @RequestBody @Valid RequestReasignarPrioridadDTO payload) {
        return ResponseEntity
                .status(200)
                .body(
                        ticketService.reasignarPrioridad(id, payload)
                );
    }
}
