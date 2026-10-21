package mx.edu.utez.casino.exception.customExceptions;

public class CustomBadRequestException extends RuntimeException {
    public CustomBadRequestException(String mensaje) {
        super(mensaje);
    }
}
