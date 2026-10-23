package com.transporte.api.service;
import com.transporte.api.dto.*;
import com.transporte.api.entity.Ruta;
import com.transporte.api.repository.*;
import com.transporte.api.exception.*;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class RutaService {
 private final RutaRepository rutas; private final ReservaRepository reservas;
 public RutaService(RutaRepository rutas,ReservaRepository reservas){this.rutas=rutas;this.reservas=reservas;}
 private Ruta obtenerEntidad(Long id){return rutas.findById(id).orElseThrow(()->new RecursoNoEncontradoException("Ruta no encontrada: "+id));}
 private RutaResponse map(Ruta r){return new RutaResponse(r.getId(),r.getOrigen(),r.getDestino(),r.getHorario(),r.getCapacidad());}
 @Transactional(readOnly=true) public List<RutaResponse> listar(){return rutas.findAll().stream().map(this::map).toList();}
 @Transactional(readOnly=true) public RutaResponse buscar(Long id){return map(obtenerEntidad(id));}
 @Transactional public RutaResponse crear(RutaRequest req){Ruta r=new Ruta(); aplicar(r,req);return map(rutas.save(r));}
 private void aplicar(Ruta r,RutaRequest req){r.setOrigen(req.origen().trim());r.setDestino(req.destino().trim());r.setHorario(req.horario());r.setCapacidad(req.capacidad());}
 @Transactional public RutaResponse actualizar(Long id,RutaRequest req){Ruta r=obtenerEntidad(id);if(reservas.countByRutaId(id)>req.capacidad())throw new ConflictoException("Capacidad inferior al número de reservas existentes"); aplicar(r,req);return map(rutas.save(r));}
 @Transactional public void eliminar(Long id){Ruta r=obtenerEntidad(id);if(reservas.countByRutaId(id)>0)throw new ConflictoException("No puede eliminar una ruta con reservas; elimine primero sus reservas");rutas.delete(r);}
}
