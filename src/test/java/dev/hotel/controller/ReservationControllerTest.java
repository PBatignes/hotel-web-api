package dev.hotel.controller;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import dev.hotel.entite.Client;
import dev.hotel.entite.Reservation;
import dev.hotel.exception.ReservationException;
import dev.hotel.service.ReservationService;
import dev.hotel.utils.Messages;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	ReservationService reservationService;

	// valeurs de tests
	String jsonBody = "{" + "\"dateDebut\":\"2019-10-01\",\r\n" + "\"dateFin\":\"2019-10-10\","
			+ "\"clientId\":\"dcf129f1-a2f9-47dc-8265-1d844244b192\"," + "\"chambres\": ["
			+ "	\"754e6f53-e8f5-4976-9fd2-95e6a427ef1c\"," + "	\"43793061-f70b-44b9-a855-adc66a2efb9f\","
			+ "	\"b13e05d9-d9a9-49a9-80cb-ee03da248102\"" + "	]" + "}";

	LocalDate dateDebut = LocalDate.of(2019, 10, 01);
	LocalDate dateFin = LocalDate.of(2019, 10, 10);
	String clientId = "dcf129f1-a2f9-47dc-8265-1d844244b192";
	List<String> chambres = Arrays.asList("754e6f53-e8f5-4976-9fd2-95e6a427ef1c",
			"43793061-f70b-44b9-a855-adc66a2efb9f", "b13e05d9-d9a9-49a9-80cb-ee03da248102");

	@Test
	void testPostReservation() throws Exception {

		when(this.reservationService.creerReservation(dateDebut, dateFin, clientId, chambres))
				.thenReturn(new Reservation(dateDebut, dateFin, new Client(), new ArrayList<>()));

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/reservations").contentType(MediaType.APPLICATION_JSON)
						.content(jsonBody))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.uuid").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.dateDebut").value(dateDebut.toString()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.dateFin").value(dateFin.toString()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.client").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.chambres").exists());
	}

	@Test
	void testPostReservationInvalid() throws Exception {

		String jsonBodyVide = "{}";

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/reservations").contentType(MediaType.APPLICATION_JSON)
						.content(jsonBodyVide))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]").value("Un des champs est invalide."));
	}

	@Test
	void testPostReservationClientNonTrouve() throws Exception {

		when(this.reservationService.creerReservation(dateDebut, dateFin, clientId, chambres))
				.thenThrow(new ReservationException(new Messages("Uuid client non trouvé.")));

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/reservations").contentType(MediaType.APPLICATION_JSON)
						.content(jsonBody))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]").value("Uuid client non trouvé."));
	}

	@Test
	void testPostReservationChambreNonTrouve() throws Exception {

		when(this.reservationService.creerReservation(dateDebut, dateFin, clientId, chambres))
				.thenThrow(new ReservationException(
						new Messages(Arrays.asList("la chambre test1 n'existe pas", "la chambre test2 n'existe pas"))));

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/reservations").contentType(MediaType.APPLICATION_JSON)
						.content(jsonBody))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]").value("la chambre test1 n'existe pas"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.messages[1]").value("la chambre test2 n'existe pas"));
	}

}
