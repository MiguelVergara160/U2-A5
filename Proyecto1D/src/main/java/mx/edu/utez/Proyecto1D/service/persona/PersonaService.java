package mx.edu.utez.Proyecto1D.service.persona;

import jakarta.transaction.Transactional;
import mx.edu.utez.Proyecto1D.controller.persona.dto.RequestUpdatePersonaDTO;
import mx.edu.utez.Proyecto1D.exception.customExceptions.CustomNotFoundException;
import mx.edu.utez.Proyecto1D.model.persona.Persona;
import mx.edu.utez.Proyecto1D.repository.persona.PersonaRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonaService {
    private final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Transactional
    public Persona updatePersona(Long id, RequestUpdatePersonaDTO payload) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("La persona con id " + id + " no existe"));

        persona.setNombres(payload.getNombres());
        persona.setPrimerApellido(payload.getPrimerApellido());
        persona.setSegundoApellido(payload.getSegundoApellido());
        persona.setCorreo(payload.getCorreo());
        persona.setCurp(payload.getCurp());
        persona.setFechaNacimiento(payload.getFechaNacimiento());

        return personaRepository.save(persona);
    }
}
