
package com.mycompany.videojuegojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 *
 * @author ferch
 */
public class VideoJuegoJPA {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("VideoJuegoPU");
        EntityManager em = emf.createEntityManager();
    }
}
