package mx.edu.utez.Prestamos_libros.controller.libro;

import jakarta.validation.Valid;
import mx.edu.utez.Prestamos_libros.controller.libro.dto.RequestCreateLibroDTO;
import mx.edu.utez.Prestamos_libros.model.libro.Libro;
import mx.edu.utez.Prestamos_libros.service.libro.LibroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin({"*"})
@RequestMapping("/libro")
public class LibroController {
    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public ResponseEntity<List<Libro>> getAllLibros() {
        return ResponseEntity
                .status(200)
                .body(
                        libroService.getAllLibros()
                );
    }

    @PostMapping
    public ResponseEntity<Libro> createLibro(@RequestBody @Valid RequestCreateLibroDTO payload) {
        return ResponseEntity
                .status(201)
                .body(
                        libroService.createLibro(payload)
                );
    }
}
