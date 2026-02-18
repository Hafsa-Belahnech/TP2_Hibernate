package com.example.service;

import com.example.model.Utilisateur;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class UtilisateurServiceTest {

    private EntityManagerFactory emf;
    private UtilisateurService service;

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("gestion-salles");
        service = new UtilisateurService(emf);
    }

    @After
    public void tearDown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    @Test
    public void testCrudOperations() {
        // Create
        Utilisateur utilisateur = new Utilisateur("Test", "User", "test.user@example.com");
        utilisateur.setDateNaissance(LocalDate.of(1990, 1, 1));
        utilisateur.setTelephone("+33612345678");

        Utilisateur savedUtilisateur = service.save(utilisateur);
        assertNotNull(savedUtilisateur.getId());

        // Read
        Optional<Utilisateur> foundUtilisateur = service.findById(savedUtilisateur.getId());
        assertTrue(foundUtilisateur.isPresent());
        assertEquals("Test", foundUtilisateur.get().getNom());

        // Update
        Utilisateur toUpdate = foundUtilisateur.get();
        toUpdate.setNom("Updated");
        service.update(toUpdate);

        Optional<Utilisateur> updatedUtilisateur = service.findById(savedUtilisateur.getId());
        assertTrue(updatedUtilisateur.isPresent());
        assertEquals("Updated", updatedUtilisateur.get().getNom());

        // Delete
        service.delete(updatedUtilisateur.get());
        Optional<Utilisateur> deletedUtilisateur = service.findById(savedUtilisateur.getId());
        assertFalse(deletedUtilisateur.isPresent());
    }

    @Test
    public void testFindByEmail() {
        // Create test user
        Utilisateur utilisateur = new Utilisateur("Email", "Test", "email.test@example.com");
        service.save(utilisateur);

        // Test find by email
        Optional<Utilisateur> foundUtilisateur = service.findByEmail("email.test@example.com");
        assertTrue(foundUtilisateur.isPresent());
        assertEquals("Email", foundUtilisateur.get().getNom());

        // Test with non-existent email
        Optional<Utilisateur> notFound = service.findByEmail("nonexistent@example.com");
        assertFalse(notFound.isPresent());

        // Clean up
        service.delete(foundUtilisateur.get());
    }

    @Test
    public void testFindAll() {
        // Create test users
        Utilisateur u1 = new Utilisateur("User", "One", "user.one@example.com");
        Utilisateur u2 = new Utilisateur("User", "Two", "user.two@example.com");
        service.save(u1);
        service.save(u2);

        // Test find all
        List<Utilisateur> utilisateurs = service.findAll();
        assertTrue(utilisateurs.size() >= 2);

        // Clean up
        service.delete(u1);
        service.delete(u2);
    }
}
