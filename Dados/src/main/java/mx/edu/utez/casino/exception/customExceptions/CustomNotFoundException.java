package mx.edu.utez.casino.exception.customExceptions;

public class CustomNotFoundException extends RuntimeException {
    public CustomNotFoundException(String mensaje) {
        super(mensaje);
    }
}
