package cassino.com.cassino_online.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice 
public class GlobalExceptionHandler {

    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST); // Retorna 400 Bad Request
    }

    // Lida com IllegalArgumentException (usadas para "Email already exists", "User not found", "Insufficient balance", etc.)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        
        if (ex.getMessage().contains("not found")) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND); // Retorna 404 Not Found
        } else if (ex.getMessage().contains("already exists")) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT); // Retorna 409 Conflict
        } else if (ex.getMessage().contains("Insufficient balance") || ex.getMessage().contains("amount must be positive")) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST); // Retorna 400 Bad Request
        }
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST); // Padrão para outros casos
    }

    // Lida com outras exceções genéricas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // Retorna 500 Internal Server Error
    }
}