/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author ferch
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Logro.findByPuntajeMayorAlPromedio",
                query = "SELECT l FROM Logro l WHERE l.puntos > (SELECT AVG(l2.puntos) FROM Logro l2)")
})
public class Logro implements Serializable{
    
    @Id
    private long id;
    
    private String nombre;
    private int puntos;
    
    @ManyToOne
    @JoinColumn(name = "videojuego_id")
    private VideoJuego videojuego;

    public Logro() {
    }

    public Logro(long id, String nombre, int puntos, VideoJuego videojuego) {
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

    public VideoJuego getVideojuego() {
        return videojuego;
    }

    public void setVideojuego(VideoJuego videojuego) {
        this.videojuego = videojuego;
    }

    
}
