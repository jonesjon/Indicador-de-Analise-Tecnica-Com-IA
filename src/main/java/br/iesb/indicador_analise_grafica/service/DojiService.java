package br.iesb.indicador_analise_grafica.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.iesb.indicador_analise_grafica.padroes.Doji;
import br.iesb.indicador_analise_grafica.repository.DojiRepository;

@Service
public class DojiService {

	@Autowired
	private DojiRepository doji;
	private static DojiRepository dojiRepository;

	@PostConstruct
	public void getDojiRepository() {
		dojiRepository = doji;
	}
	
	/*
	 * public static void adicionaDoji(Doji doji) { dojiRepository.save(doji); }
	 */
	
}

