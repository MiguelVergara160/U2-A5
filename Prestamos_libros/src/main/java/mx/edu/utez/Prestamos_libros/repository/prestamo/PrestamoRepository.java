package mx.edu.utez.Prestamos_libros.repository.prestamo;

import mx.edu.utez.Prestamos_libros.model.prestamo.EstadoPrestamo;
import mx.edu.utez.Prestamos_libros.model.prestamo.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    long countByLectorIdAndEstadoIn(Long lectorId, Collection<EstadoPrestamo> estados);

    List<Prestamo> findByLectorIdAndEstadoIn(Long lectorId, Collection<EstadoPrestamo> estados);
}
