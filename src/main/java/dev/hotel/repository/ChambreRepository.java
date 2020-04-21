package dev.hotel.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.hotel.entite.Chambre;

/**
 * Repository, accès à la table chambre via JPA
 * 
 * @author Pierre BATIGNES
 *
 */
public interface ChambreRepository extends JpaRepository<Chambre, UUID> {

}
