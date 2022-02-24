package br.com.cristal.moviegame.config.handler;


import br.com.cristal.moviegame.config.handler.exceptions.GameInitException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorMessage> handlerBadRequestException(BadCredentialsException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ErrorMessage
                .builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build()
        );
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<ErrorMessage> handlerInvalidToken(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                ErrorMessage
                        .builder()
                        .message(exception.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler({GameInitException.class})
    public ResponseEntity<ErrorMessage> handlerInitGame(GameInitException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                ErrorMessage
                        .builder()
                        .message(exception.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

}
