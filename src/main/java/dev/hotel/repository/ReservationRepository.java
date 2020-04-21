package dev.hotel.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.hotel.entite.Reservation;

/**
 * Repository, accès à la table Reservation via JPA
 * 
 * @author Pierre BATIGNES
 *
 */
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

}
