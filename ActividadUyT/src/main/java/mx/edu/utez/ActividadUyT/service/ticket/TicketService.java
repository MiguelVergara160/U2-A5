package mx.edu.utez.ActividadUyT.service.ticket;

import jakarta.transaction.Transactional;
import mx.edu.utez.ActividadUyT.controller.ticket.dto.RequestCrearTicketDTO;
import mx.edu.utez.ActividadUyT.controller.ticket.dto.RequestReasignarPrioridadDTO;
import mx.edu.utez.ActividadUyT.exception.customExceptions.CustomBadRequestException;
import mx.edu.utez.ActividadUyT.exception.customExceptions.CustomNotFoundException;
import mx.edu.utez.ActividadUyT.model.ticket.Estado;
import mx.edu.utez.ActividadUyT.model.ticket.Prioridad;
import mx.edu.utez.ActividadUyT.model.ticket.Ticket;
import mx.edu.utez.ActividadUyT.model.usuario.Usuario;
import mx.edu.utez.ActividadUyT.repository.ticket.TicketRepository;
import mx.edu.utez.ActividadUyT.repository.usuario.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UsuarioRepository usuarioRepository;

    public TicketService(TicketRepository ticketRepository, UsuarioRepository usuarioRepository) {
        this.ticketRepository = ticketRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Ticket crearTicket(RequestCrearTicketDTO payload) {
        Usuario usuario = usuarioRepository.findById(payload.getUsuarioId())
                .orElseThrow(() -> new CustomNotFoundException("El usuario no existe"));

        if (!usuario.isActivo()) {
            throw new CustomBadRequestException("El usuario no esta activo");
        }

        Ticket nuevoTicket = new Ticket();
        nuevoTicket.setDescripcion(payload.getDescripcion());
        nuevoTicket.setEstado(Estado.ABIERTO);
        nuevoTicket.setPrioridad(Prioridad.valueOf(payload.getPrioridad()));
        nuevoTicket.setUsuario(usuario);

        return ticketRepository.save(nuevoTicket);
    }

    @Transactional
    public Ticket actualizarEstadoTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("El ticket no existe"));

        switch (ticket.getEstado()) {
            case ABIERTO:
                ticket.setEstado(Estado.EN_PROCESO);
                break;
            case EN_PROCESO:
                ticket.setEstado(Estado.CERRADO);
                break;
            default:
                throw new CustomBadRequestException("El ticket esta cerrado y no se puede modificar");
        }

        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket reasignarPrioridad(Long id, RequestReasignarPrioridadDTO payload) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("El ticket no existe"));

        if (ticket.getEstado() == Estado.CERRADO) {
            throw new CustomBadRequestException("El ticket esta cerrado y no se puede modificar");
        }

        ticket.setPrioridad(Prioridad.valueOf(payload.getPrioridad()));

        return ticketRepository.save(ticket);
    }

    public List<Ticket> obtenerTicketsUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new CustomNotFoundException("El usuario no existe");
        }

        return ticketRepository.findByUsuarioId(usuarioId);
    }
}
