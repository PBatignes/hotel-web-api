package dev.hotel.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import dev.hotel.entite.Client;
import dev.hotel.exception.UuidException;
import dev.hotel.repository.ClientRepository;

@SpringJUnitConfig(ClientService.class)
class ClientServiceTest {

	@Autowired
	ClientService clientService;

	@MockBean
	ClientRepository clientRepository;

	@Test
	void testGetClientPage() {

		Integer start = 0;
		Integer size = 2;
		Pageable p = PageRequest.of(start, size);
		List<Client> clients = Arrays.asList(new Client("Batignes", "Pierre"), new Client("Doe", "John"));

		when(clientRepository.findAll(p)).thenReturn(new PageImpl<Client>(clients));

		assertThat(this.clientService.getClientPage(start, size)).containsExactlyElementsOf(clients);
	}

	@Test
	void testGetOneClientUuid() {

		UUID random = UUID.randomUUID();
		Client c = new Client("Doe", "John");

		when(clientRepository.findById(random)).thenReturn(Optional.of(c));

		assertThat(this.clientService.getOneClientUuid(random.toString())).isEqualTo(c);
	}

	@Test
	void testGetOneClientUuidNonTrouve() {

		UUID random = UUID.randomUUID();

		when(clientRepository.findById(random)).thenReturn(Optional.empty());

		assertThatExceptionOfType(UuidException.class)
				.isThrownBy(() -> this.clientService.getOneClientUuid(random.toString()))
				.withMessage("Uuid non trouvé.");
	}

	@Test
	void testGetOneClientUuidInvalide() {

		assertThatExceptionOfType(UuidException.class).isThrownBy(() -> this.clientService.getOneClientUuid("0"))
				.withMessage("Uuid invalide.");
	}

	@Test
	void testCreerClient() {

		assertThat(this.clientService.creerClient("Doe", "John").getNom()).isEqualTo("Doe");
		assertThat(this.clientService.creerClient("Doe", "John").getPrenoms()).isEqualTo("John");
		assertThat(this.clientService.creerClient("Doe", "John")).hasFieldOrProperty("uuid");
	}
}
