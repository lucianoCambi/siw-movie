package it.uniroma3.siw.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.controller.validator.RecensioneValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.MovieService;
import it.uniroma3.siw.service.RecensioneService;
import it.uniroma3.siw.service.UserService;


@Controller
public class RecensioneControlloer {
	
	@Autowired
	private RecensioneService recensioneService;
	
	@Autowired 
	private UserService userService;
	
	@Autowired
	private MovieService movieService;
	
	@Autowired 
	private CredentialsService credentialsService;
	
	@Autowired
	private RecensioneValidator recensioneValidator;
	
	
	@GetMapping("/default/formNewRecensione/{movieId}")
	public String formNewRecensione(@PathVariable("movieId") Long movieId, Model model) {
		model.addAttribute("movie", this.movieService.findById(movieId));
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		
		if(this.recensioneService.movieGiaRecensitoDaUser(movieId, credentials)){
			model.addAttribute("erroreGiaRecensito", "Hai già recensito questo film");
			return "default/movieDefault.html";
		}
		else model.addAttribute("recensione", new Recensione());
		return "default/formNewRecensione.html";
	}
	
	@GetMapping("/default/movieDefault/{movieid}")
	public String getMovieConRecensioni(@PathVariable("movie") Long movieId, Model model) {
		model.addAttribute("movie", this.movieService.findById(movieId));
		return "default/movieDefault.html";
	}
	
	@PostMapping("/default/recensione/{movieId}")
	public String newRecensione(@ModelAttribute("recensione") Recensione recensione,@PathVariable("movieId") Long movieId, BindingResult bindingResult, Model model) {
		model.addAttribute("movie", this.movieService.findById(movieId));
		this.recensioneValidator.validate(recensione, bindingResult);
		if (!bindingResult.hasErrors()) {
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			this.recensioneService.addRecensione(recensione, credentials, this.movieService.findById(movieId));
			this.movieService.aggiungiRecensioneMovie(movieId, recensione);
			this.movieService.save(this.movieService.findById(movieId));
			this.recensioneService.save(recensione);
			return "default/movieDefault.html";
		} else {
			return "default/formNewRecensione.html"; //lasciare cosi
//			return formNewRecensione(movieId, model);
//		    return "redirect:/";
		}
		
	}
	
	
	
	

}
