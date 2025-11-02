
package Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import java.time.LocalDate;
import java.time.Period;
import java.util.Set;

@Entity
@NamedQueries({
  
    @NamedQuery(name = "Jugador.findByMaxVideojuegos",
                query = "SELECT j, SIZE(j.videoJuegos) AS numJuegos FROM Jugador j ORDER BY numJuegos DESC"),
    
 
    @NamedQuery(name = "Jugador.findBySumaTotalPuntosMayorA",
                query = "SELECT j FROM Jugador j JOIN j.videoJuegos v JOIN v.logros l GROUP BY j HAVING SUM(l.puntos) > :valorDado"),
 
    @NamedQuery(name = "Jugador.findByColoniaYNumVideojuegos",
                query = "SELECT j FROM Jugador j WHERE j.direccion.colonia = :colonia AND SIZE(j.videoJuegos) > 1"),
     @NamedQuery(name = "Jugador.orderByEdadDesc",
                query = "SELECT j FROM Jugador j ORDER BY j.fechaNacimiento ASC"), // Ordenar por fechaNacimiento ASC es equivalente a ordenar por Edad DESC
    
    @NamedQuery(name = "Jugador.listCalleColoniaOrderByColonia",
                query = "SELECT j, j.direccion.calle, j.direccion.colonia FROM Jugador j ORDER BY j.direccion.colonia ASC, j.seudonimo ASC")
})
public class Jugador {
     private static final long serialVersionID = 1;
    @Id
    @GeneratedValue
    private long id;
    
    private String seudonimo;
    private String sexo;
    private LocalDate fechaNacimiento;
   
    @ManyToMany
    @JoinTable(
            name = "jugador_videojuego_map", // Nombre de la tabla intermedia que se creará
            joinColumns = @JoinColumn(name = "jugador_id"), // Columna que referencia a Jugador
            inverseJoinColumns = @JoinColumn(name = "videojuego_id") // Columna que referencia a VideoJuego
    )
    private Set<VideoJuego> videoJuegos;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "direccion_id", referencedColumnName = "id")
    private Direccion direccion;
    
    @Transient // Indica a JPA que ignore este campo en la tabla
    public int getEdad() {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    public Jugador(long id, String seudonimo, String sexo, LocalDate fechaNacimiento, Set<VideoJuego> videoJuegos, Direccion direccion) {
        this.id = id;
        this.seudonimo = seudonimo;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.videoJuegos = videoJuegos;
        this.direccion = direccion;
    }

    public Jugador() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSeudonimo() {
        return seudonimo;
    }

    public void setSeudonimo(String seudonimo) {
        this.seudonimo = seudonimo;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Set<VideoJuego> getVideoJuegos() {
        return videoJuegos;
    }

    public void setVideoJuegos(Set<VideoJuego> videoJuegos) {
        this.videoJuegos = videoJuegos;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }
    
    
}


