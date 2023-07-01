package it.uniroma3.siw.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.repository.ArtistRepository;

@Service
public class ArtistService {

	@Autowired
	private ArtistRepository artistRepository;
	
	@Transactional
	public Artist saveNewArtist (Artist artist) {
		if (!artistRepository.existsByNameAndSurname(artist.getName(), artist.getSurname())) {
			this.artistRepository.save(artist);
			return artist;
		}
		else return null;
	}

	@Transactional
	public Artist findById(Long id) {
		return this.artistRepository.findById(id).get();
	}

	@Transactional
	public Iterable<Artist> findAll() {
		return this.artistRepository.findAll();
	}
	
}
