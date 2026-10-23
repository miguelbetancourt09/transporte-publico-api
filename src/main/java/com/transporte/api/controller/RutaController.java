package com.transporte.api.controller;
import com.transporte.api.dto.*;
import com.transporte.api.service.*;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/rutas")
public class RutaController {
 private final RutaService rutas;private final ReservaService reservas;
 public RutaController(RutaService rutas,ReservaService reservas){this.rutas=rutas;this.reservas=reservas;}
 @GetMapping public List<RutaResponse> listar(){return rutas.listar();}
 @GetMapping("/{id}") public RutaResponse buscar(@PathVariable Long id){return rutas.buscar(id);}
 @GetMapping("/{id}/reservas") public List<ReservaResponse> reservas(@PathVariable Long id){return reservas.porRuta(id);}
 @PostMapping public ResponseEntity<RutaResponse> crear(@Valid @RequestBody RutaRequest r){return ResponseEntity.status(HttpStatus.CREATED).body(rutas.crear(r));}
 @PutMapping("/{id}") public RutaResponse actualizar(@PathVariable Long id,@Valid @RequestBody RutaRequest r){return rutas.actualizar(id,r);}
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void eliminar(@PathVariable Long id){rutas.eliminar(id);}
}
