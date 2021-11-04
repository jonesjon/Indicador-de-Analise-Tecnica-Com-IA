package br.iesb.indicador_analise_grafica.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.iesb.indicador_analise_grafica.padroes.Marubozu;
import br.iesb.indicador_analise_grafica.repository.MarubozuRepository;

@Service
public class MarubozuService {
	
	@Autowired
	private MarubozuRepository marubozu;
	private static MarubozuRepository marubozuRepository;

	@PostConstruct
	public void getMarubozuRepository() {
		marubozuRepository = marubozu;
	}
	
	public static void adicionaMarubozu(Marubozu marubozu) {
		marubozuRepository.save(marubozu);
	}

}
