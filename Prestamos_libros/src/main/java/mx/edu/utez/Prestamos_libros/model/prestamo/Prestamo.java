package mx.edu.utez.Prestamos_libros.model.prestamo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.Prestamos_libros.model.lector.Lector;
import mx.edu.utez.Prestamos_libros.model.libro.Libro;

import java.time.LocalDate;

@Entity
@Table(name = "prestamos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lector_id", nullable = false)
    private Lector lector;

    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;

    private LocalDate fechaPrestamo;

    private LocalDate fechaDevolucionMaxima;

    @Enumerated(EnumType.STRING)
    private EstadoPrestamo estado;
}
