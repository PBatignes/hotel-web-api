package dev.hotel.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import dev.hotel.entite.Client;
import dev.hotel.exception.UuidException;
import dev.hotel.repository.ClientRepository;

/**
 * Service, fonction métier d'un client
 * 
 * @author Pierre BATIGNES
 *
 */
@Service
public class ClientService {

	private ClientRepository clientRepository;

	/**
	 * Constructor
	 *
	 * @param clientRepository
	 */
	public ClientService(ClientRepository clientRepository) {
		super();
		this.clientRepository = clientRepository;
	}

	/**
	 * Retourne une liste de clients en fonction de la page précisé avec start
	 * (numéro de la page) et size (taille des pages)
	 * 
	 * @param start numéro de la page
	 * @param size  taille des pages
	 * @return List<Client> clients présents sur la page demandé
	 */
	public List<Client> getClientPage(Integer start, Integer size) {

		return clientRepository.findAll(PageRequest.of(start, size)).toList();
	}

	/**
	 * Recherche un client en BDD en fonction d'un uuid et le renvoie si l'uuid est
	 * correct
	 * 
	 * @param uuid
	 * @return ResponseEntity<Client>
	 */
	public Client getOneClientUuid(String uuid) {

		Optional<Client> client;

		try {
			client = clientRepository.findById(UUID.fromString(uuid));

			if (client.isPresent()) {
				return client.get();
			} else {
				throw new UuidException("Uuid non trouvé.");
			}

		} catch (IllegalArgumentException e) {

			throw new UuidException("Uuid invalide.", e);
		}

	}

	/**
	 * Crée un client et l'ajoute en base de données
	 * 
	 * @param nom
	 * @param prenoms
	 * @return Client
	 */
	@Transactional
	public Client creerClient(String nom, String prenoms) {

		Client c = new Client(nom, prenoms);

		clientRepository.save(c);

		return c;
	}
}
