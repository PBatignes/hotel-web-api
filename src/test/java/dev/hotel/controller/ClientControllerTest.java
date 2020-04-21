package dev.hotel.controller;

import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import dev.hotel.entite.Client;
import dev.hotel.exception.UuidException;
import dev.hotel.service.ClientService;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	ClientService clientService;

	@Test
	void testGetClientsJson() throws Exception {

		Integer start = 0;
		Integer size = 2;

		when(this.clientService.getClientPage(start, size))
				.thenReturn(Arrays.asList(new Client("Batignes", "Pierre"), new Client("Doe", "John")));

		this.mockMvc.perform(MockMvcRequestBuilders.get("/clients?start=" + start + "&size=" + size))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].nom").value("Batignes"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].prenoms").value("Pierre"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].nom").value("Doe"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].prenoms").value("John"));
	}

	@Test
	void testGetClientUuid() throws Exception {

		when(this.clientService.getOneClientUuid("dcf129f1-a2f9-47dc-8265-1d844244b192"))
				.thenReturn(new Client("Batignes", "Pierre"));

		this.mockMvc.perform(MockMvcRequestBuilders.get("/clients/dcf129f1-a2f9-47dc-8265-1d844244b192"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.nom").value("Batignes"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.prenoms").value("Pierre"));
	}

	@Test
	void testGetClientUuidNonTrouve() throws Exception {

		when(this.clientService.getOneClientUuid("00000000-0000-0000-0000-00000000"))
				.thenThrow(new UuidException("Uuid non trouvé."));

		this.mockMvc.perform(MockMvcRequestBuilders.get("/clients/00000000-0000-0000-0000-00000000"))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().string("Uuid non trouvé."));
	}

	@Test
	void testGetClientUuidInvalide() throws Exception {

		when(this.clientService.getOneClientUuid("0")).thenThrow(new UuidException("Uuid invalide."));

		this.mockMvc.perform(MockMvcRequestBuilders.get("/clients/0"))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().string("Uuid invalide."));
	}

	@Test
	void testPostClient() throws Exception {

		String jsonBody = "{\"nom\" : \"ALBERT\",\"prenoms\" : \"Dimitri\"}";
		String nom = "ALBERT";
		String prenoms = "Dimitri";

		when(this.clientService.creerClient(nom, prenoms)).thenReturn(new Client(nom, prenoms));

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/clients/").contentType(MediaType.APPLICATION_JSON)
						.content(jsonBody))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.nom").value("ALBERT"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.prenoms").value("Dimitri"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.uuid").exists());
	}

	@Test
	void testPostClientInvalid() throws Exception {

		String jsonBody = "{\"nom\" : \"A\",\"prenoms\" : \"Dimitri\"}";

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/clients/").contentType(MediaType.APPLICATION_JSON)
						.content(jsonBody))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().string(
						"Requete invalide, le nom et le prénom doivent être composés d'au moins 2 caractères."));
	}
}
