package mx.edu.utez.Proyecto1D.model.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.Proyecto1D.model.persona.Persona;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(name = "username1", nullable = false, unique = true)
    private String username;

    private String password;

    private boolean isEnable;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private Roles rol;

    //
    @OneToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;
}
