package com.transporte.api.entity;
import jakarta.persistence.*;
import java.time.LocalTime;
@Entity @Table(name="rutas")
public class Ruta {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(nullable=false,length=100) private String origen;
  @Column(nullable=false,length=100) private String destino;
  @Column(nullable=false) private LocalTime horario;
  @Column(nullable=false) private Integer capacidad;
  public Long getId(){return id;}
  public String getOrigen(){return origen;} public void setOrigen(String v){origen=v;}
  public String getDestino(){return destino;} public void setDestino(String v){destino=v;}
  public LocalTime getHorario(){return horario;} public void setHorario(LocalTime v){horario=v;}
  public Integer getCapacidad(){return capacidad;} public void setCapacidad(Integer v){capacidad=v;}
}
