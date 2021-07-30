package br.iesb.indicador_analise_grafica;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import br.iesb.indicador_analise_grafica.service.InfoCandleService;
import br.iesb.indicador_analise_grafica.service.OperacaoService;

public class TreinamentoRedeNeural {
	
	DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	static ArrayList<Operacao> operacoesAtivas = new ArrayList<Operacao>();
	static ArrayList<Operacao> operacoesFinalizadas = new ArrayList<Operacao>();
	static RedeNeural redeNeural = new RedeNeural();
	static ArrayList<InfoCandle> grafico = new ArrayList<InfoCandle>();
	public static void adicionarOperacao(Operacao operacao) {
		operacoesAtivas.add(operacao);
	}
	
	public static void realizaTreinamento() {
		
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

	private static boolean verificaPadraoMarteloNaoIniciada(int j) {
		return operacoesAtivas.get(j).getPadrao().equals(Padroes.MARTELO.getDescricao()) && operacoesAtivas.get(j).isStart() == false;
	}
	
	public void imprimeOperacoes() {
		for(int i=0; i<operacoesFinalizadas.size(); i++) {
			
			if(operacoesFinalizadas.get(i).isStart()) {
			
				System.out.println("Data da Opera��o: " + formato.format(operacoesFinalizadas.get(i).getData()));
				System.out.println(operacoesFinalizadas.get(i).getEntrada());
				System.out.println("Foi lucrativa? - "+operacoesFinalizadas.get(i).getLucro());
				System.out.println("Deu lucro 2x? - "+operacoesFinalizadas.get(i).getLucroMax());
				System.out.println("Preco de Entrada: "+operacoesFinalizadas.get(i).getPrecoEntrada());
				System.out.println("Pre�o de Gain: " + operacoesFinalizadas.get(i).getPrecoGain());
				System.out.println("Pre�o de Loss: " + operacoesFinalizadas.get(i).getPrecoLoss());
				System.out.println("Pre�o de Gain Maximo: " + operacoesFinalizadas.get(i).getPrecoGainMax());
				System.out.println("% Lucro: "+operacoesFinalizadas.get(i).getPercentualGain());
				System.out.println("% Prejuizo: "+operacoesFinalizadas.get(i).getPercentualLoss());
				System.out.println("% Lucro M�ximo: "+operacoesFinalizadas.get(i).getPercentualGainMax());
				System.out.println("% Da opera��o: "+operacoesFinalizadas.get(i).getPorcentagemOperacaoFinal());
				System.out.println();
			
			}else {
				
				System.out.println("Data da Opera��o: " + formato.format(operacoesFinalizadas.get(i).getData()));
				System.out.println("OPERA��O N�O INICIADA");
				System.out.println("Pre�o de Gain: " + operacoesFinalizadas.get(i).getPrecoGain());
				System.out.println("% Lucro: "+operacoesFinalizadas.get(i).getPercentualGain());
				System.out.println("Pre�o de Entrada: "+operacoesFinalizadas.get(i).getPrecoEntrada());
				System.out.println("Pre�o de Loss: " + operacoesFinalizadas.get(i).getPrecoLoss());
				System.out.println("% Prejuizo: "+operacoesFinalizadas.get(i).getPercentualLoss());
				System.out.println();
				
			}
		}
	}
	
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
