package br.iesb.indicador_analise_grafica;

import java.time.LocalDate;
import java.util.ArrayList;

import br.iesb.indicador_analise_grafica.service.MarteloService;
import br.iesb.indicador_analise_grafica.service.MarubozuService;
import br.iesb.indicador_analise_grafica.service.OperacaoService;

public class RedeNeural {

	ArrayList<Candle> ultimosCandles = new ArrayList<Candle>();
	static ArrayList<Martelo> listaMartelo = new ArrayList<Martelo>();

	static int cont1 = 0;
	static int cont2 = 0;
	private final int MEDIACURTA = 8;
	private final int MEDIA = 20;
	private final int MEDIALONGA = 200;
	private static int countIDMartelo = 0;
	private static int countIDEngolfo = 0;
	private static int countIDMarubozu = 0;
	private final static int PORCENTAGEMMAXIMAENGOLFO = 10;

	public static Operacao procuraPadraoMartelo(InfoCandle infoCandle) {

		if (infoCandle != null) {

			Double pavioSuperior = pavioSuperiorEmPorcentagem(infoCandle);
			Double pavioInferior = pavioInferiorEmPorcentagem(infoCandle);

			// Condicoes para Martelo
			if (condicaoParaMartelo(infoCandle, pavioSuperior, pavioInferior)) {
				
				Operacao operacao = new Operacao(infoCandle.getData(), infoCandle.getNomeDoPapel(), Padroes.MARTELO.getDescricao());
				operacao.setEntrada(Entrada.COMPRA.getDescricao());
				operacao.setPrecoEntrada(setPrecoEntradaCompra(infoCandle));
				operacao.setPrecoStop(setPrecoStopCompra(infoCandle));
				operacao.setPrecoPrimeiroAlvoFibonacci(calculaPrecoPrimeiroAlvoFibonacci(infoCandle, Entrada.COMPRA));
				operacao.setPrecoSegundoAlvoFibonacci(calculaPrecoSegundoAlvoFibonacci(infoCandle, Entrada.COMPRA));
				operacao.setPrecoTerceiroAlvoFibonacci(calculaPrecoTerceiroAlvoFibonacci(infoCandle, Entrada.COMPRA));
				
				
				Martelo martelo = new Martelo();
				
				martelo.setTipo(tipoCandle(infoCandle).getTipo());
				martelo.setPavioSuperior(classificaPavioSuperior(pavioSuperior).getDescricao());
				martelo.setPavioInferior(classificaPavioInferior(pavioInferior).getDescricao());
				martelo.setMarteloAcimaMedia200(verificaSePrecoAcimaMedia200(infoCandle));
				martelo.setVolumeAcimaMedia20(volumeAcimaMedia20(infoCandle));
				martelo.setOperacao(operacao);
				
				operacao.setMartelo(martelo);
				OperacaoService.adicionaOperacao(operacao);
				MarteloService.adicionaMartelo(martelo);

				return operacao;
			}
		}

		return null;

	}

	private static Boolean verificaSePrecoAcimaMedia200(InfoCandle infoCandle) {
		
		if(infoCandle.getPrecoMedia200()!= null) {
			return infoCandle.getMaxima() > infoCandle.getPrecoMedia200();
		}
		
		return null;
	}

	public static Boolean procuraPadraoMarubozu(ArrayList<InfoCandle> listInfoCandle) {

		if (listInfoCandle != null && listInfoCandle.size() >= TreinamentoRedeNeural.LIMITDECANDLEMARUBOZUENGOLFO) {

			InfoCandle infoCandle = listInfoCandle.get(0);

			Double pavioSuperior = pavioSuperiorEmPorcentagem(infoCandle);
			Double pavioInferior = pavioInferiorEmPorcentagem(infoCandle);
			Boolean validaMarubozu = false;
			Double variacaoInfoCandle = 0.0;
			Double variacaoMedia = 0.0;
			Double variacaoSoma = 0.0;

			for (int i = 1; i < listInfoCandle.size(); i++) {
				if (verificaCandleParado(listInfoCandle.get(i))) {
					return false;
				}
				variacaoSoma += calculaVariacaoCandle(listInfoCandle.get(i));
			}

			variacaoMedia = variacaoSoma / (listInfoCandle.size() - 1);
			variacaoInfoCandle = calculaVariacaoCandle(infoCandle);
			validaMarubozu = validaMarubozuPelaVariacao(variacaoInfoCandle, variacaoMedia);

			// Condicoes para Marubozu
			if (condicaoParaMarubozu(pavioSuperior, pavioInferior, validaMarubozu)) {
				
				Operacao operacao = new Operacao(infoCandle.getData(), infoCandle.getNomeDoPapel(), Padroes.MARUBOZU.getDescricao());

				if (tipoCandle(infoCandle) == TipoCandle.POSITIVO) {
					
					operacao.setEntrada(Entrada.COMPRA.getDescricao());
					operacao.setPrecoEntrada(setPrecoEntradaCompra(infoCandle));
					operacao.setPrecoStop(setPrecoStopCompra(infoCandle));
					operacao.setPrecoPrimeiroAlvoFibonacci(calculaPrecoPrimeiroAlvoFibonacci(infoCandle, Entrada.COMPRA));
					operacao.setPrecoSegundoAlvoFibonacci(calculaPrecoSegundoAlvoFibonacci(infoCandle, Entrada.COMPRA));
					operacao.setPrecoTerceiroAlvoFibonacci(calculaPrecoTerceiroAlvoFibonacci(infoCandle, Entrada.COMPRA));
					
					Marubozu marubozu = new Marubozu(countIDMarubozu, tipoCandle(infoCandle).getTipo(),
							classificaPavioSuperior(pavioSuperior).getDescricao(),
							classificaPavioInferior(pavioInferior).getDescricao(), volumeAcimaMedia20(infoCandle),
							classificaVariacaoPreco(variacaoInfoCandle, variacaoMedia).getDescricao(), operacao);

					operacao.setEntrada(Entrada.COMPRA.getDescricao());
					operacao.setMarubozu(marubozu);
					OperacaoService.adicionaOperacao(operacao);
					MarubozuService.adicionaMarubozu(marubozu);

					return true;
				} else {

					operacao.setEntrada(Entrada.VENDA.getDescricao());
					operacao.setPrecoEntrada(setPrecoEntradaVenda(infoCandle));
					operacao.setPrecoStop(setPrecoStopVenda(infoCandle));
					operacao.setPrecoPrimeiroAlvoFibonacci(calculaPrecoPrimeiroAlvoFibonacci(infoCandle, Entrada.VENDA));
					operacao.setPrecoSegundoAlvoFibonacci(calculaPrecoSegundoAlvoFibonacci(infoCandle, Entrada.VENDA));
					operacao.setPrecoTerceiroAlvoFibonacci(calculaPrecoTerceiroAlvoFibonacci(infoCandle, Entrada.VENDA));
					
					Marubozu marubozu = new Marubozu(countIDMarubozu, tipoCandle(infoCandle).getTipo(),
							classificaPavioSuperior(pavioSuperior).getDescricao(),
							classificaPavioInferior(pavioInferior).getDescricao(), volumeAcimaMedia20(infoCandle),
							classificaVariacaoPreco(variacaoInfoCandle, variacaoMedia).getDescricao(), operacao);
					
					operacao.setMarubozu(marubozu);
					OperacaoService.adicionaOperacao(operacao);
					MarubozuService.adicionaMarubozu(marubozu);

					return true;
				}

			}
		}

		return false;

	}

	private static Double setPrecoStopVenda(InfoCandle infoCandle) {
		return (infoCandle.getMaxima()+0.01);
	}

	private static Double setPrecoEntradaVenda(InfoCandle infoCandle) {
		return (infoCandle.getMinima()-0.01);
	}

	private static double setPrecoStopCompra(InfoCandle infoCandle) {
		return (infoCandle.getMinima()-0.01);
	}

	private static double setPrecoEntradaCompra(InfoCandle infoCandle) {
		return (infoCandle.getMaxima() + 0.01);
	}
	
	private static Double calculaPrecoTerceiroAlvoFibonacci(InfoCandle infoCandle, Entrada entrada) {
		
		if(infoCandle != null && entrada != null) {
			if(entrada == Entrada.COMPRA) {
				
				return Math.abs(((infoCandle.getMaxima() - infoCandle.getMinima())*1.618) + infoCandle.getMaxima());
				
			}else if(entrada == Entrada.VENDA) {
				
				return Math.abs(((infoCandle.getMaxima() - infoCandle.getMinima())*1.618) - infoCandle.getMinima());
				
			}
		}
		
		return null;
	}

	private static Double calculaPrecoSegundoAlvoFibonacci(InfoCandle infoCandle, Entrada entrada) {
		
		if(infoCandle != null && entrada != null) {
			if(entrada == Entrada.COMPRA) {
				
				return Math.abs(((infoCandle.getMaxima() - infoCandle.getMinima())) + infoCandle.getMaxima());
				
			}else if(entrada == Entrada.VENDA) {
				
				return Math.abs(((infoCandle.getMaxima() - infoCandle.getMinima())) - infoCandle.getMinima());
				
			}
		}
		
		return null;
	}

	private static Double calculaPrecoPrimeiroAlvoFibonacci(InfoCandle infoCandle, Entrada entrada) {
		
		if(infoCandle != null && entrada != null) {
			if(entrada == Entrada.COMPRA) {
				
				return Math.abs((((infoCandle.getMaxima() - infoCandle.getMinima())*0.618) + infoCandle.getMaxima()));
				
			}else if(entrada == Entrada.VENDA) {
				
				return Math.abs(((infoCandle.getMaxima() - infoCandle.getMinima())*0.618) - infoCandle.getMinima());
				
			}
		}
		
		return null;
	}

	public static Boolean procuraPadraoEngolfo(ArrayList<InfoCandle> listInfoCandle) {

		if (listInfoCandle != null && listInfoCandle.size() >= TreinamentoRedeNeural.LIMITDECANDLEMARUBOZUENGOLFO) {

			InfoCandle infoCandle = listInfoCandle.get(0);

			Double pavioSuperior = pavioSuperiorEmPorcentagem(infoCandle);
			Double pavioInferior = pavioInferiorEmPorcentagem(infoCandle);
			Boolean validaMarubozu = false;
			Double variacaoInfoCandle = 0.0;
			Double variacaoMedia = 0.0;
			Double variacaoSoma = 0.0;

			for (int i = 1; i < listInfoCandle.size(); i++) {
				if (verificaCandleParado(listInfoCandle.get(i))) {
					return false;
				}
				variacaoSoma += calculaVariacaoCandle(listInfoCandle.get(i));
			}

			variacaoMedia = variacaoSoma / (listInfoCandle.size() - 1);
			variacaoInfoCandle = calculaVariacaoCandle(infoCandle);
			validaMarubozu = validaMarubozuPelaVariacao(variacaoInfoCandle, variacaoMedia);

			// Condicoes para Marubozu
			if (condicaoParaMarubozu(pavioSuperior, pavioInferior, validaMarubozu)) {
				
				//Condicao Para Engolfo
				if (tipoCandle(infoCandle) == TipoCandle.POSITIVO) {
					
					if(condicaoParaEngolfoDeAlta(listInfoCandle, infoCandle)) {
						
						
						
					}
	
						
						return true;
					} else {
						
						if(condicaoParaEngolfoDeBaixa(listInfoCandle, infoCandle)) {
							
						}
	
						
	
						return true;
					}
			}
		}

		return false;

	}

	private static boolean condicaoParaEngolfoDeBaixa(ArrayList<InfoCandle> listInfoCandle, InfoCandle infoCandle) {
		return infoCandle.getFechamento() < listInfoCandle.get(1).getMinima() && infoCandle.getAbertura() > listInfoCandle.get(1).getMaxima();
	}

	private static boolean condicaoParaEngolfoDeAlta(ArrayList<InfoCandle> listInfoCandle, InfoCandle infoCandle) {
		return infoCandle.getFechamento() > listInfoCandle.get(1).getMaxima() &&  infoCandle.getAbertura() < listInfoCandle.get(1).getMinima();
	}

	private static boolean verificaCandleParado(InfoCandle infoCandle) {
		return infoCandle.getFechamento().compareTo(infoCandle.getAbertura()) == 0
				&& infoCandle.getMaxima().compareTo(infoCandle.getAbertura()) == 0
				&& infoCandle.getMinima().compareTo(infoCandle.getAbertura()) == 0;
	}

	private static VariacaoPreco classificaVariacaoPreco(Double variacaoInfoCandle, Double variacaoMedia) {

		Double calculaVariacao = variacaoInfoCandle / variacaoMedia;

		if (calculaVariacao >= 1 && calculaVariacao < 2) {
			return VariacaoPreco.ATE2VEZES;
		} else if (calculaVariacao >= 2 && calculaVariacao < 3) {
			return VariacaoPreco.DE2A3VEZES;
		} else if (calculaVariacao >= 3 && calculaVariacao < 4) {
			return VariacaoPreco.DE3A4VEZES;
		} else if (calculaVariacao >= 4 && calculaVariacao < 5) {
			return VariacaoPreco.DE4A5VEZES;
		} else if (calculaVariacao >= 5) {
			return VariacaoPreco.MAIORQUE5VEZES;
		}

		return VariacaoPreco.NULL;
	}

	private static Boolean validaMarubozuPelaVariacao(Double variacaoInfoCandle, Double variacaoMedia) {

		if (variacaoInfoCandle != null && variacaoMedia != null && variacaoInfoCandle > 0 && variacaoMedia > 0) {

			if (variacaoInfoCandle >= (variacaoMedia * 2)) {
				return true;
			}

		}

		return false;
	}

	private static Double calculaVariacaoCandle(InfoCandle infoCandle) {

		Double variacao = 0.0;

		if (infoCandle != null) {

			if (tipoCandle(infoCandle) == TipoCandle.NEGATIVO || tipoCandle(infoCandle) == TipoCandle.NEUTRO) {

				return variacao = Math.abs(((infoCandle.getMinima() / infoCandle.getMaxima()) - 1) * 100);

			} else if (tipoCandle(infoCandle) == TipoCandle.POSITIVO) {

				return variacao = Math.abs(((infoCandle.getMaxima() / infoCandle.getMinima()) - 1) * 100);

			} else if (infoCandle.getMaxima().compareTo(infoCandle.getMinima()) == 0) {
				return variacao;
			}

		}

		return null;
	}

	private static boolean condicaoParaMarubozu(Double pavioSuperior, Double pavioInferior, Boolean validaMarubozu) {
		return pavioSuperior <= PORCENTAGEMMAXIMAENGOLFO && pavioInferior <= PORCENTAGEMMAXIMAENGOLFO
				&& validaMarubozu == true;
	}

	private static boolean condicaoParaMartelo(InfoCandle infoCandle, Double pavioSuperior, Double pavioInferior) {
		return pavioSuperior <= 10 && pavioInferior >= 67
				&& tendenciaMediaCurta(infoCandle) == TendenciaMediaCurta.BAIXA;
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
		if (infoCandle.getAbertura().compareTo(infoCandle.getFechamento()) == 0) {
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
			} else if (infoCandle.getPrecoMedia8().compareTo(infoCandle.getPrecoMedia20()) == 0) {
				tendencia = TendenciaMediaCurta.NEUTRA;
				return tendencia;
			}
		}

		return null;

	}

	private static Boolean volumeAcimaMedia20(InfoCandle infoCandle) {

		if (infoCandle.getVolumeMedia20() != null) {
			if (infoCandle.getVolume() >= infoCandle.getVolumeMedia20()) {
				return true;
			} else {
				return false;
			}
		}

		return false;
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

		if (pavioInferior.compareTo(0.0) == 0) {
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

}
