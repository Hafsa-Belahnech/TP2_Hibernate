package com.example.service;

import com.example.model.Salle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

//Cette classe SalleService étend la classe abstraite, puis ajoute des méthodes spécifiques
public class SalleService extends AbstractCrudService<Salle, Long> {

    public SalleService(EntityManagerFactory emf) {
        super(emf);
    }

    public List<Salle> findByDisponible(boolean disponible) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Salle> query = em.createQuery(
                    "SELECT s FROM Salle s WHERE s.disponible = :disponible", Salle.class);
            query.setParameter("disponible", disponible);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Salle> findByCapaciteMinimum(int capaciteMin) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Salle> query = em.createQuery(
                    "SELECT s FROM Salle s WHERE s.capacite >= :capaciteMin", Salle.class);
            query.setParameter("capaciteMin", capaciteMin);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
