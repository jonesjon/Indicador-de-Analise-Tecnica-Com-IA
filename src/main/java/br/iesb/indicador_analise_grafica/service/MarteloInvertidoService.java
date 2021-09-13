package br.iesb.indicador_analise_grafica.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.iesb.indicador_analise_grafica.MarteloInvertido;
import br.iesb.indicador_analise_grafica.repository.MarteloInvertidoRepository;

@Service
public class MarteloInvertidoService {

	@Autowired
	private MarteloInvertidoRepository marteloInvertido;
	private static MarteloInvertidoRepository marteloInvertidoRepository;

	@PostConstruct
	public void getMarteloInvertidoRepository() {
		marteloInvertidoRepository = marteloInvertido;
	}
	
	public static void adicionaMarteloInvertido(MarteloInvertido marteloInvertido) {
		marteloInvertidoRepository.save(marteloInvertido);
	}
	
}
