package dev.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.hotel.entite.Client;


public interface HotelRepository extends JpaRepository<Client, Integer>{

}
