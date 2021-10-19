package br.iesb.indicador_analise_grafica;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.joone.engine.FullSynapse;
import org.joone.engine.LinearLayer;
import org.joone.engine.Monitor;
import org.joone.engine.NeuralNetEvent;
import org.joone.engine.NeuralNetListener;
import org.joone.engine.SigmoidLayer;
import org.joone.engine.learning.TeachingSynapse;
import org.joone.io.FileInputSynapse;
import org.joone.io.FileOutputSynapse;
import org.joone.io.MemoryOutputSynapse;
import org.joone.net.NeuralNet;

import br.iesb.indicador_analise_grafica.estatistica.EstatisticaMartelo;
import br.iesb.indicador_analise_grafica.service.BebeAbandonadoService;
import br.iesb.indicador_analise_grafica.service.DojiService;
import br.iesb.indicador_analise_grafica.service.EngolfoService;
import br.iesb.indicador_analise_grafica.service.MarteloInvertidoService;
import br.iesb.indicador_analise_grafica.service.MarteloService;
import br.iesb.indicador_analise_grafica.service.MarubozuService;
import br.iesb.indicador_analise_grafica.service.OperacaoService;
import br.iesb.indicador_analise_grafica.service.PiercingLineService;
import br.iesb.indicador_analise_grafica.service.TresSoldadosService;
import br.iesb.indicador_analise_grafica_enum.Entrada;
import br.iesb.indicador_analise_grafica_enum.Padroes;
import br.iesb.indicador_analise_grafica_enum.PavioInferior;
import br.iesb.indicador_analise_grafica_enum.PavioSuperior;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia200;
import br.iesb.indicador_analise_grafica_enum.TendenciaMediaCurta;
import br.iesb.indicador_analise_grafica_enum.TipoCandle;
import br.iesb.indicador_analise_grafica_enum.VariacaoPreco;
import br.iesb.indicador_analise_grafica_enum.VolumeAcimaMedia20;

public class RedeNeural implements NeuralNetListener {

	ArrayList<Candle> ultimosCandles = new ArrayList<Candle>();
	static ArrayList<Martelo> listaMartelo = new ArrayList<Martelo>();
	private static ArrayList<EstatisticaMartelo> estatisticasMartelo = new ArrayList<EstatisticaMartelo>();

	static int cont1 = 0;
	static int cont2 = 0;
	private final static Double MIN = 10.0;
	private final static Double MAX = 100.0;
	private final static int MEDIACURTA = 8;
	private final static int MEDIA = 20;
	private final static int MEDIALONGA = 200;
	private static int countIDMartelo = 0;
	private static int countIDEngolfo = 0;
	private static int countIDMarubozu = 0;
	private final static int PORCENTAGEMMAXIMAENGOLFO = 10;
	private static final Double PORCENTAGEMMAXIMAPAVIODOJI = 60.0;
	private static final Double PORCENTAGEMMINIMAPAVIODOJI = 40.0;
	private static final Double PORCENTAGEMMINIMAPARAESTATISTICA = 70.0;

	public static Operacao procuraPadraoMartelo(ArrayList<InfoCandle> listInfoCandle) {

		if (listInfoCandle == null) {
			return null;
		}

		for (int i = 0; i < listInfoCandle.size(); i++) {

			InfoCandle infoCandle = listInfoCandle.get(i);

			if (infoCandle != null) {

				Double pavioSuperior = pavioSuperiorEmPorcentagem(infoCandle);
				Double pavioInferior = pavioInferiorEmPorcentagem(infoCandle);

				// Condicoes para Martelo
				if (condicaoParaMartelo(infoCandle, pavioSuperior, pavioInferior)) {

					Operacao operacao = new Operacao();
					operacao.setNomeDoPapel(infoCandle.getNomeDoPapel());
					operacao.setDat(infoCandle.getData());
					operacao.setPadrao(Padroes.MARTELO.getDescricao());
					operacao.setEntrada(Entrada.COMPRA.getDescricao());
					operacao.setPrecoEntrada(setPrecoEntradaCompra(infoCandle));
					operacao.setPrecoStop(setPrecoStopCompra(infoCandle));
					operacao.setPrecoPrimeiroAlvoFibonacci(
							calculaPrecoPrimeiroAlvoFibonacci(infoCandle, Entrada.COMPRA));
					operacao.setPrecoSegundoAlvoFibonacci(calculaPrecoSegundoAlvoFibonacci(infoCandle, Entrada.COMPRA));
					operacao.setPrecoTerceiroAlvoFibonacci(
							calculaPrecoTerceiroAlvoFibonacci(infoCandle, Entrada.COMPRA));

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

				}
			}

		}

		return null;

	}

	public static Operacao procuraPadraoMarteloInvertido(InfoCandle infoCandle) {

		if (infoCandle != null) {

			Double pavioSuperior = pavioSuperiorEmPorcentagem(infoCandle);
			Double pavioInferior = pavioInferiorEmPorcentagem(infoCandle);

			// Condicoes para Martelo
			if (condicaoParaMarteloInvertido(infoCandle, pavioSuperior, pavioInferior)) {

				Operacao operacao = new Operacao(infoCandle.getData(), infoCandle.getNomeDoPapel(),
						Padroes.MARTELOINVERTIDO.getDescricao());
				operacao.setEntrada(Entrada.VENDA.getDescricao());
				operacao.setPrecoEntrada(setPrecoEntradaVenda(infoCandle));
				operacao.setPrecoStop(setPrecoStopVenda(infoCandle));
				operacao.setPrecoPrimeiroAlvoFibonacci(calculaPrecoPrimeiroAlvoFibonacci(infoCandle, Entrada.VENDA));
				operacao.setPrecoSegundoAlvoFibonacci(calculaPrecoSegundoAlvoFibonacci(infoCandle, Entrada.VENDA));
				operacao.setPrecoTerceiroAlvoFibonacci(calculaPrecoTerceiroAlvoFibonacci(infoCandle, Entrada.VENDA));

				MarteloInvertido marteloInvertido = new MarteloInvertido();

				marteloInvertido.setTipo(tipoCandle(infoCandle).getTipo());
				marteloInvertido.setPavioSuperior(classificaPavioSuperior(pavioSuperior).getDescricao());
				marteloInvertido.setPavioInferior(classificaPavioInferior(pavioInferior).getDescricao());
				marteloInvertido.setMarteloAcimaMedia200(verificaSePrecoAcimaMedia200(infoCandle));
				marteloInvertido.setVolumeAcimaMedia20(volumeAcimaMedia20(infoCandle));
				marteloInvertido.setOperacao(operacao);

				operacao.setMarteloInvertido(marteloInvertido);
				OperacaoService.adicionaOperacao(operacao);
				MarteloInvertidoService.adicionaMarteloInvertido(marteloInvertido);

			}
		}

		return null;

	}

	private static Boolean condicaoParaMarteloInvertido(InfoCandle infoCandle, Double pavioSuperior,
			Double pavioInferior) {
		return pavioSuperior >= 67 && pavioInferior <= 10
				&& tendenciaMediaCurta(infoCandle) == TendenciaMediaCurta.ALTA;
	}

	private static Boolean verificaSePrecoAcimaMedia200(InfoCandle infoCandle) {

		if (infoCandle.getPrecoMedia200() != null) {
			return infoCandle.getMaxima() > infoCandle.getPrecoMedia200();
		}

		return null;
	}

	public static Boolean procuraPadraoMarubozu(ArrayList<InfoCandle> listInfoCandle) {

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

				Operacao operacao = new Operacao(infoCandle.getData(), infoCandle.getNomeDoPapel(),
						Padroes.MARUBOZU.getDescricao());

				if (tipoCandle(infoCandle) == TipoCandle.POSITIVO) {

					operacao.setEntrada(Entrada.COMPRA.getDescricao());
					operacao.setPrecoEntrada(setPrecoEntradaCompra(infoCandle));
					operacao.setPrecoStop(setPrecoStopCompra(infoCandle));
					operacao.setPrecoPrimeiroAlvoFibonacci(
							calculaPrecoPrimeiroAlvoFibonacci(infoCandle, Entrada.COMPRA));
					operacao.setPrecoSegundoAlvoFibonacci(calculaPrecoSegundoAlvoFibonacci(infoCandle, Entrada.COMPRA));
					operacao.setPrecoTerceiroAlvoFibonacci(
							calculaPrecoTerceiroAlvoFibonacci(infoCandle, Entrada.COMPRA));

					Marubozu marubozu = new Marubozu(tipoCandle(infoCandle).getTipo(),
							classificaPavioSuperior(pavioSuperior).getDescricao(),
							classificaPavioInferior(pavioInferior).getDescricao(), volumeAcimaMedia20(infoCandle),
							classificaVariacaoPreco(calculaVariacao).getDescricao(), operacao);

					operacao.setMarubozu(marubozu);
					OperacaoService.adicionaOperacao(operacao);
					MarubozuService.adicionaMarubozu(marubozu);

					return true;
				} else {

					operacao.setEntrada(Entrada.VENDA.getDescricao());
					operacao.setPrecoEntrada(setPrecoEntradaVenda(infoCandle));
					operacao.setPrecoStop(setPrecoStopVenda(infoCandle));
					operacao.setPrecoPrimeiroAlvoFibonacci(
							calculaPrecoPrimeiroAlvoFibonacci(infoCandle, Entrada.VENDA));
					operacao.setPrecoSegundoAlvoFibonacci(calculaPrecoSegundoAlvoFibonacci(infoCandle, Entrada.VENDA));
					operacao.setPrecoTerceiroAlvoFibonacci(
							calculaPrecoTerceiroAlvoFibonacci(infoCandle, Entrada.VENDA));

					Marubozu marubozu = new Marubozu(tipoCandle(infoCandle).getTipo(),
							classificaPavioSuperior(pavioSuperior).getDescricao(),
							classificaPavioInferior(pavioInferior).getDescricao(), volumeAcimaMedia20(infoCandle),
							classificaVariacaoPreco(calculaVariacao).getDescricao(), operacao);

					operacao.setMarubozu(marubozu);
					OperacaoService.adicionaOperacao(operacao);
					MarubozuService.adicionaMarubozu(marubozu);

					return true;
				}

			}
		}

		return false;

	}

	public static Boolean procuraPadraoPiercingLine(ArrayList<InfoCandle> grafico) {

		if (grafico == null) {
			return false;
		}

		for (int i = 0; i < grafico.size() - 1; i++) {

			InfoCandle primeiroCandle = grafico.get(i);
			InfoCandle segundoCandle = grafico.get(i + 1);

			if (tipoCandle(primeiroCandle) == TipoCandle.POSITIVO) {

				if (condicaoPiercingLineDeBaixa(primeiroCandle, segundoCandle)) {

					int perfuracao = calculoPerfuracao(primeiroCandle, segundoCandle);

					Operacao operacao = new Operacao();
					operacao.setDat(segundoCandle.getData());
					operacao.setNomeDoPapel(segundoCandle.getNomeDoPapel());
					operacao.setPadrao(Padroes.DARKCLOUD.getDescricao());
					operacao.setTipoEntrada(Entrada.VENDA.getDescricao());
					operacao.setPrecoEntrada(setPrecoEntradaVenda(segundoCandle));
					operacao.setPrecoStop(setPrecoStopVenda(segundoCandle));
					operacao.setPrecoPrimeiroAlvoFibonacci(
							calculaPrecoPrimeiroAlvoFibonacci(segundoCandle, Entrada.VENDA));
					operacao.setPrecoSegundoAlvoFibonacci(
							calculaPrecoSegundoAlvoFibonacci(segundoCandle, Entrada.VENDA));
					operacao.setPrecoTerceiroAlvoFibonacci(
							calculaPrecoTerceiroAlvoFibonacci(segundoCandle, Entrada.VENDA));

					PiercingLine piercingLine = new PiercingLine();

					piercingLine.setTipo(tipoCandle(segundoCandle).getTipo());
					piercingLine.setVolumeAcimaMedia20(volumeAcimaMedia20(segundoCandle));
					piercingLine.setPerfuracao(perfuracao);
					piercingLine.setPrecoAcimaMedia8(verificaSePrecoFechamentoAcimaMedia(segundoCandle, MEDIACURTA));
					piercingLine.setPrecoAcimaMedia20(verificaSePrecoFechamentoAcimaMedia(segundoCandle, MEDIA));
					piercingLine.setPrecoAcimaMedia200(verificaSePrecoFechamentoAcimaMedia(segundoCandle, MEDIALONGA));
					piercingLine.setOperacao(operacao);
					operacao.setPiercingLine(piercingLine);

					OperacaoService.adicionaOperacao(operacao);
					PiercingLineService.adicionaPiercingLine(piercingLine);

				}

			} else if (tipoCandle(primeiroCandle) == TipoCandle.NEGATIVO) {

				if (condicaoPiercingLineDeAlta(primeiroCandle, segundoCandle)) {

					int perfuracao = calculoPerfuracao(primeiroCandle, segundoCandle);

					Operacao operacao = new Operacao();
					operacao.setDat(segundoCandle.getData());
					operacao.setNomeDoPapel(segundoCandle.getNomeDoPapel());
					operacao.setPadrao(Padroes.PIERCINGLINE.getDescricao());
					operacao.setTipoEntrada(Entrada.COMPRA.getDescricao());
					operacao.setPrecoEntrada(setPrecoEntradaCompra(segundoCandle));
					operacao.setPrecoStop(setPrecoStopCompra(segundoCandle));
					operacao.setPrecoPrimeiroAlvoFibonacci(
							calculaPrecoPrimeiroAlvoFibonacci(segundoCandle, Entrada.COMPRA));
					operacao.setPrecoSegundoAlvoFibonacci(
							calculaPrecoSegundoAlvoFibonacci(segundoCandle, Entrada.COMPRA));
					operacao.setPrecoTerceiroAlvoFibonacci(
							calculaPrecoTerceiroAlvoFibonacci(segundoCandle, Entrada.COMPRA));

					PiercingLine piercingLine = new PiercingLine();

					piercingLine.setTipo(tipoCandle(segundoCandle).getTipo());
					piercingLine.setVolumeAcimaMedia20(volumeAcimaMedia20(segundoCandle));
					piercingLine.setPerfuracao(perfuracao);
					piercingLine.setPrecoAcimaMedia8(verificaSePrecoFechamentoAcimaMedia(segundoCandle, MEDIACURTA));
					piercingLine.setPrecoAcimaMedia20(verificaSePrecoFechamentoAcimaMedia(segundoCandle, MEDIA));
					piercingLine.setPrecoAcimaMedia200(verificaSePrecoFechamentoAcimaMedia(segundoCandle, MEDIALONGA));
					piercingLine.setOperacao(operacao);
					operacao.setPiercingLine(piercingLine);

					OperacaoService.adicionaOperacao(operacao);
					PiercingLineService.adicionaPiercingLine(piercingLine);

				}

			}

		}

		return null;
	}

	public static Boolean procuraPadraoTresSoldados(ArrayList<InfoCandle> grafico) {

		if (grafico == null) {
			return null;
		}

		for (int i = 0; i < grafico.size() - 2; i++) {

			InfoCandle primeiroCandle = grafico.get(i);
			InfoCandle segundoCandle = grafico.get(i + 1);
			InfoCandle terceiroCandle = grafico.get(i + 2);

			if (condicaoParaTresSoldadosDeAlta(primeiroCandle, segundoCandle, terceiroCandle)) {

				// juntando todos os candles em um unico candle
				InfoCandle todosCandles = new InfoCandle();
				// Na compra, a maxima vai ficar com o valor do ultimo Candle. A minima vai
				// ficar com o valor do primeiro Candle

				todosCandles.setMaxima(terceiroCandle.getMaxima());
				todosCandles.setMinima(primeiroCandle.getMinima());

				Operacao operacao = new Operacao();

				operacao.setDat(terceiroCandle.getData());
				operacao.setNomeDoPapel(terceiroCandle.getNomeDoPapel());
				operacao.setPadrao(Padroes.TRESSOLDADOSDEALTA.getDescricao());
				operacao.setTipoEntrada(Entrada.COMPRA.getDescricao());
				operacao.setPrecoEntrada(setPrecoEntradaCompra(terceiroCandle));
				operacao.setPrecoStop(setPrecoStopCompra(primeiroCandle));
				operacao.setPrecoPrimeiroAlvoFibonacci(calculaPrecoPrimeiroAlvoFibonacci(todosCandles, Entrada.COMPRA));
				operacao.setPrecoSegundoAlvoFibonacci(calculaPrecoSegundoAlvoFibonacci(todosCandles, Entrada.COMPRA));
				operacao.setPrecoTerceiroAlvoFibonacci(calculaPrecoTerceiroAlvoFibonacci(todosCandles, Entrada.COMPRA));

				TresSoldados tresSoldados = new TresSoldados();

				tresSoldados.setPavioInferiorPrimeiroCandle(pavioInferiorEmPorcentagem(primeiroCandle).intValue());
				tresSoldados.setPavioSuperiorPrimeiroCandle(pavioSuperiorEmPorcentagem(primeiroCandle).intValue());
				tresSoldados.setPavioInferiorSegundoCandle(pavioInferiorEmPorcentagem(segundoCandle).intValue());
				tresSoldados.setPavioSuperiorSegundoCandle(pavioSuperiorEmPorcentagem(segundoCandle).intValue());
				tresSoldados.setPavioInferiorTerceiroCandle(pavioInferiorEmPorcentagem(terceiroCandle).intValue());
				tresSoldados.setPavioSuperiorTerceiroCandle(pavioSuperiorEmPorcentagem(terceiroCandle).intValue());
				tresSoldados.setPrecoAcimaMedia8(verificaSePrecoFechamentoAcimaMedia(terceiroCandle, MEDIACURTA));
				tresSoldados.setPrecoAcimaMedia20(verificaSePrecoFechamentoAcimaMedia(terceiroCandle, MEDIA));
				tresSoldados.setPrecoAcimaMedia200(verificaSePrecoFechamentoAcimaMedia(terceiroCandle, MEDIALONGA));

				tresSoldados.setOperacao(operacao);
				operacao.setTresSoldados(tresSoldados);

				OperacaoService.adicionaOperacao(operacao);
				TresSoldadosService.adicionaTresSoldados(tresSoldados);

			} else if (condicaoParaTresSoldadosDeBaixa(primeiroCandle, segundoCandle, terceiroCandle)) {

				// juntando todos os candles em um unico candle
				InfoCandle todosCandles = new InfoCandle();
				// Na Venda, a maxima vai ficar com o valor do primeiro Candle. A minima vai
				// ficar com o valor do terceiro Candle

				todosCandles.setMaxima(primeiroCandle.getMaxima());
				todosCandles.setMinima(terceiroCandle.getMinima());

				Operacao operacao = new Operacao();

				operacao.setDat(terceiroCandle.getData());
				operacao.setNomeDoPapel(terceiroCandle.getNomeDoPapel());
				operacao.setPadrao(Padroes.TRESSOLDADOSDEBAIXA.getDescricao());
				operacao.setTipoEntrada(Entrada.VENDA.getDescricao());
				operacao.setPrecoEntrada(setPrecoEntradaVenda(terceiroCandle));
				operacao.setPrecoStop(setPrecoStopVenda(primeiroCandle));
				operacao.setPrecoPrimeiroAlvoFibonacci(calculaPrecoPrimeiroAlvoFibonacci(todosCandles, Entrada.VENDA));
				operacao.setPrecoSegundoAlvoFibonacci(calculaPrecoSegundoAlvoFibonacci(todosCandles, Entrada.VENDA));
				operacao.setPrecoTerceiroAlvoFibonacci(calculaPrecoTerceiroAlvoFibonacci(todosCandles, Entrada.VENDA));

				TresSoldados tresSoldados = new TresSoldados();

				tresSoldados.setPavioInferiorPrimeiroCandle(pavioInferiorEmPorcentagem(primeiroCandle).intValue());
				tresSoldados.setPavioSuperiorPrimeiroCandle(pavioSuperiorEmPorcentagem(primeiroCandle).intValue());
				tresSoldados.setPavioInferiorSegundoCandle(pavioInferiorEmPorcentagem(segundoCandle).intValue());
				tresSoldados.setPavioSuperiorSegundoCandle(pavioSuperiorEmPorcentagem(segundoCandle).intValue());
				tresSoldados.setPavioInferiorTerceiroCandle(pavioInferiorEmPorcentagem(terceiroCandle).intValue());
				tresSoldados.setPavioSuperiorTerceiroCandle(pavioSuperiorEmPorcentagem(terceiroCandle).intValue());
				tresSoldados.setPrecoAcimaMedia8(verificaSePrecoFechamentoAcimaMedia(terceiroCandle, MEDIACURTA));
				tresSoldados.setPrecoAcimaMedia20(verificaSePrecoFechamentoAcimaMedia(terceiroCandle, MEDIA));
				tresSoldados.setPrecoAcimaMedia200(verificaSePrecoFechamentoAcimaMedia(terceiroCandle, MEDIALONGA));
				tresSoldados.setVolumeAcimaMedia20(volumeAcimaMedia20(terceiroCandle));

				tresSoldados.setOperacao(operacao);
				operacao.setTresSoldados(tresSoldados);

				OperacaoService.adicionaOperacao(operacao);
				TresSoldadosService.adicionaTresSoldados(tresSoldados);

			}

		}

		return null;
	}

	public static Boolean procuraPadraoBebeAbandonado(ArrayList<InfoCandle> grafico) {

		if (grafico == null) {
			return null;
		}

		for (int i = 0; i < grafico.size() - 2; i++) {

			InfoCandle primeiroCandle = grafico.get(i);
			InfoCandle segundoCandle = grafico.get(i + 1);
			InfoCandle terceiroCandle = grafico.get(i + 2);

			if (condicaoParaBebeAbandonadoDeBaixa(primeiroCandle, segundoCandle, terceiroCandle)) {

				InfoCandle todosCandles = new InfoCandle();

				todosCandles.setMaxima(segundoCandle.getMaxima());
				todosCandles.setMinima(terceiroCandle.getMinima());

				Operacao operacao = new Operacao();

				operacao.setDat(terceiroCandle.getData());
				operacao.setNomeDoPapel(terceiroCandle.getNomeDoPapel());
				operacao.setPadrao(Padroes.BEBEABANDONADODEBAIXA.getDescricao());
				operacao.setTipoEntrada(Entrada.VENDA.getDescricao());
				operacao.setPrecoEntrada(setPrecoEntradaVenda(terceiroCandle));
				operacao.setPrecoStop(setPrecoStopVenda(segundoCandle));
				operacao.setPrecoPrimeiroAlvoFibonacci(calculaPrecoPrimeiroAlvoFibonacci(todosCandles, Entrada.VENDA));
				operacao.setPrecoSegundoAlvoFibonacci(calculaPrecoSegundoAlvoFibonacci(todosCandles, Entrada.VENDA));
				operacao.setPrecoTerceiroAlvoFibonacci(calculaPrecoTerceiroAlvoFibonacci(todosCandles, Entrada.VENDA));

				BebeAbandonado bebeAbandonado = new BebeAbandonado();

				bebeAbandonado.setPrimeiroCandleMarubozu(condicaoParaMarubozuSimples(primeiroCandle));
				bebeAbandonado.setSegundoCandleDoji(condicaoParaDoji(pavioSuperiorEmPorcentagem(segundoCandle),
						pavioInferiorEmPorcentagem(segundoCandle)));
				bebeAbandonado.setTerceiroCandleMarubozu(condicaoParaMarubozuSimples(terceiroCandle));
				bebeAbandonado.setPrecoAcimaMedia8(verificaSePrecoFechamentoAcimaMedia(terceiroCandle, MEDIACURTA));
				bebeAbandonado.setPrecoAcimaMedia20(verificaSePrecoFechamentoAcimaMedia(terceiroCandle, MEDIA));
				bebeAbandonado.setPrecoAcimaMedia200(verificaSePrecoFechamentoAcimaMedia(terceiroCandle, MEDIALONGA));

				bebeAbandonado.setOperacao(operacao);
				operacao.setBebeAbandonado(bebeAbandonado);

				OperacaoService.adicionaOperacao(operacao);
				BebeAbandonadoService.adicionaBebeAbandonado(bebeAbandonado);

			} else if (condicaoParaBebeAbandonadoDeAlta(primeiroCandle, segundoCandle, terceiroCandle)) {

				InfoCandle todosCandles = new InfoCandle();

				todosCandles.setMinima(segundoCandle.getMinima());
				todosCandles.setMaxima(terceiroCandle.getMaxima());

				Operacao operacao = new Operacao();

				operacao.setDat(terceiroCandle.getData());
				operacao.setNomeDoPapel(terceiroCandle.getNomeDoPapel());
				operacao.setPadrao(Padroes.BEBEABANDONADODEALTA.getDescricao());
				operacao.setTipoEntrada(Entrada.COMPRA.getDescricao());
				operacao.setPrecoEntrada(setPrecoEntradaCompra(terceiroCandle));
				operacao.setPrecoStop(setPrecoStopCompra(segundoCandle));
				operacao.setPrecoPrimeiroAlvoFibonacci(calculaPrecoPrimeiroAlvoFibonacci(todosCandles, Entrada.COMPRA));
				operacao.setPrecoSegundoAlvoFibonacci(calculaPrecoSegundoAlvoFibonacci(todosCandles, Entrada.COMPRA));
				operacao.setPrecoTerceiroAlvoFibonacci(calculaPrecoTerceiroAlvoFibonacci(todosCandles, Entrada.COMPRA));

				BebeAbandonado bebeAbandonado = new BebeAbandonado();

				bebeAbandonado.setPrimeiroCandleMarubozu(condicaoParaMarubozuSimples(primeiroCandle));
				bebeAbandonado.setSegundoCandleDoji(condicaoParaDoji(pavioSuperiorEmPorcentagem(segundoCandle),
						pavioInferiorEmPorcentagem(segundoCandle)));
				bebeAbandonado.setTerceiroCandleMarubozu(condicaoParaMarubozuSimples(terceiroCandle));
				bebeAbandonado.setPrecoAcimaMedia8(verificaSePrecoFechamentoAcimaMedia(terceiroCandle, MEDIACURTA));
				bebeAbandonado.setPrecoAcimaMedia20(verificaSePrecoFechamentoAcimaMedia(terceiroCandle, MEDIA));
				bebeAbandonado.setPrecoAcimaMedia200(verificaSePrecoFechamentoAcimaMedia(terceiroCandle, MEDIALONGA));

				bebeAbandonado.setOperacao(operacao);
				operacao.setBebeAbandonado(bebeAbandonado);

				OperacaoService.adicionaOperacao(operacao);
				BebeAbandonadoService.adicionaBebeAbandonado(bebeAbandonado);

			}

		}
		return null;
	}

	private static ArrayList<PossibilidadeMartelo> todasAsPossibilidadesMartelo() {

		List<TipoCandle> tipos = TipoCandle.retornaTipos();
		List<PavioSuperior> pavioS = PavioSuperior.getPavioSuperiorMartelo();
		List<PavioInferior> pavioInf = PavioInferior.getPavioInferiorMartelo();
		List<VolumeAcimaMedia20> vol = VolumeAcimaMedia20.getListVolumeAcimaMedia20();
		List<PrecoAcimaMedia200> precos = PrecoAcimaMedia200.getListPrecoAcimaMedia200();

		ArrayList<PossibilidadeMartelo> possibilidades = new ArrayList<PossibilidadeMartelo>();

		tipos.stream().forEach(tipo -> {
			pavioS.stream().forEach(pavioSuperior -> {
				pavioInf.stream().forEach(pavioInferior -> {
					vol.stream().forEach(volume -> {
						precos.stream().forEach(preco -> {

							PossibilidadeMartelo possibilidade = new PossibilidadeMartelo(tipo, pavioSuperior,
									pavioInferior, volume, preco);

							if (possibilidade.tipoCandle != TipoCandle.NULL
									&& possibilidade.pavioSuperior != PavioSuperior.NULL
									&& possibilidade.pavioInferior != PavioInferior.NULL
									&& possibilidade.volumeAcimaMedia20 != VolumeAcimaMedia20.NULL
									&& possibilidade.precoAcimaMedia200 != PrecoAcimaMedia200.NULL) {

								possibilidades.add(possibilidade);

							}

						});
					});
				});
			});
		});

		return possibilidades;

	}

	public static void preenchendoEstatisticaMartelo() {

		ArrayList<PossibilidadeMartelo> todasPossibilidades = todasAsPossibilidadesMartelo();

		todasPossibilidades.stream().forEach(p -> {

			EstatisticaMartelo estatisticaMartelo = new EstatisticaMartelo();

			estatisticaMartelo.setConfiguracaoMartelo(p);

			Double numeradorGeral = OperacaoService.contaMarteloEspecificoInciadoNaoChegouAlvo(p.tipoCandle.getTipo(),
					p.pavioSuperior.getDescricao(), p.pavioInferior.getDescricao(), p.volumeAcimaMedia20.getValor(),
					p.precoAcimaMedia200.getValor());

			Double denominadorGeral = OperacaoService.contaMarteloEspecificoInciado(p.tipoCandle.getTipo(),
					p.pavioSuperior.getDescricao(), p.pavioInferior.getDescricao(), p.volumeAcimaMedia20.getValor(),
					p.precoAcimaMedia200.getValor());

			estatisticaMartelo.setFrequenciaGeral(denominadorGeral.intValue());

			Double numeradorUltimosCincoAnos = OperacaoService
					.contaMarteloEspecificoInciadoUltimosCincoAnosNaoChegouAlvo(p.tipoCandle.getTipo(),
							p.pavioSuperior.getDescricao(), p.pavioInferior.getDescricao(),
							p.volumeAcimaMedia20.getValor(), p.precoAcimaMedia200.getValor());

			Double denominadorUltimosCincoAnos = OperacaoService.contaMarteloEspecificoInciadoUltimosCincoAnos(
					p.tipoCandle.getTipo(), p.pavioSuperior.getDescricao(), p.pavioInferior.getDescricao(),
					p.volumeAcimaMedia20.getValor(), p.precoAcimaMedia200.getValor());

			estatisticaMartelo.setFrequenciaUltimosCincoAnos(denominadorUltimosCincoAnos.intValue());

			Double numeradorUltimoAno = OperacaoService.contaMarteloEspecificoInciadoUltimoAnoNaoChegouAlvo(
					p.tipoCandle.getTipo(), p.pavioSuperior.getDescricao(), p.pavioInferior.getDescricao(),
					p.volumeAcimaMedia20.getValor(), p.precoAcimaMedia200.getValor());

			Double denominadorUltimoAno = OperacaoService.contaMarteloEspecificoInciadoUltimoAno(p.tipoCandle.getTipo(),
					p.pavioSuperior.getDescricao(), p.pavioInferior.getDescricao(), p.volumeAcimaMedia20.getValor(),
					p.precoAcimaMedia200.getValor());

			estatisticaMartelo.setFrequenciaUltimoAno(denominadorUltimoAno.intValue());

			if (denominadorGeral != 0) {
				Double estatisticaGeral = 100.0 - ((numeradorGeral / denominadorGeral) * 100);
				estatisticaMartelo.setAssertividadeGeral(estatisticaGeral);
			}

			if (denominadorUltimosCincoAnos != 0) {
				Double estatisticaUltimosCincoAnos = 100.0
						- ((numeradorUltimosCincoAnos / denominadorUltimosCincoAnos) * 100);
				estatisticaMartelo.setAssertividadeUltimosCincoAnos(estatisticaUltimosCincoAnos);
			}

			if (denominadorUltimoAno != 0) {
				Double estatisticaUltimoAno = 100.0 - ((numeradorUltimoAno / denominadorUltimoAno) * 100);
				estatisticaMartelo.setAssertividadeUltimoAno(estatisticaUltimoAno);
			}

			estatisticasMartelo.add(estatisticaMartelo);

			try {
				preencherTxtTreinamentoRedeNeuralMartelo();
			} catch (IOException e) {
				e.printStackTrace();
			}

		});

	}

	private static void preencherTxtTreinamentoRedeNeuralMartelo() throws IOException {

		FileWriter arq = new FileWriter("resource\\ArquivoTreinamentoMartelo.txt");
		PrintWriter gravarArq = new PrintWriter(arq);

		estatisticasMartelo.stream().forEach(estatisticaMartelo -> {
			Double assertividadeComPeso = calculaAssertividadeComPeso(estatisticaMartelo);

			gravarArq.printf(estatisticaMartelo.getConfiguracaoMartelo().tipoCandle.getID() + ";"
					+ estatisticaMartelo.getConfiguracaoMartelo().pavioSuperior.getID() + ";"
					+ estatisticaMartelo.getConfiguracaoMartelo().pavioInferior.getID() + ";"
					+ estatisticaMartelo.getConfiguracaoMartelo().volumeAcimaMedia20.getValor() + ";"
					+ estatisticaMartelo.getConfiguracaoMartelo().precoAcimaMedia200.getValor() + ";");

			if (condicaoParaCompraMartelo(assertividadeComPeso)) {

				gravarArq.printf(1 + ";");

			} else {

				gravarArq.printf(0 + ";");

			}

			gravarArq.println();

		});

		arq.close();

	}
	
	public static void preencherTxtValidacaoRedeNeuralMartelo() throws IOException {

		FileWriter arq = new FileWriter("resource\\ArquivoValidacaoMartelo.txt");
		PrintWriter gravarArq = new PrintWriter(arq);

		LocalDate data = LocalDate.parse("2021-01-01");
		
		ArrayList<Operacao> operacoes = OperacaoService.getOperacoesUltimoAno(MIN, MAX, data);
		ArrayList<Martelo> martelos = new ArrayList<Martelo>();
		
		operacoes.stream().forEach(operacao -> {
			if(Padroes.comparaPadrao(operacao.getPadrao()) == Padroes.MARTELO) {
				martelos.add(operacao.getMartelo());
			}
		});
		
		martelos.stream().forEach(martelo -> {
			gravarArq.printf(TipoCandle.comparaTipoCandle(martelo.getTipo()).getID() + ";"
					+ PavioSuperior.comparaPavioSuperior(martelo.getPavioSuperior()).getID() + ";"
					+ PavioInferior.comparaPavioInferior(martelo.getPavioInferior()).getID() + ";"
					+ VolumeAcimaMedia20.comparaVolumeAcimaMedia20(martelo.getVolumeAcimaMedia20()).getValor() + ";"
					+ PrecoAcimaMedia200.comparaPrecoAcimaMedia200(martelo.getMarteloAcimaMedia200()).getValor() + ";");
			gravarArq.println();
			
		});

		arq.close();

	}

	public static void realizaTreinamentoRedeNeural() {

		
		String inputFile = "resource\\ArquivoTreinamentoMartelo.txt";
		String inputFileValidacao = "resource\\\\ArquivoValidacaoMartelo.txt";
		
		String outputFile = "resource\\ArquivoTreinamentoMarteloSaida.txt";
		  
	    
		LinearLayer input = new LinearLayer();
	    
		SigmoidLayer hidden = new SigmoidLayer();
	    
		LinearLayer output = new LinearLayer();
	    
	    
		input.setLayerName("input");
		
		hidden.setLayerName("hidden");
		
		output.setLayerName("output");

			    
	    
		input.setRows(5);
		hidden.setRows(4);
		output.setRows(1);

	   
		FullSynapse synapse_IH = new FullSynapse();
		
		FullSynapse synapse_HO = new FullSynapse();

	    
		synapse_IH.setName("IH");
		
		synapse_HO.setName("HO");
		
	
		input.addOutputSynapse(synapse_IH);
		hidden.addInputSynapse(synapse_IH);
		
		hidden.addOutputSynapse(synapse_HO);
		output.addInputSynapse(synapse_HO);

		
		FileInputSynapse inputStream = new FileInputSynapse();
		inputStream.setAdvancedColumnSelector("1,2,3,4,5");
		inputStream.setInputFile(new File(inputFile));
	    
		input.addInputSynapse(inputStream);

		TeachingSynapse trainer = new TeachingSynapse();
		
		FileInputSynapse samples = new FileInputSynapse();
		
		samples.setInputFile(new File(inputFile));
		
		samples.setAdvancedColumnSelector("6");

		trainer.setDesired(samples);

		FileOutputSynapse error = new FileOutputSynapse();
		error.setFileName(outputFile);
	   	trainer.addResultSynapse(error);
		output.addOutputSynapse(trainer);

		
		NeuralNet nnet = new NeuralNet();

		
		nnet.addLayer(input, NeuralNet.INPUT_LAYER);
		
		nnet.addLayer(hidden, NeuralNet.HIDDEN_LAYER);
		
		nnet.addLayer(output, NeuralNet.OUTPUT_LAYER);

		nnet.setTeacher(trainer);

		Monitor monitor = nnet.getMonitor();
		monitor.setLearningRate(0.8);
		monitor.setMomentum(0.3);

		
		monitor.addNeuralNetListener(nnet);

		monitor.setTrainingPatterns(144);
		monitor.setTotCicles(2000);
		monitor.setLearning(true);
		nnet.go();

		while (nnet.isRunning()) {
			
		}

		
		FileInputSynapse inputStreamValidacao = new FileInputSynapse();
		inputStreamValidacao.setAdvancedColumnSelector("1,2,3,4,5");
		inputStreamValidacao.setInputFile(new File(inputFileValidacao));
	    input.addInputSynapse(inputStreamValidacao);
	    
		nnet.addLayer(input, NeuralNet.INPUT_LAYER);
		MemoryOutputSynapse memOut = new MemoryOutputSynapse();
		output.removeAllOutputs();
		output.addOutputSynapse(memOut);
		nnet.getMonitor().setTotCicles(1);
		nnet.getMonitor().setLearning(false);
		
		nnet.go();

		for(int i=0; i<185; i++) {
			double[] pattern = memOut.getNextPattern();
			
			System.out.println("Saida " + i+1 + ": " + pattern[0]);
			System.out.println();
		}
		

	}
	
	

	private static boolean condicaoParaCompraMartelo(Double assertividadeComPeso) {
		return assertividadeComPeso.compareTo(PORCENTAGEMMINIMAPARAESTATISTICA) == 0
				|| assertividadeComPeso > PORCENTAGEMMINIMAPARAESTATISTICA;
	}

	private static double calculaAssertividadeComPeso(EstatisticaMartelo estatisticaMartelo) {
		return (estatisticaMartelo.getAssertividadeGeral() + (estatisticaMartelo.getAssertividadeUltimosCincoAnos() * 2)
				+ (estatisticaMartelo.getAssertividadeUltimoAno() * 3)) / 6;
	}

	private static boolean condicaoParaBebeAbandonadoDeAlta(InfoCandle primeiroCandle, InfoCandle segundoCandle,
			InfoCandle terceiroCandle) {

		return tipoCandle(primeiroCandle) == TipoCandle.NEGATIVO
				&& segundoCandle.getMaxima() < primeiroCandle.getMinima()
				&& terceiroCandle.getMinima() > segundoCandle.getMaxima()
				&& tipoCandle(terceiroCandle) == TipoCandle.POSITIVO;
	}

	private static boolean condicaoParaMarubozuSimples(InfoCandle primeiroCandle) {
		return pavioSuperiorEmPorcentagem(primeiroCandle) < 10 && pavioInferiorEmPorcentagem(primeiroCandle) < 10;
	}

	private static boolean condicaoParaBebeAbandonadoDeBaixa(InfoCandle primeiroCandle, InfoCandle segundoCandle,
			InfoCandle terceiroCandle) {
		return tipoCandle(primeiroCandle) == TipoCandle.POSITIVO
				&& segundoCandle.getMinima() > primeiroCandle.getMaxima()
				&& terceiroCandle.getMaxima() < segundoCandle.getMinima()
				&& tipoCandle(terceiroCandle) == TipoCandle.NEGATIVO;
	}

	private static boolean condicaoParaTresSoldadosDeBaixa(InfoCandle primeiroCandle, InfoCandle segundoCandle,
			InfoCandle terceiroCandle) {
		return tipoCandle(primeiroCandle) == TipoCandle.NEGATIVO && pavioSuperiorEmPorcentagem(primeiroCandle) < 40
				&& pavioInferiorEmPorcentagem(primeiroCandle) < 40 && tipoCandle(segundoCandle) == TipoCandle.NEGATIVO
				&& pavioSuperiorEmPorcentagem(segundoCandle) < 40 && pavioInferiorEmPorcentagem(segundoCandle) < 40
				&& tipoCandle(terceiroCandle) == TipoCandle.NEGATIVO && pavioSuperiorEmPorcentagem(terceiroCandle) < 40
				&& pavioInferiorEmPorcentagem(terceiroCandle) < 40
				&& tendenciaMediaCurta(terceiroCandle) == TendenciaMediaCurta.ALTA;
	}

	private static boolean condicaoParaTresSoldadosDeAlta(InfoCandle primeiroCandle, InfoCandle segundoCandle,
			InfoCandle terceiroCandle) {
		return tipoCandle(primeiroCandle) == TipoCandle.POSITIVO && pavioSuperiorEmPorcentagem(primeiroCandle) < 40
				&& pavioInferiorEmPorcentagem(primeiroCandle) < 40 && tipoCandle(segundoCandle) == TipoCandle.POSITIVO
				&& pavioSuperiorEmPorcentagem(segundoCandle) < 40 && pavioInferiorEmPorcentagem(segundoCandle) < 40
				&& tipoCandle(terceiroCandle) == TipoCandle.POSITIVO && pavioSuperiorEmPorcentagem(terceiroCandle) < 40
				&& pavioInferiorEmPorcentagem(terceiroCandle) < 40
				&& tendenciaMediaCurta(terceiroCandle) == TendenciaMediaCurta.BAIXA;
	}

	private static int calculoPerfuracao(InfoCandle primeiroCandle, InfoCandle segundoCandle) {
		Double variacaoTotal = Math.abs(primeiroCandle.getFechamento() - primeiroCandle.getAbertura());
		Double variacaoPerfuracao = Math.abs(primeiroCandle.getFechamento() - segundoCandle.getFechamento());

		Double variacao = Math.abs(variacaoPerfuracao / (variacaoTotal - 1) * 100);
		return variacao.intValue();
	}

	private static boolean condicaoPiercingLineDeAlta(InfoCandle primeiroCandle, InfoCandle segundoCandle) {
		return segundoCandle.getAbertura() < primeiroCandle.getFechamento()
				&& precoMetadeCorpoCandle(primeiroCandle) < segundoCandle.getFechamento()
				&& segundoCandle.getFechamento() < primeiroCandle.getAbertura()
				&& pavioSuperiorEmPorcentagem(primeiroCandle) < 20 && pavioInferiorEmPorcentagem(primeiroCandle) < 20
				&& segundoCandle.getMaxima() < primeiroCandle.getMaxima();
	}

	private static boolean condicaoPiercingLineDeBaixa(InfoCandle primeiroCandle, InfoCandle segundoCandle) {
		return segundoCandle.getAbertura() > primeiroCandle.getFechamento()
				&& precoMetadeCorpoCandle(primeiroCandle) > segundoCandle.getFechamento()
				&& segundoCandle.getFechamento() > primeiroCandle.getAbertura()
				&& pavioSuperiorEmPorcentagem(primeiroCandle) < 20 && pavioInferiorEmPorcentagem(primeiroCandle) < 20
				&& segundoCandle.getMinima() < primeiroCandle.getMinima();
	}

	private static Double precoMetadeCorpoCandle(InfoCandle candle) {

		if (candle == null) {
			return null;
		}

		Double dif = Math.abs((candle.getFechamento() - candle.getAbertura()) / 2);

		if (tipoCandle(candle) == TipoCandle.POSITIVO) {
			return candle.getAbertura() + dif;
		} else if (tipoCandle(candle) == TipoCandle.NEGATIVO) {
			return candle.getFechamento() + dif;
		}

		return 0.0;
	}

	private static Double setPrecoStopVenda(InfoCandle infoCandle) {
		return (infoCandle.getMaxima() + 0.01);
	}

	private static Double setPrecoEntradaVenda(InfoCandle infoCandle) {
		return (infoCandle.getMinima() - 0.01);
	}

	private static double setPrecoStopCompra(InfoCandle infoCandle) {
		return (infoCandle.getMinima() - 0.01);
	}

	private static double setPrecoEntradaCompra(InfoCandle infoCandle) {
		return (infoCandle.getMaxima() + 0.01);
	}

	private static Double calculaPrecoTerceiroAlvoFibonacci(InfoCandle infoCandle, Entrada entrada) {

		if (infoCandle != null && entrada != null) {
			if (entrada == Entrada.COMPRA) {

				return Math.abs(((infoCandle.getMaxima() - infoCandle.getMinima()) * 1.618) + infoCandle.getMaxima());

			} else if (entrada == Entrada.VENDA) {

				return Math.abs(((infoCandle.getMaxima() - infoCandle.getMinima()) * 1.618) - infoCandle.getMinima());

			}
		}

		return null;
	}

	private static Double calculaPrecoSegundoAlvoFibonacci(InfoCandle infoCandle, Entrada entrada) {

		if (infoCandle != null && entrada != null) {
			if (entrada == Entrada.COMPRA) {

				return Math.abs(((infoCandle.getMaxima() - infoCandle.getMinima())) + infoCandle.getMaxima());

			} else if (entrada == Entrada.VENDA) {

				return Math.abs(((infoCandle.getMaxima() - infoCandle.getMinima())) - infoCandle.getMinima());

			}
		}

		return null;
	}

	private static Double calculaPrecoPrimeiroAlvoFibonacci(InfoCandle infoCandle, Entrada entrada) {

		if (infoCandle != null && entrada != null) {
			if (entrada == Entrada.COMPRA) {

				return Math.abs((((infoCandle.getMaxima() - infoCandle.getMinima()) * 0.618) + infoCandle.getMaxima()));

			} else if (entrada == Entrada.VENDA) {

				return Math.abs(((infoCandle.getMaxima() - infoCandle.getMinima()) * 0.618) - infoCandle.getMinima());

			}
		}

		return null;
	}

	public static Boolean procuraPadraoEngolfo(ArrayList<InfoCandle> listInfoCandle) {

		if (listInfoCandle != null && listInfoCandle.size() >= TreinamentoRedeNeural.LIMITDECANDLEENGOLFO) {

			if (condicaoParaEngolfoDeAlta(listInfoCandle)) {

				Double calculaVariacaoUltimoCandle = calculaVariacaoCandle(listInfoCandle.get(0));
				Double calculaVariacaoPrimeiroCandle = calculaVariacaoCandle(listInfoCandle.get(1));
				Double variacaoEngolfo = calculaVariacaoUltimoCandle / calculaVariacaoPrimeiroCandle;

				Operacao operacao = new Operacao(listInfoCandle.get(0).getData(),
						listInfoCandle.get(0).getNomeDoPapel(), Padroes.ENGOLFO.getDescricao());
				operacao.setEntrada(Entrada.COMPRA.getDescricao());
				operacao.setPrecoEntrada(setPrecoEntradaCompra(listInfoCandle.get(0)));
				operacao.setPrecoStop(setPrecoStopCompra(listInfoCandle.get(0)));
				operacao.setPrecoPrimeiroAlvoFibonacci(
						calculaPrecoPrimeiroAlvoFibonacci(listInfoCandle.get(0), Entrada.COMPRA));
				operacao.setPrecoSegundoAlvoFibonacci(
						calculaPrecoSegundoAlvoFibonacci(listInfoCandle.get(0), Entrada.COMPRA));
				operacao.setPrecoTerceiroAlvoFibonacci(
						calculaPrecoTerceiroAlvoFibonacci(listInfoCandle.get(0), Entrada.COMPRA));

				Engolfo engolfo = new Engolfo();
				engolfo.setTipo(tipoCandle(listInfoCandle.get(0)).getTipo());
				engolfo.setPavioInferior(
						classificaPavioInferior(pavioInferiorEmPorcentagem(listInfoCandle.get(0))).getDescricao());
				engolfo.setPavioSuperior(
						classificaPavioSuperior(pavioSuperiorEmPorcentagem(listInfoCandle.get(0))).getDescricao());
				engolfo.setVolumeAcimaMedia20(volumeAcimaMedia20(listInfoCandle.get(0)));
				engolfo.setAcimaMedia8(verificaSePrecoFechamentoAcimaMedia(listInfoCandle.get(0), MEDIACURTA));
				engolfo.setAcimaMedia20(verificaSePrecoFechamentoAcimaMedia(listInfoCandle.get(0), MEDIA));
				engolfo.setAcimaMedia200(verificaSePrecoFechamentoAcimaMedia(listInfoCandle.get(0), MEDIALONGA));
				engolfo.setOperacao(operacao);

				operacao.setEngolfo(engolfo);

				OperacaoService.adicionaOperacao(operacao);
				EngolfoService.adicionaEngolfo(engolfo);

			} else if (condicaoParaEngolfoDeBaixa(listInfoCandle)) {

				Double calculaVariacaoUltimoCandle = calculaVariacaoCandle(listInfoCandle.get(0));
				Double calculaVariacaoPrimeiroCandle = calculaVariacaoCandle(listInfoCandle.get(1));
				Double variacaoEngolfo = calculaVariacaoUltimoCandle / calculaVariacaoPrimeiroCandle;

				Operacao operacao = new Operacao(listInfoCandle.get(0).getData(),
						listInfoCandle.get(0).getNomeDoPapel(), Padroes.ENGOLFO.getDescricao());
				operacao.setEntrada(Entrada.VENDA.getDescricao());
				operacao.setPrecoEntrada(setPrecoEntradaVenda(listInfoCandle.get(0)));
				operacao.setPrecoStop(setPrecoStopVenda(listInfoCandle.get(0)));
				operacao.setPrecoPrimeiroAlvoFibonacci(
						calculaPrecoPrimeiroAlvoFibonacci(listInfoCandle.get(0), Entrada.VENDA));
				operacao.setPrecoSegundoAlvoFibonacci(
						calculaPrecoSegundoAlvoFibonacci(listInfoCandle.get(0), Entrada.VENDA));
				operacao.setPrecoTerceiroAlvoFibonacci(
						calculaPrecoTerceiroAlvoFibonacci(listInfoCandle.get(0), Entrada.VENDA));

				Engolfo engolfo = new Engolfo();
				engolfo.setTipo(tipoCandle(listInfoCandle.get(0)).getTipo());
				engolfo.setPavioInferior(
						classificaPavioInferior(pavioInferiorEmPorcentagem(listInfoCandle.get(0))).getDescricao());
				engolfo.setPavioSuperior(
						classificaPavioSuperior(pavioSuperiorEmPorcentagem(listInfoCandle.get(0))).getDescricao());
				engolfo.setVolumeAcimaMedia20(volumeAcimaMedia20(listInfoCandle.get(0)));
				engolfo.setAcimaMedia8(verificaSePrecoFechamentoAcimaMedia(listInfoCandle.get(0), MEDIACURTA));
				engolfo.setAcimaMedia20(verificaSePrecoFechamentoAcimaMedia(listInfoCandle.get(0), MEDIA));
				engolfo.setAcimaMedia200(verificaSePrecoFechamentoAcimaMedia(listInfoCandle.get(0), MEDIALONGA));

				engolfo.setOperacao(operacao);

				operacao.setEngolfo(engolfo);

				OperacaoService.adicionaOperacao(operacao);
				EngolfoService.adicionaEngolfo(engolfo);

			}

		}

		return false;

	}

	public static Boolean procuraPadraoDoji(InfoCandle infoCandle) {

		if (infoCandle != null) {

			Double pavioSuperior = pavioSuperiorEmPorcentagem(infoCandle);
			Double pavioInferior = pavioInferiorEmPorcentagem(infoCandle);

			if (condicaoParaDoji(pavioSuperior, pavioInferior)) {
				Operacao operacaoCompra = new Operacao();
				operacaoCompra.setPadrao(Padroes.DOJICOMPRA.getDescricao());
				operacaoCompra.setDat(infoCandle.getData());
				operacaoCompra.setEntrada(Entrada.COMPRA.getDescricao());
				operacaoCompra.setNomeDoPapel(infoCandle.getNomeDoPapel());
				operacaoCompra.setPrecoEntrada(setPrecoEntradaCompra(infoCandle));
				operacaoCompra.setPrecoStop(setPrecoStopCompra(infoCandle));
				operacaoCompra
						.setPrecoPrimeiroAlvoFibonacci(calculaPrecoPrimeiroAlvoFibonacci(infoCandle, Entrada.COMPRA));
				operacaoCompra
						.setPrecoSegundoAlvoFibonacci(calculaPrecoSegundoAlvoFibonacci(infoCandle, Entrada.COMPRA));
				operacaoCompra
						.setPrecoTerceiroAlvoFibonacci(calculaPrecoTerceiroAlvoFibonacci(infoCandle, Entrada.COMPRA));

				Doji dojiCompra = new Doji();

				dojiCompra.setTipo(tipoCandle(infoCandle).getTipo());
				dojiCompra.setPavioInferior(pavioInferior.intValue());
				dojiCompra.setPavioSuperior(pavioSuperior.intValue());
				dojiCompra.setVolumeAcimaMedia20(volumeAcimaMedia20(infoCandle));
				dojiCompra.setTamanhoPavioCorpo(tamanhoPavioCorpo(infoCandle));
				dojiCompra.setPrecoAcimaMedia8(verificaSePrecoFechamentoAcimaMedia(infoCandle, MEDIACURTA));
				dojiCompra.setPrecoAcimaMedia20(verificaSePrecoFechamentoAcimaMedia(infoCandle, MEDIA));
				dojiCompra.setPrecoAcimaMedia200(verificaSePrecoFechamentoAcimaMedia(infoCandle, MEDIALONGA));
				dojiCompra.setOperacao(operacaoCompra);
				operacaoCompra.setDoji(dojiCompra);

				OperacaoService.adicionaOperacao(operacaoCompra);
				DojiService.adicionaDoji(dojiCompra);

				Operacao operacaoVenda = new Operacao();
				operacaoVenda.setPadrao(Padroes.DOJIVENDA.getDescricao());
				operacaoVenda.setDat(infoCandle.getData());
				operacaoVenda.setNomeDoPapel(infoCandle.getNomeDoPapel());
				operacaoVenda.setEntrada(Entrada.VENDA.getDescricao());
				operacaoVenda.setPrecoEntrada(setPrecoEntradaVenda(infoCandle));
				operacaoVenda.setPrecoStop(setPrecoStopVenda(infoCandle));
				operacaoVenda
						.setPrecoPrimeiroAlvoFibonacci(calculaPrecoPrimeiroAlvoFibonacci(infoCandle, Entrada.VENDA));
				operacaoVenda.setPrecoSegundoAlvoFibonacci(calculaPrecoSegundoAlvoFibonacci(infoCandle, Entrada.VENDA));
				operacaoVenda
						.setPrecoTerceiroAlvoFibonacci(calculaPrecoTerceiroAlvoFibonacci(infoCandle, Entrada.VENDA));

				Doji dojiVenda = new Doji();

				dojiVenda.setTipo(tipoCandle(infoCandle).getTipo());
				dojiVenda.setPavioInferior(pavioInferior.intValue());
				dojiVenda.setPavioSuperior(pavioSuperior.intValue());
				dojiVenda.setVolumeAcimaMedia20(volumeAcimaMedia20(infoCandle));
				dojiVenda.setTamanhoPavioCorpo(tamanhoPavioCorpo(infoCandle));
				dojiVenda.setPrecoAcimaMedia8(verificaSePrecoFechamentoAcimaMedia(infoCandle, MEDIACURTA));
				dojiVenda.setPrecoAcimaMedia20(verificaSePrecoFechamentoAcimaMedia(infoCandle, MEDIA));
				dojiVenda.setPrecoAcimaMedia200(verificaSePrecoFechamentoAcimaMedia(infoCandle, MEDIALONGA));
				dojiVenda.setOperacao(operacaoVenda);
				operacaoVenda.setDoji(dojiVenda);

				OperacaoService.adicionaOperacao(operacaoVenda);
				DojiService.adicionaDoji(dojiVenda);

			}

		}

		return null;
	}

	private static boolean condicaoParaDoji(Double pavioSuperior, Double pavioInferior) {
		return pavioSuperior >= PORCENTAGEMMINIMAPAVIODOJI && pavioSuperior <= PORCENTAGEMMAXIMAPAVIODOJI
				&& pavioInferior >= PORCENTAGEMMINIMAPAVIODOJI && pavioInferior <= PORCENTAGEMMAXIMAPAVIODOJI;
	}

	public static Integer tamanhoPavioCorpo(InfoCandle infoCandle) {

		if (infoCandle != null) {

			Double variacaoPavio = 0.0;
			Double variacaoCorpo = 0.0;
			Double variacao = 1000.0;

			if (infoCandle.getAbertura().compareTo(infoCandle.getFechamento()) == 0) {
				return variacao.intValue();
			}

			variacaoPavio = Math.abs(infoCandle.getMaxima() - infoCandle.getMinima());
			variacaoCorpo = Math.abs(infoCandle.getAbertura() - infoCandle.getFechamento());
			variacao = variacaoPavio / variacaoCorpo;

			return variacao.intValue();

		}

		return null;

	}

	private static Boolean verificaSePrecoFechamentoAcimaMedia(InfoCandle infoCandle, int media) {
		if (media == MEDIACURTA) {
			if (infoCandle.getPrecoMedia8() != null) {
				return infoCandle.getFechamento() > infoCandle.getPrecoMedia8();
			}
		}
		if (media == MEDIA) {
			if (infoCandle.getPrecoMedia20() != null) {
				return infoCandle.getFechamento() > infoCandle.getPrecoMedia20();
			}
		}
		if (media == MEDIALONGA) {
			if (infoCandle.getPrecoMedia200() != null) {
				return infoCandle.getFechamento() > infoCandle.getPrecoMedia200();
			}
		}
		return null;
	}

	private static boolean condicaoParaEngolfoDeBaixa(ArrayList<InfoCandle> listInfoCandle) {
		if (listInfoCandle.size() >= 2) {
			return listInfoCandle.get(0).getFechamento() < listInfoCandle.get(1).getMinima()
					&& listInfoCandle.get(0).getAbertura() > listInfoCandle.get(1).getMaxima();
		}
		return false;
	}

	private static boolean condicaoParaEngolfoDeAlta(ArrayList<InfoCandle> listInfoCandle) {
		if (listInfoCandle.size() >= 2) {
			return listInfoCandle.get(0).getFechamento() > listInfoCandle.get(1).getMaxima()
					&& listInfoCandle.get(0).getAbertura() < listInfoCandle.get(1).getMinima();
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
		} else if (pavioSuperior > 95 && pavioSuperior.compareTo(100.0) == 0 || pavioSuperior.compareTo(100.0) == -1) {
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

	@Override
	public void netStarted(NeuralNetEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cicleTerminated(NeuralNetEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void netStopped(NeuralNetEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void errorChanged(NeuralNetEvent e) {

		Monitor mon = (Monitor)e.getSource();
	    
	    // Imprimir o erro a cada 200 ciclos de treinamento
	    if (mon.getCurrentCicle() % 200 == 0)
	    {
	      // Imprimir o ciclo de treinamento atual, bem como o erro global da rede neural
	      System.out.println(mon.getCurrentCicle() + " epochs remaining - RMSE = " + mon.getGlobalError());
	    }

		
	}

	@Override
	public void netStoppedError(NeuralNetEvent e, String error) {
		// TODO Auto-generated method stub

	}

}
