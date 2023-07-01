package it.uniroma3.siw.service;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.MovieRepository;
import it.uniroma3.siw.repository.RecensioneRepository;
import it.uniroma3.siw.repository.UserRepository;

@Service
public class RecensioneService {

	@Autowired
	private RecensioneRepository recensioneRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CredentialsRepository credentialsRepository;
	
	@Transactional
	public  boolean movieGiaRecensitoDaUser(Long movieId, Credentials credentials) {
		User user = credentials.getUser();
		for(Recensione recensione : this.movieRepository.findById(movieId).get().getRecensioni()) {
			if(recensione.getUtente().getId().equals(user.getId())) return true;
				
		}
		return false;
	}

	@Transactional
	public void addRecensione( Recensione recensione, Credentials credentials, Movie movie) {
		recensione.setMovie(movie);
		recensione.setUtente(credentials.getUser());
	}

//	@Transactional
//	public void deleteRecensione(Long recensioneId) {
//
//		Recensione recensione = this.recensioneRepository.findById(recensioneId).get();
//		this.recensioneRepository.delete(recensione);
//	}

	public void save(@Valid Recensione recensione) {

		this.recensioneRepository.save(recensione);
	}
	
}
