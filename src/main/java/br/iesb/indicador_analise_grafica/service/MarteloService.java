package br.iesb.indicador_analise_grafica.service;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.iesb.indicador_analise_grafica.padroes.Martelo;
import br.iesb.indicador_analise_grafica.repository.MarteloRepository;

@Service
public class MarteloService {
	
	@Autowired
	private MarteloRepository martelo;
	private static MarteloRepository marteloRepository;

	@PostConstruct
	public void getInfoCandleRepository() {
		marteloRepository = martelo;
	}
	
	/*
	 * public static void adicionaMartelo(Martelo martelo) {
	 * marteloRepository.save(martelo); }
	 */
	
	public static ArrayList<Martelo> getMartelosUltimoAno(){
		return marteloRepository.getMartelosUltimoAno();
	}

}
