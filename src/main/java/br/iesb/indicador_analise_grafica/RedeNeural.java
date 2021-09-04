package br.iesb.indicador_analise_grafica;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import br.iesb.indicador_analise_grafica.service.DojiService;
import br.iesb.indicador_analise_grafica.service.EngolfoService;
import br.iesb.indicador_analise_grafica.service.InfoCandleService;
import br.iesb.indicador_analise_grafica.service.MarteloService;
import br.iesb.indicador_analise_grafica.service.MarubozuService;
import br.iesb.indicador_analise_grafica.service.OperacaoService;


public class RedeNeural {

	ArrayList<Candle> ultimosCandles = new ArrayList<Candle>();
	static ArrayList<Martelo> listaMartelo = new ArrayList<Martelo>();

	static int cont1 = 0;
	static int cont2 = 0;
	private final static int MEDIACURTA = 8;
	private final static int MEDIA = 20;
	private final static int MEDIALONGA = 200;
	private static int countIDMartelo = 0;
	private static int countIDEngolfo = 0;
	private static int countIDMarubozu = 0;
	private final static int PORCENTAGEMMAXIMAENGOLFO = 10;
	private final static int PORCENTAGEMMAXIMAPAVIODOJI = 60;
	private final static int PORCENTAGEMMINIMAPAVIODOJI = 40;
	
	@Autowired
	private OperacaoService operacaoService;

	public Operacao procuraPadraoMartelo(InfoCandle infoCandle) {

		if (infoCandle != null) {

			Double pavioSuperior = pavioSuperiorEmPorcentagem(infoCandle);
			Double pavioInferior = pavioInferiorEmPorcentagem(infoCandle);
			
			// Condicoes para Martelo
			if (condicaoParaMartelo(infoCandle, pavioSuperior, pavioInferior)) {
				
				Operacao operacao = new Operacao();
				operacao.setPadrao(Padroes.MARTELO.getDescricao());
				operacao.setInfoCandle(infoCandle);
				operacao.setEntrada(Entrada.COMPRA.getDescricao());
				operacao.setPrecoEntrada(setPrecoEntradaCompra(infoCandle));
				operacao.setPrecoStop(setPrecoStopCompra(infoCandle));
				operacao.setPrecoPrimeiroAlvoFibonacci(calculaPrecoPrimeiroAlvoFibonacci(infoCandle, Entrada.COMPRA));
				operacao.setPrecoSegundoAlvoFibonacci(calculaPrecoSegundoAlvoFibonacci(infoCandle, Entrada.COMPRA));
				operacao.setPrecoTerceiroAlvoFibonacci(calculaPrecoTerceiroAlvoFibonacci(infoCandle, Entrada.COMPRA));
				
				
				Martelo martelo = new Martelo();
				
				martelo.setTipo(tipoCandle(infoCandle).getTipo());
				martelo.setPavioSuperior(pavioSuperior);
				martelo.setPavioInferior(pavioInferior);
				martelo.setMarteloAcimaMedia200(verificaSePrecoAcimaMedia200(infoCandle));
				martelo.setVolumeAcimaMedia20(volumeAcimaMedia20(infoCandle));
				martelo.setOperacao(operacao);
				
				operacao.setMartelo(martelo);
				operacaoService.adicionaOperacao(operacao);
				MarteloService.adicionaMartelo(martelo);

				return operacao;
			}
		}

		return null;

	}
	
	public Boolean procuraPadraoEngolfo(ArrayList<InfoCandle> listInfoCandle) {

		if (listInfoCandle != null && listInfoCandle.size() >= TreinamentoRedeNeural.LIMITDECANDLEENGOLFO) {

			if(condicaoParaEngolfoDeAlta(listInfoCandle)) {
				
				Double calculaVariacaoUltimoCandle = calculaVariacaoCandle(listInfoCandle.get(0));
				Double calculaVariacaoPrimeiroCandle = calculaVariacaoCandle(listInfoCandle.get(1));
				Double variacaoEngolfo = calculaVariacaoUltimoCandle / calculaVariacaoPrimeiroCandle;

				Operacao operacao = new Operacao();
				operacao.setPadrao(Padroes.MARTELO.getDescricao());
				operacao.setInfoCandle(listInfoCandle.get(0));
				operacao.setEntrada(Entrada.COMPRA.getDescricao());
				operacao.setPrecoEntrada(setPrecoEntradaCompra(listInfoCandle.get(0)));
				operacao.setPrecoStop(setPrecoStopCompra(listInfoCandle.get(0)));
				operacao.setPrecoPrimeiroAlvoFibonacci(calculaPrecoPrimeiroAlvoFibonacci(listInfoCandle.get(0), Entrada.COMPRA));
				operacao.setPrecoSegundoAlvoFibonacci(calculaPrecoSegundoAlvoFibonacci(listInfoCandle.get(0), Entrada.COMPRA));
				operacao.setPrecoTerceiroAlvoFibonacci(calculaPrecoTerceiroAlvoFibonacci(listInfoCandle.get(0), Entrada.COMPRA));

				Engolfo engolfo = new Engolfo();
				engolfo.setTipo(tipoCandle(listInfoCandle.get(0)).getTipo());
				engolfo.setPavioInferior(classificaPavioInferior(pavioInferiorEmPorcentagem(listInfoCandle.get(0))).getDescricao());
				engolfo.setPavioSuperior(classificaPavioSuperior(pavioSuperiorEmPorcentagem(listInfoCandle.get(0))).getDescricao());
				engolfo.setVolumeAcimaMedia20(volumeAcimaMedia20(listInfoCandle.get(0)));
				engolfo.setAcimaMedia8(verificaSePrecoFechamentoAcimaMedia(listInfoCandle.get(0), MEDIACURTA));
				engolfo.setAcimaMedia20(verificaSePrecoFechamentoAcimaMedia(listInfoCandle.get(0), MEDIA));
				engolfo.setAcimaMedia200(verificaSePrecoFechamentoAcimaMedia(listInfoCandle.get(0), MEDIALONGA));
				engolfo.setOperacao(operacao);
				
				operacao.setEngolfo(engolfo);
	
				operacaoService.adicionaOperacao(operacao);
				EngolfoService.adicionaEngolfo(engolfo);
				 
				 
			}else if(condicaoParaEngolfoDeBaixa(listInfoCandle)) {
				
				Double calculaVariacaoUltimoCandle = calculaVariacaoCandle(listInfoCandle.get(0));
				Double calculaVariacaoPrimeiroCandle = calculaVariacaoCandle(listInfoCandle.get(1));
				Double variacaoEngolfo = calculaVariacaoUltimoCandle / calculaVariacaoPrimeiroCandle;
				
				Operacao operacao = new Operacao();
				operacao.setPadrao(Padroes.MARTELO.getDescricao());
				operacao.setEntrada(Entrada.VENDA.getDescricao());
				operacao.setPrecoEntrada(setPrecoEntradaVenda(listInfoCandle.get(0)));
				operacao.setPrecoStop(setPrecoStopVenda(listInfoCandle.get(0)));
				operacao.setPrecoPrimeiroAlvoFibonacci(calculaPrecoPrimeiroAlvoFibonacci(listInfoCandle.get(0), Entrada.VENDA));
				operacao.setPrecoSegundoAlvoFibonacci(calculaPrecoSegundoAlvoFibonacci(listInfoCandle.get(0), Entrada.VENDA));
				operacao.setPrecoTerceiroAlvoFibonacci(calculaPrecoTerceiroAlvoFibonacci(listInfoCandle.get(0), Entrada.VENDA));
				
				Engolfo engolfo = new Engolfo();
				engolfo.setTipo(tipoCandle(listInfoCandle.get(0)).getTipo());
				engolfo.setPavioInferior(classificaPavioInferior(pavioInferiorEmPorcentagem(listInfoCandle.get(0))).getDescricao());
				engolfo.setPavioSuperior(classificaPavioSuperior(pavioSuperiorEmPorcentagem(listInfoCandle.get(0))).getDescricao());
				engolfo.setVolumeAcimaMedia20(volumeAcimaMedia20(listInfoCandle.get(0)));
				engolfo.setAcimaMedia8(verificaSePrecoFechamentoAcimaMedia(listInfoCandle.get(0), MEDIACURTA));
				engolfo.setAcimaMedia20(verificaSePrecoFechamentoAcimaMedia(listInfoCandle.get(0), MEDIA));
				engolfo.setAcimaMedia200(verificaSePrecoFechamentoAcimaMedia(listInfoCandle.get(0), MEDIALONGA));
				
				engolfo.setOperacao(operacao);
				
				operacao.setEngolfo(engolfo);
	
				operacaoService.adicionaOperacao(operacao);
				EngolfoService.adicionaEngolfo(engolfo);
				
			}
			
		}

		return false;

	}

	public Boolean procuraPadraoMarubozu(ArrayList<InfoCandle> listInfoCandle) {

		if (listInfoCandle != null && listInfoCandle.size() >= TreinamentoRedeNeural.LIMITDECANDLEMARUBOZU) {

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
			Double calculaVariacao = variacaoInfoCandle / variacaoMedia;
			
			validaMarubozu = validaMarubozuPelaVariacao(calculaVariacao);

			// Condicoes para Marubozu
			if (condicaoParaMarubozu(pavioSuperior, pavioInferior, validaMarubozu)) {
				
				Operacao operacao = new Operacao();
				operacao.setPadrao(Padroes.MARTELO.getDescricao());				
				operacao.setInfoCandle(infoCandle);
				
				if (tipoCandle(infoCandle) == TipoCandle.POSITIVO) {
					
					operacao.setEntrada(Entrada.COMPRA.getDescricao());
					operacao.setPrecoEntrada(setPrecoEntradaCompra(infoCandle));
					operacao.setPrecoStop(setPrecoStopCompra(infoCandle));
					operacao.setPrecoPrimeiroAlvoFibonacci(calculaPrecoPrimeiroAlvoFibonacci(infoCandle, Entrada.COMPRA));
					operacao.setPrecoSegundoAlvoFibonacci(calculaPrecoSegundoAlvoFibonacci(infoCandle, Entrada.COMPRA));
					operacao.setPrecoTerceiroAlvoFibonacci(calculaPrecoTerceiroAlvoFibonacci(infoCandle, Entrada.COMPRA));
					
					Marubozu marubozu = new Marubozu(tipoCandle(infoCandle).getTipo(),
							classificaPavioSuperior(pavioSuperior).getDescricao(),
							classificaPavioInferior(pavioInferior).getDescricao(), volumeAcimaMedia20(infoCandle),
							classificaVariacaoPreco(calculaVariacao).getDescricao(), operacao);

					operacao.setMarubozu(marubozu);
					operacaoService.adicionaOperacao(operacao);
					MarubozuService.adicionaMarubozu(marubozu);

					return true;
				} else {
					
					
					operacao.setEntrada(Entrada.VENDA.getDescricao());
					operacao.setPrecoEntrada(setPrecoEntradaVenda(infoCandle));
					operacao.setPrecoStop(setPrecoStopVenda(infoCandle));
					operacao.setPrecoPrimeiroAlvoFibonacci(calculaPrecoPrimeiroAlvoFibonacci(infoCandle, Entrada.VENDA));
					operacao.setPrecoSegundoAlvoFibonacci(calculaPrecoSegundoAlvoFibonacci(infoCandle, Entrada.VENDA));
					operacao.setPrecoTerceiroAlvoFibonacci(calculaPrecoTerceiroAlvoFibonacci(infoCandle, Entrada.VENDA));
					
					Marubozu marubozu = new Marubozu(tipoCandle(infoCandle).getTipo(),
							classificaPavioSuperior(pavioSuperior).getDescricao(),
							classificaPavioInferior(pavioInferior).getDescricao(), volumeAcimaMedia20(infoCandle),
							classificaVariacaoPreco(calculaVariacao).getDescricao(), operacao);
					
					operacao.setMarubozu(marubozu);
					operacaoService.adicionaOperacao(operacao);
					MarubozuService.adicionaMarubozu(marubozu);

					return true;
				}

			}
		}

		return false;

	}
	
	public Boolean procuraPadraoDoji(InfoCandle infoCandle) {

		if (infoCandle != null) {
			
			Double pavioSuperior = pavioSuperiorEmPorcentagem(infoCandle);
			Double pavioInferior = pavioInferiorEmPorcentagem(infoCandle);
			
			if(condicaoParaDoji(pavioSuperior, pavioInferior)) {
				Operacao operacao = new Operacao();
				operacao.setPadrao(Padroes.DOJI.getDescricao());				
				operacao.setEntrada(Entrada.INDEFINIDO.getDescricao());
				operacao.setInfoCandle(infoCandle);
				
				Doji doji = new Doji();
				
				doji.setTipo(tipoCandle(infoCandle).getTipo());
				doji.setPavioInferior(pavioInferior.intValue());
				doji.setPavioSuperior(pavioSuperior.intValue());
				doji.setVolumeAcimaMedia20(volumeAcimaMedia20(infoCandle));
				doji.setTamanhoPavioCorpo(tamanhoPavioCorpo(infoCandle));
				doji.setPrecoAcimaMedia8(verificaSePrecoFechamentoAcimaMedia(infoCandle, MEDIACURTA));
				doji.setPrecoAcimaMedia20(verificaSePrecoFechamentoAcimaMedia(infoCandle, MEDIA));
				doji.setPrecoAcimaMedia200(verificaSePrecoFechamentoAcimaMedia(infoCandle, MEDIALONGA));
				doji.setOperacao(operacao);
				operacao.setDoji(doji);
				
				operacaoService.adicionaOperacao(operacao);
				DojiService.adicionaDoji(doji);
				
			}
			
		}
		
		return null;
	}
	
	public static Integer tamanhoPavioCorpo(InfoCandle infoCandle) {
		
		if (infoCandle != null) {

			Double variacaoPavio = 0.0;
			Double variacaoCorpo = 0.0;
			Double variacao = 1000.0;
			
			if(infoCandle.getAbertura().compareTo(infoCandle.getFechamento()) == 0) {
				return variacao.intValue();
			}
			
			variacaoPavio = Math.abs(infoCandle.getMaxima() - infoCandle.getMinima());
			variacaoCorpo = Math.abs(infoCandle.getAbertura() - infoCandle.getFechamento());
			variacao = variacaoPavio/variacaoCorpo;
			
			return variacao.intValue();

		}

		return null;
		
	}

	private static boolean condicaoParaDoji(Double pavioSuperior, Double pavioInferior) {
		return pavioSuperior >= PORCENTAGEMMINIMAPAVIODOJI && pavioSuperior <= PORCENTAGEMMAXIMAPAVIODOJI && pavioInferior >= PORCENTAGEMMINIMAPAVIODOJI && pavioInferior <= PORCENTAGEMMAXIMAPAVIODOJI;
	}
	
	private static Boolean verificaSePrecoAcimaMedia200(InfoCandle infoCandle) {
		
		if(infoCandle.getPrecoMedia200()!= null) {
			return infoCandle.getMaxima() > infoCandle.getPrecoMedia200();
		}
		
		return null;
	}

	public static Double setPrecoStopVenda(InfoCandle infoCandle) {
		return (infoCandle.getMaxima()+0.01);
	}

	public static Double setPrecoEntradaVenda(InfoCandle infoCandle) {
		return (infoCandle.getMinima()-0.01);
	}

	public static double setPrecoStopCompra(InfoCandle infoCandle) {
		return (infoCandle.getMinima()-0.01);
	}

	public static double setPrecoEntradaCompra(InfoCandle infoCandle) {
		return (infoCandle.getMaxima() + 0.01);
	}
	
	public static Double calculaPrecoTerceiroAlvoFibonacci(InfoCandle infoCandle, Entrada entrada) {
		
		if(infoCandle != null && entrada != null) {
			if(entrada == Entrada.COMPRA) {
				
				return Math.abs(((infoCandle.getMaxima() - infoCandle.getMinima())*1.618) + infoCandle.getMaxima());
				
			}else if(entrada == Entrada.VENDA) {
				
				return Math.abs(((infoCandle.getMaxima() - infoCandle.getMinima())*1.618) - infoCandle.getMinima());
				
			}
		}
		
		return null;
	}

	public static Double calculaPrecoSegundoAlvoFibonacci(InfoCandle infoCandle, Entrada entrada) {
		
		if(infoCandle != null && entrada != null) {
			if(entrada == Entrada.COMPRA) {
				
				return Math.abs(((infoCandle.getMaxima() - infoCandle.getMinima())) + infoCandle.getMaxima());
				
			}else if(entrada == Entrada.VENDA) {
				
				return Math.abs(((infoCandle.getMaxima() - infoCandle.getMinima())) - infoCandle.getMinima());
				
			}
		}
		
		return null;
	}

	public static Double calculaPrecoPrimeiroAlvoFibonacci(InfoCandle infoCandle, Entrada entrada) {
		
		if(infoCandle != null && entrada != null) {
			if(entrada == Entrada.COMPRA) {
				
				return Math.abs((((infoCandle.getMaxima() - infoCandle.getMinima())*0.618) + infoCandle.getMaxima()));
				
			}else if(entrada == Entrada.VENDA) {
				
				return Math.abs(((infoCandle.getMaxima() - infoCandle.getMinima())*0.618) - infoCandle.getMinima());
				
			}
		}
		
		return null;
	}

	private static Boolean verificaSePrecoFechamentoAcimaMedia(InfoCandle infoCandle, int media) {
		if(media == MEDIACURTA) {
			if(infoCandle.getPrecoMedia8() != null) {
				return infoCandle.getFechamento() > infoCandle.getPrecoMedia8();
			}
		}
		if(media == MEDIA) {
			if(infoCandle.getPrecoMedia20() != null) {
				return infoCandle.getFechamento() > infoCandle.getPrecoMedia20();
			}
		}
		if(media == MEDIALONGA) {
			if(infoCandle.getPrecoMedia200() != null) {
				return infoCandle.getFechamento() > infoCandle.getPrecoMedia200();
			}
		}
		return null;
	}

	private static boolean condicaoParaEngolfoDeBaixa(ArrayList<InfoCandle> listInfoCandle) {
		if(listInfoCandle.size()>=2) {
			return listInfoCandle.get(0).getFechamento() < listInfoCandle.get(1).getMinima() && listInfoCandle.get(0).getAbertura() > listInfoCandle.get(1).getMaxima();
		}
		return false;
	}

	private static boolean condicaoParaEngolfoDeAlta(ArrayList<InfoCandle> listInfoCandle) {
		if(listInfoCandle.size()>=2) {
			return listInfoCandle.get(0).getFechamento() > listInfoCandle.get(1).getMaxima() &&  listInfoCandle.get(0).getAbertura() < listInfoCandle.get(1).getMinima();
		}
		return false;
	}

	private static boolean verificaCandleParado(InfoCandle infoCandle) {
		return infoCandle.getFechamento().compareTo(infoCandle.getAbertura()) == 0
				&& infoCandle.getMaxima().compareTo(infoCandle.getAbertura()) == 0
				&& infoCandle.getMinima().compareTo(infoCandle.getAbertura()) == 0;
	}

	private static VariacaoPreco classificaVariacaoPreco(Double variacao) {

		

		if (variacao >= 1 && variacao < 2) {
			return VariacaoPreco.ATE2VEZES;
		} else if (variacao >= 2 && variacao < 3) {
			return VariacaoPreco.DE2A3VEZES;
		} else if (variacao >= 3 && variacao < 4) {
			return VariacaoPreco.DE3A4VEZES;
		} else if (variacao >= 4 && variacao < 5) {
			return VariacaoPreco.DE4A5VEZES;
		} else if (variacao >= 5) {
			return VariacaoPreco.MAIORQUE5VEZES;
		}

		return VariacaoPreco.NULL;
	}

	private static Boolean validaMarubozuPelaVariacao(Double calculaVariacao) {

		if (calculaVariacao != null && calculaVariacao > 0) {

			if (calculaVariacao > 2.0) {
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

		TipoCandle tipo = null;

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
			if (infoCandle.getVolume().compareTo(infoCandle.getVolumeMedia20()) >= 0) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	private static PavioSuperior classificaPavioSuperior(Double pavioSuperior) {
		
		if (pavioSuperior.compareTo(0.0) == 0) {
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
