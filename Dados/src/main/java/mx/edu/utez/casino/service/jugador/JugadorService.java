package mx.edu.utez.casino.service.jugador;

import mx.edu.utez.casino.controller.jugador.dto.RequestCreateJugadorDTO;
import mx.edu.utez.casino.controller.jugador.dto.RequestRecargarSaldoDTO;
import mx.edu.utez.casino.exception.customExceptions.CustomNotFoundException;
import mx.edu.utez.casino.model.jugador.Jugador;
import mx.edu.utez.casino.repository.jugador.JugadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JugadorService {

    private final JugadorRepository jugadorRepository;

    public JugadorService(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    public List<Jugador> getAllJugadores() {
        return jugadorRepository.findAll();
    }

    public Jugador createJugador(RequestCreateJugadorDTO payload) {
        Jugador jugador = new Jugador();
        jugador.setNombre(payload.getNombre());
        jugador.setSaldo(payload.getSaldo());
        jugador.setActivo(true);
        return jugadorRepository.save(jugador);
    }

    public Jugador recargarSaldo(Long jugadorId, RequestRecargarSaldoDTO payload) {
        Optional<Jugador> jugadorOpt = jugadorRepository.findById(jugadorId);

        if (!jugadorOpt.isPresent()) {
            throw new CustomNotFoundException("El jugador no existe");
        }

        Jugador jugador = jugadorOpt.get();
        jugador.setSaldo(jugador.getSaldo() + payload.getMonto());
        return jugadorRepository.save(jugador);
    }
}
