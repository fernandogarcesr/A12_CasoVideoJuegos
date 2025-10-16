/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author ferch
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "VideoJuego.obtenerTodos", query = "SELECT v FROM VideoJuego v"),
    @NamedQuery(name = "VideoJuego.buscarPorNombre", query = "SELECT v FROM VideoJuego v WHERE v.desarrolladora LIKE :nombre"),
    @NamedQuery(name = "VideoJuego.buscarPorDesarrolladora", query = "SELECT v FROM VideoJuego v WHERE v.desarrolladora LIKE :desarrolladora"),
    @NamedQuery(name = "VideoJuego.filtrarPorPuntajeMayorA", query = "SELECT v FROM VideoJuego v WHERE v.puntaje > :puntajeMinimo"),
    @NamedQuery(name = "VideoJuego.ordenarPorNombre", query = "SELECT v FROM VideoJuego v ORDER BY v.desarrolladora ASC"),
    @NamedQuery(name = "VideoJuego.ordenarPorPuntajeDesc", query = "SELECT v FROM VideoJuego v ORDER BY v.puntaje DESC"),
    @NamedQuery(name = "VideoJuego.contarVideojuegosPorDesarrolladora", query = "SELECT v.desarrolladora, COUNT(v) FROM VideoJuego v GROUP BY v.desarrolladora"),
    @NamedQuery(name = "VideoJuego.buscarSinJugadores", query = "SELECT v FROM VideoJuego v WHERE v.logros IS EMPTY"),
    @NamedQuery(name = "VideoJuego.buscarConLogrosMayorA", query = "SELECT DISTINCT v FROM VideoJuego v JOIN v.logros l WHERE l.puntos > :puntosMinimos"),
    @NamedQuery(name = "VideoJuego.actualizarPuntajePorNombre", query = "UPDATE VideoJuego v SET v.puntaje = :nuevoPuntaje WHERE v.desarrolladora = :nombre"),
    @NamedQuery(name = "VideoJuego.eliminarPorNombre", query = "DELETE FROM VideoJuego v WHERE v.desarrolladora = :nombre")
})
public class VideoJuego implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int puntaje;
    private String desarrolladora;
    
    @OneToMany(mappedBy = "videojuego",cascade = CascadeType.ALL)
    private Set<Logro> logros;

    public VideoJuego() {
    }

    public VideoJuego(long id, int puntaje, String desarrolladora, Set<Logro> logros) {
        this.id = id;
        this.puntaje = puntaje;
        this.desarrolladora = desarrolladora;
        this.logros = logros;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public String getDesarrolladora() {
        return desarrolladora;
    }

    public void setDesarrolladora(String desarrolladora) {
        this.desarrolladora = desarrolladora;
    }

    public Set<Logro> getLogros() {
        return logros;
    }

    public void setLogros(Set<Logro> logros) {
        this.logros = logros;
    }
    
    
}
