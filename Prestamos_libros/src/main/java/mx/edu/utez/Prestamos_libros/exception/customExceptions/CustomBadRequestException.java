package mx.edu.utez.Prestamos_libros.exception.customExceptions;

public class CustomBadRequestException extends RuntimeException {
    public CustomBadRequestException(String mensaje) {
        super(mensaje);
    }
}
