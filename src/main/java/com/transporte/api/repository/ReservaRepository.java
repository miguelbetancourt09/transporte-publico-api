package com.transporte.api.repository;
import com.transporte.api.entity.Reserva;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ReservaRepository extends JpaRepository<Reserva,Long>{
  List<Reserva> findByRutaId(Long rutaId);
  long countByRutaId(Long rutaId);
  boolean existsByRutaIdAndAsientoAndIdNot(Long rutaId,Integer asiento,Long id);
  boolean existsByRutaIdAndAsiento(Long rutaId,Integer asiento);
}
