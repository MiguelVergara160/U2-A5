package mx.edu.utez.Prestamos_libros.controller.lector;

import jakarta.validation.Valid;
import mx.edu.utez.Prestamos_libros.controller.lector.dto.RequestCreateLectorDTO;
import mx.edu.utez.Prestamos_libros.model.lector.Lector;
import mx.edu.utez.Prestamos_libros.service.lector.LectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin({"*"})
@RequestMapping("/lector")
public class LectorController {
    private final LectorService lectorService;

    public LectorController(LectorService lectorService) {
        this.lectorService = lectorService;
    }

    @GetMapping
    public ResponseEntity<List<Lector>> getAllLectores() {
        return ResponseEntity
                .status(200)
                .body(
                        lectorService.getAllLectores()
                );
    }

    @PostMapping
    public ResponseEntity<Lector> createLector(@RequestBody @Valid RequestCreateLectorDTO payload) {
        return ResponseEntity
                .status(201)
                .body(
                        lectorService.createLector(payload)
                );
    }

    @PatchMapping("/{id}/rehabilitar")
    public ResponseEntity<Lector> rehabilitarLector(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(
                        lectorService.rehabilitarLector(id)
                );
    }
}
