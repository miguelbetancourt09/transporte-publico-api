package com.transporte.api.controller;
import com.transporte.api.dto.*;
import com.transporte.api.service.ReservaService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/reservas")
public class ReservaController {
 private final ReservaService servicio;
 public ReservaController(ReservaService servicio){this.servicio=servicio;}
 @GetMapping public List<ReservaResponse> listar(){return servicio.listar();}
 @GetMapping("/{id}") public ReservaResponse buscar(@PathVariable Long id){return servicio.buscar(id);}
 @PostMapping public ResponseEntity<ReservaResponse> crear(@Valid @RequestBody ReservaRequest r){return ResponseEntity.status(HttpStatus.CREATED).body(servicio.crear(r));}
 @PutMapping("/{id}") public ReservaResponse actualizar(@PathVariable Long id,@Valid @RequestBody ReservaRequest r){return servicio.actualizar(id,r);}
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void eliminar(@PathVariable Long id){servicio.eliminar(id);}
}
