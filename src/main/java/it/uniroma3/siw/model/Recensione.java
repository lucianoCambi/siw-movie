package it.uniroma3.siw.model;

import java.util.Objects;

import javax.persistence.*;
import javax.persistence.Id;
import javax.validation.constraints.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Recensione {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	  private Long id;
	 
	 @NotBlank
	 private String titolo;
	 
	 @NotNull
	 private Integer valutazione;
	 
	 @OneToOne
	 private User utente;
	 
	 private String testo;
	 
	 @ManyToOne(fetch = FetchType.LAZY)//(cascade = {CascadeType.MERGE})
	 private Movie movie;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public Integer getValutazione() {
		return valutazione;
	}

	public void setValutazione(Integer valutazione) {
		this.valutazione = valutazione;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(titolo, valutazione);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recensione other = (Recensione) obj;
		return Objects.equals(titolo, other.titolo) && Objects.equals(valutazione, other.valutazione);
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public User getUtente() {
		return utente;
	}

	public void setUtente(User utente) {
		this.utente = utente;
	}
	 
}
