package mx.edu.utez.Proyecto1D.controller.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateUserDTO {
    @NotBlank(message = "El username es obligatorio")
    @Size(min = 6, max = 20, message = "El username debe contener entre 6 y 20 carcateres")
    private String username;

    private String descripcion;
}
