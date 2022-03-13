package br.iesb.indicador_analise_grafica;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import br.iesb.indicador_analise_grafica.service.InfoCandleService;
import br.iesb.indicador_analise_grafica.service.OperacaoService;
import br.iesb.indicador_analise_grafica.service.PapeisOperaveisService;
import br.iesb.indicador_analise_grafica_enum.EntradaEnum;

public class TreinamentoRedeNeural {

	DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	static ArrayList<Operacao> operacoesAtivas = new ArrayList<Operacao>();
	static ArrayList<Operacao> operacoesFinalizadas = new ArrayList<Operacao>();
	static RedeNeural redeNeural = new RedeNeural();
	static ArrayList<InfoCandle> grafico = new ArrayList<InfoCandle>();
	private final static Double MIN = 10.0;
	private final static Double MAX = 100.0;
	private final static int LIMITVERIFICACONTINUIDADE = 10;
	private final static long NUMDISTANCIAENTREDATAS = 25;
	private final static int LIMIT = 50;
	private final static String DATAINICIAL = "2000-01-01";
	public static final int LIMITDECANDLEMARUBOZU = 5;
	public static final int LIMITDECANDLEENGOLFO = 2;
	public static final int CANDLESPIECINGLINE = 2;

	public static void adicionarOperacao(Operacao operacao) {
		operacoesAtivas.add(operacao);
	}

	public static boolean realizaTreinamentoProcurandoPadroes() {

		System.out.println("Iniciando busca por padroes...");

		int qtdPapeis = InfoCandleService.getQtdPapeis();
		String nomeDoUltimoPapel = "";

		for (int i = 0; i < qtdPapeis; i++) {
			ArrayList<String> allPapeis = new ArrayList<String>();
			allPapeis = InfoCandleService.getListForAllPapeis(nomeDoUltimoPapel, LIMIT);

			if (allPapeis.size() == 0) {
				return true;
			}

			for (int j = 0; j < allPapeis.size(); j++) {
				grafico.clear();
				grafico = InfoCandleService.getInfoCandlePeloNome(allPapeis.get(j));

				for (int k = 0; k < grafico.size(); k++) {
//					RedeNeural.procuraPadraoMartelo(grafico.get(k));
//					ArrayList<InfoCandle> listaUltimosCandles = InfoCandleService.getUltimosInfoCandle(allPapeis.get(j),
//							grafico.get(k).getData(), LIMITDECANDLEMARUBOZUENGOLFO);
//					RedeNeural.procuraPadraoMarubozu(listaUltimosCandles);
				}
			}

			nomeDoUltimoPapel = allPapeis.get(allPapeis.size() - 1);

		}

		System.out.println("Finalizando busca por padroes.");

		return false;

	}

	public static void realizaTreinamentoProcurandoPadroesEmPapeisOperaveis(LocalDate dataFiltro) {

		System.out.println("Iniciando busca por padroes...");

		ArrayList<PapeisOperaveis> po = new ArrayList<PapeisOperaveis>();
		po.addAll(PapeisOperaveisService.getAllPapeis());

		for (int i = 0; i < po.size(); i++) {

			String nomeDoPapel = po.get(i).getNomeDoPapel();
			
			grafico.clear();
			
			grafico = InfoCandleService.getInfoCandlePeloNome(nomeDoPapel);
			
			if(dataFiltro != null) {
				Boolean temDataFiltro = false;
				Boolean res = false;
				for(int z = grafico.size()-1 ; z >= 0; z--) {
					res = grafico.get(z).getData().isEqual(dataFiltro);
					if(Boolean.TRUE.equals(res)) {
						temDataFiltro = true;
						break;
					}
				}
				
				if(Boolean.FALSE.equals(temDataFiltro)) {
					System.out.println("Não tem candle para a data desejada: " + dataFiltro.toString() + 
							" no papel: " + po.get(i).getNomeDoPapel());
					continue;
				}
			}
			
			System.out.println();
			System.out.println("Papel: " + nomeDoPapel);
			System.out.println();
			System.out.println("Procurando Marubozu...");
			RedeNeural.procuraPadraoMarubozu(dataFiltro == null ? grafico : 
				new ArrayList<InfoCandle>(grafico.subList(grafico.size() - 6, grafico.size())), 
				LIMITDECANDLEMARUBOZU); // mandar ultimos 6
			System.out.println();
			System.out.println("Procurando Martelo...");
			RedeNeural.procuraPadraoMartelo(dataFiltro == null ? grafico : 
				new ArrayList<InfoCandle>(grafico.subList(grafico.size() - 1, grafico.size()))); // mandar ultimos 1
			System.out.println();
			System.out.println("Procurando Martelo Invertido...");
			RedeNeural.procuraPadraoMarteloInvertido(dataFiltro == null ? grafico : 
				new ArrayList<InfoCandle>(grafico.subList(grafico.size() - 1, grafico.size()))); // mandar ultimos 1
			System.out.println();
			System.out.println("Procurando Doji...");
			RedeNeural.procuraPadraoDoji(dataFiltro == null ? grafico : 
				new ArrayList<InfoCandle>(grafico.subList(grafico.size() - 1, grafico.size()))); // mandar ultimos 1
			System.out.println();
			System.out.println("Procurando Piercing Line...");
			RedeNeural.procuraPadraoPiercingLine(dataFiltro == null ? grafico : 
				new ArrayList<InfoCandle>(grafico.subList(grafico.size() - 2, grafico.size()))); // mandar ultimos 2
			System.out.println();
			System.out.println("Procurando Engolfo...");
			RedeNeural.procuraPadraoEngolfo(dataFiltro == null ? grafico : 
				new ArrayList<InfoCandle>(grafico.subList(grafico.size() - 2, grafico.size())), 
				LIMITDECANDLEENGOLFO); // mandar ultimos 2
			System.out.println();
			System.out.println("Procurando Tres Soldados...");
			RedeNeural.procuraPadraoTresSoldados(dataFiltro == null ? grafico : 
				new ArrayList<InfoCandle>(grafico.subList(grafico.size() - 3, grafico.size()))); // mandar ultimos 3
			System.out.println();
			System.out.println("Procurando Bebe Abandonado...");
			RedeNeural.procuraPadraoBebeAbandonado(dataFiltro == null ? grafico : 
				new ArrayList<InfoCandle>(grafico.subList(grafico.size() - 3, grafico.size()))); // mandar ultimos 3

		}

		System.out.println("Finalizando busca por padroes.");

	}

	public static void confereAlvosDasOperacoesPossiveis() {
		
		System.out.println("Conferindo alvos...");
		
		ArrayList<PapeisOperaveis> po = new ArrayList<PapeisOperaveis>();
		po.addAll(PapeisOperaveisService.getAllPapeis());

		for (int i = 0; i < po.size(); i++) {
			
			ArrayList<Operacao> operacoes = new ArrayList<Operacao>();
			operacoes = OperacaoService.getOperacoesPossiveis(MIN, MAX, po.get(i).getNomeDoPapel());
			
			ArrayList<InfoCandle> graficoGeral = InfoCandleService.getInfoCandlePeloNome(po.get(i).getNomeDoPapel());
			
			for(int j=0;j<operacoes.size();j++) {
				Operacao operacao = operacoes.get(j);
				ArrayList<InfoCandle> verificaContinuidade = new ArrayList<InfoCandle>();
				verificaContinuidade = InfoCandleService.verificaGraficoContinuo(operacao.getDat(), operacao.getNomeDoPapel(), LIMITVERIFICACONTINUIDADE);
				
				if(verificaContinuidadeDoGrafico(operacao.getDat(), verificaContinuidade)) {
					List<InfoCandle> graficoTemp = graficoGeral.stream().filter( infoCandle -> infoCandle.getData().compareTo(operacao.getDat()) > 0 )
							.collect(Collectors.toList());
					
					ArrayList<InfoCandle> grafico = new ArrayList<InfoCandle>(graficoTemp);
					//grafico = InfoCandleService.getGraficoAPartirDaData(operacao.getDat(), operacao.getNomeDoPapel());
					
					for(int k=0; k<grafico.size(); k++) {
						
						if (operacao.isStart()) {
							
							if (verificaSeOperacaoCompra(operacao)) {
								
								if(!verificaSeOperacaoAindaNaoChegouNoPrimeiroAlvo(operacao)) {
									
									if(verificaSeMaximaChegouPrimeiroAlvoCompra(grafico.get(k), operacao)) {
										
										operacao.setPrimeiroAlvoAtingido(true);
										operacao.setPorcentagemOperacaoFinal(operacao.getPorcentagemOperacaoFinal() + calculaPorcentagemPrimeiroAlvo(operacao));
										if(verificaSeMaximaChegouSegundoAlvoCompra(grafico.get(k), operacao)) {
											
											operacao.setSegundoAlvoAtingido(true);
											operacao.setPorcentagemOperacaoFinal(operacao.getPorcentagemOperacaoFinal() + calculaPorcentagemSegundoAlvo(operacao));
											
											if(verificaSeMaximaChegouTerceiroAlvoCompra(grafico.get(k), operacao)) {
												
												operacao.setTerceiroAlvoAtingido(true);
												operacao.setPorcentagemOperacaoFinal(operacao.getPorcentagemOperacaoFinal() + calculaPorcentagemTerceiroAlvo(operacao));
												operacao.setDataFinal(grafico.get(k).getData());
												operacao.setOperacaoFinalizada(true);
												k = grafico.size();
												
											}
											
										}
										
									}else if(verificaSeOperacaoChegouAoPrecoLossCompra(grafico.get(k), operacao)) {
										
										operacao.setPorcentagemOperacaoFinal(calculaPorcentagemStop(operacao));
										operacao.setDataFinal(grafico.get(k).getData());
										operacao.setOperacaoFinalizada(true);
										k = grafico.size();
										
									}
									
								}else if(verificaSeOperacaoChegouNoPrimeiroAlvoMasNaoNoSegundo(operacao)) {
									
									if(verificaSeMaximaChegouSegundoAlvoCompra(grafico.get(k), operacao)) {
										
										operacao.setSegundoAlvoAtingido(true);
										operacao.setPorcentagemOperacaoFinal(operacao.getPorcentagemOperacaoFinal() + calculaPorcentagemSegundoAlvo(operacao));
										
										if(verificaSeMaximaChegouTerceiroAlvoCompra(grafico.get(k), operacao)) {
											
											operacao.setTerceiroAlvoAtingido(true);
											operacao.setPorcentagemOperacaoFinal(operacao.getPorcentagemOperacaoFinal() + calculaPorcentagemTerceiroAlvo(operacao));
											operacao.setDataFinal(grafico.get(k).getData());
											operacao.setOperacaoFinalizada(true);
											k = grafico.size();
											
										}
										
									}else if(verificaSeOperacaoChegouAoPrecoEntradaAposPrimeiroAlvoCompra(grafico.get(k), operacao)) {
										
										operacao.setDataFinal(grafico.get(k).getData());
										operacao.setOperacaoFinalizada(true);
										k = grafico.size();
										
									}
									
								}else if(verificaSeOperacaoChegouNoSegundoAlvoMasNaoNoTerceiro(operacao)) {
									
									if(verificaSeMaximaChegouTerceiroAlvoCompra(grafico.get(k), operacao)) {
										
										operacao.setTerceiroAlvoAtingido(true);
										operacao.setPorcentagemOperacaoFinal(operacao.getPorcentagemOperacaoFinal() + calculaPorcentagemTerceiroAlvo(operacao));
										operacao.setDataFinal(grafico.get(k).getData());
										operacao.setOperacaoFinalizada(true);
										k = grafico.size();
										
									}else if(verificaSeOperacaoChegouNoPrimeiroAlvoAposChegarNoSegundoAlvoCompra(grafico.get(k), operacao)) {
										
										operacao.setDataFinal(grafico.get(k).getData());
										operacao.setOperacaoFinalizada(true);
										k = grafico.size();
										operacao.setPorcentagemOperacaoFinal(operacao.getPorcentagemOperacaoFinal() + calculaPorcentagemPrimeiroAlvo(operacao));
										
									}
									
								}
								
							}else if (verificaSeOperacaoVenda(operacao)) {
								
								if(!verificaSeOperacaoAindaNaoChegouNoPrimeiroAlvo(operacao)) {
									
									if(verificaSeMinimaChegouPrimeiroAlvoVenda(grafico.get(k), operacao)) {
										operacao.setPrimeiroAlvoAtingido(true);
										operacao.setPorcentagemOperacaoFinal(operacao.getPorcentagemOperacaoFinal() + calculaPorcentagemPrimeiroAlvo(operacao));
										
										if(verificaSeMinimaChegouSegundoAlvoVenda(grafico.get(k), operacao)) {
											
											operacao.setSegundoAlvoAtingido(true);
											operacao.setPorcentagemOperacaoFinal(operacao.getPorcentagemOperacaoFinal() + calculaPorcentagemSegundoAlvo(operacao));
											
											if(verificaSeMinimaChegouTerceiroAlvoVenda(grafico.get(k), operacao)) {
												
												operacao.setTerceiroAlvoAtingido(true);
												operacao.setPorcentagemOperacaoFinal(operacao.getPorcentagemOperacaoFinal() + calculaPorcentagemTerceiroAlvo(operacao));
												operacao.setDataFinal(grafico.get(k).getData());
												operacao.setOperacaoFinalizada(true);
												k = grafico.size();
												
											}
										}
										
									}else if(verificaSeOperacaoChegouAoPrecoLossVenda(grafico.get(k), operacao)) {
										
										operacao.setPorcentagemOperacaoFinal(calculaPorcentagemStop(operacao));
										operacao.setDataFinal(grafico.get(k).getData());
										operacao.setOperacaoFinalizada(true);
										k = grafico.size();
										
									}
								}else if(verificaSeOperacaoChegouNoPrimeiroAlvoMasNaoNoSegundo(operacao)) {
									
									if(verificaSeMinimaChegouSegundoAlvoVenda(grafico.get(k), operacao)) {
										
										operacao.setSegundoAlvoAtingido(true);
										operacao.setPorcentagemOperacaoFinal(operacao.getPorcentagemOperacaoFinal() + calculaPorcentagemSegundoAlvo(operacao));
										
										if(verificaSeMinimaChegouTerceiroAlvoVenda(grafico.get(k), operacao)) {
											
											operacao.setTerceiroAlvoAtingido(true);
											operacao.setPorcentagemOperacaoFinal(operacao.getPorcentagemOperacaoFinal() + calculaPorcentagemTerceiroAlvo(operacao));
											operacao.setDataFinal(grafico.get(k).getData());
											operacao.setOperacaoFinalizada(true);
											k = grafico.size();
											
										}
										
									}else if(verificaSeOperacaoChegouAoPrecoEntradaAposPrimeiroAlvoVenda(grafico.get(k), operacao)) {
										
										operacao.setDataFinal(grafico.get(k).getData());
										operacao.setOperacaoFinalizada(true);
										k = grafico.size();
										
									}
									
								}else if(verificaSeOperacaoChegouNoSegundoAlvoMasNaoNoTerceiro(operacao)) {
									
									if(verificaSeMinimaChegouTerceiroAlvoVenda(grafico.get(k), operacao)) {
										
										operacao.setTerceiroAlvoAtingido(true);
										operacao.setPorcentagemOperacaoFinal(operacao.getPorcentagemOperacaoFinal() + calculaPorcentagemTerceiroAlvo(operacao));
										operacao.setDataFinal(grafico.get(k).getData());
										operacao.setOperacaoFinalizada(true);
										k = grafico.size();
										
									}else if(verificaSeOperacaoChegouNoPrimeiroAlvoAposChegarNoSegundoAlvoVenda(grafico.get(k), operacao)) {
										
										operacao.setPorcentagemOperacaoFinal(operacao.getPorcentagemOperacaoFinal() + calculaPorcentagemPrimeiroAlvo(operacao));
										operacao.setDataFinal(grafico.get(k).getData());
										operacao.setOperacaoFinalizada(true);
										k = grafico.size();
										
									}
									
								}
								
							}
							
						}else {
							
							if (verificaSeOperacaoCompra(operacao)) {

								if (verificaSeMaximaChegouAoPrecoDeEntradaCompra(grafico.get(k), operacao)) {
									operacao.setStart(true);
									k--;
								} else if (verificaSeMinimaChegouPrecoLossCompra(grafico.get(k), operacao)) {
									//operacao.setOperacaoFinalizada(true);
									k = grafico.size();
								}

							} else if (verificaSeOperacaoVenda(operacao)) {

								if (verificaSeOperacaoChegouAoPrecoDeEntradaVenda(grafico.get(k), operacao)) {
									operacao.setStart(true);
									k--;
								} else if (verificaSeMaximaChegouPrecoLossVenda(grafico.get(k), operacao)) {
									//operacao.setOperacaoFinalizada(true);
									k = grafico.size();
								}

							}
							
						}
						
					}
					
					OperacaoService.adicionaOperacao(operacao);
					
				}
			}
			

		}
		
		System.out.println("Finalizando...");
	}

	private static Double calculaPorcentagemStop(Operacao operacao) {
		return Math.abs((((operacao.getPrecoStop()/operacao.getPrecoEntrada()) - 1)*100));
	}

	private static boolean verificaSeOperacaoChegouNoPrimeiroAlvoAposChegarNoSegundoAlvoVenda(InfoCandle infoCandle, Operacao operacao) {
		return infoCandle.getMaxima() >= operacao.getPrecoPrimeiroAlvoFibonacci();
	}

	private static boolean verificaSeMinimaChegouTerceiroAlvoVenda(InfoCandle infoCandle, Operacao operacao) {
		return infoCandle.getMinima() <= operacao.getPrecoTerceiroAlvoFibonacci();
	}

	private static boolean verificaSeOperacaoChegouAoPrecoEntradaAposPrimeiroAlvoVenda(InfoCandle infoCandle, Operacao operacao) {
		return infoCandle.getMaxima() >= operacao.getPrecoEntrada();
	}

	private static boolean verificaSeMinimaChegouSegundoAlvoVenda(InfoCandle infoCandle, Operacao operacao) {
		return infoCandle.getMinima() <= operacao.getPrecoSegundoAlvoFibonacci();
	}

	private static boolean verificaSeOperacaoChegouAoPrecoLossVenda(InfoCandle infoCandle, Operacao operacao) {
		return infoCandle.getMaxima() >= operacao.getPrecoStop();
	}

	private static boolean verificaSeOperacaoChegouNoPrimeiroAlvoAposChegarNoSegundoAlvoCompra(InfoCandle infoCandle, Operacao operacao) {
		return infoCandle.getMinima() <= operacao.getPrecoPrimeiroAlvoFibonacci();
	}

	private static Double calculaPorcentagemTerceiroAlvo(Operacao operacao) {
		return Math.abs((((operacao.getPrecoTerceiroAlvoFibonacci()/operacao.getPrecoEntrada()) - 1)*100)/3);
	}

	private static boolean verificaSeMaximaChegouTerceiroAlvoCompra(InfoCandle infoCandle, Operacao operacao) {
		return infoCandle.getMaxima() >= operacao.getPrecoTerceiroAlvoFibonacci();
	}

	private static boolean verificaSeOperacaoChegouAoPrecoEntradaAposPrimeiroAlvoCompra(InfoCandle infoCandle, Operacao operacao) {
		return infoCandle.getMinima() <= operacao.getPrecoEntrada();
	}

	private static Double calculaPorcentagemSegundoAlvo(Operacao operacao) {
		return Math.abs((((operacao.getPrecoSegundoAlvoFibonacci()/operacao.getPrecoEntrada()) - 1)*100)/3);
	}

	private static boolean verificaSeMaximaChegouSegundoAlvoCompra(InfoCandle infoCandle, Operacao operacao) {
		return infoCandle.getMaxima() >= operacao.getPrecoSegundoAlvoFibonacci();
	}

	private static Double calculaPorcentagemPrimeiroAlvo(Operacao operacao) {
		return Math.abs((((operacao.getPrecoPrimeiroAlvoFibonacci()/operacao.getPrecoEntrada()) - 1)*100)/3);
	}

	private static Double calculaPorcentagemLoss(Operacao operacao) {
		return Math.abs((operacao.getPrecoStop()/(operacao.getPrecoEntrada() - 1)) * 100);
	}

	private static boolean verificaSeOperacaoChegouNoSegundoAlvoMasNaoNoTerceiro(Operacao operacao) {
		return operacao.getSegundoAlvoAtingido() && !operacao.getTerceiroAlvoAtingido();
	}

	private static boolean verificaSeOperacaoChegouNoPrimeiroAlvoMasNaoNoSegundo(Operacao operacao) {
		return operacao.getPrimeiroAlvoAtingido() && !operacao.getSegundoAlvoAtingido();
	}

	private static boolean verificaSeOperacaoChegouAoPrecoLossCompra(InfoCandle infoCandle, Operacao operacao) {
		return infoCandle.getMinima() <= operacao.getPrecoStop();
	}

	private static boolean verificaSeOperacaoAindaNaoChegouNoPrimeiroAlvo(Operacao operacao) {
		return operacao.getPrimeiroAlvoAtingido();
	}

	private static boolean verificaSeMinimaChegouPrimeiroAlvoVenda(InfoCandle infoCandle, Operacao operacao) {
		return infoCandle.getMinima() <= operacao.getPrecoPrimeiroAlvoFibonacci();
	}

	private static boolean verificaSeMaximaChegouPrimeiroAlvoCompra(InfoCandle infoCandle, Operacao operacao) {
		return infoCandle.getMaxima() >= operacao.getPrecoPrimeiroAlvoFibonacci();
	}

	private static boolean verificaSeMaximaChegouPrecoLossVenda(InfoCandle infoCandle, Operacao operacao) {
		return infoCandle.getMaxima() >= operacao.getPrecoStop();
	}

	private static boolean verificaSeOperacaoChegouAoPrecoDeEntradaVenda(InfoCandle infoCandle, Operacao operacao) {
		return infoCandle.getMinima() <= operacao.getPrecoEntrada();
	}

	private static boolean verificaSeMinimaChegouPrecoLossCompra(InfoCandle infoCandle, Operacao operacao) {
		return infoCandle.getMinima() <= operacao.getPrecoStop();
	}

	public static void verificaEstatistica() {
		String nomeDoPapelOperacao = "";
		LocalDate dataUltimaOperacao = LocalDate.parse(DATAINICIAL);
		ArrayList<String> listaNomesPapeisOperaveis = OperacaoService.getAllPapeisOperacoesPossiveis(MIN, MAX);

		for (int i = 0; i < listaNomesPapeisOperaveis.size(); i++) {
			nomeDoPapelOperacao = listaNomesPapeisOperaveis.get(i);
			dataUltimaOperacao = LocalDate.parse(DATAINICIAL);

			ArrayList<Operacao> operacoes = new ArrayList<Operacao>();

		}

	}

	public static void verificaEstatisticasMartelo(Operacao operacao) {

	}

	private static boolean verificaSeMaximaChegouAoPrecoDeEntradaCompra(InfoCandle infoCandle, Operacao operacao) {
		return infoCandle.getMaxima() >= operacao.getPrecoEntrada();
	}

	private static boolean verificaSeOperacaoVenda(Operacao operacao) {
		return operacao.getTipoEntrada() == EntradaEnum.VENDA;
	}

	private static boolean verificaSeOperacaoCompra(Operacao operacao) {
		return operacao.getTipoEntrada() == EntradaEnum.COMPRA;
	}

	private static Boolean verificaContinuidadeDoGrafico(LocalDate data, ArrayList<InfoCandle> verif) {
		
		if(verif.size() > 0) {
			return ChronoUnit.DAYS.between(data, verif.get(verif.size()-1).getData()) < NUMDISTANCIAENTREDATAS;
		}
		return false;
	}

	public void percentualFinal() {

		Double percentualFinal = 0.0;
		int cont = 0;
		for (int i = 0; i < operacoesFinalizadas.size(); i++) {
			if (operacoesFinalizadas.get(i).isStart()) {
				percentualFinal += operacoesFinalizadas.get(i).getPorcentagemOperacaoFinal();
				cont++;
			}
		}
		System.out.println("Percentual Final do teste: " + percentualFinal);
		System.out.println("Quantidade de operações realizadas: " + cont);
	}

}
