package com.transporte.api.exception;
import java.time.Instant;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
@RestControllerAdvice
public class ApiExceptionHandler {
 private ResponseEntity<Map<String,Object>> error(HttpStatus status,String message){return ResponseEntity.status(status).body(Map.of("fecha",Instant.now().toString(),"estado",status.value(),"mensaje",message));}
 @ExceptionHandler(RecursoNoEncontradoException.class) public ResponseEntity<Map<String,Object>> noEncontrado(RecursoNoEncontradoException e){return error(HttpStatus.NOT_FOUND,e.getMessage());}
 @ExceptionHandler(ConflictoException.class) public ResponseEntity<Map<String,Object>> conflicto(ConflictoException e){return error(HttpStatus.CONFLICT,e.getMessage());}
 @ExceptionHandler(MethodArgumentNotValidException.class) public ResponseEntity<Map<String,Object>> validacion(MethodArgumentNotValidException e){return error(HttpStatus.BAD_REQUEST,e.getBindingResult().getFieldErrors().stream().map(x->x.getField()+": "+x.getDefaultMessage()).distinct().reduce((a,b)->a+"; "+b).orElse("Datos inválidos"));}
 @ExceptionHandler(DataIntegrityViolationException.class) public ResponseEntity<Map<String,Object>> integridad(DataIntegrityViolationException e){return error(HttpStatus.CONFLICT,"Conflicto de datos: verifique ruta, asiento y relaciones.");}
}
