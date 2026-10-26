package mx.edu.utez.Proyecto1D.repository.persona;

import mx.edu.utez.Proyecto1D.model.persona.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
}
