package mx.edu.utez.Prestamos_libros.service.libro;

import mx.edu.utez.Prestamos_libros.controller.libro.dto.RequestCreateLibroDTO;
import mx.edu.utez.Prestamos_libros.model.libro.Libro;
import mx.edu.utez.Prestamos_libros.repository.libro.LibroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public List<Libro> getAllLibros() {
        return libroRepository.findAll();
    }

    @Transactional
    public Libro createLibro(RequestCreateLibroDTO payload) {
        Libro libro = new Libro();
        libro.setTitulo(payload.getTitulo().trim());
        libro.setEjemplaresDisponibles(payload.getEjemplaresDisponibles());
        return libroRepository.save(libro);
    }
}
