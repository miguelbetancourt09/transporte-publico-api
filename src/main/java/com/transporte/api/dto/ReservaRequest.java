package com.transporte.api.dto;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
public record ReservaRequest(@NotBlank String pasajero,@NotNull @Min(1) Integer asiento,@NotNull @PastOrPresent LocalDate fechaReserva,@NotNull Long rutaId){}
