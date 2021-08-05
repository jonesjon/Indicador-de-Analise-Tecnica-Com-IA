package br.iesb.indicador_analise_grafica;

import java.time.LocalDate;
import java.util.ArrayList;

import br.iesb.indicador_analise_grafica.service.MarteloService;
import br.iesb.indicador_analise_grafica.service.OperacaoService;

public class RedeNeural {

	ArrayList<Candle> ultimosCandles = new ArrayList<Candle>();
	static ArrayList<Martelo> listaMartelo = new ArrayList<Martelo>();

	static int cont1 = 0;
	static int cont2 = 0;
	private final int MEDIACURTA = 8;
	private final int MEDIA = 20;
	private final int MEDIALONGA = 200;
	private static int countID = 0;
	private final static int PORCENTAGEMMAXIMAENGOLFO = 10;

	public static Operacao procuraPadraoMartelo(InfoCandle infoCandle) {

		if (infoCandle != null) {

			Double pavioSuperior = pavioSuperiorEmPorcentagem(infoCandle);
			Double pavioInferior = pavioInferiorEmPorcentagem(infoCandle);

			// Condicoes para Martelo
			if (condicaoParaMartelo(infoCandle, pavioSuperior, pavioInferior)) {
				countID++;
				Operacao operacao = new Operacao(infoCandle.getData(), infoCandle.getNomeDoPapel(), Padroes.MARTELO.getDescricao(),
						precoDeAlvoMaxima(infoCandle), precoDeAlvoMinima(infoCandle), projecaoPositiva(infoCandle),
						precoDeAlvoMinima(infoCandle));
				operacao.setEntrada(Entrada.COMPRA.getDescricao());
				Martelo martelo = new Martelo(countID,
						tipoCandle(infoCandle).getTipo(), classificaPavioSuperior(pavioSuperior).getDescricao(),
						classificaPavioInferior(pavioInferior).getDescricao(), volumeAcimaMedia20(infoCandle),
						operacao);
				operacao.setMartelo(martelo);
				OperacaoService.adicionaOperacao(operacao);
				MarteloService.adicionaMartelo(martelo);

				return operacao;
			}
		}

		return null;

	}

	public static Operacao procuraPadraoEngolfo(ArrayList<InfoCandle> listInfoCandle) {

		if (listInfoCandle != null) {
			
			InfoCandle infoCandle = listInfoCandle.get(listInfoCandle.size()-1);
			
			Double pavioSuperior = pavioSuperiorEmPorcentagem(infoCandle);
			Double pavioInferior = pavioInferiorEmPorcentagem(infoCandle);
			Boolean validaMarubozu = false;
			Double variacaoInfoCandle = 0.0;
			Double variacaoMedia = 0.0;
			Double variacaoSoma = 0.0;
			
			for(int i = 0; i < listInfoCandle.size()-1; i++) {
				variacaoSoma += calculaVariacaoCandle(listInfoCandle.get(i));
			}
			
			variacaoMedia = variacaoSoma/(listInfoCandle.size()-1);
			
			variacaoInfoCandle = calculaVariacaoCandle(infoCandle);
			
			validaMarubozu = validaMarubozuPelaVariacao(variacaoInfoCandle, variacaoMedia);

			// Condicoes para Marubozu
			if (condicaoParaMarubozu(pavioSuperior, pavioInferior, validaMarubozu)) {
				
				

				return null;
			}
		}

		return null;

	}

	private static double precoDeAlvoMinima(InfoCandle infoCandle) {
		return infoCandle.getMinima() - 0.01;
	}

	private static double precoDeAlvoMaxima(InfoCandle infoCandle) {
		return infoCandle.getMaxima() + 0.01;
	}

	private static Boolean validaMarubozuPelaVariacao(Double variacaoInfoCandle, Double variacaoMedia) {
		
		if(variacaoInfoCandle >= (variacaoMedia*2)) {
			return true;
		}
		return false;
	}

	private static Double calculaVariacaoCandle(InfoCandle infoCandle) {
		
		Double variacao = 0.0;
		
		if(infoCandle != null) {
			
			if(infoCandle.getAbertura()>infoCandle.getFechamento()) {
				
				return variacao =((infoCandle.getFechamento()/infoCandle.getAbertura())-1)*100;
				
			}else if(infoCandle.getFechamento()>infoCandle.getAbertura()) {
				
				return variacao =((infoCandle.getAbertura()/infoCandle.getFechamento())-1)*100;
				
			}else if(infoCandle.getMaxima()==infoCandle.getMinima()) {
				return variacao;
			}
			
		}
		
		return null;
	}

	private static boolean condicaoParaMarubozu(Double pavioSuperior, Double pavioInferior, Boolean validaMarubozu) {
		return pavioSuperior <= PORCENTAGEMMAXIMAENGOLFO && pavioInferior <= PORCENTAGEMMAXIMAENGOLFO && validaMarubozu == true;
	}

	private static boolean condicaoParaMartelo(InfoCandle infoCandle, Double pavioSuperior, Double pavioInferior) {
		return pavioSuperior <= 10 && pavioInferior >= 67
				&& tendenciaMediaCurta(infoCandle) == TendenciaMediaCurta.BAIXA;
	}

	private void classificarMartelo(Candle candle) {

	}

	private static TipoCandle tipoCandle(InfoCandle infoCandle) {

		TipoCandle tipo;

		if (infoCandle.getAbertura() < infoCandle.getFechamento()) {
			tipo = TipoCandle.POSITIVO;
			return tipo;
		}
		if (infoCandle.getAbertura() > infoCandle.getFechamento()) {
			tipo = TipoCandle.NEGATIVO;
			return tipo;
		}
		if (infoCandle.getAbertura() == infoCandle.getFechamento()) {
			tipo = TipoCandle.NEUTRO;
			return tipo;
		}
		tipo = TipoCandle.NULL;
		return tipo;
	}

	private static Double pavioSuperiorEmPorcentagem(InfoCandle infoCandle) {

		Double pavio;

		if (infoCandle != null) {
			if (infoCandle.getAbertura() < infoCandle.getFechamento()) {
				pavio = 100 * (infoCandle.getMaxima() - infoCandle.getFechamento())
						/ (infoCandle.getMaxima() - infoCandle.getMinima());
				return pavio;
			} else {
				pavio = 100 * (infoCandle.getMaxima() - infoCandle.getAbertura())
						/ (infoCandle.getMaxima() - infoCandle.getMinima());
				return pavio;
			}
		}

		return null;

	}

	private static Double projecaoPositiva(InfoCandle infoCandle) {

		Double projecao, projecaoFinal;

		projecao = Math.abs((((infoCandle.getMinima() - 0.02) * 100) / (infoCandle.getMaxima() + 0.02)) - 100);
		projecaoFinal = ((projecao + 100) * infoCandle.getMaxima()) / 100;

		return projecaoFinal;

	}

	private Double projecaoNegativa(Candle candle) {

		Double projecao;

		projecao = candle.minima - (candle.maxima - candle.minima);

		return projecao;

	}

	private static Double pavioInferiorEmPorcentagem(InfoCandle infoCandle) {

		Double pavio;

		if (infoCandle != null) {
			if (infoCandle.getAbertura() < infoCandle.getFechamento()) {
				pavio = 100 * (infoCandle.getAbertura() - infoCandle.getMinima())
						/ (infoCandle.getMaxima() - infoCandle.getMinima());
				return pavio;
			} else {
				pavio = 100 * (infoCandle.getFechamento() - infoCandle.getMinima())
						/ (infoCandle.getMaxima() - infoCandle.getMinima());
				return pavio;
			}
		}

		return null;

	}

	private static TendenciaMediaCurta tendenciaMediaCurta(InfoCandle infoCandle) {
		// Tendencia das medias de 8 e 20

		TendenciaMediaCurta tendencia;

		if (infoCandle.getPrecoMedia20() != null) {

			if (infoCandle.getPrecoMedia8() > infoCandle.getPrecoMedia20()) {
				tendencia = TendenciaMediaCurta.ALTA;
				return tendencia;
			} else if (infoCandle.getPrecoMedia8() < infoCandle.getPrecoMedia20()) {
				tendencia = TendenciaMediaCurta.BAIXA;
				return tendencia;
			} else if (infoCandle.getPrecoMedia8() == infoCandle.getPrecoMedia20()) {
				tendencia = TendenciaMediaCurta.NEUTRA;
				return tendencia;
			}
		}

		return null;

	}

	private static Boolean volumeAcimaMedia20(InfoCandle infoCandle) {

		if (infoCandle.getVolume() != null) {
			if (infoCandle.getVolume() >= infoCandle.getVolumeMedia20()) {
				return true;
			} else {
				return false;
			}
		}

		return null;
	}

	private static PavioSuperior classificaPavioSuperior(Double pavioSuperior) {

		if (pavioSuperior == 0) {
			return PavioSuperior.SEMPAVIO;
		} else if (pavioSuperior > 0 && pavioSuperior <= 5) {
			return PavioSuperior.PAVIO5PORCENTO;
		} else if (pavioSuperior > 5 && pavioSuperior <= 10) {
			return PavioSuperior.PAVIO10PORCENTO;
		} else if (pavioSuperior > 10 && pavioSuperior <= 33) {
			return PavioSuperior.PAVIO33PORCENTO;
		} else if (pavioSuperior > 33 && pavioSuperior <= 40) {
			return PavioSuperior.PAVIO40PORCENTO;
		} else if (pavioSuperior > 40 && pavioSuperior <= 67) {
			return PavioSuperior.PAVIO67PORCENTO;
		} else if (pavioSuperior > 67 && pavioSuperior <= 80) {
			return PavioSuperior.PAVIO80PORCENTO;
		} else if (pavioSuperior > 80 && pavioSuperior <= 90) {
			return PavioSuperior.PAVIO90PORCENTO;
		} else if (pavioSuperior > 90 && pavioSuperior <= 95) {
			return PavioSuperior.PAVIO95PORCENTO;
		} else if (pavioSuperior > 95 && pavioSuperior <= 100) {
			return PavioSuperior.PAVIO100PORCENTO;
		}

		return PavioSuperior.NULL;

	}

	private static PavioInferior classificaPavioInferior(Double pavioInferior) {

		if (pavioInferior.equals(0.0)) {
			return PavioInferior.SEMPAVIO;
		} else if (pavioInferior > 0 && pavioInferior <= 5) {
			return PavioInferior.PAVIO5PORCENTO;
		} else if (pavioInferior > 5 && pavioInferior <= 10) {
			return PavioInferior.PAVIO10PORCENTO;
		} else if (pavioInferior > 10 && pavioInferior <= 33) {
			return PavioInferior.PAVIO33PORCENTO;
		} else if (pavioInferior > 33 && pavioInferior <= 40) {
			return PavioInferior.PAVIO40PORCENTO;
		} else if (pavioInferior > 40 && pavioInferior <= 67) {
			return PavioInferior.PAVIO67PORCENTO;
		} else if (pavioInferior > 67 && pavioInferior <= 80) {
			return PavioInferior.PAVIO80PORCENTO;
		} else if (pavioInferior > 80 && pavioInferior <= 90) {
			return PavioInferior.PAVIO90PORCENTO;
		} else if (pavioInferior > 90 && pavioInferior <= 95) {
			return PavioInferior.PAVIO95PORCENTO;
		} else if (pavioInferior > 95 && pavioInferior <= 100) {
			return PavioInferior.PAVIO100PORCENTO;
		}

		return PavioInferior.NULL;

	}

	private int distAberturaParaMaxima(Candle candle) {
		double dist = 100 * (candle.maxima - candle.abertura) / (candle.maxima - candle.minima);

		if (candle.abertura == candle.maxima) {
			return 1;
		}
		if (dist != 0 && dist <= 5) {
			return 2;
		}
		if (dist > 5 && dist <= 10) {
			return 3;
		}
		return 0;

	}

	private int distFechamentoParaMaxima(Candle candle) {
		double dist = 100 * (candle.maxima - candle.fechamento) / (candle.maxima - candle.minima);

		if (dist == 0) {
			return 1;
		}
		if (dist != 0 && dist <= 5) {
			return 2;
		}
		if (dist > 5 && dist <= 10) {
			return 3;
		}
		return 0;

	}

	private int tamanhoCorpoPavio(Candle candle) {

		if (candle == null) {
			return 0;
		} else {
			if (candle.abertura >= candle.fechamento) {
				double tam = 100 * (candle.abertura - candle.fechamento) / (candle.abertura - candle.minima);
				if (tam <= 33 && tam > 20) {
					return 1;
				}
				if (tam <= 20 && tam > 10) {
					return 2;
				}
				if (tam <= 10) {
					return 3;
				}
			} else {
				double tam = 100 * (candle.fechamento - candle.abertura) / (candle.fechamento - candle.minima);
				if (tam <= 33 && tam > 20) {
					return 1;
				}
				if (tam <= 20 && tam > 10) {
					return 2;
				}
				if (tam <= 10) {
					return 3;
				}
			}
		}
		return 0;
	}

	private Double retornaMediaMovelPorData(LocalDate data, int media) {

		if (Grafico.listaDeMedias != null) {
			for (int i = 0; i < Grafico.listaDeMedias.size(); i++) {
				if (Grafico.listaDeMedias.get(i).getData() == data) {
					if (Grafico.listaDeMedias.get(i).getMEDIA() == media) {
						return Grafico.listaDeMedias.get(i).getValor();
					}
				}
			}
		}
		return null;
	}

	private Double retornaMediaMovelVolumePorData(LocalDate data, int media) {
		if (Grafico.listaDeMedias != null) {
			for (int i = 0; i < Grafico.listaDeMedias.size(); i++) {
				if (Grafico.listaDeMedias.get(i).getData() == data
						&& Grafico.listaDeMedias.get(i).getMEDIA() == media) {
					return Grafico.listaDeMedias.get(i).getValorMediaVolume();
				}
			}
		}
		return null;
	}

}
