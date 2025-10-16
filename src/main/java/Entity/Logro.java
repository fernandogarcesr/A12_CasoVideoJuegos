/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author ferch
 */
@Entity
public class Logro implements Serializable{
    
    @Id
    private long id;
    
    private String nombre;
    private int puntos;
    
    @ManyToOne
    @JoinColumn(name = "videojuego_id")
    private Set<VideoJuego> videojuego;

    public Logro() {
    }

    public Logro(long id, String nombre, int puntos, Set<VideoJuego> videojuego) {
        this.id = id;
        this.nombre = nombre;
        this.puntos = puntos;
        this.videojuego = videojuego;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public Set<VideoJuego> getVideojuego() {
        return videojuego;
    }

    public void setVideojuego(Set<VideoJuego> videojuego) {
        this.videojuego = videojuego;
    }
    
    
}
