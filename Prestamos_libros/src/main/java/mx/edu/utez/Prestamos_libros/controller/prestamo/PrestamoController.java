package mx.edu.utez.Prestamos_libros.controller.prestamo;

import jakarta.validation.Valid;
import mx.edu.utez.Prestamos_libros.controller.prestamo.dto.RequestSolicitarPrestamoDTO;
import mx.edu.utez.Prestamos_libros.model.prestamo.Prestamo;
import mx.edu.utez.Prestamos_libros.service.prestamo.PrestamoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin({"*"})
@RequestMapping("/prestamo")
public class PrestamoController {
    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PostMapping
    public ResponseEntity<Prestamo> solicitarPrestamo(@RequestBody @Valid RequestSolicitarPrestamoDTO payload) {
        return ResponseEntity
                .status(201)
                .body(
                        prestamoService.solicitarPrestamo(payload)
                );
    }

    @PatchMapping("/{id}/devolver")
    public ResponseEntity<Prestamo> devolverLibro(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(
                        prestamoService.devolverLibro(id)
                );
    }

    @PatchMapping("/{id}/renovar")
    public ResponseEntity<Prestamo> renovarPrestamo(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(
                        prestamoService.renovarPrestamo(id)
                );
    }

    @GetMapping("/lector/{lectorId}/activos")
    public ResponseEntity<List<Prestamo>> listarPrestamosActivosLector(@PathVariable Long lectorId) {
        return ResponseEntity
                .status(200)
                .body(
                        prestamoService.listarPrestamosActivosLector(lectorId)
                );
    }
}
