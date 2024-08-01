/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group9.musicplayer.Model;

import com.group9.musicplayer.Model.*;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author psalm
 */
public class SongRepository {
    private EntityManagerFactory emf = null;
    private EntityManager em = null;

    public SongRepository() {
       emf = Persistence.createEntityManagerFactory("employees-db");
        em = emf.createEntityManager(); // Name from persistence.xml
    }

    public void persistEmployee(Song e) {
        em.getTransaction().begin(); // Start transaction
        em.persist(e);
        em.getTransaction().commit(); // Commit changes
    }

    public void mergeEmployee(Song e) {
        em.getTransaction().begin(); // Start transaction
        em.merge(e);
        em.getTransaction().commit(); // Commit changes
    }

    public void removeEmployee(Song e) {
        em.getTransaction().begin(); // Start transaction
        em.remove(e);
        em.getTransaction().commit(); // Commit changes
    }

    public Song find(int id) {
        return em.find(Song.class, id);
    }

    public List<Song> list() {
        return em.createQuery("FROM model.Employee m", Song.class).getResultList();
    }

    public void close() {
        emf.close();
        em.close();
    }

}
