package dev.hotel.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.hotel.dto.ReservationDto;
import dev.hotel.exception.ReservationException;
import dev.hotel.service.ReservationService;
import dev.hotel.utils.Messages;

/**
 * Controller Reservation, post sur /reservation/
 * 
 * @author Pierre BATIGNES
 *
 */
@RestController
@RequestMapping("reservations")
public class ReservationController {

	private ReservationService reservationService;

	/**
	 * Constructor
	 *
	 * @param reservationService
	 */
	public ReservationController(ReservationService reservationService) {
		super();
		this.reservationService = reservationService;
	}

	@PostMapping
	public ResponseEntity<?> postReservation(@Valid @RequestBody ReservationDto r, BindingResult result) {

		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(new Messages("Un des champs est invalide."));
		} else {
			return ResponseEntity.ok(reservationService.creerReservation(r.getDateDebut(), r.getDateFin(),
					r.getClientId(), r.getChambres()));
		}
	}

	@ExceptionHandler(ReservationException.class)
	public ResponseEntity<Messages> onReservationException(ReservationException e) {

		return ResponseEntity.badRequest().body(e.getMessages());
	}

}
