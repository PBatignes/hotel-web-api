package dev.hotel.entite;

import javax.persistence.Entity;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Client extends BaseEntite {

	@JsonProperty("nom")
	@Size(min = 2)
	private String nom;

	@JsonProperty("prenoms")
	@Size(min = 2)
	private String prenoms;

	public Client() {
	}

	public Client(String nom, String prenoms) {
		this.nom = nom;
		this.prenoms = prenoms;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenoms() {
		return prenoms;
	}

	public void setPrenoms(String prenoms) {
		this.prenoms = prenoms;
	}
}
