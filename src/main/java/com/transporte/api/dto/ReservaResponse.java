package com.transporte.api.dto;
import java.time.LocalDate;
public record ReservaResponse(Long id,String pasajero,Integer asiento,LocalDate fechaReserva,Long rutaId){}
