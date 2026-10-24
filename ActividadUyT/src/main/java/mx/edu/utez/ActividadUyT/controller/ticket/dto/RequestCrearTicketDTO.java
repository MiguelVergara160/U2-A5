package mx.edu.utez.ActividadUyT.controller.ticket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestCrearTicketDTO {
    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;

    @NotBlank(message = "La prioridad es obligatoria")
    @Pattern(
            regexp = "^(BAJA|MEDIA|ALTA)$",
            message = "La prioridad debe ser BAJA, MEDIA o ALTA"
    )
    private String prioridad;

    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;
}
