package mx.edu.utez.Prestamos_libros.service.lector;

import mx.edu.utez.Prestamos_libros.controller.lector.dto.RequestCreateLectorDTO;
import mx.edu.utez.Prestamos_libros.exception.customExceptions.CustomNotFoundException;
import mx.edu.utez.Prestamos_libros.model.lector.Lector;
import mx.edu.utez.Prestamos_libros.repository.lector.LectorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LectorService {

    private final LectorRepository lectorRepository;

    public LectorService(LectorRepository lectorRepository) {
        this.lectorRepository = lectorRepository;
    }

    public List<Lector> getAllLectores() {
        return lectorRepository.findAll();
    }

    @Transactional
    public Lector createLector(RequestCreateLectorDTO payload) {
        Lector lector = new Lector();
        lector.setNombre(payload.getNombre().trim());
        lector.setMembresiaPremium(payload.isMembresiaPremium());
        lector.setActivo(true);
        return lectorRepository.save(lector);
    }

    @Transactional
    public Lector rehabilitarLector(Long id) {
        Lector lector = lectorRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("El lector con id " + id + " no existe"));
        lector.setActivo(true);
        return lectorRepository.save(lector);
    }
}
