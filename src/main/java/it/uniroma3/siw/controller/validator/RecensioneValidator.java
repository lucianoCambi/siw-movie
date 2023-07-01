package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.repository.RecensioneRepository;

@Component
public class RecensioneValidator implements Validator {
	
	@Autowired
	private RecensioneRepository recensioneRepository;

	@Override
	public void validate(Object o, Errors errors) { //sistemare validate 
		Recensione recensione = (Recensione)o;
//		if (recensione.getTitolo()!=null) //va bene 
//			errors.reject("recensione.nonValida");
		if (recensione.getValutazione()==null || recensione.getTitolo()==null || recensione.getTitolo().equals(""))  //problema sul getValutazione
			errors.reject("recensione.nonValida");
//		if (recensione.getTitolo()==null || recensione.getTitolo().equals(""))
//			errors.reject("titolo", "recensione.nonValida");
			
//		if (recensione.getTitolo()!=null && this.recensioneRepository.existsByTitolo(recensione.getTitolo()))  //problema
//			errors.reject("recensione.nonValida");
//		}
	}
	@Override
	public boolean supports(Class<?> aClass) {
		return Recensione.class.equals(aClass);
	}
}