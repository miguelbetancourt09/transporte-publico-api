package com.transporte.api.entity;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity @Table(name="reservas",uniqueConstraints=@UniqueConstraint(name="uk_ruta_asiento",columnNames={"ruta_id","asiento"}))
public class Reserva {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(nullable=false,length=120) private String pasajero;
  @Column(nullable=false) private Integer asiento;
  @Column(nullable=false,name="fecha_reserva") private LocalDate fechaReserva;
  @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="ruta_id",nullable=false) private Ruta ruta;
  public Long getId(){return id;}
  public String getPasajero(){return pasajero;} public void setPasajero(String v){pasajero=v;}
  public Integer getAsiento(){return asiento;} public void setAsiento(Integer v){asiento=v;}
  public LocalDate getFechaReserva(){return fechaReserva;} public void setFechaReserva(LocalDate v){fechaReserva=v;}
  public Ruta getRuta(){return ruta;} public void setRuta(Ruta v){ruta=v;}
}
