package dev.hotel.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.hotel.entite.Client;

/**
 * Repository, accès à la table Client via JPA
 * 
 * @author Pierre BATIGNES
 *
 */
public interface ClientRepository extends JpaRepository<Client, UUID> {

}
