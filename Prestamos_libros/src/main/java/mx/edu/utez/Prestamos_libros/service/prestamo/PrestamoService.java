package mx.edu.utez.Prestamos_libros.service.prestamo;

import mx.edu.utez.Prestamos_libros.controller.prestamo.dto.RequestSolicitarPrestamoDTO;
import mx.edu.utez.Prestamos_libros.exception.customExceptions.CustomBadRequestException;
import mx.edu.utez.Prestamos_libros.exception.customExceptions.CustomNotFoundException;
import mx.edu.utez.Prestamos_libros.model.lector.Lector;
import mx.edu.utez.Prestamos_libros.model.libro.Libro;
import mx.edu.utez.Prestamos_libros.model.prestamo.EstadoPrestamo;
import mx.edu.utez.Prestamos_libros.model.prestamo.Prestamo;
import mx.edu.utez.Prestamos_libros.repository.lector.LectorRepository;
import mx.edu.utez.Prestamos_libros.repository.libro.LibroRepository;
import mx.edu.utez.Prestamos_libros.repository.prestamo.PrestamoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrestamoService {

    private static final int DIAS_PRESTAMO = 14;
    private static final int DIAS_RENOVACION = 7;
    private static final int LIMITE_PRESTAMOS = 3;
    private static final int LIMITE_PRESTAMOS_PREMIUM = 5;
    private static final List<EstadoPrestamo> ESTADOS_ACTIVOS =
            List.of(EstadoPrestamo.PRESTADO, EstadoPrestamo.VENCIDO);

    private final PrestamoRepository prestamoRepository;
    private final LectorRepository lectorRepository;
    private final LibroRepository libroRepository;

    public PrestamoService(PrestamoRepository prestamoRepository,
                           LectorRepository lectorRepository,
                           LibroRepository libroRepository) {
        this.prestamoRepository = prestamoRepository;
        this.lectorRepository = lectorRepository;
        this.libroRepository = libroRepository;
    }

    @Transactional
    public Prestamo solicitarPrestamo(RequestSolicitarPrestamoDTO payload) {
        Lector lector = buscarLector(payload.getLectorId());
        if (!lector.isActivo()) {
            throw new CustomBadRequestException("El lector está bloqueado y no puede solicitar préstamos");
        }

        Libro libro = libroRepository.findById(payload.getLibroId())
                .orElseThrow(() -> new CustomNotFoundException(
                        "El libro con id " + payload.getLibroId() + " no existe"));
        if (libro.getEjemplaresDisponibles() < 1) {
            throw new CustomBadRequestException("No hay ejemplares disponibles del libro '" + libro.getTitulo() + "'");
        }

        int limite = lector.isMembresiaPremium() ? LIMITE_PRESTAMOS_PREMIUM : LIMITE_PRESTAMOS;
        long activos = prestamoRepository.countByLectorIdAndEstadoIn(lector.getId(), ESTADOS_ACTIVOS);
        if (activos >= limite) {
            throw new CustomBadRequestException("El lector ya alcanzó el límite de " + limite
                    + " préstamos activos" + (lector.isMembresiaPremium() ? " (membresía premium)" : ""));
        }

        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() - 1);
        libroRepository.save(libro);

        LocalDate hoy = LocalDate.now();
        Prestamo prestamo = new Prestamo();
        prestamo.setLector(lector);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(hoy);
        prestamo.setFechaDevolucionMaxima(hoy.plusDays(DIAS_PRESTAMO));
        prestamo.setEstado(EstadoPrestamo.PRESTADO);

        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public Prestamo devolverLibro(Long prestamoId) {
        Prestamo prestamo = buscarPrestamo(prestamoId);

        if (prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new CustomBadRequestException("El préstamo con id " + prestamoId + " ya fue devuelto");
        }

        boolean entregaTardia = LocalDate.now().isAfter(prestamo.getFechaDevolucionMaxima());

        prestamo.setEstado(EstadoPrestamo.DEVUELTO);

        Libro libro = prestamo.getLibro();
        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() + 1);
        libroRepository.save(libro);

        if (entregaTardia) {
            Lector lector = prestamo.getLector();
            lector.setActivo(false);
            lectorRepository.save(lector);
        }

        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public Prestamo renovarPrestamo(Long prestamoId) {
        Prestamo prestamo = buscarPrestamo(prestamoId);

        if (prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new CustomBadRequestException("El préstamo con id " + prestamoId
                    + " ya fue devuelto y no se puede renovar");
        }

        actualizarSiVencido(prestamo);
        if (prestamo.getEstado() == EstadoPrestamo.VENCIDO) {
            throw new CustomBadRequestException("El préstamo con id " + prestamoId
                    + " está vencido y no se puede renovar");
        }

        prestamo.setFechaDevolucionMaxima(prestamo.getFechaDevolucionMaxima().plusDays(DIAS_RENOVACION));

        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public List<Prestamo> listarPrestamosActivosLector(Long lectorId) {
        Lector lector = buscarLector(lectorId);

        List<Prestamo> prestamos = prestamoRepository.findByLectorIdAndEstadoIn(lector.getId(), ESTADOS_ACTIVOS);

        for (Prestamo prestamo : prestamos) {
            actualizarSiVencido(prestamo);
        }
        return prestamoRepository.saveAll(prestamos);
    }

    private Prestamo buscarPrestamo(Long id) {
        return prestamoRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("El préstamo con id " + id + " no existe"));
    }

    private Lector buscarLector(Long id) {
        return lectorRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("El lector con id " + id + " no existe"));
    }

    private void actualizarSiVencido(Prestamo prestamo) {
        if (prestamo.getEstado() == EstadoPrestamo.PRESTADO
                && LocalDate.now().isAfter(prestamo.getFechaDevolucionMaxima())) {
            prestamo.setEstado(EstadoPrestamo.VENCIDO);
        }
    }
}
