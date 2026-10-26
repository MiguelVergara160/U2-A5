package mx.edu.utez.Proyecto1D.exception.customExceptions;

public class CustomNotFoundException extends RuntimeException {
    public CustomNotFoundException(String mensaje) {
        super(mensaje);
    }
}
