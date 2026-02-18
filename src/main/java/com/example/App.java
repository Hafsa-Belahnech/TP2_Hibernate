package com.example;

import com.example.model.Salle;
import com.example.model.Utilisateur;
import com.example.service.SalleService;
import com.example.service.UtilisateurService;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class App {
    public static void main(String[] args) {
        // On Crée d'abord l'EntityManagerFactory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestion-salles");

        //Puis les services
        UtilisateurService utilisateurService = new UtilisateurService(emf);
        SalleService salleService = new SalleService(emf);

        try {
            // On teste Tles opérations CRUD pour la classe Utilisateur
            System.out.println("\n=== Test CRUD Utilisateur ===");
            testCrudUtilisateur(utilisateurService);

            //On Teste les opérations CRUD pour la classe Salle
            System.out.println("\n=== Test CRUD Salle ===");
            testCrudSalle(salleService);

        } finally {
            // On Ferme ici l'EntityManagerFactory (c'est comme la session)
            emf.close();
        }
    }

    private static void testCrudUtilisateur(UtilisateurService service) {
        // Création des utilisateur et insertions des valeurs par Create
        //Utilisateur 1
        System.out.println("Création d'utilisateurs...");
        Utilisateur u1 = new Utilisateur("Grey", "Sarah", "sarah.grey@example.com");
        u1.setDateNaissance(LocalDate.of(1985, 5, 15));
        u1.setTelephone("+212612345678");

        //Utilisateur 2
        Utilisateur u2 = new Utilisateur("Martin", "Sophia", "sophia.martin@example.com");
        u2.setDateNaissance(LocalDate.of(1990, 10, 20));
        u2.setTelephone("+212687654321");

        service.save(u1);
        service.save(u2);

        // Lecture et affichage des utilisateurs par Read
        System.out.println("\nLecture de tous les utilisateurs :");
        List<Utilisateur> utilisateurs = service.findAll();
        utilisateurs.forEach(System.out::println);

        //Recherche d'utilisateur par Id et affichage
        System.out.println("\nRecherche d'un utilisateur par ID :");
        Optional<Utilisateur> utilisateurOpt = service.findById(1L);
        utilisateurOpt.ifPresent(System.out::println);

        //Recherche d'utilisateur par email et affichage
        System.out.println("\nRecherche d'un utilisateur par email :");
        Optional<Utilisateur> utilisateurParEmail = service.findByEmail("sophia.martin@example.com");
        utilisateurParEmail.ifPresent(System.out::println);

        // Mise à jour par Update
        System.out.println("\nMise à jour d'un utilisateur :");
        utilisateurOpt.ifPresent(utilisateur -> {
            utilisateur.setTelephone("+212699887766");
            service.update(utilisateur);
            System.out.println("Utilisateur mis à jour : " + utilisateur);
        });

        // Suppression de l'utilisateur par Delete
        System.out.println("\nSuppression d'un utilisateur :");
        service.deleteById(2L);
        System.out.println("Utilisateur avec ID=2 supprimé");

        // Affichage des utilisateurs après mise à jour de la table
        System.out.println("\nListe des utilisateurs après suppression :");
        service.findAll().forEach(System.out::println);
    }

    private static void testCrudSalle(SalleService service) {
        // Création et insertions des valeurs par (Create)
        System.out.println("Création de salles...");
        Salle s1 = new Salle("Salle A102", 30);
        s1.setDescription("Salle de réunion équipée d'un projecteur");
        s1.setEtage(1);

        Salle s2 = new Salle("Amphithéâtre B202", 150);
        s2.setDescription("Grand amphithéâtre pour conférences");
        s2.setEtage(2);

        Salle s3 = new Salle("Salle C302", 10);
        s3.setDescription("Petite salle pour entretiens");
        s3.setEtage(3);
        s3.setDisponible(false);

        service.save(s1);
        service.save(s2);
        service.save(s3);

        // Lecture et affichage des salles par  (Read)
        System.out.println("\nLecture de toutes les salles :");
        List<Salle> salles = service.findAll();
        salles.forEach(System.out::println);

        //Recherche d'une salle par ID
        System.out.println("\nRecherche d'une salle par ID :");
        Optional<Salle> salleOpt = service.findById(2L);
        salleOpt.ifPresent(System.out::println);
        
        //Recherche des salles disponibles en faisant appel à la méthode appropriée 
        System.out.println("\nRecherche des salles disponibles :");
        List<Salle> sallesDisponibles = service.findByDisponible(true);
        sallesDisponibles.forEach(System.out::println);

        //Recherche des salles avec capacité minimum de 50, en faisant appel à la méthode appropriée 
        System.out.println("\nRecherche des salles avec capacité minimum de 50 :");
        List<Salle> sallesGrandes = service.findByCapaciteMinimum(50);
        sallesGrandes.forEach(System.out::println);

        // Mise à jour par (Update)
        System.out.println("\nMise à jour d'une salle :");
        salleOpt.ifPresent(salle -> {
            salle.setCapacite(200);
            service.update(salle);
            System.out.println("Salle mise à jour : " + salle);
        });

        // Suppression d'une salle par  (Delete)
        System.out.println("\nSuppression d'une salle :");
        service.deleteById(3L);
        System.out.println("Salle avec ID=3 supprimée");

        System.out.println("\nListe des salles après suppression :");
        service.findAll().forEach(System.out::println);
    }
}
