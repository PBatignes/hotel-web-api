package dev.hotel.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import dev.hotel.entite.Chambre;
import dev.hotel.entite.Client;
import dev.hotel.repository.ChambreRepository;
import dev.hotel.repository.ClientRepository;
import dev.hotel.repository.ReservationRepository;

@SpringJUnitConfig(ReservationService.class)
class ReservationServiceTest {

	@Autowired
	ReservationService reservationService;

	@MockBean
	ClientRepository clientRepository;

	@MockBean
	ChambreRepository chambreRepository;

	@MockBean
	ReservationRepository reservationRepository;

	LocalDate dateDebut = LocalDate.of(2019, 10, 01);
	LocalDate dateFin = LocalDate.of(2019, 10, 10);
	String clientId = UUID.randomUUID().toString();
	UUID chambre = UUID.randomUUID();
	List<String> chambresId = Arrays.asList(chambre.toString(), chambre.toString());

	@Test
	void testCreerReservation() {

		when(this.chambreRepository.findById(any(UUID.class))).thenReturn(Optional.of(new Chambre()));
		when(this.clientRepository.findById(any(UUID.class))).thenReturn(Optional.of(new Client()));

		assertThat(this.reservationService.creerReservation(dateDebut, dateFin, clientId, chambresId))
				.hasFieldOrProperty("uuid");
		assertThat(this.reservationService.creerReservation(dateDebut, dateFin, clientId, chambresId).getDateDebut())
				.isEqualTo(dateDebut);
		assertThat(this.reservationService.creerReservation(dateDebut, dateFin, clientId, chambresId).getDateFin())
				.isEqualTo(dateFin);
		assertThat(this.reservationService.creerReservation(dateDebut, dateFin, clientId, chambresId).getClient())
				.isInstanceOf(Client.class);
		assertThat(this.reservationService.creerReservation(dateDebut, dateFin, clientId, chambresId).getChambres())
				.isNotEmpty();
	}

}
