package com.transporte.api.dto;
import jakarta.validation.constraints.*;
import java.time.LocalTime;
public record RutaRequest(@NotBlank String origen,@NotBlank String destino,@NotNull LocalTime horario,@NotNull @Min(1) Integer capacidad){}
