package com.transporte.api.service;
import com.transporte.api.dto.*;
import com.transporte.api.entity.*;
import com.transporte.api.repository.*;
import com.transporte.api.exception.*;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class ReservaService {
 private final ReservaRepository reservas;private final RutaRepository rutas;
 public ReservaService(ReservaRepository reservas,RutaRepository rutas){this.reservas=reservas;this.rutas=rutas;}
 private Reserva entidad(Long id){return reservas.findById(id).orElseThrow(()->new RecursoNoEncontradoException("Reserva no encontrada: "+id));}
 private ReservaResponse map(Reserva r){return new ReservaResponse(r.getId(),r.getPasajero(),r.getAsiento(),r.getFechaReserva(),r.getRuta().getId());}
 @Transactional(readOnly=true) public List<ReservaResponse> listar(){return reservas.findAll().stream().map(this::map).toList();}
 @Transactional(readOnly=true) public ReservaResponse buscar(Long id){return map(entidad(id));}
 @Transactional(readOnly=true) public List<ReservaResponse> porRuta(Long id){if(!rutas.existsById(id))throw new RecursoNoEncontradoException("Ruta no encontrada: "+id);return reservas.findByRutaId(id).stream().map(this::map).toList();}
 private void aplicar(Reserva r,ReservaRequest req,boolean nuevo){
   Ruta ruta=rutas.findById(req.rutaId()).orElseThrow(()->new RecursoNoEncontradoException("Ruta no encontrada: "+req.rutaId()));
   if(req.asiento()>ruta.getCapacidad())throw new ConflictoException("Asiento fuera de capacidad de la ruta");
   boolean ocupado=nuevo?reservas.existsByRutaIdAndAsiento(req.rutaId(),req.asiento()):reservas.existsByRutaIdAndAsientoAndIdNot(req.rutaId(),req.asiento(),r.getId());
   if(ocupado)throw new ConflictoException("Asiento ya reservado en esta ruta");
   if(nuevo || !r.getRuta().getId().equals(req.rutaId())){
     if(reservas.countByRutaId(req.rutaId())>=ruta.getCapacidad())throw new ConflictoException("La ruta no tiene cupos disponibles");
   }
   r.setRuta(ruta);r.setPasajero(req.pasajero().trim());r.setAsiento(req.asiento());r.setFechaReserva(req.fechaReserva());
 }
 @Transactional public ReservaResponse crear(ReservaRequest req){Reserva r=new Reserva();aplicar(r,req,true);return map(reservas.saveAndFlush(r));}
 @Transactional public ReservaResponse actualizar(Long id,ReservaRequest req){Reserva r=entidad(id);aplicar(r,req,false);return map(reservas.saveAndFlush(r));}
 @Transactional public void eliminar(Long id){reservas.delete(entidad(id));}
}
