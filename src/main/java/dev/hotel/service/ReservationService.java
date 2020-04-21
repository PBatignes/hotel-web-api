package dev.hotel.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import dev.hotel.entite.Chambre;
import dev.hotel.entite.Client;
import dev.hotel.entite.Reservation;
import dev.hotel.exception.ReservationException;
import dev.hotel.repository.ChambreRepository;
import dev.hotel.repository.ClientRepository;
import dev.hotel.repository.ReservationRepository;
import dev.hotel.utils.Messages;

/**
 * Service, fonction métier d'une réservation
 * 
 * @author Pierre BATIGNES
 *
 */
@Service
public class ReservationService {

	private ReservationRepository reservationRepository;

	private ChambreRepository chambreRepository;

	private ClientRepository clientRepository;

	/**
	 * Constructor
	 *
	 * @param reservationRepository
	 * @param chambreRepository
	 * @param clientRepository
	 */
	public ReservationService(ReservationRepository reservationRepository, ChambreRepository chambreRepository,
			ClientRepository clientRepository) {
		super();
		this.reservationRepository = reservationRepository;
		this.chambreRepository = chambreRepository;
		this.clientRepository = clientRepository;
	}

	/**
	 * Crée une réservation et l'ajoute en base de données
	 * 
	 * @param dateDebut
	 * @param dateFin
	 * @param clientId
	 * @param chambresId
	 * @return Reservation
	 */
	@Transactional
	public Reservation creerReservation(LocalDate dateDebut, LocalDate dateFin, String clientId,
			List<String> chambresId) {

		Optional<Client> client = null;
		List<Chambre> chambres = new ArrayList<>();
		Messages messages = new Messages();
		String chambreIdError = "";

		try {
			// recherche du client
			client = clientRepository.findById(UUID.fromString(clientId));

			if (!client.isPresent()) { // client.isEmpty() imcompatible avec le compiler maven
				messages.addMessage("Uuid client non trouvé.");
			}

			try {
				// recherche des chambres
				for (String chambreId : chambresId) {
					chambreIdError = chambreId;

					Optional<Chambre> chambreOpt = chambreRepository.findById(UUID.fromString(chambreId));

					if (chambreOpt.isPresent()) {

						chambres.add(chambreOpt.get());
					} else {

						messages.addMessage("la chambre " + chambreId + " n'existe pas");
					}
				}
			} catch (IllegalArgumentException e) {
				messages.addMessage("L'uuid de la chambre " + chambreIdError + " est invalide.");
			}

		} catch (IllegalArgumentException e) {
			messages.addMessage("Uuid du client " + clientId + " est invalide.");
		}

		// creation et sauvegarde de la reservation
		if (messages.isEmpty()) {

			Reservation reservation = new Reservation(dateDebut, dateFin, client.get(), chambres);
			reservationRepository.save(reservation);

			return reservation;

		} else { // génération exception si erreur

			throw new ReservationException(messages);
		}
	}

}
