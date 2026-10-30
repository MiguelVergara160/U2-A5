package mx.edu.utez.Prestamos_libros.exception.customExceptions;

public class CustomNotFoundException extends RuntimeException {
    public CustomNotFoundException(String mensaje) {
        super(mensaje);
    }
}
