package br.iesb.indicador_analise_grafica.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.iesb.indicador_analise_grafica.padroes.BebeAbandonado;
import br.iesb.indicador_analise_grafica.repository.BebeAbandonadoRepository;

@Service
public class BebeAbandonadoService {

	@Autowired
	private BebeAbandonadoRepository bebeAbandonado;
	private static BebeAbandonadoRepository bebeAbandonadoRepository;

	@PostConstruct
	public void getTresSoldadosRepository() {
		bebeAbandonadoRepository = bebeAbandonado;
	}
	
	public static void adicionaBebeAbandonado(BebeAbandonado bebeAbandonado) {
		bebeAbandonadoRepository.save(bebeAbandonado);
	}
	
}
