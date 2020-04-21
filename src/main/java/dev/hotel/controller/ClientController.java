package dev.hotel.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.hotel.dto.ClientDto;
import dev.hotel.entite.Client;
import dev.hotel.exception.UuidException;
import dev.hotel.service.ClientService;

/**
 * Controller client, get et post sur /clients/
 * 
 * @author Pierre BATIGNES
 *
 */
@RestController
@RequestMapping("clients")
public class ClientController {

	private ClientService clientService;

	/**
	 * Constructor
	 *
	 * @param clientRepository
	 */
	public ClientController(ClientService clientService) {
		super();
		this.clientService = clientService;
	}

	/**
	 * Reçoit une requete get avec deux paramètres start et size permettant de
	 * renvoyer une liste de client correspondant à la page demandé
	 * 
	 * @param start
	 * @param size
	 * @return List<Client>
	 */
	@GetMapping
	public List<Client> getClientsJson(@RequestParam Integer start, @RequestParam Integer size) {

		return clientService.getClientPage(start, size);
	}

	/**
	 * Reçoit une requete get avec un uuid permmettant de renvoyer le client
	 * correspondant
	 * 
	 * @param uuid
	 * @return ResponseEntity<Client>
	 */
	@GetMapping("{uuid}")
	public ResponseEntity<Client> getClientUUID(@PathVariable String uuid) {

		return ResponseEntity.ok().body(clientService.getOneClientUuid(uuid));
	}

	/**
	 * Catch l'exception de type UuidException et renvoie une réponse composé d'un
	 * status NOT_FOUND(404) et du message de l'exception
	 * 
	 * @param e
	 * @return ResponseEntity<String>
	 */
	@ExceptionHandler(UuidException.class)
	public ResponseEntity<String> onUuidException(UuidException e) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	/**
	 * Reçoit une requete post contenant les informations necessaires à la creation
	 * d'un client et renvoie le client créer si les informations sont corrects
	 * 
	 * @param c
	 * @param result
	 * @return ResponseEntity<?>
	 */
	@PostMapping
	public ResponseEntity<?> postClient(@Valid @RequestBody ClientDto c, BindingResult result) {

		if (result.hasErrors()) {
			return ResponseEntity.badRequest()
					.body("Requete invalide, le nom et le prénom doivent être composés d'au moins 2 caractères.");
		} else {
			return ResponseEntity.ok(clientService.creerClient(c.getNom(), c.getPrenoms()));
		}

	}

}
