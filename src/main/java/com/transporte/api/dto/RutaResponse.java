package com.transporte.api.dto;
import java.time.LocalTime;
public record RutaResponse(Long id,String origen,String destino,LocalTime horario,Integer capacidad){}
