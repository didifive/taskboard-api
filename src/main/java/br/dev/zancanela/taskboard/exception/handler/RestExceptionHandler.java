package br.dev.zancanela.taskboard.exception.handler;

import br.dev.zancanela.taskboard.controller.dto.response.ApiErrorDto;
import br.dev.zancanela.taskboard.exception.BadRequestException;
import br.dev.zancanela.taskboard.exception.DataIntegrityViolationException;
import br.dev.zancanela.taskboard.exception.EntityNotFoundException;
import br.dev.zancanela.taskboard.exception.TaskboardApiException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler( { TaskboardApiException.class } )
    public ResponseEntity<ApiErrorDto> handleTaskboardApiException(TaskboardApiException e, HttpServletRequest request){
        ApiErrorDto err = new ApiErrorDto(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro no sistema :(",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(err.status()).body(err);
    }

    @ExceptionHandler( { EntityNotFoundException.class } )
    public ResponseEntity<ApiErrorDto> handleEntityNotFoundException(EntityNotFoundException e, HttpServletRequest request){
        ApiErrorDto err = new ApiErrorDto(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                "Objeto não encontrado",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(err.status()).body(err);
    }

    @ExceptionHandler( { DataIntegrityViolationException.class } )
    public ResponseEntity<ApiErrorDto> handleDataIntegrityViolationException(DataIntegrityViolationException e, HttpServletRequest request){
        ApiErrorDto err = new ApiErrorDto(
                Instant.now(),
                HttpStatus.CONFLICT.value(),
                "Operação não permitida",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(err.status()).body(err);
    }

    @ExceptionHandler( { BadRequestException.class } )
    public ResponseEntity<ApiErrorDto> handleBadRequestException(BadRequestException e, HttpServletRequest request){
        ApiErrorDto err = new ApiErrorDto(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Requisição inválida",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(err.status()).body(err);
    }

}