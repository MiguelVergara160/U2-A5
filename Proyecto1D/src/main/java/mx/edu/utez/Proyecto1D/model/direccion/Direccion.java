package mx.edu.utez.Proyecto1D.model.direccion;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.Proyecto1D.model.persona.Persona;

@Entity
@Table(name = "direcciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String calle;
    private int noExterior;
    private String colonia;
    private String municipio;
    private String estado;
    private String codigoPostal;

    @ManyToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;
}
