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
	private final static Double MIN = 10.0;
	private final static Double MAX = 100.0;
	private final static int LIMITVERIFICACONTINUIDADE = 10;
	private final static long NUMDISTANCIAENTREDATAS = 25;
	private final static int LIMIT = 50;
	private final static String DATAINICIAL = "2000-01-01";

	public static void adicionarOperacao(Operacao operacao) {
		operacoesAtivas.add(operacao);
	}

	public static boolean realizaTreinamentoProcurandoPadroes() {

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
					RedeNeural.procuraPadraoMartelo(grafico.get(k));
				}
			}

			nomeDoUltimoPapel = allPapeis.get(allPapeis.size() - 1);

		}

		return false;

	}

	public static void confereAlvosDasOperacoes() {

		String nomeDoPapelOperacao = "";
		LocalDate dataUltimaOperacao = LocalDate.parse(DATAINICIAL);
		ArrayList<String> listaNomesPapeisOperaveis = OperacaoService.getAllPapeisOperacoesPossiveis(MIN, MAX);

		for (int k = 0; k < listaNomesPapeisOperaveis.size(); k++) {

			nomeDoPapelOperacao = listaNomesPapeisOperaveis.get(k);
			dataUltimaOperacao = LocalDate.parse(DATAINICIAL);

			ArrayList<Operacao> operacoes = new ArrayList<Operacao>();
			operacoes = OperacaoService.getOperacoesPossiveis(MIN, MAX, nomeDoPapelOperacao, dataUltimaOperacao, LIMIT);

			if(operacoes != null) {

				ArrayList<InfoCandle> verificaContinuidade = new ArrayList<InfoCandle>();
				ArrayList<InfoCandle> grafico = new ArrayList<InfoCandle>();

				for (int i = 0; i < operacoes.size(); i++) {
					LocalDate data = operacoes.get(i).getData();
					String nomeDoPapel = operacoes.get(i).getNomeDoPapel();
					verificaContinuidade = InfoCandleService.verificaGraficoContinuo(data, nomeDoPapel,
							LIMITVERIFICACONTINUIDADE);
					int aux = verificaContinuidade.size() - 1;

					if (aux >= 0) {

						if (verificaContinuidadeDoGrafico(data, verificaContinuidade, aux)
								&& verifPrecoEntradaMaiorQueMin(operacoes, i)
								&& verifPrecoDeEntradaMenorQueMin(operacoes, i)) {

							grafico = InfoCandleService.getGraficoAPartirDaData(data, nomeDoPapel);

							for (int j = 0; j < grafico.size(); j++) {

								if (operacoes.get(i).isStart()) {

									if (verificaSeOperacaoCompra(operacoes, i)) {

										if (verificaSeOperacaoAindaNaoDeuAlvo(operacoes, i)) {
											if (verificaSeMaximaChegouAoPrimeiroAlvo(operacoes, grafico, i, j)) {
												operacoes.get(i).setLucro(true);
											} else if (verificaSeMinimaChegouAoPrecoLoss(operacoes, grafico, i, j)) {
												operacoes.get(i).setPorcentagemOperacaoFinal(
														operacoes.get(i).getPercentualLoss());
												j = grafico.size();
											}
										} else if (verificaSeOperacaoDeuPrimeiroAlvoMasAindaNaoChegouNoSegundo(
												operacoes, i)) {
											if (verificaSeMaximaChegouAoSegundoAlvo(operacoes, grafico, i, j)) {
												operacoes.get(i).setLucroMax(true);
												operacoes.get(i).setPorcentagemOperacaoFinal(
														(operacoes.get(i).getPercentualGainMax() / 2)
																+ ((operacoes.get(i).getPercentualGain()) / 2));
											} else if (verificaSeOperacaoChegouAoPrecoDeEntradaAposChegarAoPrimeiroAlvo(
													operacoes, grafico, i, j)) {
												Double percentualGain = (operacoes.get(i).getPercentualGain() / 2);
												operacoes.get(i).setPorcentagemOperacaoFinal(percentualGain);
												j = grafico.size();
											}
										}

									} else if (verificaSeOperacaoVenda(operacoes, i)) {

										if (verificaSeOperacaoAindaNaoDeuAlvo(operacoes, i)) {
											if (verificaSeMinimaChegouNoPrimeiroAlvo(operacoes, grafico, i, j)) {
												operacoes.get(i).setLucro(true);
											} else if (verificaSeMaximaChegouAoPrecoLoss(operacoes, grafico, i, j)) {
												operacoes.get(i).setPorcentagemOperacaoFinal(
														operacoes.get(i).getPercentualLoss());
												j = grafico.size();
											}
										} else if (verificaSeOperacaoDeuPrimeiroAlvoMasAindaNaoChegouNoSegundo(
												operacoes, i)) {
											if (verificaSeMinimaChegouAoPrecoDeGainMax(operacoes, grafico, i, j)) {
												operacoes.get(i).setLucroMax(true);
												Double percentualGainMax = (operacoes.get(i).getPercentualGainMax() / 2)
														+ ((operacoes.get(i).getPercentualGain()) / 2);
												operacoes.get(i).setPorcentagemOperacaoFinal(percentualGainMax);
											} else if (verificaSeMaximaChegouAoPrecoDeEntradaAposChegarPrimeiroAlvo(
													operacoes, grafico, i, j)) {
												Double percentualLoss = operacoes.get(i).getPrecoLoss();
												operacoes.get(i).setPorcentagemOperacaoFinal(percentualLoss);
												j = grafico.size();
											}
										}

									}

								} else {
									if (verificaSeOperacaoCompra(operacoes, i)) {

										if (verificaSeMaximaChegouAoPrecoDeEntradaAposChegarPrimeiroAlvo(operacoes,
												grafico, i, j)) {
											operacoes.get(i).setStart(true);
										} else if (grafico.get(j).getMinima() <= operacoes.get(i)
												.getPrecoCancelarEntrada()) {
											j = grafico.size();
										}

									} else if (verificaSeOperacaoVenda(operacoes, i)) {

										if (verificaSeOperacaoChegouAoPrecoDeEntradaAposChegarAoPrimeiroAlvo(operacoes,
												grafico, i, j)) {
											operacoes.get(i).setStart(true);
										} else if (grafico.get(j).getMaxima() >= operacoes.get(i)
												.getPrecoCancelarEntrada()) {
											j = grafico.size();
										}

									}
								}

							}

							OperacaoService.adicionaOperacao(operacoes.get(i));

						}
					}
				}

				dataUltimaOperacao = operacoes.get(operacoes.size() - 1).getData();
			}
		}
	}
	
	public static void verificaEstatistica() {
		String nomeDoPapelOperacao = "";
		LocalDate dataUltimaOperacao = LocalDate.parse(DATAINICIAL);
		ArrayList<String> listaNomesPapeisOperaveis = OperacaoService.getAllPapeisOperacoesPossiveis(MIN, MAX);
		
		for(int i=0; i<listaNomesPapeisOperaveis.size(); i++) {
			nomeDoPapelOperacao = listaNomesPapeisOperaveis.get(i);
			dataUltimaOperacao = LocalDate.parse(DATAINICIAL);
			
			ArrayList<Operacao> operacoes = new ArrayList<Operacao>();
			
		}
		
	}
	
	public static void verificaEstatisticasMartelo(Operacao operacao) {
		
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
		return operacoes.get(i).getPrecoEntrada() < MAX;
	}

	private static boolean verifPrecoEntradaMaiorQueMin(ArrayList<Operacao> operacoes, int i) {
		return operacoes.get(i).getPrecoEntrada() > MIN;
	}

	private static boolean verificaContinuidadeDoGrafico(LocalDate data, ArrayList<InfoCandle> verif, int aux) {
		return ChronoUnit.DAYS.between(data, verif.get(aux).getData()) < NUMDISTANCIAENTREDATAS;
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
		for (int i = 0; i < operacoesFinalizadas.size(); i++) {
			if (operacoesFinalizadas.get(i).isStart()) {
				percentualFinal += operacoesFinalizadas.get(i).getPorcentagemOperacaoFinal();
				cont++;
			}
		}
		System.out.println("Percentual Final do teste: " + percentualFinal);
		System.out.println("Quantidade de operações realizadas: " + cont);
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
