package it.uniroma3.siw.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.repository.ArtistRepository;
import it.uniroma3.siw.repository.MovieRepository;
import it.uniroma3.siw.repository.RecensioneRepository;

@Service
public class MovieService {

	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired 
	private ArtistRepository artistRepository;
	
	@Autowired
	private RecensioneRepository recensioneRepository;

	@Transactional
	public Movie findById(Long id) {
		return this.movieRepository.findById(id).get();
	}

	@Transactional
	public Iterable<Movie> findAll() {
		return this.movieRepository.findAll();
	}
	
	@Transactional
	public Movie setDirectorToMovie(Long movieId, Long directorId) {
		Artist director = this.artistRepository.findById(directorId).get();
		Movie movie = this.movieRepository.findById(movieId).get();
		movie.setDirector(director);
		this.movieRepository.save(movie);
		return movie;
	}

	public void save(Movie movie) {
		this.movieRepository.save(movie);
	}

	public Object findByYear(int year) {
		return this.movieRepository.findByYear(year);
	}
	
	public List<Artist> actorsToAdd (Long movieId) {
		List<Artist> actorsToAdd = new ArrayList<>();

		for (Artist a : artistRepository.findActorsNotInMovie(movieId)) {
			actorsToAdd.add(a);
		}
		return actorsToAdd;
	}
	
	@Transactional
	public Movie addActorToMOvie (Long movieId, Long actorId) {
		Movie movie = this.movieRepository.findById(movieId).get();
		Artist actor = this.artistRepository.findById(actorId).get();
		Set<Artist> actors = movie.getActors();
		actors.add(actor);
		this.movieRepository.save(movie);
		return movie;
	}
	
	@Transactional
	public Movie removeActorFromMovie (Long movieId, Long actorId) {
		Movie movie = this.movieRepository.findById(movieId).get();
		Artist actor = this.artistRepository.findById(actorId).get();
		Set<Artist> actors = movie.getActors();
		actors.remove(actor);
		this.movieRepository.save(movie);
		return movie;
	}
	
	@Transactional
	public Movie aggiungiRecensioneMovie (Long movieId, Recensione recensione) {
		Movie  movie = this.movieRepository.findById(movieId).get();
		movie.getRecensioni().add(recensione);
		this.movieRepository.save(movie);
		return movie;
	}

	@Transactional
	public Movie deleteRecensione(Long movieId, Long recensioneId) {

		Movie movie = this.movieRepository.findById(movieId).get();
		Recensione recensione = this.recensioneRepository.findById(recensioneId).get();
		movie.getRecensioni().remove(recensione);
		this.recensioneRepository.deleteById(recensioneId);
		this.movieRepository.save(movie);
		return movie;
	}
	
}
