package br.iesb.indicador_analise_grafica.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.iesb.indicador_analise_grafica.PiercingLine;
import br.iesb.indicador_analise_grafica.repository.PiercingLineRepository;

@Service
public class PiercingLineService {

	@Autowired
	private PiercingLineRepository piercingLine;
	private static PiercingLineRepository piercingLineRepository;

	@PostConstruct
	public void getPiercingLineRepository() {
		piercingLineRepository = piercingLine;
	}
	
	public static void adicionaPiercingLine(PiercingLine piercingLine) {
		piercingLineRepository.save(piercingLine);
	}
	
}
