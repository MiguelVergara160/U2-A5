package mx.edu.utez.Prestamos_libros.controller.libro.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestCreateLibroDTO {
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 150, message = "El título no puede tener más de 150 caracteres")
    private String titulo;

    @NotNull(message = "Los ejemplares disponibles son obligatorios")
    @Min(value = 0, message = "Los ejemplares disponibles no pueden ser negativos")
    private Integer ejemplaresDisponibles;
}
