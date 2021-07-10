package br.iesb.indicador_analise_grafica;

import java.time.LocalDate;
import java.util.ArrayList;

public class RedeNeural {
	
	private Grafico grafico = new Grafico();
	private Indicador indicador = new Indicador();
	private static Martelo martelo;
	ArrayList<Candle> ultimosCandles = new ArrayList<Candle>();
	ArrayList<Martelo> listaMartelo = new ArrayList<Martelo>();
	
	static int cont1 = 0;
	static int cont2 = 0;
	private final int MEDIACURTA = 8;
	private final int MEDIA = 20;
	private final int MEDIALONGA = 200;
	
	
	public boolean procuraPadraoUmCandle(Candle candle) {
		
		if(candle != null) {
		
			Double pavioSuperior = pavioSuperiorEmPorcentagem(candle);
			Double pavioInferior = pavioInferiorEmPorcentagem(candle);
			
			//Condições para Martelo
			if(pavioSuperior <= 10 && pavioInferior >= 67 && tendenciaMediaCurta(candle) == TendenciaMediaCurta.BAIXA) {
			
				martelo = new Martelo(candle.data, tipoCandle(candle), classificaPavioSuperior(pavioSuperior), classificaPavioInferior(pavioInferior), volumeAcimaMedia20(candle));
				listaMartelo.add(martelo);
				
				Operacao operacao = new Operacao(candle.data, Padroes.MARTELO, (candle.maxima + 0.01), (candle.minima - 0.01), projecaoPositiva(candle), candle.minima - 0.01);
				operacao.setMartelo(martelo);
				TreinamentoRedeNeural.adicionarOperacao(operacao);
				
				return true;
				
			}
		
		}else {
			System.out.println("Gráfico Vazio");
		}
		
		return false;
		
	}
	
	private void classificarMartelo(Candle candle) {
		
		
		
	}
	
	private TipoCandle tipoCandle(Candle candle) {
		
		TipoCandle tipo;
		
		if(candle.abertura < candle.fechamento) {
			tipo = TipoCandle.POSITIVO;
			return tipo;
		}
		if(candle.abertura > candle.fechamento) {
			tipo = TipoCandle.NEGATIVO;
			return tipo;
		}
		if(candle.abertura == candle.fechamento){
			tipo = TipoCandle.NEUTRO;
			return tipo;
		}
		return null;
	}
	
	private Double pavioSuperiorEmPorcentagem(Candle candle) {
		
		Double pavio;
		
		if(candle!=null) {
			if(candle.abertura < candle.fechamento) {
				pavio = 100*(candle.maxima - candle.fechamento)/ (candle.maxima - candle.minima);
				return pavio;
			}else{
				pavio = 100*(candle.maxima - candle.abertura)/ (candle.maxima - candle.minima);
				return pavio;
			}
		}
		
		return null;
		
	}
	
	private Double projecaoPositiva(Candle candle) {
		
		Double projecao, projecaoFinal;
		
		projecao = Math.abs((((candle.minima-0.02)*100)/(candle.maxima+0.02))-100);
		projecaoFinal = ((projecao+100)*candle.maxima)/100;
		
		return projecaoFinal;
		
	}
	
	private Double projecaoNegativa(Candle candle) {
		
		Double projecao;
		
		projecao = candle.minima - (candle.maxima - candle.minima);
		
		return projecao;
		
	}
	
	private Double pavioInferiorEmPorcentagem(Candle candle) {
		
		Double pavio;
		
		if(candle!=null) {
			if(candle.abertura < candle.fechamento) {
				pavio = 100*(candle.abertura - candle.minima)/ (candle.maxima - candle.minima);
				return pavio;
			}else{
				pavio = 100*(candle.fechamento - candle.minima)/ (candle.maxima - candle.minima);
				return pavio;
			}
		}
			
		return null;
		
	}
	
	private TendenciaMediaCurta tendenciaMediaCurta(Candle candle) {
		//Tendencia das medias de 8 e 20
		
		TendenciaMediaCurta tendencia;
		
		if(retornaMediaMovelPorData(candle.data, MEDIA) != null) {
		
			if(retornaMediaMovelPorData(candle.data, MEDIACURTA) > retornaMediaMovelPorData(candle.data, MEDIA)) {
				tendencia = TendenciaMediaCurta.ALTA;
				return tendencia;
			}else if(retornaMediaMovelPorData(candle.data, MEDIACURTA) < retornaMediaMovelPorData(candle.data, MEDIA)) {
				tendencia = TendenciaMediaCurta.BAIXA;
				return tendencia;
			}else if(retornaMediaMovelPorData(candle.data, MEDIACURTA) == retornaMediaMovelPorData(candle.data, MEDIA)) {
				tendencia = TendenciaMediaCurta.NEUTRA;
				return tendencia;
			}
		
		}
		
		return null;
		
	}
	
	private Boolean volumeAcimaMedia20(Candle candle) {
		
		if(retornaMediaMovelVolumePorData(candle.data, MEDIA) != null) {
			if(retornaMediaMovelVolumePorData(candle.data, MEDIA)>=candle.volume) {
				return true;
			}else {
				return false;
			}
		}
		
		return null;
	}
	
	private PavioSuperior classificaPavioSuperior(Double pavioSuperior) {
		
		if(pavioSuperior == 0) {
			return PavioSuperior.SEMPAVIO;
		}else if(pavioSuperior > 0 && pavioSuperior <= 5) {
			return PavioSuperior.PAVIO5PORCENTO;
		}else if(pavioSuperior > 5 && pavioSuperior <= 10) {
			return PavioSuperior.PAVIO10PORCENTO;
		}else if(pavioSuperior > 10 && pavioSuperior <= 33) {
			return PavioSuperior.PAVIO33PORCENTO;
		}else if(pavioSuperior > 33 && pavioSuperior <= 40) {
			return PavioSuperior.PAVIO40PORCENTO;
		}else if(pavioSuperior > 40 && pavioSuperior <= 67) {
			return PavioSuperior.PAVIO67PORCENTO;
		}else if(pavioSuperior > 67 && pavioSuperior <= 80) {
			return PavioSuperior.PAVIO80PORCENTO;
		}else if(pavioSuperior > 80 && pavioSuperior <= 90) {
			return PavioSuperior.PAVIO90PORCENTO;
		}else if(pavioSuperior > 90 && pavioSuperior <= 95) {
			return PavioSuperior.PAVIO95PORCENTO;
		}else if(pavioSuperior > 95 && pavioSuperior <= 100) {
			return PavioSuperior.PAVIO100PORCENTO;
		}
		
		return null;
		
	}
	
private PavioInferior classificaPavioInferior(Double pavioInferior) {
		
		if(pavioInferior == 0) {
			return PavioInferior.SEMPAVIO;
		}else if(pavioInferior > 0 && pavioInferior <= 5) {
			return PavioInferior.PAVIO5PORCENTO;
		}else if(pavioInferior > 5 && pavioInferior <= 10) {
			return PavioInferior.PAVIO10PORCENTO;
		}else if(pavioInferior > 10 && pavioInferior <= 33) {
			return PavioInferior.PAVIO33PORCENTO;
		}else if(pavioInferior > 33 && pavioInferior <= 40) {
			return PavioInferior.PAVIO40PORCENTO;
		}else if(pavioInferior > 40 && pavioInferior <= 67) {
			return PavioInferior.PAVIO67PORCENTO;
		}else if(pavioInferior > 67 && pavioInferior <= 80) {
			return PavioInferior.PAVIO80PORCENTO;
		}else if(pavioInferior > 80 && pavioInferior <= 90) {
			return PavioInferior.PAVIO90PORCENTO;
		}else if(pavioInferior > 90 && pavioInferior <= 95) {
			return PavioInferior.PAVIO95PORCENTO;
		}else if(pavioInferior > 95 && pavioInferior <= 100) {
			return PavioInferior.PAVIO100PORCENTO;
		}
		
		return null;
		
	}
	
	private int distAberturaParaMaxima(Candle candle) {
		double dist = 100*(candle.maxima - candle.abertura)/ (candle.maxima - candle.minima);
		
		if(candle.abertura == candle.maxima) {
			return 1;
		}
		if(dist!=0 && dist<=5) {
			return 2;
		}
		if(dist>5 && dist<=10) {
			return 3;
		}
		return 0;
		
	}
	
	private int distFechamentoParaMaxima(Candle candle) {
		double dist = 100*(candle.maxima - candle.fechamento)/ (candle.maxima - candle.minima);
		
		if(dist == 0) {
			return 1;
		}
		if(dist!=0 && dist<=5) {
			return 2;
		}
		if(dist>5 && dist<=10) {
			return 3;
		}
		return 0;
		
	}
	
	private int tamanhoCorpoPavio(Candle candle) {
		
		if(candle == null) {
			return 0;
		}else {
			if(candle.abertura >= candle.fechamento){
				double tam = 100*(candle.abertura - candle.fechamento)/(candle.abertura - candle.minima);
				if(tam <= 33 && tam>20) {
					return 1;
				}
				if(tam <=20 && tam>10) {
					return 2;
				}
				if(tam<=10) {
					return 3;
				}
			}else {
				double tam = 100*(candle.fechamento - candle.abertura)/(candle.fechamento - candle.minima);
				if(tam <= 33 && tam>20) {
					return 1;
				}
				if(tam <=20 && tam>10) {
					return 2;
				}
				if(tam<=10) {
					return 3;
				}
			}
		}
		return 0;
	}
	
	private Double retornaMediaMovelPorData(LocalDate data, int media) {
		
		if(Grafico.listaDeMedias != null) {
			for(int i=0; i<Grafico.listaDeMedias.size(); i++) {
				if(Grafico.listaDeMedias.get(i).getData() == data) {
					if(Grafico.listaDeMedias.get(i).getMEDIA() == media) {
						return Grafico.listaDeMedias.get(i).getValor();
					}
				}
			}
		}
		return null;
	}
	
	private Double retornaMediaMovelVolumePorData(LocalDate data, int media) {
		if(Grafico.listaDeMedias != null) {
			for(int i=0; i<Grafico.listaDeMedias.size(); i++) {
				if(Grafico.listaDeMedias.get(i).getData() == data && Grafico.listaDeMedias.get(i).getMEDIA() == media) {
					return Grafico.listaDeMedias.get(i).getValorMediaVolume();
				}
			}
		}
		return null;
	}

}
