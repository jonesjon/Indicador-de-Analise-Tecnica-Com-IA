package br.iesb.indicador_analise_grafica.service;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.iesb.indicador_analise_grafica.PapeisOperaveis;
import br.iesb.indicador_analise_grafica.repository.PapeisOperaveisRepository;

@Service
public class PapeisOperaveisService {

	@Autowired
	private PapeisOperaveisRepository papeisOperaveis;
	private static PapeisOperaveisRepository papeisOperaveisRepository;

	@PostConstruct
	public void getInfoCandleRepository() {
		papeisOperaveisRepository = papeisOperaveis;
	}
	
	public static void setAllPapeis(ArrayList<PapeisOperaveis> po) {
		papeisOperaveisRepository.saveAll(po);
	}
	
	public static ArrayList<PapeisOperaveis> getAllPapeis(){
		return (ArrayList<PapeisOperaveis>) papeisOperaveisRepository.findAll();
	}
	
}
