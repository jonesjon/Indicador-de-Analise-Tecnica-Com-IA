package br.iesb.indicador_analise_grafica.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.iesb.indicador_analise_grafica.InfoCandle;
import br.iesb.indicador_analise_grafica.controle.Controle;
import br.iesb.indicador_analise_grafica.repository.ControleRepository;
import br.iesb.indicador_analise_grafica.repository.InfoCandleRepository;

@Service
public class InfoCandleService {

	@Autowired
	private InfoCandleRepository infoCandle;
	private static InfoCandleRepository infoCandleRepository;
	
	@Autowired
	private ControleRepository controleRepository;

	@PostConstruct
	public void getInfoCandleRepository() {
		infoCandleRepository = infoCandle;
	}

	public static void adicionaCandle(InfoCandle infoCandle) {
		infoCandleRepository.save(infoCandle);
	}

	public static ArrayList<InfoCandle> getInfoCandle() {
		return (ArrayList<InfoCandle>) infoCandleRepository.findAll();
	}

	public static ArrayList<InfoCandle> getInfoCandlePeloNome(String papel) {
		return infoCandleRepository.findByNomeDoPapel(papel);
	}
	
	public static ArrayList<InfoCandle> getUltimosInfoCandle(String nomeDoPapel, LocalDate data, int limit){
		return infoCandleRepository.findByUltimosCandles(nomeDoPapel, data, limit);
	}

	public static int countByPapelName(String papel) {
		return infoCandleRepository.countByNomeDoPapel(papel);
	}

	public static ArrayList<InfoCandle> getListForMediaMovel(String papel) {
		return infoCandleRepository.findByListForMediaMovel(papel);
	}
	
	public static ArrayList<String> getListForAllPapeis(String nomeDoPapel, int limit){
		return infoCandleRepository.findByListForAllPapeis(nomeDoPapel, limit);
	}
	
	public static ArrayList<InfoCandle> getGraficoAPartirDaData(LocalDate data, String nomeDoPapel){
		return infoCandleRepository.findByDatAfterAndNomeDoPapel(data, nomeDoPapel);
	}
	
	public static ArrayList<InfoCandle> verificaGraficoContinuo(LocalDate data, String nomeDoPapel, int limit){
		return infoCandleRepository.findByVerificaGraficoContinuo(data,nomeDoPapel, limit);
	}

	public static int getQtdPapeis() {
		return infoCandleRepository.findQtdPapeis();
	}
	
	public Controle getControle( ) {
		Optional<Controle> controle = controleRepository.findById(1L);
		if(controle.isPresent()) 
			return controle.get();
		return null;
	}
	
	public Controle saveControle(Controle controle) {
		return controleRepository.save(controle);
	}
	
	public LocalDate getMaxDate() {
		return infoCandleRepository.findMaxDat();
	}
	
	public ArrayList<InfoCandle> getUltimos200Candles(String nomePapel) {
		return this.infoCandle.ultimos200Candles(nomePapel);
	}
}
