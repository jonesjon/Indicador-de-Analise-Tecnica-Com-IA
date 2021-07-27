package br.iesb.indicador_analise_grafica.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import br.iesb.indicador_analise_grafica.InfoCandle;
import br.iesb.indicador_analise_grafica.repository.InfoCandleRepository;
import net.bytebuddy.implementation.Implementation.Context;

@Service
public class InfoCandleService {

	@Autowired
	private InfoCandleRepository infoCandle;
	private static InfoCandleRepository infoCandleRepository;

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

	public static List<InfoCandle> getCandlePeloNome(String papel) {
		return infoCandleRepository.findByNomeDoPapel(papel);
	}

	public static int countByPapelName(String papel) {
		return infoCandleRepository.countByNomeDoPapel(papel);
	}

	public static ArrayList<InfoCandle> getListForMediaMovel(String papel) {
		return infoCandleRepository.findByListForMediaMovel(papel);
	}

}
