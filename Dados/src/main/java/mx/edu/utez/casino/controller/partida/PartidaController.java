package mx.edu.utez.casino.controller.partida;

import jakarta.validation.Valid;
import mx.edu.utez.casino.controller.partida.dto.RequestIniciarPartidaDTO;
import mx.edu.utez.casino.model.partida.Partida;
import mx.edu.utez.casino.model.tiro.Tiro;
import mx.edu.utez.casino.service.partida.PartidaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/partidas")
public class PartidaController {

    private final PartidaService partidaService;

    public PartidaController(PartidaService partidaService) {
        this.partidaService = partidaService;
    }

    @PostMapping
    public ResponseEntity<Partida> iniciarPartida(@RequestBody @Valid RequestIniciarPartidaDTO payload) {
        return ResponseEntity.status(201).body(
                partidaService.iniciarPartida(payload)
        );
    }

    @PostMapping("/{id}/tiros")
    public ResponseEntity<Tiro> realizarTiro(@PathVariable Long id) {
        return ResponseEntity.status(200).body(
                partidaService.realizarTiro(id)
        );
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Partida> finalizarPartidaManual(@PathVariable Long id) {
        return ResponseEntity.status(200).body(
                partidaService.finalizarPartidaManual(id)
        );
    }

    @GetMapping("/jugador/{jugadorId}")
    public ResponseEntity<List<Partida>> obtenerHistorialPartida(@PathVariable Long jugadorId) {
        return ResponseEntity.status(200).body(
                partidaService.obtenerHistorialPartida(jugadorId)
        );
    }
}
