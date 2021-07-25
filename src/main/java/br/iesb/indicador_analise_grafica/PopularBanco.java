package br.iesb.indicador_analise_grafica;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import br.iesb.indicador_analise_grafica.repository.InfoCandleRepository;
import net.bytebuddy.implementation.Implementation.Context;

@Service
public class PopularBanco {
	
	@Autowired
	private  InfoCandleRepository infoCandle;
	private static InfoCandleRepository infoCandleRepository;
	@PostConstruct
	public void getInfoCandleRepository(){
		infoCandleRepository=infoCandle;
	}
	
	public static void adicionaCandle(InfoCandle infoCandle){
		infoCandleRepository.save(infoCandle);
	}
	
	public static List<InfoCandle> getInfoCandle(){
		return (List<InfoCandle>) infoCandleRepository.findAll();
	}

	public static List<InfoCandle> getCandlePeloNome(String nome) {
		return infoCandleRepository.findByNomeDoPapel(nome);
	}
	
	

}
