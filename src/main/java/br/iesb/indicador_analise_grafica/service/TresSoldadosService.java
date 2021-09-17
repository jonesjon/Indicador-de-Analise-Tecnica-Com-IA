package br.iesb.indicador_analise_grafica.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.iesb.indicador_analise_grafica.TresSoldados;
import br.iesb.indicador_analise_grafica.repository.TresSoldadosRepository;

@Service
public class TresSoldadosService {
	
	@Autowired
	private TresSoldadosRepository tresSoldados;
	private static TresSoldadosRepository tresSoldadosRepository;

	@PostConstruct
	public void getTresSoldadosRepository() {
		tresSoldadosRepository = tresSoldados;
	}
	
	public static void adicionaTresSoldados(TresSoldados tresSoldados) {
		tresSoldadosRepository.save(tresSoldados);
	}

}
