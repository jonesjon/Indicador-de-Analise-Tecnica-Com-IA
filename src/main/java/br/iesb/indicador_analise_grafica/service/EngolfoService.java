package br.iesb.indicador_analise_grafica.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.iesb.indicador_analise_grafica.Engolfo;
import br.iesb.indicador_analise_grafica.repository.EngolfoRepository;

@Service
public class EngolfoService {

	@Autowired
	private EngolfoRepository engolfo;
	private static EngolfoRepository engolfoRepository;

	@PostConstruct
	public void getMarubozuRepository() {
		engolfoRepository = engolfo;
	}
	
	public static void adicionaEngolfo(Engolfo engolfo) {
		engolfoRepository.save(engolfo);
	}
	
}
