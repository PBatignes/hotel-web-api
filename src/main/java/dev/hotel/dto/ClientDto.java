package dev.hotel.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ClientDto {

	@NotNull
	@Size(min = 2)
	private String nom;

	@NotNull
	@Size(min = 2)
	private String prenoms;

	/**
	 * Constructor
	 *
	 * @param nom
	 * @param prenoms
	 */
	public ClientDto(String nom, String prenoms) {
		super();
		this.nom = nom;
		this.prenoms = prenoms;
	}

	/**
	 * Getter
	 *
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Setter
	 *
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Getter
	 *
	 * @return the prenoms
	 */
	public String getPrenoms() {
		return prenoms;
	}

	/**
	 * Setter
	 *
	 * @param prenoms the prenoms to set
	 */
	public void setPrenoms(String prenoms) {
		this.prenoms = prenoms;
	}

}
