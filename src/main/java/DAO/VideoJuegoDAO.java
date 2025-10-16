
package DAO;

import Entity.VideoJuego;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class VideoJuegoDAO {
    private static EntityManager em;

 public VideoJuegoDAO(EntityManager em) {
        this.em = em;
    }
    public boolean agregar(VideoJuego videojuego) {
        try {
            em.getTransaction().begin();
            em.persist(videojuego);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al agregar videojuego: " + e.getMessage());
            return false;
        }
    }

    public boolean editar(VideoJuego videojuego) {
        try {
            em.getTransaction().begin();
            em.merge(videojuego);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al editar videojuego: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(Long id) {
        try {
            em.getTransaction().begin();
            VideoJuego vj = em.find(VideoJuego.class, id);
            if (vj != null) {
                em.remove(vj);
                em.getTransaction().commit();
                return true;
            } else {
                System.out.println("No se encontro videojuego con ID: " + id);
                em.getTransaction().rollback();
                return false;
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al eliminar videojuego: " + e.getMessage());
            return false;
        }
    }

    public VideoJuego buscarPorId(Long id) {
        try {
            return em.find(VideoJuego.class, id);
        } catch (Exception e) {
            System.err.println("Error al buscar videojuego: " + e.getMessage());
            return null;
        }
    }

    public List<VideoJuego> obtenerTodos() {
        try {
            return em.createNamedQuery("VideoJuego.obtenerTodos", VideoJuego.class).getResultList();
        } catch (Exception e) {
            System.err.println("Error en obtenerTodos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<VideoJuego> buscarPorNombre(String nombre) {
        try {
            return em.createNamedQuery("VideoJuego.buscarPorNombre", VideoJuego.class)
                    .setParameter("nombre", "%" + nombre + "%")
                    .getResultList();
        } catch (Exception e) {
            System.err.println("Error en buscarPorNombre: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<VideoJuego> buscarPorDesarrolladora(String desarrolladora) {
        try {
            return em.createNamedQuery("VideoJuego.buscarPorDesarrolladora", VideoJuego.class)
                    .setParameter("desarrolladora", "%" + desarrolladora + "%")
                    .getResultList();
        } catch (Exception e) {
            System.err.println("Error en buscarPorDesarrolladora: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<VideoJuego> filtrarPorPuntajeMayorA(int puntajeMinimo) {
        try {
            return em.createNamedQuery("VideoJuego.filtrarPorPuntajeMayorA", VideoJuego.class)
                    .setParameter("puntajeMinimo", puntajeMinimo)
                    .getResultList();
        } catch (Exception e) {
            System.err.println("Error en filtrarPorPuntajeMayorA: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<VideoJuego> ordenarPorNombre() {
        try {
            return em.createNamedQuery("VideoJuego.ordenarPorNombre", VideoJuego.class).getResultList();
        } catch (Exception e) {
            System.err.println("Error en ordenarPorNombre: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<VideoJuego> ordenarPorPuntajeDesc() {
        try {
            return em.createNamedQuery("VideoJuego.ordenarPorPuntajeDesc", VideoJuego.class).getResultList();
        } catch (Exception e) {
            System.err.println("Error en ordenarPorPuntajeDesc: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Object[]> contarVideojuegosPorDesarrolladora() {
        try {
            return em.createNamedQuery("VideoJuego.contarVideojuegosPorDesarrolladora", Object[].class).getResultList();
        } catch (Exception e) {
            System.err.println("Error en contarVideojuegosPorDesarrolladora: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<VideoJuego> buscarSinJugadores() {
        try {
            return em.createNamedQuery("VideoJuego.buscarSinJugadores", VideoJuego.class).getResultList();
        } catch (Exception e) {
            System.err.println("Error en buscarSinJugadores: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<VideoJuego> buscarConLogrosMayorA(int puntosMinimos) {
        try {
            return em.createNamedQuery("VideoJuego.buscarConLogrosMayorA", VideoJuego.class)
                    .setParameter("puntosMinimos", puntosMinimos)
                    .getResultList();
        } catch (Exception e) {
            System.err.println("Error en buscarConLogrosMayorA: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public int actualizarPuntajePorNombre(String nombre, int nuevoPuntaje) {
        try {
            em.getTransaction().begin();
            int actualizados = em.createNamedQuery("VideoJuego.actualizarPuntajePorNombre")
                    .setParameter("nombre", nombre)
                    .setParameter("nuevoPuntaje", nuevoPuntaje)
                    .executeUpdate();
            em.getTransaction().commit();
            return actualizados;
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error en actualizarPuntajePorNombre: " + e.getMessage());
            return 0;
        }
    }

    public int eliminarPorNombre(String nombre) {
        try {
            em.getTransaction().begin();
            int eliminados = em.createNamedQuery("VideoJuego.eliminarPorNombre")
                    .setParameter("nombre", nombre)
                    .executeUpdate();
            em.getTransaction().commit();
            return eliminados;
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error en eliminarPorNombre: " + e.getMessage());
            return 0;
        }
    }
    
}
