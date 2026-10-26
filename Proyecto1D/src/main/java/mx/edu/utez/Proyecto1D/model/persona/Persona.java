package mx.edu.utez.Proyecto1D.model.persona;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.Proyecto1D.model.cursos.Curso;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "personas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombres;
    private String primerApellido;
    private String segundoApellido;

    private LocalDate fechaNacimiento;

    private String curp;
    private String correo;

/*
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "persona_id"),
            inverseJoinColumns = @JoinColumn(name = "curso_id"),
            name = "personas_cursos"

    )
    private List<Curso> cursos;*/
}
