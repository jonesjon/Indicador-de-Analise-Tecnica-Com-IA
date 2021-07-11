package br.iesb.indicador_analise_grafica;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class Grafico {
	
	private static String nomeDoPapel;
	static ArrayList<Candle> grafico = new ArrayList<Candle>();
	ArrayList<Candle> auxiliar = new ArrayList<Candle>();
	Indicador indicador = new Indicador();
	DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
	static ArrayList<MediaMovel> listaDeMedias = new ArrayList<MediaMovel>();
	
	public ArrayList<Candle> returnGrafico(){
		return grafico;
	}
	
	public void adicionaCandle(Candle candle) {
		grafico.add(candle);
	}
	
	public void printArrayList(ArrayList<Candle> list) {
		
		if(!list.isEmpty()) {
			for(int i=0; i<list.size(); i++) {
				System.out.println("Data: "+ formato.format(list.get(i).data));
				System.out.println("Abertura: " + list.get(i).abertura);
				System.out.println("Fechamento: " + list.get(i).fechamento);
				System.out.println("Maxima: "+ list.get(i).maxima);
				System.out.println("Manima: "+ list.get(i).minima);
				System.out.println("Volume: "+ list.get(i).volume);
				System.out.println("");
			}
		}else {
			System.out.println("Grafico esta vazio.");
		}
	}
	
	public void ordenaGrafico() {
		for(int i=0; i<grafico.size(); i++) {
			for(int j=i+1; j<grafico.size(); j++) {
				if(grafico.get(i).data.getDayOfYear() > grafico.get(j).data.getDayOfYear()) {
					auxiliar.set(0, grafico.get(i));
					grafico.set(i, grafico.get(j));
					grafico.set(j, auxiliar.get(0));
					auxiliar.clear();
				}
			}
		}

	}
	
	public void precoMaximoDoGrafico() {
		double auxiliar = 0;
		
		for(int i=0; i<grafico.size(); i++) {
			if(auxiliar < grafico.get(i).maxima) {
				auxiliar = grafico.get(i).maxima;
			}
		}
	}
	
	public ArrayList<Candle> ultimosCandles(int qtdCandles){
		
		if(grafico.size()>=qtdCandles) {
			ArrayList<Candle> ultimos = new ArrayList<Candle>();
			
			for(int i=grafico.size()-1; i>grafico.size()-1-qtdCandles; i--) {
				ultimos.add(0, grafico.get(i));
			}
			return ultimos;
		}else {
			ArrayList<Candle> candle = null;
			return candle;
		}
	}
	
	public Double mediaMovel(int media) {
		if(grafico.size()>media-2) {
			Double mediaMovel = indicador.mediaMovel(media, grafico);
			return mediaMovel;
		}else {
			return null;
		}
	}
	
	public Double mediaMovelVolume(int media) {
		if(grafico.size()>media-2) {
			Double mediaMovelVolume = indicador.mediaMovelVolume(media, grafico);
			return mediaMovelVolume;
		}else {
			return null;
		}
	}
	
	public void adicionaMediaMovelNaLista(Candle candle, int media) {
		if(mediaMovel(media)!= null) {
			
			
			MediaMovel mediaMovel = new MediaMovel(candle.data, candle.getPapel(), media, mediaMovel(media), mediaMovelVolume(media));
			listaDeMedias.add(mediaMovel);
			
		}
	}

}
