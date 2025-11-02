package com.mycompany.videojuegojpa;

import DAO.VideoJuegoDAO;
import Entity.Logro;
import Entity.VideoJuego;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author ferch
 */
public class VideoJuegoJPA {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("VideoJuegoJPA"); 
    
    // ID base para los logros (asumiendo que no tienen @GeneratedValue)
    private static final long ID_LOGRO_BASE = 5000L;
   
    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        // Inicializamos el DAO con el EntityManager, como lo requiere tu clase
        VideoJuegoDAO videojuegoDAO = new VideoJuegoDAO(em);

        System.out.println("=================================================");
        System.out.println("         INICIO DE PRUEBAS VIDEOJUEGO DAO         ");
        System.out.println("=================================================");

        // 1. CONFIGURACIÓN: Cargar datos (Orden de persistencia corregido)
        setupData(videojuegoDAO, em);

        // 2. PRUEBAS CRUD BÁSICAS
        pruebaCRUDBasico(videojuegoDAO);

        // 3. PRUEBAS CONSULTAS AVANZADAS (Usando los métodos que debiste añadir al DAO)
        pruebaConsultasAvanzadas(videojuegoDAO);
        
        System.out.println("\n=================================================");
        System.out.println("           FIN DE PRUEBAS VIDEOJUEGO DAO          ");
        System.out.println("=================================================");
        
        em.close();
        emf.close();
    }
    
    private static void setupData(VideoJuegoDAO dao, EntityManager em) {
        System.out.println("\n--- CONFIGURACION DE DATOS DE PRUEBA ---");
        
        // --- 1. CREAR LOGROS (Sin persistir individualmente) ---
        Logro l_legend = new Logro(ID_LOGRO_BASE + 1, "Leyenda Maxima", 500, null); 
        Logro l_epic = new Logro(ID_LOGRO_BASE + 2, "Epic Win", 200, null); 
        Logro l_med = new Logro(ID_LOGRO_BASE + 3, "Punto Medio", 50, null);
        Logro l_epic_2 = new Logro(ID_LOGRO_BASE + 4, "Epic Win II", 200, null);


        // --- 2. CREAR VIDEOJUEGOS ---
        // V1: Mayor Logros (4) y Logro Más Alto (Total Puntos: 750)
        VideoJuego v1 = new VideoJuego(0, 10, "CD Projekt RED", new HashSet<>()); 
        
        // V2: Muchos Puntos (2, Total Puntos: 400)
        VideoJuego v2 = new VideoJuego(0, 8, "Square Enix", new HashSet<>()); 
        
        // V3: Sin Logros (0, 0 puntos) - Cumple requisito de 'sin logros'
        VideoJuego v3 = new VideoJuego(0, 5, "Atari", new HashSet<>()); 

        // -----------------------------------------------------------
        // 3. ESTABLECER LA RELACIÓN BIDIRECCIONAL (Necesario antes de persistir)
        // -----------------------------------------------------------

        // Asignar Logros a V1 (CD Projekt RED)
        l_legend.setVideojuego(v1); 
        l_epic.setVideojuego(v1);
        l_med.setVideojuego(v1);
        
        // Asignar la inversa (para que el CascadeType.ALL funcione)
        v1.getLogros().add(l_legend); 
        v1.getLogros().add(l_epic);
        v1.getLogros().add(l_med);
        
        // Asignar Logros a V2 (Square Enix)
        l_epic_2.setVideojuego(v2);
        v2.getLogros().add(l_epic_2);
        
        // --- 4. PERSISTIR LOS VIDEOJUEGOS ---
        // JPA/Hibernate guardará v1 y, en cascada, guardará los Logros (l_legend, l_epic, etc.)
        dao.agregar(v1); 
        dao.agregar(v2);
        dao.agregar(v3); 
        
        // Ya que los IDs se generaron en la persistencia, no necesitamos actualizar la inversa
        System.out.println("Se insertaron 3 VideoJuegos y 4 Logros de prueba.");
    }
    
    private static void pruebaCRUDBasico(VideoJuegoDAO dao) {
        System.out.println("\n--- PRUEBAS CRUD BASICAS ---");
        
        // 1. Agregar (Se hizo en setup, pero probamos otro)
        VideoJuego nuevoJuego = new VideoJuego(0, 9, "Nintendo", new HashSet<>());
        dao.agregar(nuevoJuego);
        System.out.println("Agregado: " + nuevoJuego.getDesarrolladora());

        // 2. Buscar por ID 
        Long idPrueba = nuevoJuego.getId(); 
        VideoJuego juegoEncontrado = dao.buscarPorId(idPrueba);
        if (juegoEncontrado != null) {
            System.out.println("Buscado: ID " + idPrueba + " (" + juegoEncontrado.getDesarrolladora() + ")");
            
            // 3. Editar
            juegoEncontrado.setDesarrolladora("Nintendo EDITADO");
            dao.editar(juegoEncontrado);
            System.out.println("Editado: Desarrolladora cambiada a Nintendo EDITADO.");
            
            // 4. Eliminar
            dao.eliminar(juegoEncontrado.getId());
            System.out.println("Eliminado: Nintendo EDITADO (ID: " + juegoEncontrado.getId() + ")");
        } else {
             System.out.println("ERROR en CRUD: Fallo la busqueda.");
        }
    }

    private static void pruebaConsultasAvanzadas(VideoJuegoDAO dao) {
        System.out.println("\n=================================================");
        System.out.println("        PRUEBAS CONSULTAS AVANZADAS (JPQL)       ");
        System.out.println("=================================================");

        // REQ 1: Listar los videojuegos con mayor número de logros asociados.
        System.out.println("\n--- CONSULTA A: Mayor Numero de Logros (Debe ser CD Projekt RED) ---");
        List<Object[]> juegosConLogros = dao.listarVideojuegosConMayorNumLogros();
        if (!juegosConLogros.isEmpty()) {
            juegosConLogros.forEach(obj -> {
                VideoJuego v = (VideoJuego) obj[0];
                Long numLogros = (Long) obj[1];
                System.out.println("  - " + v.getDesarrolladora() + ": " + numLogros + " logros.");
            });
        } else {
            System.out.println("  [VACIO] No hay datos o la consulta fallo.");
        }


        // REQ 2: Listar los videojuegos que no tienen ningún logro registrado.
        System.out.println("\n--- CONSULTA B: Videojuegos Sin Logros (Debe mostrar 'Atari') ---");
        // Tu NamedQuery "VideoJuego.buscarSinJugadores" es para este requisito:
        List<VideoJuego> juegosSinLogros = dao.buscarSinJugadores(); 
        if (!juegosSinLogros.isEmpty()) {
            juegosSinLogros.forEach(v -> System.out.println("  - ✔ " + v.getDesarrolladora()));
        } else {
            System.out.println("  [VACIO] La consulta no devolvio resultados.");
        }


        // REQ 3: Listar los videojuegos cuya suma total de puntos de logros supera un valor dado.
        int umbralPuntos = 450; 
        System.out.println("\n--- CONSULTA C: Suma Total de Puntos de Logros > " + umbralPuntos + " ---");
        // Debe mostrar solo CD Projekt RED (750 puntos)
        List<VideoJuego> juegosPuntosMayorA = dao.listarVideojuegosPorSumaPuntosMayorA(umbralPuntos);
        if (!juegosPuntosMayorA.isEmpty()) {
            juegosPuntosMayorA.forEach(v -> System.out.println("  -  " + v.getDesarrolladora()));
        } else {
            System.out.println("  [VACIO] Ningun videojuego supera los " + umbralPuntos + " puntos.");
        }


        // REQ 4: Mostrar el videojuego con el logro más alto en puntos.
        System.out.println("\n--- CONSULTA D: Videojuego con Logro de Mayor Puntaje (Debe ser 'CD Projekt RED') ---");
        VideoJuego juegoMasAlto = dao.obtenerVideojuegoConLogroMasAlto();
        if (juegoMasAlto != null) {
            System.out.println("  - El videojuego es: " + juegoMasAlto.getDesarrolladora());
        } else {
            System.out.println("  [VACIO] No se encontro ningun videojuego con logros.");
        }
    }
    
    
 
   
}
