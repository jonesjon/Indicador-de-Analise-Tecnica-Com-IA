package br.iesb.indicador_analise_grafica;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import br.iesb.indicador_analise_grafica.service.InfoCandleService;
import br.iesb.indicador_analise_grafica.service.OperacaoService;

public class TreinamentoRedeNeural {
	
	DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	static ArrayList<Operacao> operacoesAtivas = new ArrayList<Operacao>();
	static ArrayList<Operacao> operacoesFinalizadas = new ArrayList<Operacao>();
	static RedeNeural redeNeural = new RedeNeural();
	static ArrayList<InfoCandle> grafico = new ArrayList<InfoCandle>();
	private final static Double min = 10.0;
	private final static Double max = 100.0;
	private final static int limitVerificaContinuidade = 10;
	private final static long numDistanciaEntreDatasMax = 25;
	
	public static void adicionarOperacao(Operacao operacao) {
		operacoesAtivas.add(operacao);
	}
	
	public static void realizaTreinamentoProcurandoPadroes() {
		
		ArrayList<String> allPapeis = new ArrayList<String>();
		allPapeis = InfoCandleService.getListForAllPapeis();
		
		for(int i=0; i<allPapeis.size(); i++) {
			grafico.clear();
			grafico = InfoCandleService.getInfoCandlePeloNome(allPapeis.get(i));
			
			for(int j=0; j<grafico.size(); j++) {
				RedeNeural.procuraPadraoUmCandle(grafico.get(j));
			}
		}
		
		
		
		
		/*
		 * for(int i=0; i<grafico.size(); i++) {
		 * 
		 * if(!operacoesAtivas.isEmpty()){
		 * 
		 * for(int j=0; j<operacoesAtivas.size(); j++) {
		 * 
		 * if(verificaPadraoMarteloNaoIniciada(j)) {
		 * 
		 * if(Grafico.grafico.get(i).maxima >= operacoesAtivas.get(j).getPrecoEntrada()
		 * && Grafico.grafico.get(i).minima >
		 * operacoesAtivas.get(j).getPrecoCancelarEntrada()) {
		 * operacoesAtivas.get(j).setEntrada(Entrada.COMPRA);
		 * operacoesAtivas.get(j).setStart(true); }else if(Grafico.grafico.get(i).minima
		 * <= operacoesAtivas.get(j).getPrecoCancelarEntrada()) {
		 * operacoesFinalizadas.add(operacoesAtivas.get(j)); operacoesAtivas.remove(j);
		 * }
		 * 
		 * }else if(operacoesAtivas.get(j).isStart() == true) {
		 * 
		 * if(operacoesAtivas.get(j).getLucro() == false &&
		 * operacoesAtivas.get(j).getLucroMax() == false) {
		 * 
		 * if(operacoesAtivas.get(j).getEntrada() == Entrada.COMPRA) {
		 * 
		 * if(Grafico.grafico.get(i).maxima >= operacoesAtivas.get(j).getPrecoGain()) {
		 * operacoesAtivas.get(j).setLucro(true);
		 * operacoesAtivas.get(j).setPorcentagemOperacaoFinal(operacoesAtivas.get(j).
		 * getPercentualGain()/2); }else if(Grafico.grafico.get(i).minima <=
		 * operacoesAtivas.get(j).getPrecoLoss()) {
		 * operacoesAtivas.get(j).setLucro(false);
		 * operacoesAtivas.get(j).setPorcentagemOperacaoFinal(operacoesAtivas.get(j).
		 * getPercentualLoss()); operacoesFinalizadas.add(operacoesAtivas.get(j));
		 * operacoesAtivas.remove(j); }
		 * 
		 * }else { if(Grafico.grafico.get(i).maxima >=
		 * operacoesAtivas.get(j).getPrecoLoss()) {
		 * operacoesAtivas.get(j).setLucro(false);
		 * operacoesAtivas.get(j).setPorcentagemOperacaoFinal(operacoesAtivas.get(j).
		 * getPercentualLoss()); operacoesFinalizadas.add(operacoesAtivas.get(j));
		 * operacoesAtivas.remove(j); }else if(Grafico.grafico.get(i).minima <=
		 * operacoesAtivas.get(j).getPrecoGain()) {
		 * operacoesAtivas.get(j).setLucro(true);
		 * operacoesAtivas.get(j).setPorcentagemOperacaoFinal(operacoesAtivas.get(j).
		 * getPercentualGain()/2); } } }else if(operacoesAtivas.get(j).getLucro() ==
		 * true && operacoesAtivas.get(j).getLucroMax() == false) {
		 * 
		 * if(operacoesAtivas.get(j).getEntrada() == Entrada.COMPRA) {
		 * 
		 * if(Grafico.grafico.get(i).maxima >= operacoesAtivas.get(j).getPrecoGainMax())
		 * { operacoesAtivas.get(j).setLucroMax(true);
		 * operacoesAtivas.get(j).setPorcentagemOperacaoFinal(operacoesAtivas.get(j).
		 * getPorcentagemOperacaoFinal() +
		 * operacoesAtivas.get(j).getPercentualGainMax()/2);
		 * operacoesFinalizadas.add(operacoesAtivas.get(j)); operacoesAtivas.remove(j);
		 * }else if(Grafico.grafico.get(i).minima <=
		 * operacoesAtivas.get(j).getPrecoEntrada()) {
		 * operacoesFinalizadas.add(operacoesAtivas.get(j)); operacoesAtivas.remove(j);
		 * } }else {
		 * 
		 * if(Grafico.grafico.get(i).maxima >= operacoesAtivas.get(j).getPrecoEntrada())
		 * { operacoesFinalizadas.add(operacoesAtivas.get(j));
		 * operacoesAtivas.remove(j); }else if(Grafico.grafico.get(i).minima <=
		 * operacoesAtivas.get(j).getPrecoGainMax()){
		 * operacoesAtivas.get(j).setLucroMax(true);
		 * operacoesAtivas.get(j).setPorcentagemOperacaoFinal(operacoesAtivas.get(j).
		 * getPorcentagemOperacaoFinal() +
		 * operacoesAtivas.get(j).getPercentualGainMax()/2);
		 * operacoesFinalizadas.add(operacoesAtivas.get(j)); operacoesAtivas.remove(j);
		 * }
		 * 
		 * } } }
		 * 
		 * 
		 * }
		 * 
		 * }
		 * 
		 * }
		 */
	}
	
	public static void confereAlvosDasOperacoes() {
		ArrayList<Operacao> operacoes = new ArrayList<Operacao>();
		operacoes = OperacaoService.getOperacoesPossiveis(min, max);
		
		ArrayList<InfoCandle> verificaContinuidade = new ArrayList<InfoCandle>();
		ArrayList<InfoCandle> grafico = new ArrayList<InfoCandle>();
		
		for(int i=0; i<operacoes.size(); i++) {
			LocalDate data = operacoes.get(i).getMartelo().getData();
			String nomeDoPapel = operacoes.get(i).getMartelo().getNomeDoPapel();
			verificaContinuidade = InfoCandleService.verificaGraficoContinuo(data, nomeDoPapel, limitVerificaContinuidade);
			int aux = verificaContinuidade.size() - 1;
			
			if(aux >= 0) {

				if(verificaContinuidadeDoGrafico(data, verificaContinuidade, aux) && 
											verifPrecoEntradaMaiorQueMin(operacoes, i) && 
													verifPrecoDeEntradaMenorQueMin(operacoes, i)) {
					
					grafico = InfoCandleService.getGraficoAPartirDaData(data, nomeDoPapel);
					
					for(int j=0; j<grafico.size(); j++) {
						
						if(operacoes.get(i).isStart()) {
							
							if(verificaSeOperacaoCompra(operacoes, i)) {
								
								if(verificaSeOperacaoAindaNaoDeuAlvo(operacoes, i)) {
									if(verificaSeMaximaChegouAoPrimeiroAlvo(operacoes, grafico, i, j)) {
										operacoes.get(i).setLucro(true);
									}else if(verificaSeMinimaChegouAoPrecoLoss(operacoes, grafico, i, j)) {
										operacoes.get(i).setPorcentagemOperacaoFinal(operacoes.get(i).getPercentualLoss());
										j = grafico.size();
									}
								}else if(verificaSeOperacaoDeuPrimeiroAlvoMasAindaNaoChegouNoSegundo(operacoes, i)) {
									if(verificaSeMaximaChegouAoSegundoAlvo(operacoes, grafico, i, j)) {
										operacoes.get(i).setLucroMax(true);
										operacoes.get(i).setPorcentagemOperacaoFinal((operacoes.get(i).getPercentualGainMax()/2) + ((operacoes.get(i).getPercentualGain())/2));
									}else if(verificaSeOperacaoChegouAoPrecoDeEntradaAposChegarAoPrimeiroAlvo(operacoes, grafico,
											i, j)) {
										Double percentualGain = (operacoes.get(i).getPercentualGain()/2);
										operacoes.get(i).setPorcentagemOperacaoFinal(percentualGain);
										j = grafico.size();
									}
								}
								
							}else if(verificaSeOperacaoVenda(operacoes, i)) {
								
								if(verificaSeOperacaoAindaNaoDeuAlvo(operacoes, i)) {
									if(verificaSeMinimaChegouNoPrimeiroAlvo(operacoes, grafico, i, j)) {
										operacoes.get(i).setLucro(true);
									}else if(verificaSeMaximaChegouAoPrecoLoss(operacoes, grafico, i, j)) {
										operacoes.get(i).setPorcentagemOperacaoFinal(operacoes.get(i).getPercentualLoss());
										j = grafico.size();
									}
								}else if(verificaSeOperacaoDeuPrimeiroAlvoMasAindaNaoChegouNoSegundo(operacoes, i)) {
									if(verificaSeMinimaChegouAoPrecoDeGainMax(operacoes, grafico, i, j)) {
										operacoes.get(i).setLucroMax(true);
										Double percentualGainMax = (operacoes.get(i).getPercentualGainMax()/2) + ((operacoes.get(i).getPercentualGain())/2);
										operacoes.get(i).setPorcentagemOperacaoFinal(percentualGainMax);
									}else if(verificaSeMaximaChegouAoPrecoDeEntradaAposChegarPrimeiroAlvo(operacoes, grafico, i,
											j)) {
										Double percentualLoss = operacoes.get(i).getPrecoLoss();
										operacoes.get(i).setPorcentagemOperacaoFinal(percentualLoss);
										j = grafico.size();
									}
								}
								
							}
							
						}else {
							if(verificaSeOperacaoCompra(operacoes, i)) {
								
								if(verificaSeMaximaChegouAoPrecoDeEntradaAposChegarPrimeiroAlvo(operacoes, grafico, i, j)) {
									operacoes.get(i).setStart(true);
								}else if(grafico.get(j).getMinima() <= operacoes.get(i).getPrecoCancelarEntrada()) {
									j = grafico.size();
								}
								
							}else if(verificaSeOperacaoVenda(operacoes, i)) {
								
								if(verificaSeOperacaoChegouAoPrecoDeEntradaAposChegarAoPrimeiroAlvo(operacoes, grafico, i,
										j)) {
									operacoes.get(i).setStart(true);
								}else if(grafico.get(j).getMaxima() >= operacoes.get(i).getPrecoCancelarEntrada()) {
									j = grafico.size();
								}
								
							}
						}
						
					}
					
					OperacaoService.adicionaOperacao(operacoes.get(i));
					
				}
		}
		}
		
	}

	private static boolean verificaSeMaximaChegouAoPrecoDeEntradaAposChegarPrimeiroAlvo(ArrayList<Operacao> operacoes,
			ArrayList<InfoCandle> grafico, int i, int j) {
		return grafico.get(j).getMaxima() >= operacoes.get(i).getPrecoEntrada();
	}

	private static boolean verificaSeMinimaChegouAoPrecoDeGainMax(ArrayList<Operacao> operacoes,
			ArrayList<InfoCandle> grafico, int i, int j) {
		return grafico.get(j).getMinima() <= operacoes.get(i).getPrecoGainMax();
	}

	private static boolean verificaSeMaximaChegouAoPrecoLoss(ArrayList<Operacao> operacoes,
			ArrayList<InfoCandle> grafico, int i, int j) {
		return grafico.get(j).getMaxima() >= operacoes.get(i).getPrecoLoss();
	}

	private static boolean verificaSeMinimaChegouNoPrimeiroAlvo(ArrayList<Operacao> operacoes,
			ArrayList<InfoCandle> grafico, int i, int j) {
		return grafico.get(j).getMinima() <= operacoes.get(i).getPrecoGain();
	}

	private static boolean verificaSeOperacaoChegouAoPrecoDeEntradaAposChegarAoPrimeiroAlvo(
			ArrayList<Operacao> operacoes, ArrayList<InfoCandle> grafico, int i, int j) {
		return grafico.get(j).getMinima() <= operacoes.get(i).getPrecoEntrada();
	}

	private static boolean verificaSeMaximaChegouAoSegundoAlvo(ArrayList<Operacao> operacoes,
			ArrayList<InfoCandle> grafico, int i, int j) {
		return grafico.get(j).getMaxima() >= operacoes.get(i).getPrecoGainMax();
	}

	private static boolean verificaSeMinimaChegouAoPrecoLoss(ArrayList<Operacao> operacoes,
			ArrayList<InfoCandle> grafico, int i, int j) {
		return grafico.get(j).getMinima() <= operacoes.get(i).getPrecoLoss();
	}

	private static boolean verificaSeMaximaChegouAoPrimeiroAlvo(ArrayList<Operacao> operacoes,
			ArrayList<InfoCandle> grafico, int i, int j) {
		return grafico.get(j).getMaxima() >= operacoes.get(i).getPrecoGain();
	}

	private static boolean verificaSeOperacaoDeuPrimeiroAlvoMasAindaNaoChegouNoSegundo(ArrayList<Operacao> operacoes,
			int i) {
		return operacoes.get(i).getLucro() && !operacoes.get(i).getLucroMax();
	}

	private static boolean verificaSeOperacaoAindaNaoDeuAlvo(ArrayList<Operacao> operacoes, int i) {
		return !operacoes.get(i).getLucro();
	}

	private static boolean verificaSeOperacaoVenda(ArrayList<Operacao> operacoes, int i) {
		return operacoes.get(i).getEntrada().equals(Entrada.VENDA.getDescricao());
	}

	private static boolean verificaSeOperacaoCompra(ArrayList<Operacao> operacoes, int i) {
		return operacoes.get(i).getEntrada().equals(Entrada.COMPRA.getDescricao());
	}

	private static boolean verifPrecoDeEntradaMenorQueMin(ArrayList<Operacao> operacoes, int i) {
		return operacoes.get(i).getPrecoEntrada() < max;
	}

	private static boolean verifPrecoEntradaMaiorQueMin(ArrayList<Operacao> operacoes, int i) {
		return operacoes.get(i).getPrecoEntrada() > min;
	}

	private static boolean verificaContinuidadeDoGrafico(LocalDate data, ArrayList<InfoCandle> verif, int aux) {
		return ChronoUnit.DAYS.between(data, verif.get(aux).getData())  < numDistanciaEntreDatasMax;
	}
	
	/*
	 * public void imprimeOperacoes() { for(int i=0; i<operacoesFinalizadas.size();
	 * i++) {
	 * 
	 * if(operacoesFinalizadas.get(i).isStart()) {
	 * 
	 * System.out.println("Data da Opera��o: " +
	 * formato.format(operacoesFinalizadas.get(i).getData()));
	 * System.out.println(operacoesFinalizadas.get(i).getEntrada());
	 * System.out.println("Foi lucrativa? - "+operacoesFinalizadas.get(i).getLucro()
	 * );
	 * System.out.println("Deu lucro 2x? - "+operacoesFinalizadas.get(i).getLucroMax
	 * ()); System.out.println("Preco de Entrada: "+operacoesFinalizadas.get(i).
	 * getPrecoEntrada()); System.out.println("Pre�o de Gain: " +
	 * operacoesFinalizadas.get(i).getPrecoGain());
	 * System.out.println("Pre�o de Loss: " +
	 * operacoesFinalizadas.get(i).getPrecoLoss());
	 * System.out.println("Pre�o de Gain Maximo: " +
	 * operacoesFinalizadas.get(i).getPrecoGainMax());
	 * System.out.println("% Lucro: "+operacoesFinalizadas.get(i).getPercentualGain(
	 * )); System.out.println("% Prejuizo: "+operacoesFinalizadas.get(i).
	 * getPercentualLoss());
	 * System.out.println("% Lucro M�ximo: "+operacoesFinalizadas.get(i).
	 * getPercentualGainMax());
	 * System.out.println("% Da opera��o: "+operacoesFinalizadas.get(i).
	 * getPorcentagemOperacaoFinal()); System.out.println();
	 * 
	 * }else {
	 * 
	 * System.out.println("Data da Opera��o: " +
	 * formato.format(operacoesFinalizadas.get(i).getData()));
	 * System.out.println("OPERA��O N�O INICIADA");
	 * System.out.println("Pre�o de Gain: " +
	 * operacoesFinalizadas.get(i).getPrecoGain());
	 * System.out.println("% Lucro: "+operacoesFinalizadas.get(i).getPercentualGain(
	 * )); System.out.println("Pre�o de Entrada: "+operacoesFinalizadas.get(i).
	 * getPrecoEntrada()); System.out.println("Pre�o de Loss: " +
	 * operacoesFinalizadas.get(i).getPrecoLoss());
	 * System.out.println("% Prejuizo: "+operacoesFinalizadas.get(i).
	 * getPercentualLoss()); System.out.println();
	 * 
	 * } } }
	 */
	
	public void percentualFinal() {
		
		Double percentualFinal = 0.0;
		int cont = 0;
		for(int i=0; i<operacoesFinalizadas.size(); i++) {
			if(operacoesFinalizadas.get(i).isStart()) {
				percentualFinal += operacoesFinalizadas.get(i).getPorcentagemOperacaoFinal();
				cont++;
			}
		}
		System.out.println("Percentual Final do teste: "+percentualFinal);
		System.out.println("Quantidade de operações realizadas: "+cont);
	}
	
	/*
	 * public void percentualFinalMarteloSemPavioSuperior() { Double percentualFinal
	 * = 0.0; int cont = 0; for(int i=0; i<operacoesFinalizadas.size(); i++) {
	 * if(operacoesFinalizadas.get(i).isStart()) {
	 * if(operacoesFinalizadas.get(i).getMartelo().getPavioSuperior() ==
	 * PavioSuperior.SEMPAVIO) { percentualFinal +=
	 * operacoesFinalizadas.get(i).getPorcentagemOperacaoFinal(); cont++; } } }
	 * System.out.println("Percentual Final Martelo Sem Pavio Superior: "
	 * +percentualFinal);
	 * System.out.println("Quantidade de opera��es realizadas: "+cont); }
	 */
	
	/*
	 * public void percentualGainLossMarteloSemPavioSuperior() { Double
	 * percentualFinal = 0.0; Double operacoesGain = 1.0; Double cont = 1.0; for(int
	 * i=0; i<operacoesFinalizadas.size(); i++) {
	 * if(operacoesFinalizadas.get(i).isStart()) {
	 * if(operacoesFinalizadas.get(i).getMartelo().getPavioSuperior() ==
	 * PavioSuperior.SEMPAVIO) { if(operacoesFinalizadas.get(i).getLucro()) {
	 * operacoesGain++; } cont++; } } }
	 * 
	 * percentualFinal = (operacoesGain/cont)*100;
	 * 
	 * System.out.println();
	 * System.out.println("Percentual Gain Loss Martelo Sem Pavio Superior: "
	 * +percentualFinal);
	 * System.out.println("Quantidade de opera��es realizadas: "+cont); }
	 */
	
	/*
	 * public void percentualGainLossMarteloSemPavioSuperiorPavioInferiorMax() {
	 * Double percentualFinal = 0.0; Double operacoesGain = 1.0; Double cont = 1.0;
	 * for(int i=0; i<operacoesFinalizadas.size(); i++) {
	 * if(operacoesFinalizadas.get(i).isStart()) {
	 * if(operacoesFinalizadas.get(i).getMartelo().getPavioSuperior() ==
	 * PavioSuperior.SEMPAVIO &&
	 * operacoesFinalizadas.get(i).getMartelo().getPavioInferior() ==
	 * PavioInferior.PAVIO100PORCENTO) { if(operacoesFinalizadas.get(i).getLucro())
	 * { operacoesGain++; } cont++; } } }
	 * 
	 * percentualFinal = (operacoesGain/cont)*100;
	 * 
	 * System.out.println(); System.out.
	 * println("Percentual Gain Loss Martelo Sem Pavio Superior Pavio Inferior 95% a 100%: "
	 * +percentualFinal);
	 * System.out.println("Quantidade de opera��es realizadas: "+cont); }
	 */

}
