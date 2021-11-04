package br.iesb.indicador_analise_grafica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
import org.joone.io.StreamInputSynapse;
import org.joone.net.NeuralNet;
import org.joone.net.NeuralNetLoader;

import br.iesb.indicador_analise_grafica.estatistica.EstatisticaEngolfo;
import br.iesb.indicador_analise_grafica.estatistica.EstatisticaMartelo;
import br.iesb.indicador_analise_grafica.estatistica.EstatisticaPiercingLine;
import br.iesb.indicador_analise_grafica.estatistica.EstatisticaTresSoldados;
import br.iesb.indicador_analise_grafica.padroes.BebeAbandonado;
import br.iesb.indicador_analise_grafica.padroes.Doji;
import br.iesb.indicador_analise_grafica.padroes.Engolfo;
import br.iesb.indicador_analise_grafica.padroes.Martelo;
import br.iesb.indicador_analise_grafica.padroes.MarteloInvertido;
import br.iesb.indicador_analise_grafica.padroes.Marubozu;
import br.iesb.indicador_analise_grafica.padroes.PiercingLine;
import br.iesb.indicador_analise_grafica.padroes.TresSoldados;
import br.iesb.indicador_analise_grafica.possibilidades.PossibilidadeEngolfo;
import br.iesb.indicador_analise_grafica.possibilidades.PossibilidadeMartelo;
import br.iesb.indicador_analise_grafica.possibilidades.PossibilidadePiercingLine;
import br.iesb.indicador_analise_grafica.possibilidades.PossibilidadeTresSoldados;
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
import br.iesb.indicador_analise_grafica_enum.Perfil;
import br.iesb.indicador_analise_grafica_enum.Perfuracao;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia20;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia200;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia8;
import br.iesb.indicador_analise_grafica_enum.TendenciaMediaCurta;
import br.iesb.indicador_analise_grafica_enum.TipoCandle;
import br.iesb.indicador_analise_grafica_enum.VariacaoPreco;
import br.iesb.indicador_analise_grafica_enum.VolumeAcimaMedia20;

public class RedeNeural implements NeuralNetListener {

	ArrayList<Candle> ultimosCandles = new ArrayList<Candle>();
	static ArrayList<Martelo> listaMartelo = new ArrayList<Martelo>();
	private static ArrayList<EstatisticaMartelo> estatisticasMartelo = new ArrayList<EstatisticaMartelo>();
	private static ArrayList<EstatisticaPiercingLine> estatisticasPiercingLine = new ArrayList<EstatisticaPiercingLine>();
	private static ArrayList<EstatisticaEngolfo> estatisticasEngolfo = new ArrayList<EstatisticaEngolfo>();
	private static ArrayList<EstatisticaTresSoldados> estatisticasTresSoldados = new ArrayList<EstatisticaTresSoldados>();

	static int cont1 = 0;
	static int cont2 = 0;
	private final static Double MIN = 10.0;
	private final static Double MAX = 100.0;
	private final static int MEDIACURTA = 8;
	private final static int MEDIA = 20;
	private final static int MEDIALONGA = 200;
	private final static int PORCENTAGEMMAXIMAENGOLFO = 10;
	private static final Double PORCENTAGEMMAXIMAPAVIODOJI = 60.0;
	private static final Double PORCENTAGEMMINIMAPAVIODOJI = 40.0;

	public static Boolean procuraPadraoMartelo(ArrayList<InfoCandle> grafico) {

		if (grafico == null) {
			return false;
		}

		for (int i = 0; i < grafico.size(); i++) {

			InfoCandle infoCandle = grafico.get(i);

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

	public static Boolean procuraPadraoMarteloInvertido(ArrayList<InfoCandle> grafico) {

		if (grafico == null) {
			return false;
		}

		for (int i = 0; i < grafico.size(); i++) {

			InfoCandle infoCandle = grafico.get(i);

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
					operacao.setPrecoPrimeiroAlvoFibonacci(
							calculaPrecoPrimeiroAlvoFibonacci(infoCandle, Entrada.VENDA));
					operacao.setPrecoSegundoAlvoFibonacci(calculaPrecoSegundoAlvoFibonacci(infoCandle, Entrada.VENDA));
					operacao.setPrecoTerceiroAlvoFibonacci(
							calculaPrecoTerceiroAlvoFibonacci(infoCandle, Entrada.VENDA));

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
		}
		return null;

	}

	public static Boolean procuraPadraoMarubozu(ArrayList<InfoCandle> grafico, int limitMarubozu) {

		if (grafico != null && grafico.size() >= limitMarubozu) {

			for (int i = 0; i < grafico.size() - limitMarubozu; i++) {

				InfoCandle infoCandle = grafico.get(i + limitMarubozu);

				Double pavioSuperior = pavioSuperiorEmPorcentagem(infoCandle);
				Double pavioInferior = pavioInferiorEmPorcentagem(infoCandle);
				Boolean validaMarubozu = false;
				Double variacaoInfoCandle = 0.0;
				Double variacaoMedia = 0.0;
				Double variacaoSoma = 0.0;

				for (int j = i; j < i + limitMarubozu; j++) {
					if (verificaCandleParado(grafico.get(j))) {
						return false;
					}
					variacaoSoma += calculaVariacaoCandle(grafico.get(j));
				}

				variacaoMedia = variacaoSoma / (limitMarubozu - 1);
				variacaoInfoCandle = calculaVariacaoCandle(infoCandle);
				Double calculaVariacao = variacaoInfoCandle / variacaoMedia;

				validaMarubozu = validaMarubozuPelaVariacao(calculaVariacao);

				// Condicoes para Marubozu
				if (condicaoParaMarubozu(pavioSuperior, pavioInferior, validaMarubozu)) {

					Operacao operacao = new Operacao();
					operacao.setDat(infoCandle.getData());
					operacao.setNomeDoPapel(infoCandle.getNomeDoPapel());
					operacao.setPadrao(Padroes.MARUBOZU.getDescricao());

					if (tipoCandle(infoCandle) == TipoCandle.POSITIVO) {

						operacao.setEntrada(Entrada.COMPRA.getDescricao());
						operacao.setPrecoEntrada(setPrecoEntradaCompra(infoCandle));
						operacao.setPrecoStop(setPrecoStopCompra(infoCandle));
						operacao.setPrecoPrimeiroAlvoFibonacci(
								calculaPrecoPrimeiroAlvoFibonacci(infoCandle, Entrada.COMPRA));
						operacao.setPrecoSegundoAlvoFibonacci(
								calculaPrecoSegundoAlvoFibonacci(infoCandle, Entrada.COMPRA));
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
						operacao.setPrecoSegundoAlvoFibonacci(
								calculaPrecoSegundoAlvoFibonacci(infoCandle, Entrada.VENDA));
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

				tresSoldados.setPavioInferiorPrimeiroCandle(
						classificaPavioInferior(pavioInferiorEmPorcentagem(primeiroCandle)).getDescricao());
				tresSoldados.setPavioSuperiorPrimeiroCandle(
						classificaPavioSuperior(pavioSuperiorEmPorcentagem(primeiroCandle)).getDescricao());
				tresSoldados.setPavioInferiorSegundoCandle(
						classificaPavioInferior(pavioInferiorEmPorcentagem(segundoCandle)).getDescricao());
				tresSoldados.setPavioSuperiorSegundoCandle(
						classificaPavioSuperior(pavioSuperiorEmPorcentagem(segundoCandle)).getDescricao());
				tresSoldados.setPavioInferiorTerceiroCandle(
						classificaPavioInferior(pavioInferiorEmPorcentagem(terceiroCandle)).getDescricao());
				tresSoldados.setPavioSuperiorTerceiroCandle(
						classificaPavioSuperior(pavioSuperiorEmPorcentagem(terceiroCandle)).getDescricao());
				tresSoldados.setPrecoAcimaMedia8(verificaSePrecoFechamentoAcimaMedia(terceiroCandle, MEDIACURTA));
				tresSoldados.setPrecoAcimaMedia20(verificaSePrecoFechamentoAcimaMedia(terceiroCandle, MEDIA));
				tresSoldados.setPrecoAcimaMedia200(verificaSePrecoFechamentoAcimaMedia(terceiroCandle, MEDIALONGA));
				tresSoldados.setVolumeAcimaMedia20(volumeAcimaMedia20(terceiroCandle));

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

				tresSoldados.setPavioInferiorPrimeiroCandle(
						classificaPavioInferior(pavioInferiorEmPorcentagem(primeiroCandle)).getDescricao());
				tresSoldados.setPavioSuperiorPrimeiroCandle(
						classificaPavioSuperior(pavioSuperiorEmPorcentagem(primeiroCandle)).getDescricao());
				tresSoldados.setPavioInferiorSegundoCandle(
						classificaPavioInferior(pavioInferiorEmPorcentagem(segundoCandle)).getDescricao());
				tresSoldados.setPavioSuperiorSegundoCandle(
						classificaPavioSuperior(pavioSuperiorEmPorcentagem(segundoCandle)).getDescricao());
				tresSoldados.setPavioInferiorTerceiroCandle(
						classificaPavioInferior(pavioInferiorEmPorcentagem(terceiroCandle)).getDescricao());
				tresSoldados.setPavioSuperiorTerceiroCandle(
						classificaPavioSuperior(pavioSuperiorEmPorcentagem(terceiroCandle)).getDescricao());
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
				bebeAbandonado.setVolumeAcimaMedia20(volumeAcimaMedia20(terceiroCandle));

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
				bebeAbandonado.setVolumeAcimaMedia20(volumeAcimaMedia20(terceiroCandle));

				bebeAbandonado.setOperacao(operacao);
				operacao.setBebeAbandonado(bebeAbandonado);

				OperacaoService.adicionaOperacao(operacao);
				BebeAbandonadoService.adicionaBebeAbandonado(bebeAbandonado);

			}

		}
		return null;
	}

	public static Boolean procuraPadraoEngolfo(ArrayList<InfoCandle> grafico, int limitEngolfo) {

		if (grafico != null && grafico.size() >= limitEngolfo) {

			for (int i = 0; i < grafico.size() - 1; i++) {

				InfoCandle primeiroCandle = grafico.get(i);
				InfoCandle segundoCandle = grafico.get(i + 1);

				if (condicaoParaEngolfoDeAlta(primeiroCandle, segundoCandle)) {

					Double variacaoUltimoCandle = calculaVariacaoCandle(segundoCandle);
					Double variacaoPrimeiroCandle = calculaVariacaoCandle(primeiroCandle);
					Double variacaoEngolfo = variacaoUltimoCandle / variacaoPrimeiroCandle;

					Operacao operacao = new Operacao();
					operacao.setDat(segundoCandle.getData());
					operacao.setNomeDoPapel(segundoCandle.getNomeDoPapel());
					operacao.setPadrao(Padroes.ENGOLFO.getDescricao());
					operacao.setEntrada(Entrada.COMPRA.getDescricao());
					operacao.setPrecoEntrada(setPrecoEntradaCompra(segundoCandle));
					operacao.setPrecoStop(setPrecoStopCompra(segundoCandle));
					operacao.setPrecoPrimeiroAlvoFibonacci(
							calculaPrecoPrimeiroAlvoFibonacci(segundoCandle, Entrada.COMPRA));
					operacao.setPrecoSegundoAlvoFibonacci(
							calculaPrecoSegundoAlvoFibonacci(segundoCandle, Entrada.COMPRA));
					operacao.setPrecoTerceiroAlvoFibonacci(
							calculaPrecoTerceiroAlvoFibonacci(segundoCandle, Entrada.COMPRA));

					Engolfo engolfo = new Engolfo();
					engolfo.setTipo(tipoCandle(segundoCandle).getTipo());
					engolfo.setPavioInferior(
							classificaPavioInferior(pavioInferiorEmPorcentagem(segundoCandle)).getDescricao());
					engolfo.setPavioSuperior(
							classificaPavioSuperior(pavioSuperiorEmPorcentagem(segundoCandle)).getDescricao());
					engolfo.setVolumeAcimaMedia20(volumeAcimaMedia20(segundoCandle));
					engolfo.setAcimaMedia8(verificaSePrecoFechamentoAcimaMedia(segundoCandle, MEDIACURTA));
					engolfo.setAcimaMedia20(verificaSePrecoFechamentoAcimaMedia(segundoCandle, MEDIA));
					engolfo.setAcimaMedia200(verificaSePrecoFechamentoAcimaMedia(segundoCandle, MEDIALONGA));
					engolfo.setVariacao(classificaVariacaoPreco(variacaoEngolfo).getDescricao());
					engolfo.setOperacao(operacao);

					operacao.setEngolfo(engolfo);

					OperacaoService.adicionaOperacao(operacao);
					EngolfoService.adicionaEngolfo(engolfo);

				} else if (condicaoParaEngolfoDeBaixa(primeiroCandle, segundoCandle)) {

					Double variacaoUltimoCandle = calculaVariacaoCandle(segundoCandle);
					Double variacaoPrimeiroCandle = calculaVariacaoCandle(primeiroCandle);
					Double variacaoEngolfo = variacaoUltimoCandle / variacaoPrimeiroCandle;

					Operacao operacao = new Operacao();
					operacao.setDat(segundoCandle.getData());
					operacao.setNomeDoPapel(segundoCandle.getNomeDoPapel());
					operacao.setPadrao(Padroes.ENGOLFO.getDescricao());
					operacao.setEntrada(Entrada.VENDA.getDescricao());
					operacao.setPrecoEntrada(setPrecoEntradaVenda(segundoCandle));
					operacao.setPrecoStop(setPrecoStopVenda(segundoCandle));
					operacao.setPrecoPrimeiroAlvoFibonacci(
							calculaPrecoPrimeiroAlvoFibonacci(segundoCandle, Entrada.VENDA));
					operacao.setPrecoSegundoAlvoFibonacci(
							calculaPrecoSegundoAlvoFibonacci(segundoCandle, Entrada.VENDA));
					operacao.setPrecoTerceiroAlvoFibonacci(
							calculaPrecoTerceiroAlvoFibonacci(segundoCandle, Entrada.VENDA));

					Engolfo engolfo = new Engolfo();
					engolfo.setTipo(tipoCandle(segundoCandle).getTipo());
					engolfo.setPavioInferior(
							classificaPavioInferior(pavioInferiorEmPorcentagem(segundoCandle)).getDescricao());
					engolfo.setPavioSuperior(
							classificaPavioSuperior(pavioSuperiorEmPorcentagem(segundoCandle)).getDescricao());
					engolfo.setVolumeAcimaMedia20(volumeAcimaMedia20(segundoCandle));
					engolfo.setAcimaMedia8(verificaSePrecoFechamentoAcimaMedia(segundoCandle, MEDIACURTA));
					engolfo.setAcimaMedia20(verificaSePrecoFechamentoAcimaMedia(segundoCandle, MEDIA));
					engolfo.setAcimaMedia200(verificaSePrecoFechamentoAcimaMedia(segundoCandle, MEDIALONGA));
					engolfo.setVariacao(classificaVariacaoPreco(variacaoEngolfo).getDescricao());

					engolfo.setOperacao(operacao);

					operacao.setEngolfo(engolfo);

					OperacaoService.adicionaOperacao(operacao);
					EngolfoService.adicionaEngolfo(engolfo);

				}

			}

		}

		return false;

	}

	public static Boolean procuraPadraoDoji(ArrayList<InfoCandle> grafico) {

		if (grafico == null) {
			return false;
		}

		for (int i = 0; i < grafico.size(); i++) {

			InfoCandle infoCandle = grafico.get(i);

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
					operacaoCompra.setPrecoPrimeiroAlvoFibonacci(
							calculaPrecoPrimeiroAlvoFibonacci(infoCandle, Entrada.COMPRA));
					operacaoCompra
							.setPrecoSegundoAlvoFibonacci(calculaPrecoSegundoAlvoFibonacci(infoCandle, Entrada.COMPRA));
					operacaoCompra.setPrecoTerceiroAlvoFibonacci(
							calculaPrecoTerceiroAlvoFibonacci(infoCandle, Entrada.COMPRA));

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
					operacaoVenda.setPrecoPrimeiroAlvoFibonacci(
							calculaPrecoPrimeiroAlvoFibonacci(infoCandle, Entrada.VENDA));
					operacaoVenda
							.setPrecoSegundoAlvoFibonacci(calculaPrecoSegundoAlvoFibonacci(infoCandle, Entrada.VENDA));
					operacaoVenda.setPrecoTerceiroAlvoFibonacci(
							calculaPrecoTerceiroAlvoFibonacci(infoCandle, Entrada.VENDA));

					Doji dojiVenda = new Doji();

					dojiVenda.setTipo(tipoCandle(infoCandle).getTipo());
					dojiCompra.setPavioInferior(pavioInferior.intValue());
					dojiCompra.setPavioSuperior(pavioSuperior.intValue());
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

	private static ArrayList<PossibilidadeTresSoldados> todasAsPossibilidadesTresSoldados() {

		List<Padroes> padroes = new ArrayList<>();
		padroes.add(Padroes.TRESSOLDADOSDEALTA);
		padroes.add(Padroes.TRESSOLDADOSDEBAIXA);
		List<PavioSuperior> pavioSuperiorPrimeiroCandle = PavioSuperior.getPavioSuperiorTresSoldados();
		List<PavioInferior> pavioInferiorPrimeiroCandle = PavioInferior.getPavioInferiorTresSoldados();
		List<PavioSuperior> pavioSuperiorTerceiroCandle = PavioSuperior.getPavioSuperiorTresSoldados();
		List<PavioInferior> pavioInferiorTerceiroCandle = PavioInferior.getPavioInferiorTresSoldados();
		List<PrecoAcimaMedia200> precoAcimaMedia200 = PrecoAcimaMedia200.getListPrecoAcimaMedia200();

		ArrayList<PossibilidadeTresSoldados> possibilidades = new ArrayList<PossibilidadeTresSoldados>();

		padroes.stream().forEach(padrao -> {
			pavioSuperiorPrimeiroCandle.stream().forEach(pavioSupPrimeiroCandle -> {
				pavioInferiorPrimeiroCandle.stream().forEach(pavioInfPrimeiroCandle -> {
					pavioSuperiorTerceiroCandle.stream().forEach(pavioSupTerceiroCandle -> {
						pavioInferiorTerceiroCandle.stream().forEach(pavioInfTerceiroCandle -> {
							precoAcimaMedia200.stream().forEach(preco200 -> {
								PossibilidadeTresSoldados possibilidade = new PossibilidadeTresSoldados(padrao,
										pavioSupPrimeiroCandle, pavioInfPrimeiroCandle, pavioSupTerceiroCandle,
										pavioInfTerceiroCandle, preco200);

								possibilidades.add(possibilidade);

							});
						});
					});
				});
			});
		});

		return possibilidades;

	}

	private static ArrayList<PossibilidadeEngolfo> todasAsPossibilidadesEngolfo() {

		List<TipoCandle> tipoCandle = TipoCandle.retornaTiposSemNeutro();
		List<PavioSuperior> pavioSuperior = PavioSuperior.getPavioSuperiorEngolfo();
		List<PavioInferior> pavioInferior = PavioInferior.getPavioInferiorEngolfo();
		List<VolumeAcimaMedia20> volumeAcimaMedia20 = VolumeAcimaMedia20.getListVolumeAcimaMedia20();
		List<PrecoAcimaMedia8> precoAcimaMedia8 = PrecoAcimaMedia8.getListPrecoAcimaMedia8();
		List<PrecoAcimaMedia20> precoAcimaMedia20 = PrecoAcimaMedia20.getListPrecoAcimaMedia20();
		List<PrecoAcimaMedia200> precoAcimaMedia200 = PrecoAcimaMedia200.getListPrecoAcimaMedia200();
		List<VariacaoPreco> variacao = VariacaoPreco.getListVariacaoEngolfo();

		ArrayList<PossibilidadeEngolfo> possibilidades = new ArrayList<PossibilidadeEngolfo>();

		tipoCandle.stream().forEach(tipo -> {
			pavioSuperior.stream().forEach(pavioS -> {
				pavioInferior.stream().forEach(pavioI -> {
					volumeAcimaMedia20.stream().forEach(vol -> {
						precoAcimaMedia8.stream().forEach(preco8 -> {
							precoAcimaMedia20.stream().forEach(preco20 -> {
								precoAcimaMedia200.stream().forEach(preco200 -> {
									variacao.stream().forEach(var -> {
										PossibilidadeEngolfo possibilidade = new PossibilidadeEngolfo(tipo, pavioS,
												pavioI, vol, preco8, preco20, preco200, var);
										possibilidades.add(possibilidade);
									});
								});
							});
						});
					});
				});
			});
		});

		return possibilidades;

	}

	private static ArrayList<PossibilidadePiercingLine> todasAsPossibilidadesPiercingLine() {

		List<TipoCandle> tipos = TipoCandle.retornaTipos();
		List<VolumeAcimaMedia20> vol = VolumeAcimaMedia20.getListVolumeAcimaMedia20();
		List<Perfuracao> perf = Perfuracao.retornaPerfuracoes();
		List<PrecoAcimaMedia8> preco8 = PrecoAcimaMedia8.getListPrecoAcimaMedia8();
		List<PrecoAcimaMedia20> preco20 = PrecoAcimaMedia20.getListPrecoAcimaMedia20();
		List<PrecoAcimaMedia200> preco200 = PrecoAcimaMedia200.getListPrecoAcimaMedia200();

		ArrayList<PossibilidadePiercingLine> possibilidades = new ArrayList<PossibilidadePiercingLine>();

		tipos.stream().forEach(tipo -> {
			vol.stream().forEach(volume -> {
				perf.stream().forEach(perfuracao -> {
					preco8.stream().forEach(precoCurto -> {
						preco20.stream().forEach(precoMedio -> {
							preco200.stream().forEach(precoLongo -> {

								PossibilidadePiercingLine possibilidade = new PossibilidadePiercingLine(tipo, volume,
										perfuracao, precoCurto, precoMedio, precoLongo);

								if (possibilidade.getTipoCandle() != TipoCandle.NULL
										&& possibilidade.getTipoCandle() != TipoCandle.NEUTRO
										&& possibilidade.getVolumeAcimaMedia20() != VolumeAcimaMedia20.NULL
										&& possibilidade.getPerfuracao() != Perfuracao.NULL
										&& possibilidade.getPrecoAcimaMedia8() != PrecoAcimaMedia8.NULL
										&& possibilidade.getPrecoAcimaMedia20() != PrecoAcimaMedia20.NULL
										&& possibilidade.getPrecoAcimaMedia200() != PrecoAcimaMedia200.NULL) {

									possibilidades.add(possibilidade);

								}

							});
						});
					});
				});
			});
		});

		return possibilidades;

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

							if (possibilidade.getTipoCandle() != TipoCandle.NULL
									&& possibilidade.getPavioSuperior() != PavioSuperior.NULL
									&& possibilidade.getPavioInferior() != PavioInferior.NULL
									&& possibilidade.getVolumeAcimaMedia20() != VolumeAcimaMedia20.NULL
									&& possibilidade.getPrecoAcimaMedia200() != PrecoAcimaMedia200.NULL) {

								possibilidades.add(possibilidade);

							}

						});
					});
				});
			});
		});

		return possibilidades;

	}

	public static void preenchendoEstatisticaTresSoldados(Perfil perfil) {

		int FREQUENCIAMINIMA = 0;

		ArrayList<PossibilidadeTresSoldados> todasPossibilidades = todasAsPossibilidadesTresSoldados();

		todasPossibilidades.stream().forEach(p -> {

			EstatisticaTresSoldados estatisticaTresSoldados = new EstatisticaTresSoldados();

			estatisticaTresSoldados.setConfiguracaoTresSoldados(p);

			Double denominadorGeral = OperacaoService.contaTresSoldadosEspecificoIniciadoGeral(p.getPadrao(),
					p.getPavioSuperiorPrimeiroCandle(), p.getPavioInferiorPrimeiroCandle(),
					p.getPavioSuperiorTerceiroCandle(), p.getPavioInferiorTerceiroCandle(), p.getPrecoAcimaMedia200());

			if (denominadorGeral.intValue() > FREQUENCIAMINIMA) {

				estatisticaTresSoldados.setFrequenciaGeral(denominadorGeral.intValue());

				Double numeradorGeral = OperacaoService.contaTresSoldadosEspecificoIniciadoGeralNaoChegouAlvo(
						p.getPadrao(), p.getPavioSuperiorPrimeiroCandle(), p.getPavioInferiorPrimeiroCandle(),
						p.getPavioSuperiorTerceiroCandle(), p.getPavioInferiorTerceiroCandle(),
						p.getPrecoAcimaMedia200());

				Double numeradorUltimosCincoAnos = OperacaoService
						.contaTresSoldadosEspecificoIniciadoUltimosCincoAnosNaoChegouAlvo(p.getPadrao(),
								p.getPavioSuperiorPrimeiroCandle(), p.getPavioInferiorPrimeiroCandle(),
								p.getPavioSuperiorTerceiroCandle(), p.getPavioInferiorTerceiroCandle(),
								p.getPrecoAcimaMedia200());

				Double denominadorUltimosCincoAnos = OperacaoService
						.contaTresSoldadosEspecificoIniciadoUltimosCincoAnos(p.getPadrao(),
								p.getPavioSuperiorPrimeiroCandle(), p.getPavioInferiorPrimeiroCandle(),
								p.getPavioSuperiorTerceiroCandle(), p.getPavioInferiorTerceiroCandle(),
								p.getPrecoAcimaMedia200());

				estatisticaTresSoldados.setFrequenciaUltimosCincoAnos(denominadorUltimosCincoAnos.intValue());

				Double numeradorUltimoAno = OperacaoService.contaTresSoldadosEspecificoIniciadoUltimoAnoNaoChegouAlvo(
						p.getPadrao(), p.getPavioSuperiorPrimeiroCandle(), p.getPavioInferiorPrimeiroCandle(),
						p.getPavioSuperiorTerceiroCandle(), p.getPavioInferiorTerceiroCandle(),
						p.getPrecoAcimaMedia200());

				Double denominadorUltimoAno = OperacaoService.contaTresSoldadosEspecificoIniciadoUltimoAno(
						p.getPadrao(), p.getPavioSuperiorPrimeiroCandle(), p.getPavioInferiorPrimeiroCandle(),
						p.getPavioSuperiorTerceiroCandle(), p.getPavioInferiorTerceiroCandle(),
						p.getPrecoAcimaMedia200());

				estatisticaTresSoldados.setFrequenciaUltimoAno(denominadorUltimoAno.intValue());

				if (denominadorGeral != 0) {
					Double estatisticaGeral = 100.0 - ((numeradorGeral / denominadorGeral) * 100);
					estatisticaTresSoldados.setAssertividadeGeralPrimeiroAlvo(estatisticaGeral);
				}

				if (denominadorUltimosCincoAnos != 0) {
					Double estatisticaUltimosCincoAnos = 100.0
							- ((numeradorUltimosCincoAnos / denominadorUltimosCincoAnos) * 100);
					estatisticaTresSoldados.setAssertividadeUltimosCincoAnosPrimeiroAlvo(estatisticaUltimosCincoAnos);
				}

				if (denominadorUltimoAno != 0) {
					Double estatisticaUltimoAno = 100.0 - ((numeradorUltimoAno / denominadorUltimoAno) * 100);
					estatisticaTresSoldados.setAssertividadeUltimoAnoPrimeiroAlvo(estatisticaUltimoAno);
				}

				estatisticasTresSoldados.add(estatisticaTresSoldados);

			}

		});

		try {
			preencherTxtTreinamentoRedeNeuralTresSoldados(perfil);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void preenchendoEstatisticaPiercingLine(Perfil perfil) {

		ArrayList<PossibilidadePiercingLine> todasPossibilidades = todasAsPossibilidadesPiercingLine();

		todasPossibilidades.stream().forEach(p -> {

			EstatisticaPiercingLine estatisticaPiercingLine = new EstatisticaPiercingLine();

			estatisticaPiercingLine.setConfiguracaoPiercingLine(p);

			Double numeradorGeral = OperacaoService.contaPiercingLineEspecificoInciadoNaoChegouAlvo(p.getTipoCandle(),
					p.getVolumeAcimaMedia20(), p.getPerfuracao(), p.getPrecoAcimaMedia8(), p.getPrecoAcimaMedia20(),
					p.getPrecoAcimaMedia200());

			Double denominadorGeral = OperacaoService.contaPiercingLineEspecificoInciado(p.getTipoCandle(),
					p.getVolumeAcimaMedia20(), p.getPerfuracao(), p.getPrecoAcimaMedia8(), p.getPrecoAcimaMedia20(),
					p.getPrecoAcimaMedia200());

			estatisticaPiercingLine.setFrequenciaGeral(denominadorGeral.intValue());

			Double numeradorUltimosCincoAnos = OperacaoService
					.contaPiercingLineEspecificoInciadoUltimosCincoAnosNaoChegouAlvo(p.getTipoCandle(),
							p.getVolumeAcimaMedia20(), p.getPerfuracao(), p.getPrecoAcimaMedia8(),
							p.getPrecoAcimaMedia20(), p.getPrecoAcimaMedia200());

			Double denominadorUltimosCincoAnos = OperacaoService.contaPiercingLineEspecificoInciadoUltimosCincoAnos(
					p.getTipoCandle(), p.getVolumeAcimaMedia20(), p.getPerfuracao(), p.getPrecoAcimaMedia8(),
					p.getPrecoAcimaMedia20(), p.getPrecoAcimaMedia200());

			estatisticaPiercingLine.setFrequenciaUltimosCincoAnos(denominadorUltimosCincoAnos.intValue());

			Double numeradorUltimoAno = OperacaoService.contaPiercingLineEspecificoInciadoUltimoAnoNaoChegouAlvo(
					p.getTipoCandle(), p.getVolumeAcimaMedia20(), p.getPerfuracao(), p.getPrecoAcimaMedia8(),
					p.getPrecoAcimaMedia20(), p.getPrecoAcimaMedia200());

			Double denominadorUltimoAno = OperacaoService.contaPiercingLineEspecificoInciadoUltimoAno(p.getTipoCandle(),
					p.getVolumeAcimaMedia20(), p.getPerfuracao(), p.getPrecoAcimaMedia8(), p.getPrecoAcimaMedia20(),
					p.getPrecoAcimaMedia200());

			estatisticaPiercingLine.setFrequenciaUltimoAno(denominadorUltimoAno.intValue());

			if (denominadorGeral != 0) {
				Double estatisticaGeral = 100.0 - ((numeradorGeral / denominadorGeral) * 100);
				estatisticaPiercingLine.setAssertividadeGeralPrimeiroAlvo(estatisticaGeral);
			}

			if (denominadorUltimosCincoAnos != 0) {
				Double estatisticaUltimosCincoAnos = 100.0
						- ((numeradorUltimosCincoAnos / denominadorUltimosCincoAnos) * 100);
				estatisticaPiercingLine.setAssertividadeUltimosCincoAnosPrimeiroAlvo(estatisticaUltimosCincoAnos);
			}

			if (denominadorUltimoAno != 0) {
				Double estatisticaUltimoAno = 100.0 - ((numeradorUltimoAno / denominadorUltimoAno) * 100);
				estatisticaPiercingLine.setAssertividadeUltimoAnoPrimeiroAlvo(estatisticaUltimoAno);
			}

			estatisticasPiercingLine.add(estatisticaPiercingLine);

		});

		try {
			preencherTxtTreinamentoRedeNeuralPiercingLine(perfil);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void preenchendoEstatisticaMartelo(Perfil perfil) {

		ArrayList<PossibilidadeMartelo> todasPossibilidades = todasAsPossibilidadesMartelo();

		todasPossibilidades.stream().forEach(p -> {

			EstatisticaMartelo estatisticaMartelo = new EstatisticaMartelo();

			estatisticaMartelo.setConfiguracaoMartelo(p);

			Double numeradorGeral = OperacaoService.contaMarteloEspecificoInciadoNaoChegouAlvo(
					p.getTipoCandle().getTipo(), p.getPavioSuperior().getDescricao(),
					p.getPavioInferior().getDescricao(), p.getVolumeAcimaMedia20().getValor(),
					p.getPrecoAcimaMedia200().getValor());

			Double denominadorGeral = OperacaoService.contaMarteloEspecificoInciado(p.getTipoCandle().getTipo(),
					p.getPavioSuperior().getDescricao(), p.getPavioInferior().getDescricao(),
					p.getVolumeAcimaMedia20().getValor(), p.getPrecoAcimaMedia200().getValor());

			estatisticaMartelo.setFrequenciaGeral(denominadorGeral.intValue());

			Double numeradorUltimosCincoAnos = OperacaoService
					.contaMarteloEspecificoInciadoUltimosCincoAnosNaoChegouAlvo(p.getTipoCandle().getTipo(),
							p.getPavioSuperior().getDescricao(), p.getPavioInferior().getDescricao(),
							p.getVolumeAcimaMedia20().getValor(), p.getPrecoAcimaMedia200().getValor());

			Double denominadorUltimosCincoAnos = OperacaoService.contaMarteloEspecificoInciadoUltimosCincoAnos(
					p.getTipoCandle().getTipo(), p.getPavioSuperior().getDescricao(),
					p.getPavioInferior().getDescricao(), p.getVolumeAcimaMedia20().getValor(),
					p.getPrecoAcimaMedia200().getValor());

			estatisticaMartelo.setFrequenciaUltimosCincoAnos(denominadorUltimosCincoAnos.intValue());

			Double numeradorUltimoAno = OperacaoService.contaMarteloEspecificoInciadoUltimoAnoNaoChegouAlvo(
					p.getTipoCandle().getTipo(), p.getPavioSuperior().getDescricao(),
					p.getPavioInferior().getDescricao(), p.getVolumeAcimaMedia20().getValor(),
					p.getPrecoAcimaMedia200().getValor());

			Double denominadorUltimoAno = OperacaoService.contaMarteloEspecificoInciadoUltimoAno(
					p.getTipoCandle().getTipo(), p.getPavioSuperior().getDescricao(),
					p.getPavioInferior().getDescricao(), p.getVolumeAcimaMedia20().getValor(),
					p.getPrecoAcimaMedia200().getValor());

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
				preencherTxtTreinamentoRedeNeuralMartelo(perfil);
			} catch (IOException e) {
				e.printStackTrace();
			}

		});

	}

	public static void preenchendoEstatisticaEngolfo(Perfil perfil) {

		ArrayList<PossibilidadeEngolfo> todasPossibilidades = todasAsPossibilidadesEngolfo();

		todasPossibilidades.stream().forEach(p -> {

			EstatisticaEngolfo estatisticaEngolfo = new EstatisticaEngolfo();

			estatisticaEngolfo.setConfiguracaoEngolfo(p);

			Double numeradorGeral = OperacaoService.contaEngolfoEspecificoInciadoNaoChegouAlvo(p.getTipoCandle(),
					p.getPavioSuperior(), p.getPavioInferior(), p.getVolumeAcimaMedia20(), p.getPrecoAcimaMedia8(),
					p.getPrecoAcimaMedia20(), p.getPrecoAcimaMedia200(), p.getVariacao());

			Double denominadorGeral = OperacaoService.contaEngolfoEspecificoInciado(p.getTipoCandle(),
					p.getPavioSuperior(), p.getPavioInferior(), p.getVolumeAcimaMedia20(), p.getPrecoAcimaMedia8(),
					p.getPrecoAcimaMedia20(), p.getPrecoAcimaMedia200(), p.getVariacao());

			estatisticaEngolfo.setFrequenciaGeral(denominadorGeral.intValue());

			Double numeradorUltimosCincoAnos = OperacaoService
					.contaEngolfoEspecificoInciadoUltimosCincoAnosNaoChegouAlvo(p.getTipoCandle(), p.getPavioSuperior(),
							p.getPavioInferior(), p.getVolumeAcimaMedia20(), p.getPrecoAcimaMedia8(),
							p.getPrecoAcimaMedia20(), p.getPrecoAcimaMedia200(), p.getVariacao());

			Double denominadorUltimosCincoAnos = OperacaoService.contaEngolfoEspecificoInciadoUltimosCincoAnos(
					p.getTipoCandle(), p.getPavioSuperior(), p.getPavioInferior(), p.getVolumeAcimaMedia20(),
					p.getPrecoAcimaMedia8(), p.getPrecoAcimaMedia20(), p.getPrecoAcimaMedia200(), p.getVariacao());

			estatisticaEngolfo.setFrequenciaUltimosCincoAnos(denominadorUltimosCincoAnos.intValue());

			Double numeradorUltimoAno = OperacaoService.contaEngolfoEspecificoInciadoUltimoAnoNaoChegouAlvo(
					p.getTipoCandle(), p.getPavioSuperior(), p.getPavioInferior(), p.getVolumeAcimaMedia20(),
					p.getPrecoAcimaMedia8(), p.getPrecoAcimaMedia20(), p.getPrecoAcimaMedia200(), p.getVariacao());

			Double denominadorUltimoAno = OperacaoService.contaEngolfoEspecificoInciadoUltimoAno(p.getTipoCandle(),
					p.getPavioSuperior(), p.getPavioInferior(), p.getVolumeAcimaMedia20(), p.getPrecoAcimaMedia8(),
					p.getPrecoAcimaMedia20(), p.getPrecoAcimaMedia200(), p.getVariacao());

			estatisticaEngolfo.setFrequenciaUltimoAno(denominadorUltimoAno.intValue());

			if (denominadorGeral != 0) {
				Double estatisticaGeral = 100.0 - ((numeradorGeral / denominadorGeral) * 100);
				estatisticaEngolfo.setAssertividadeGeralPrimeiroAlvo(estatisticaGeral);
			}

			if (denominadorUltimosCincoAnos != 0) {
				Double estatisticaUltimosCincoAnos = 100.0
						- ((numeradorUltimosCincoAnos / denominadorUltimosCincoAnos) * 100);
				estatisticaEngolfo.setAssertividadeUltimosCincoAnosPrimeiroAlvo(estatisticaUltimosCincoAnos);
			}

			if (denominadorUltimoAno != 0) {
				Double estatisticaUltimoAno = 100.0 - ((numeradorUltimoAno / denominadorUltimoAno) * 100);
				estatisticaEngolfo.setAssertividadeUltimoAnoPrimeiroAlvo(estatisticaUltimoAno);
			}

			estatisticasEngolfo.add(estatisticaEngolfo);

		});

		try {
			preencherTxtTreinamentoRedeNeuralEngolfo(perfil);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void preencherTxtTreinamentoRedeNeuralTresSoldados(Perfil perfil) throws IOException {

		FileWriter arq = new FileWriter("resource\\ArquivoTreinamentoTresSoldadosPerfil" + perfil.getNome() + ".txt");
		PrintWriter gravarArq = new PrintWriter(arq);

		estatisticasTresSoldados.stream().forEach(estatisticaTresSoldados -> {

			Double assertividadeComPeso = estatisticaTresSoldados.getAssertividadeGeralPrimeiroAlvo();

			if (estatisticaTresSoldados.getFrequenciaGeral() > 1) {
				assertividadeComPeso = estatisticaTresSoldados.getAssertividadeGeralPrimeiroAlvo();
			} else {
				assertividadeComPeso = 0.0;
			}

			String padraoString = "";
			String pavioSuperiorPrimeiroCandleString = "";
			String pavioInferiorPrimeiroCandleString = "";
			String pavioSuperiorSegundoCandleString = "";
			String pavioInferiorSegundoCandleString = "";
			String pavioSuperiorTerceiroCandleString = "";
			String pavioInferiorTerceiroCandleString = "";
			String precoAcimaMedia200String = "";

			if (estatisticaTresSoldados.getConfiguracaoTresSoldados().getPadrao() == Padroes.TRESSOLDADOSDEALTA) {
				padraoString = "1;";
			} else if (estatisticaTresSoldados.getConfiguracaoTresSoldados()
					.getPadrao() == Padroes.TRESSOLDADOSDEBAIXA) {
				padraoString = "0;";
			}

			if (estatisticaTresSoldados.getConfiguracaoTresSoldados()
					.getPavioSuperiorPrimeiroCandle() == PavioSuperior.SEMPAVIO) {
				pavioSuperiorPrimeiroCandleString = "0;0;";
			} else if (estatisticaTresSoldados.getConfiguracaoTresSoldados()
					.getPavioSuperiorPrimeiroCandle() == PavioSuperior.PAVIO5PORCENTO) {
				pavioSuperiorPrimeiroCandleString = "0;1;";
			} else if (estatisticaTresSoldados.getConfiguracaoTresSoldados()
					.getPavioSuperiorPrimeiroCandle() == PavioSuperior.PAVIO10PORCENTO) {
				pavioSuperiorPrimeiroCandleString = "1;0;";
			} else if (estatisticaTresSoldados.getConfiguracaoTresSoldados()
					.getPavioSuperiorPrimeiroCandle() == PavioSuperior.PAVIO33PORCENTO) {
				pavioSuperiorPrimeiroCandleString = "1;1;";
			}

			if (estatisticaTresSoldados.getConfiguracaoTresSoldados()
					.getPavioInferiorPrimeiroCandle() == PavioInferior.SEMPAVIO) {
				pavioInferiorPrimeiroCandleString = "0;0;";
			} else if (estatisticaTresSoldados.getConfiguracaoTresSoldados()
					.getPavioInferiorPrimeiroCandle() == PavioInferior.PAVIO5PORCENTO) {
				pavioInferiorPrimeiroCandleString = "0;1;";
			} else if (estatisticaTresSoldados.getConfiguracaoTresSoldados()
					.getPavioInferiorPrimeiroCandle() == PavioInferior.PAVIO10PORCENTO) {
				pavioInferiorPrimeiroCandleString = "1;0;";
			} else if (estatisticaTresSoldados.getConfiguracaoTresSoldados()
					.getPavioInferiorPrimeiroCandle() == PavioInferior.PAVIO33PORCENTO) {
				pavioInferiorPrimeiroCandleString = "1;1;";
			}

			if (estatisticaTresSoldados.getConfiguracaoTresSoldados()
					.getPavioSuperiorTerceiroCandle() == PavioSuperior.SEMPAVIO) {
				pavioSuperiorTerceiroCandleString = "0;0;";
			} else if (estatisticaTresSoldados.getConfiguracaoTresSoldados()
					.getPavioSuperiorTerceiroCandle() == PavioSuperior.PAVIO5PORCENTO) {
				pavioSuperiorTerceiroCandleString = "0;1;";
			} else if (estatisticaTresSoldados.getConfiguracaoTresSoldados()
					.getPavioSuperiorTerceiroCandle() == PavioSuperior.PAVIO10PORCENTO) {
				pavioSuperiorTerceiroCandleString = "1;0;";
			} else if (estatisticaTresSoldados.getConfiguracaoTresSoldados()
					.getPavioSuperiorTerceiroCandle() == PavioSuperior.PAVIO33PORCENTO) {
				pavioSuperiorTerceiroCandleString = "1;1;";
			}

			if (estatisticaTresSoldados.getConfiguracaoTresSoldados()
					.getPavioInferiorTerceiroCandle() == PavioInferior.SEMPAVIO) {
				pavioInferiorTerceiroCandleString = "0;0;";
			} else if (estatisticaTresSoldados.getConfiguracaoTresSoldados()
					.getPavioInferiorTerceiroCandle() == PavioInferior.PAVIO5PORCENTO) {
				pavioInferiorTerceiroCandleString = "0;1;";
			} else if (estatisticaTresSoldados.getConfiguracaoTresSoldados()
					.getPavioInferiorTerceiroCandle() == PavioInferior.PAVIO10PORCENTO) {
				pavioInferiorTerceiroCandleString = "1;0;";
			} else if (estatisticaTresSoldados.getConfiguracaoTresSoldados()
					.getPavioInferiorTerceiroCandle() == PavioInferior.PAVIO33PORCENTO) {
				pavioInferiorTerceiroCandleString = "1;1;";
			}

			if (estatisticaTresSoldados.getConfiguracaoTresSoldados()
					.getPrecoAcimaMedia200() == PrecoAcimaMedia200.SIM) {
				precoAcimaMedia200String = "1;";
			} else {
				precoAcimaMedia200String = "0;";
			}

			gravarArq.printf(padraoString + pavioSuperiorPrimeiroCandleString + pavioInferiorPrimeiroCandleString
					+ pavioSuperiorSegundoCandleString + pavioInferiorSegundoCandleString
					+ pavioSuperiorTerceiroCandleString + pavioInferiorTerceiroCandleString + precoAcimaMedia200String);

			String manter = "";
			String direcao = "";

			if (condicaoParaMargemSegurancaTresSoldados(assertividadeComPeso, perfil)) {
				manter = "0;";
			} else {
				manter = "1;";
			}

			if (estatisticaTresSoldados.getConfiguracaoTresSoldados().getPadrao() == Padroes.TRESSOLDADOSDEALTA) {
				direcao = "1;";
			} else if (estatisticaTresSoldados.getConfiguracaoTresSoldados()
					.getPadrao() == Padroes.TRESSOLDADOSDEBAIXA) {
				direcao = "0;";
			}

			gravarArq.printf(manter + direcao);

			gravarArq.println();

		});

		arq.close();

	}

	private static void preencherTxtTreinamentoRedeNeuralEngolfo(Perfil perfil) throws IOException {

		FileWriter arq = new FileWriter("resource\\ArquivoTreinamentoEngolfoPerfil" + perfil.getNome() + ".txt");
		PrintWriter gravarArq = new PrintWriter(arq);

		estatisticasEngolfo.stream().forEach(estatisticaEngolfo -> {
			Double assertividadeComPeso = calculaAssertividadeComPesoEngolfo(estatisticaEngolfo);
			String tipoCandleString = "";
			String pavioSuperiorString = "";
			String pavioInferiorString = "";
			String volumeAcimaMedia20String = "";
			String precoAcimaMedia8String = "";
			String precoAcimaMedia20String = "";
			String precoAcimaMedia200String = "";
			String variacaoString = "";

			if (estatisticaEngolfo.getConfiguracaoEngolfo().getTipoCandle() == TipoCandle.NEGATIVO) {
				tipoCandleString = "0;";
			} else if (estatisticaEngolfo.getConfiguracaoEngolfo().getTipoCandle() == TipoCandle.POSITIVO) {
				tipoCandleString = "1;";
			}

			if (estatisticaEngolfo.getConfiguracaoEngolfo().getPavioSuperior() == PavioSuperior.SEMPAVIO) {
				pavioSuperiorString = "0;0;0;";
			} else if (estatisticaEngolfo.getConfiguracaoEngolfo().getPavioSuperior() == PavioSuperior.PAVIO5PORCENTO) {
				pavioSuperiorString = "0;0;1;";
			} else if (estatisticaEngolfo.getConfiguracaoEngolfo()
					.getPavioSuperior() == PavioSuperior.PAVIO10PORCENTO) {
				pavioSuperiorString = "0;1;0;";
			} else if (estatisticaEngolfo.getConfiguracaoEngolfo()
					.getPavioSuperior() == PavioSuperior.PAVIO33PORCENTO) {
				pavioSuperiorString = "0;1;1;";
			} else if (estatisticaEngolfo.getConfiguracaoEngolfo()
					.getPavioSuperior() == PavioSuperior.PAVIO40PORCENTO) {
				pavioSuperiorString = "1;0;0;";
			}

			if (estatisticaEngolfo.getConfiguracaoEngolfo().getPavioInferior() == PavioInferior.SEMPAVIO) {
				pavioInferiorString = "0;0;0;";
			} else if (estatisticaEngolfo.getConfiguracaoEngolfo().getPavioInferior() == PavioInferior.PAVIO5PORCENTO) {
				pavioInferiorString = "0;0;1;";
			} else if (estatisticaEngolfo.getConfiguracaoEngolfo()
					.getPavioInferior() == PavioInferior.PAVIO10PORCENTO) {
				pavioInferiorString = "0;1;0;";
			} else if (estatisticaEngolfo.getConfiguracaoEngolfo()
					.getPavioInferior() == PavioInferior.PAVIO33PORCENTO) {
				pavioInferiorString = "0;1;1;";
			} else if (estatisticaEngolfo.getConfiguracaoEngolfo()
					.getPavioInferior() == PavioInferior.PAVIO40PORCENTO) {
				pavioInferiorString = "1;0;0;";
			}

			if (estatisticaEngolfo.getConfiguracaoEngolfo().getVolumeAcimaMedia20() == VolumeAcimaMedia20.SIM) {
				volumeAcimaMedia20String = "1;";
			} else {
				volumeAcimaMedia20String = "0;";
			}

			if (estatisticaEngolfo.getConfiguracaoEngolfo().getPrecoAcimaMedia8() == PrecoAcimaMedia8.SIM) {
				precoAcimaMedia8String = "1;";
			} else {
				precoAcimaMedia8String = "0;";
			}

			if (estatisticaEngolfo.getConfiguracaoEngolfo().getPrecoAcimaMedia20() == PrecoAcimaMedia20.SIM) {
				precoAcimaMedia20String = "1;";
			} else {
				precoAcimaMedia20String = "0;";
			}

			if (estatisticaEngolfo.getConfiguracaoEngolfo().getPrecoAcimaMedia200() == PrecoAcimaMedia200.SIM) {
				precoAcimaMedia200String = "1;";
			} else {
				precoAcimaMedia200String = "0;";
			}

			if (estatisticaEngolfo.getConfiguracaoEngolfo().getVariacao() == VariacaoPreco.ATE2VEZES) {
				variacaoString = "0;0;0;";
			} else if (estatisticaEngolfo.getConfiguracaoEngolfo().getVariacao() == VariacaoPreco.DE2A3VEZES) {
				variacaoString = "0;0;1;";
			} else if (estatisticaEngolfo.getConfiguracaoEngolfo().getVariacao() == VariacaoPreco.DE3A4VEZES) {
				variacaoString = "0;1;0;";
			} else if (estatisticaEngolfo.getConfiguracaoEngolfo().getVariacao() == VariacaoPreco.DE4A5VEZES) {
				variacaoString = "0;1;1;";
			} else if (estatisticaEngolfo.getConfiguracaoEngolfo().getVariacao() == VariacaoPreco.MAIORQUE5VEZES) {
				variacaoString = "1;0;0;";
			}

			gravarArq.printf(tipoCandleString + pavioSuperiorString + pavioInferiorString + volumeAcimaMedia20String
					+ precoAcimaMedia8String + precoAcimaMedia20String + precoAcimaMedia200String + variacaoString);

			String manter = "";
			String direcao = "";

			if (condicaoParaMargemSegurancaEngolfo(assertividadeComPeso, perfil)) {
				manter = "0;";
			} else {
				manter = "1;";
			}

			if (estatisticaEngolfo.getConfiguracaoEngolfo().getTipoCandle() == TipoCandle.POSITIVO) {
				direcao = "1;";
			} else if (estatisticaEngolfo.getConfiguracaoEngolfo().getTipoCandle() == TipoCandle.NEGATIVO) {
				direcao = "0;";
			}

			gravarArq.printf(manter + direcao);

			gravarArq.println();

		});

		arq.close();

	}

	private static void preencherTxtTreinamentoRedeNeuralPiercingLine(Perfil perfil) throws IOException {

		FileWriter arq = new FileWriter("resource\\ArquivoTreinamentoPiercingLinePerfil" + perfil.getNome() + ".txt");
		PrintWriter gravarArq = new PrintWriter(arq);

		estatisticasPiercingLine.stream().forEach(estatisticaPiercingLine -> {
			Double assertividadeComPeso = calculaAssertividadeComPesoPiercingLine(estatisticaPiercingLine);
			String tipoCandleString = "";
			String perfuracao = "";

			if (estatisticaPiercingLine.getConfiguracaoPiercingLine().getTipoCandle() == TipoCandle.NEGATIVO) {
				tipoCandleString = "0;";
			} else if (estatisticaPiercingLine.getConfiguracaoPiercingLine().getTipoCandle() == TipoCandle.POSITIVO) {
				tipoCandleString = "1;";
			}

			if (estatisticaPiercingLine.getConfiguracaoPiercingLine().getPerfuracao() == Perfuracao.POUCA) {
				perfuracao = "0;0;";
			} else if (estatisticaPiercingLine.getConfiguracaoPiercingLine().getPerfuracao() == Perfuracao.MEDIANA) {
				perfuracao = "0;1;";
			} else if (estatisticaPiercingLine.getConfiguracaoPiercingLine().getPerfuracao() == Perfuracao.MUITA) {
				perfuracao = "1;0;";
			} else if (estatisticaPiercingLine.getConfiguracaoPiercingLine().getPerfuracao() == Perfuracao.EXTREMA) {
				perfuracao = "1;1;";
			}

			gravarArq.printf(tipoCandleString
					+ estatisticaPiercingLine.getConfiguracaoPiercingLine().getVolumeAcimaMedia20().getValor() + ";"
					+ perfuracao
					+ estatisticaPiercingLine.getConfiguracaoPiercingLine().getPrecoAcimaMedia8().getValor() + ";"
					+ estatisticaPiercingLine.getConfiguracaoPiercingLine().getPrecoAcimaMedia20().getValor() + ";"
					+ estatisticaPiercingLine.getConfiguracaoPiercingLine().getPrecoAcimaMedia200().getValor() + ";");

			String manter = "";
			String direcao = "";

			if (condicaoParaPiercingLine(assertividadeComPeso, perfil)) {

				manter = "0;";

			} else {
				manter = "1;";
			}

			if (estatisticaPiercingLine.getConfiguracaoPiercingLine().getTipoCandle() == TipoCandle.POSITIVO) {

				direcao = "1;";

			} else if (estatisticaPiercingLine.getConfiguracaoPiercingLine().getTipoCandle() == TipoCandle.NEGATIVO) {

				direcao = "0;";

			}

			gravarArq.printf(manter + direcao);

			gravarArq.println();

		});

		arq.close();

	}

	private static void preencherTxtTreinamentoRedeNeuralMartelo(Perfil perfil) throws IOException {

		FileWriter arq = new FileWriter("resource\\ArquivoTreinamentoMarteloPerfil" + perfil.getNome() + ".txt");
		PrintWriter gravarArq = new PrintWriter(arq);

		estatisticasMartelo.stream().forEach(estatisticaMartelo -> {
			Double assertividadeComPeso = calculaAssertividadeComPesoMartelo(estatisticaMartelo);
			String tipoCandleString = "";
			String pavioSuperiorString = "";
			String pavioInferiorString = "";

			if (estatisticaMartelo.getConfiguracaoMartelo().getTipoCandle().getID() == 1) {
				tipoCandleString = "0;1;";
			} else if (estatisticaMartelo.getConfiguracaoMartelo().getTipoCandle().getID() == 2) {
				tipoCandleString = "1;0;";
			} else if (estatisticaMartelo.getConfiguracaoMartelo().getTipoCandle().getID() == 3) {
				tipoCandleString = "1;1;";
			}

			if (estatisticaMartelo.getConfiguracaoMartelo().getPavioSuperior().getID() == 1) {
				pavioSuperiorString = "0;1;";
			} else if (estatisticaMartelo.getConfiguracaoMartelo().getPavioSuperior().getID() == 2) {
				pavioSuperiorString = "1;0;";
			} else if (estatisticaMartelo.getConfiguracaoMartelo().getPavioSuperior().getID() == 3) {
				pavioSuperiorString = "1;1;";
			}

			if (estatisticaMartelo.getConfiguracaoMartelo().getPavioInferior().getID() == 7) {
				pavioInferiorString = "0;0;";
			} else if (estatisticaMartelo.getConfiguracaoMartelo().getPavioInferior().getID() == 8) {
				pavioInferiorString = "0;1;";
			} else if (estatisticaMartelo.getConfiguracaoMartelo().getPavioInferior().getID() == 9) {
				pavioInferiorString = "1;0;";
			} else if (estatisticaMartelo.getConfiguracaoMartelo().getPavioInferior().getID() == 10) {
				pavioInferiorString = "1;1;";
			}

			gravarArq.printf(tipoCandleString + pavioSuperiorString + pavioInferiorString
					+ estatisticaMartelo.getConfiguracaoMartelo().getVolumeAcimaMedia20().getValor() + ";"
					+ estatisticaMartelo.getConfiguracaoMartelo().getPrecoAcimaMedia200().getValor() + ";");

			if (condicaoParaCompraMartelo(assertividadeComPeso, perfil)) {

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
			if (Padroes.comparaPadrao(operacao.getPadrao()) == Padroes.MARTELO) {
				martelos.add(operacao.getMartelo());
			}
		});

		martelos.stream().forEach(martelo -> {
			String tipoCandleString = "";
			String pavioSuperiorString = "";
			String pavioInferiorString = "";

			if (TipoCandle.comparaTipoCandle(martelo.getTipo()).getID() == 1) {
				tipoCandleString = "0;1;";
			} else if (TipoCandle.comparaTipoCandle(martelo.getTipo()).getID() == 2) {
				tipoCandleString = "1;0;";
			} else if (TipoCandle.comparaTipoCandle(martelo.getTipo()).getID() == 3) {
				tipoCandleString = "1;1;";
			}

			if (PavioSuperior.comparaPavioSuperior(martelo.getPavioSuperior()).getID() == 1) {
				pavioSuperiorString = "0;1;";
			} else if (PavioSuperior.comparaPavioSuperior(martelo.getPavioSuperior()).getID() == 2) {
				pavioSuperiorString = "1;0;";
			} else if (PavioSuperior.comparaPavioSuperior(martelo.getPavioSuperior()).getID() == 3) {
				pavioSuperiorString = "1;1;";
			}

			if (PavioInferior.comparaPavioInferior(martelo.getPavioInferior()).getID() == 7) {
				pavioInferiorString = "0;0;";
			} else if (PavioInferior.comparaPavioInferior(martelo.getPavioInferior()).getID() == 8) {
				pavioInferiorString = "0;1;";
			} else if (PavioInferior.comparaPavioInferior(martelo.getPavioInferior()).getID() == 9) {
				pavioInferiorString = "1;0;";
			} else if (PavioInferior.comparaPavioInferior(martelo.getPavioInferior()).getID() == 10) {
				pavioInferiorString = "1;1;";
			}

			gravarArq.printf(tipoCandleString + pavioSuperiorString + pavioInferiorString
					+ VolumeAcimaMedia20.comparaVolumeAcimaMedia20(martelo.getVolumeAcimaMedia20()).getValor() + ";"
					+ PrecoAcimaMedia200.comparaPrecoAcimaMedia200(martelo.getMarteloAcimaMedia200()).getValor() + ";");
			gravarArq.println();

		});

		arq.close();

	}

	public static void preencherTxtValidacaoRedeNeuralPiercingLine() throws IOException {

		FileWriter arq = new FileWriter("resource\\ArquivoValidacaoPiercingLine.txt");
		PrintWriter gravarArq = new PrintWriter(arq);

		LocalDate data = LocalDate.parse("2021-01-01");

		ArrayList<Operacao> operacoes = OperacaoService.getOperacoesUltimoAno(MIN, MAX, data);
		ArrayList<PiercingLine> piercingLine = new ArrayList<PiercingLine>();

		operacoes.stream().forEach(operacao -> {
			if (Padroes.comparaPadrao(operacao.getPadrao()) == Padroes.PIERCINGLINE
					|| Padroes.comparaPadrao(operacao.getPadrao()) == Padroes.DARKCLOUD) {
				piercingLine.add(operacao.getPiercingLine());
			}
		});

		piercingLine.stream().forEach(piercing -> {
			String tipoCandleString = "";
			String vol = "";
			String perfuracao = "";
			String preco8 = "";
			String preco20 = "";
			String preco200 = "";

			if (TipoCandle.comparaTipoCandle(piercing.getTipo()) == TipoCandle.NEGATIVO) {
				tipoCandleString = "0;";
			} else if (TipoCandle.comparaTipoCandle(piercing.getTipo()) == TipoCandle.POSITIVO) {
				tipoCandleString = "1;";
			}

			if (VolumeAcimaMedia20
					.comparaVolumeAcimaMedia20(piercing.getVolumeAcimaMedia20()) == VolumeAcimaMedia20.SIM) {
				vol = "1;";
			} else {
				vol = "0;";
			}

			if (Perfuracao.comparaPerfuracaoComInteiro(piercing.getPerfuracao()) == Perfuracao.POUCA) {
				perfuracao = "0;0;";
			} else if (Perfuracao.comparaPerfuracaoComInteiro(piercing.getPerfuracao()) == Perfuracao.MEDIANA) {
				perfuracao = "0;1;";
			} else if (Perfuracao.comparaPerfuracaoComInteiro(piercing.getPerfuracao()) == Perfuracao.MUITA) {
				perfuracao = "1;0;";
			} else if (Perfuracao.comparaPerfuracaoComInteiro(piercing.getPerfuracao()) == Perfuracao.EXTREMA) {
				perfuracao = "1;1;";
			}

			if (PrecoAcimaMedia8.comparaPrecoAcimaMedia8(piercing.getPrecoAcimaMedia8()) == PrecoAcimaMedia8.SIM) {
				preco8 = "1;";
			} else {
				preco8 = "0;";
			}

			if (PrecoAcimaMedia20.comparaPrecoAcimaMedia20(piercing.getPrecoAcimaMedia20()) == PrecoAcimaMedia20.SIM) {
				preco20 = "1;";
			} else {
				preco20 = "0;";
			}

			if (PrecoAcimaMedia200
					.comparaPrecoAcimaMedia200(piercing.getPrecoAcimaMedia200()) == PrecoAcimaMedia200.SIM) {
				preco200 = "1;";
			} else {
				preco200 = "0;";
			}

			gravarArq.printf(tipoCandleString + vol + perfuracao + preco8 + preco20 + preco200);

			gravarArq.println();

		});

		arq.close();

	}

	public static void preencherTxtValidacaoRedeNeuralEngolfo() throws IOException {

		FileWriter arq = new FileWriter("resource\\ArquivoValidacaoEngolfo.txt");
		PrintWriter gravarArq = new PrintWriter(arq);

		LocalDate data = LocalDate.parse("2021-01-01");

		ArrayList<Operacao> operacoes = OperacaoService.getOperacoesUltimoAno(MIN, MAX, data);
		ArrayList<Engolfo> engolfos = new ArrayList<Engolfo>();

		operacoes.stream().forEach(operacao -> {
			if (Padroes.comparaPadrao(operacao.getPadrao()) == Padroes.ENGOLFO) {
				engolfos.add(operacao.getEngolfo());
			}
		});

		engolfos.stream().forEach(engolfo -> {
			Boolean validaEngolfo = true;
			String tipoCandleString = "";
			String pavioSuperiorString = "";
			String pavioInferiorString = "";
			String volumeAcimaMedia20String = "";
			String precoAcimaMedia8String = "";
			String precoAcimaMedia20String = "";
			String precoAcimaMedia200String = "";
			String variacaoString = "";

			if (PavioSuperior.comparaPavioSuperior(engolfo.getPavioSuperior()) == PavioSuperior.SEMPAVIO) {
				pavioSuperiorString = "0;0;0;";
			} else if (PavioSuperior.comparaPavioSuperior(engolfo.getPavioSuperior()) == PavioSuperior.PAVIO5PORCENTO) {
				pavioSuperiorString = "0;0;1;";
			} else if (PavioSuperior
					.comparaPavioSuperior(engolfo.getPavioSuperior()) == PavioSuperior.PAVIO10PORCENTO) {
				pavioSuperiorString = "0;1;0;";
			} else if (PavioSuperior
					.comparaPavioSuperior(engolfo.getPavioSuperior()) == PavioSuperior.PAVIO33PORCENTO) {
				pavioSuperiorString = "0;1;1;";
			} else if (PavioSuperior
					.comparaPavioSuperior(engolfo.getPavioSuperior()) == PavioSuperior.PAVIO40PORCENTO) {
				pavioSuperiorString = "1;0;0;";
			} else if (PavioSuperior.comparaPavioSuperior(engolfo.getPavioSuperior()) == PavioSuperior.PAVIO67PORCENTO
					|| PavioSuperior.comparaPavioSuperior(engolfo.getPavioSuperior()) == PavioSuperior.PAVIO80PORCENTO
					|| PavioSuperior
							.comparaPavioSuperior(engolfo.getPavioSuperior()) == PavioSuperior.PAVIO90PORCENTO) {
				validaEngolfo = false;
			}

			if (PavioInferior.comparaPavioInferior(engolfo.getPavioInferior()) == PavioInferior.SEMPAVIO) {
				pavioInferiorString = "0;0;0;";
			} else if (PavioInferior.comparaPavioInferior(engolfo.getPavioInferior()) == PavioInferior.PAVIO5PORCENTO) {
				pavioInferiorString = "0;0;1;";
			} else if (PavioInferior
					.comparaPavioInferior(engolfo.getPavioInferior()) == PavioInferior.PAVIO10PORCENTO) {
				pavioInferiorString = "0;1;0;";
			} else if (PavioInferior
					.comparaPavioInferior(engolfo.getPavioInferior()) == PavioInferior.PAVIO33PORCENTO) {
				pavioInferiorString = "0;1;1;";
			} else if (PavioInferior
					.comparaPavioInferior(engolfo.getPavioInferior()) == PavioInferior.PAVIO40PORCENTO) {
				pavioInferiorString = "1;0;0;";
			} else if (PavioInferior.comparaPavioInferior(engolfo.getPavioInferior()) == PavioInferior.PAVIO67PORCENTO
					|| PavioInferior.comparaPavioInferior(engolfo.getPavioInferior()) == PavioInferior.PAVIO80PORCENTO
					|| PavioInferior
							.comparaPavioInferior(engolfo.getPavioInferior()) == PavioInferior.PAVIO90PORCENTO) {
				validaEngolfo = false;
			}

			if (validaEngolfo == true) {

				if (TipoCandle.comparaTipoCandle(engolfo.getTipo()) == TipoCandle.NEGATIVO) {
					tipoCandleString = "0;";
				} else if (TipoCandle.comparaTipoCandle(engolfo.getTipo()) == TipoCandle.POSITIVO) {
					tipoCandleString = "1;";
				}

				if (VolumeAcimaMedia20
						.comparaVolumeAcimaMedia20(engolfo.getVolumeAcimaMedia20()) == VolumeAcimaMedia20.SIM) {
					volumeAcimaMedia20String = "1;";
				} else {
					volumeAcimaMedia20String = "0;";
				}

				if (PrecoAcimaMedia8.comparaPrecoAcimaMedia8(engolfo.getAcimaMedia8()) == PrecoAcimaMedia8.SIM) {
					precoAcimaMedia8String = "1;";
				} else {
					precoAcimaMedia8String = "0;";
				}

				if (PrecoAcimaMedia20.comparaPrecoAcimaMedia20(engolfo.getAcimaMedia20()) == PrecoAcimaMedia20.SIM) {
					precoAcimaMedia20String = "1;";
				} else {
					precoAcimaMedia20String = "0;";
				}

				if (PrecoAcimaMedia200
						.comparaPrecoAcimaMedia200(engolfo.getAcimaMedia200()) == PrecoAcimaMedia200.SIM) {
					precoAcimaMedia200String = "1;";
				} else {
					precoAcimaMedia200String = "0;";
				}

				if (VariacaoPreco.comparaVariacaoPreco(engolfo.getVariacao()) == VariacaoPreco.ATE2VEZES) {
					variacaoString = "0;0;0;";
				} else if (VariacaoPreco.comparaVariacaoPreco(engolfo.getVariacao()) == VariacaoPreco.DE2A3VEZES) {
					variacaoString = "0;0;1;";
				} else if (VariacaoPreco.comparaVariacaoPreco(engolfo.getVariacao()) == VariacaoPreco.DE3A4VEZES) {
					variacaoString = "0;1;0;";
				} else if (VariacaoPreco.comparaVariacaoPreco(engolfo.getVariacao()) == VariacaoPreco.DE4A5VEZES) {
					variacaoString = "0;1;1;";
				} else if (VariacaoPreco.comparaVariacaoPreco(engolfo.getVariacao()) == VariacaoPreco.MAIORQUE5VEZES) {
					variacaoString = "1;0;0;";
				}

				gravarArq.printf(tipoCandleString + pavioSuperiorString + pavioInferiorString + volumeAcimaMedia20String
						+ precoAcimaMedia8String + precoAcimaMedia20String + precoAcimaMedia200String + variacaoString);
				gravarArq.println();

			}
		});

		arq.close();

	}

	public static void preencherTxtValidacaoRedeNeuralTresSoldados() throws IOException {

		FileWriter arq = new FileWriter("resource\\ArquivoValidacaoTresSoldados.txt");
		PrintWriter gravarArq = new PrintWriter(arq);

		LocalDate data = LocalDate.parse("2021-01-01");

		ArrayList<Operacao> operacoes = OperacaoService.getOperacoesUltimoAno(MIN, MAX, data);
		ArrayList<TresSoldados> tresSoldados = new ArrayList<TresSoldados>();

		operacoes.stream().forEach(operacao -> {
			if (Padroes.comparaPadrao(operacao.getPadrao()) == Padroes.TRESSOLDADOSDEALTA
					|| Padroes.comparaPadrao(operacao.getPadrao()) == Padroes.TRESSOLDADOSDEBAIXA) {
				tresSoldados.add(operacao.getTresSoldados());
			}
		});

		tresSoldados.stream().forEach(tres -> {

			if (PavioSuperior.comparaPavioSuperior(tres.getPavioSuperiorPrimeiroCandle()) == PavioSuperior.SEMPAVIO
					|| PavioSuperior
							.comparaPavioSuperior(tres.getPavioSuperiorPrimeiroCandle()) == PavioSuperior.PAVIO5PORCENTO
					|| PavioSuperior.comparaPavioSuperior(
							tres.getPavioSuperiorPrimeiroCandle()) == PavioSuperior.PAVIO10PORCENTO
					|| PavioSuperior.comparaPavioSuperior(
							tres.getPavioSuperiorPrimeiroCandle()) == PavioSuperior.PAVIO33PORCENTO) {

				if (PavioInferior.comparaPavioInferior(tres.getPavioInferiorPrimeiroCandle()) == PavioInferior.SEMPAVIO
						|| PavioInferior.comparaPavioInferior(
								tres.getPavioInferiorPrimeiroCandle()) == PavioInferior.PAVIO5PORCENTO
						|| PavioInferior.comparaPavioInferior(
								tres.getPavioInferiorPrimeiroCandle()) == PavioInferior.PAVIO10PORCENTO
						|| PavioInferior.comparaPavioInferior(
								tres.getPavioInferiorPrimeiroCandle()) == PavioInferior.PAVIO33PORCENTO) {

					if (PavioSuperior
							.comparaPavioSuperior(tres.getPavioSuperiorTerceiroCandle()) == PavioSuperior.SEMPAVIO
							|| PavioSuperior.comparaPavioSuperior(
									tres.getPavioSuperiorTerceiroCandle()) == PavioSuperior.PAVIO5PORCENTO
							|| PavioSuperior.comparaPavioSuperior(
									tres.getPavioSuperiorTerceiroCandle()) == PavioSuperior.PAVIO10PORCENTO
							|| PavioSuperior.comparaPavioSuperior(
									tres.getPavioSuperiorTerceiroCandle()) == PavioSuperior.PAVIO33PORCENTO) {

						if (PavioInferior
								.comparaPavioInferior(tres.getPavioInferiorTerceiroCandle()) == PavioInferior.SEMPAVIO
								|| PavioInferior.comparaPavioInferior(
										tres.getPavioInferiorTerceiroCandle()) == PavioInferior.PAVIO5PORCENTO
								|| PavioInferior.comparaPavioInferior(
										tres.getPavioInferiorTerceiroCandle()) == PavioInferior.PAVIO10PORCENTO
								|| PavioInferior.comparaPavioInferior(
										tres.getPavioInferiorTerceiroCandle()) == PavioInferior.PAVIO33PORCENTO) {

							String padraoString = "";
							String pavioSuperiorPrimeiroCandleString = "";
							String pavioInferiorPrimeiroCandleString = "";
							String pavioSuperiorTerceiroCandleString = "";
							String pavioInferiorTerceiroCandleString = "";
							String precoAcimaMedia200String = "";

							if (Padroes.comparaPadrao(tres.getOperacao().getPadrao()) == Padroes.TRESSOLDADOSDEALTA) {
								padraoString = "1;";
							} else if (Padroes
									.comparaPadrao(tres.getOperacao().getPadrao()) == Padroes.TRESSOLDADOSDEBAIXA) {
								padraoString = "0;";
							}

							if (PavioSuperior.comparaPavioSuperior(
									tres.getPavioSuperiorPrimeiroCandle()) == PavioSuperior.SEMPAVIO) {
								pavioSuperiorPrimeiroCandleString = "0;0;";
							} else if (PavioSuperior.comparaPavioSuperior(
									tres.getPavioSuperiorPrimeiroCandle()) == PavioSuperior.PAVIO5PORCENTO) {
								pavioSuperiorPrimeiroCandleString = "0;1;";
							} else if (PavioSuperior.comparaPavioSuperior(
									tres.getPavioSuperiorPrimeiroCandle()) == PavioSuperior.PAVIO10PORCENTO) {
								pavioSuperiorPrimeiroCandleString = "1;0;";
							} else if (PavioSuperior.comparaPavioSuperior(
									tres.getPavioSuperiorPrimeiroCandle()) == PavioSuperior.PAVIO33PORCENTO) {
								pavioSuperiorPrimeiroCandleString = "1;1;";
							}

							if (PavioInferior.comparaPavioInferior(
									tres.getPavioInferiorPrimeiroCandle()) == PavioInferior.SEMPAVIO) {
								pavioInferiorPrimeiroCandleString = "0;0;";
							} else if (PavioInferior.comparaPavioInferior(
									tres.getPavioInferiorPrimeiroCandle()) == PavioInferior.PAVIO5PORCENTO) {
								pavioInferiorPrimeiroCandleString = "0;1;";
							} else if (PavioInferior.comparaPavioInferior(
									tres.getPavioInferiorPrimeiroCandle()) == PavioInferior.PAVIO10PORCENTO) {
								pavioInferiorPrimeiroCandleString = "1;0;";
							} else if (PavioInferior.comparaPavioInferior(
									tres.getPavioInferiorPrimeiroCandle()) == PavioInferior.PAVIO33PORCENTO) {
								pavioInferiorPrimeiroCandleString = "1;1;";
							}

							if (PavioSuperior.comparaPavioSuperior(
									tres.getPavioSuperiorTerceiroCandle()) == PavioSuperior.SEMPAVIO) {
								pavioSuperiorTerceiroCandleString = "0;0;";
							} else if (PavioSuperior.comparaPavioSuperior(
									tres.getPavioSuperiorTerceiroCandle()) == PavioSuperior.PAVIO5PORCENTO) {
								pavioSuperiorTerceiroCandleString = "0;1;";
							} else if (PavioSuperior.comparaPavioSuperior(
									tres.getPavioSuperiorTerceiroCandle()) == PavioSuperior.PAVIO10PORCENTO) {
								pavioSuperiorTerceiroCandleString = "1;0;";
							} else if (PavioSuperior.comparaPavioSuperior(
									tres.getPavioSuperiorTerceiroCandle()) == PavioSuperior.PAVIO33PORCENTO) {
								pavioSuperiorTerceiroCandleString = "1;1;";
							}

							if (PavioInferior.comparaPavioInferior(
									tres.getPavioInferiorTerceiroCandle()) == PavioInferior.SEMPAVIO) {
								pavioInferiorTerceiroCandleString = "0;0;";
							} else if (PavioInferior.comparaPavioInferior(
									tres.getPavioInferiorTerceiroCandle()) == PavioInferior.PAVIO5PORCENTO) {
								pavioInferiorTerceiroCandleString = "0;1;";
							} else if (PavioInferior.comparaPavioInferior(
									tres.getPavioInferiorTerceiroCandle()) == PavioInferior.PAVIO10PORCENTO) {
								pavioInferiorTerceiroCandleString = "1;0;";
							} else if (PavioInferior.comparaPavioInferior(
									tres.getPavioInferiorTerceiroCandle()) == PavioInferior.PAVIO33PORCENTO) {
								pavioInferiorTerceiroCandleString = "1;1;";
							}

							if (PrecoAcimaMedia200.comparaPrecoAcimaMedia200(
									tres.getPrecoAcimaMedia200()) == PrecoAcimaMedia200.SIM) {
								precoAcimaMedia200String = "1;";
							} else {
								precoAcimaMedia200String = "0;";
							}

							gravarArq.printf(padraoString + pavioSuperiorPrimeiroCandleString
									+ pavioInferiorPrimeiroCandleString + pavioSuperiorTerceiroCandleString
									+ pavioInferiorTerceiroCandleString + precoAcimaMedia200String);
							gravarArq.println();

						}

					}

				}

			}

		});

		arq.close();

	}

	public static void testaRedeNeuralMarteloNaPratica(Perfil perfil) {

		LocalDate data = LocalDate.parse("2021-01-01");
		ArrayList<Operacao> operacoes = OperacaoService.getOperacoesUltimoAno(MIN, MAX, data);
		ArrayList<Martelo> martelo = new ArrayList<Martelo>();
		ArrayList<Operacao> operacoesMartelo = new ArrayList<>();

		operacoes.stream().forEach(operacao -> {
			if (Padroes.comparaPadrao(operacao.getPadrao()) == Padroes.MARTELO) {
				martelo.add(operacao.getMartelo());
				operacoesMartelo.add(operacao);
			}
		});

		String inputFileValidacao = "resource\\ArquivoValidacaoMartelo.txt";

		Double assertividade = 0.0;
		Double acerto = 0.0;
		Double total = 0.0;
		Double porcentagemFinal = 0.0;

		NeuralNetLoader loader = new NeuralNetLoader("resource\\RedeNeuralMarteloPerfil" + perfil.getNome() + ".snet");
		NeuralNet nnet = loader.getNeuralNet();

		Monitor monitor = nnet.getMonitor();

		monitor.addNeuralNetListener(nnet);

		MemoryOutputSynapse memOut = new MemoryOutputSynapse();

		nnet.getOutputLayer().removeAllOutputs();
		nnet.getOutputLayer().addOutputSynapse(memOut);

		FileInputSynapse inputStream = new FileInputSynapse();

		inputStream = (FileInputSynapse) nnet.getInputLayer().getAllInputs().get(0);

		inputStream.setAdvancedColumnSelector("1,2,3,4,5,6,7,8");
		inputStream.setInputFile(new File(inputFileValidacao));

		nnet.getOutputLayer().addInputSynapse(inputStream);

		nnet.getMonitor().setTrainingPatterns(operacoesMartelo.size());
		nnet.getMonitor().setTotCicles(1);
		nnet.getMonitor().setLearning(false);
		nnet.go();

		while (nnet.isRunning()) {
			for (int i = 0; i < operacoesMartelo.size(); i++) {
				double[] pattern = memOut.getNextPattern();

				if (pattern[0] > 0.5) {
					if (operacoesMartelo.get(i).isStart()) {
						total += 1.0;
						if (operacoesMartelo.get(i).getPrimeiroAlvoAtingido()) {
							acerto += 1.0;
							porcentagemFinal += operacoesMartelo.get(i).getPorcentagemOperacaoFinal();
						} else {
							porcentagemFinal -= operacoesMartelo.get(i).getPorcentagemOperacaoFinal();
						}

					}
				}

			}

		}

		assertividade = (acerto / total) * 100;

		System.out.println();
		System.out.println("__________________________________________________________________________");
		System.out.println();
		System.out.println();
		System.out.println("Perfil: " + perfil.getNome());
		System.out.println();
		System.out.println("Assertividade de " + assertividade + "%");
		System.out.println();
		System.out.println("Total de operacoes no periodo de 01/01/2021 até 27/07/2021: " + total.intValue());
		System.out.println();
		System.out.println("Porcentagem Final No Periodo: " + porcentagemFinal + "%");
		System.out.println();
		System.out.println();
		System.out.println("__________________________________________________________________________");
		System.out.println();

		nnet.stop();

	}

	public static void testaRedeNeuralPiercingLineNaPratica(Perfil perfil) {

		LocalDate data = LocalDate.parse("2021-01-01");

		ArrayList<Operacao> operacoes = OperacaoService.getOperacoesUltimoAno(MIN, MAX, data);
		ArrayList<PiercingLine> piercingLine = new ArrayList<PiercingLine>();
		ArrayList<Operacao> operacoesPiercingLine = new ArrayList<>();

		operacoes.stream().forEach(operacao -> {
			if (Padroes.comparaPadrao(operacao.getPadrao()) == Padroes.PIERCINGLINE
					|| Padroes.comparaPadrao(operacao.getPadrao()) == Padroes.DARKCLOUD) {
				piercingLine.add(operacao.getPiercingLine());
				operacoesPiercingLine.add(operacao);
			}
		});

		String inputFileValidacao = "resource\\ArquivoValidacaoPiercingLine.txt";

		Double assertividade = 0.0;
		Double acerto = 0.0;
		Double total = 0.0;
		Double porcentagemFinal = 0.0;

		NeuralNetLoader loader = new NeuralNetLoader(
				"resource\\RedeNeuralPiercingLinePerfil" + perfil.getNome() + ".snet");
		NeuralNet nnet = loader.getNeuralNet();

		Monitor monitor = nnet.getMonitor();

		monitor.addNeuralNetListener(nnet);

		MemoryOutputSynapse memOut = new MemoryOutputSynapse();

		nnet.getOutputLayer().removeAllOutputs();
		nnet.getOutputLayer().addOutputSynapse(memOut);

		FileInputSynapse inputStream = new FileInputSynapse();

		inputStream = (FileInputSynapse) nnet.getInputLayer().getAllInputs().get(0);

		inputStream.setAdvancedColumnSelector("1,2,3,4,5,6,7");
		inputStream.setInputFile(new File(inputFileValidacao));

		nnet.getOutputLayer().addInputSynapse(inputStream);

		nnet.getMonitor().setTrainingPatterns(operacoesPiercingLine.size());
		nnet.getMonitor().setTotCicles(1);
		nnet.getMonitor().setLearning(false);
		nnet.go();

		while (nnet.isRunning()) {
			for (int i = 0; i < operacoesPiercingLine.size(); i++) {
				double[] pattern = memOut.getNextPattern();

				if (pattern[0] < 0.5 && pattern[1] > 0.5) {
					if (operacoesPiercingLine.get(i).isStart()) {
						total += 1.0;
						if (operacoesPiercingLine.get(i).getPrimeiroAlvoAtingido()) {
							acerto += 1.0;
							porcentagemFinal += operacoesPiercingLine.get(i).getPorcentagemOperacaoFinal();
						} else {
							porcentagemFinal -= operacoesPiercingLine.get(i).getPorcentagemOperacaoFinal();
						}
					}
				} else if (pattern[0] < 0.5 && pattern[1] < 0.5) {
					if (operacoesPiercingLine.get(i).isStart()) {
						total += 1.0;
						if (operacoesPiercingLine.get(i).getPrimeiroAlvoAtingido()) {
							acerto += 1.0;
							porcentagemFinal += operacoesPiercingLine.get(i).getPorcentagemOperacaoFinal();
						} else {
							porcentagemFinal -= operacoesPiercingLine.get(i).getPorcentagemOperacaoFinal();
						}
					}
				}

			}

		}

		assertividade = (acerto / total) * 100;

		System.out.println();
		System.out.println("__________________________________________________________________________");
		System.out.println();
		System.out.println();
		System.out.println("Perfil: " + perfil.getNome());
		System.out.println();
		System.out.println("Assertividade de " + assertividade + "%");
		System.out.println();
		System.out.println("Total de operacoes no periodo de 01/01/2021 até 27/07/2021: " + total.intValue());
		System.out.println();
		System.out.println("Porcentagem Final No Periodo: " + porcentagemFinal + "%");
		System.out.println();
		System.out.println();
		System.out.println("__________________________________________________________________________");
		System.out.println();

		nnet.stop();

	}

	public static void testaRedeNeuralEngolfoNaPratica(Perfil perfil) {

		LocalDate data = LocalDate.parse("2021-01-01");

		ArrayList<Operacao> operacoes = OperacaoService.getOperacoesUltimoAno(MIN, MAX, data);
		ArrayList<Engolfo> engolfos = new ArrayList<Engolfo>();
		ArrayList<Operacao> operacoesEngolfo = new ArrayList<Operacao>();

		operacoes.stream().forEach(operacao -> {
			if (Padroes.comparaPadrao(operacao.getPadrao()) == Padroes.ENGOLFO) {
				if (PavioInferior
						.comparaPavioInferior(operacao.getEngolfo().getPavioInferior()) != PavioInferior.PAVIO67PORCENTO
						&& PavioInferior.comparaPavioInferior(
								operacao.getEngolfo().getPavioInferior()) != PavioInferior.PAVIO80PORCENTO
						&& PavioInferior.comparaPavioInferior(
								operacao.getEngolfo().getPavioInferior()) != PavioInferior.PAVIO90PORCENTO) {

					if (PavioSuperior.comparaPavioSuperior(
							operacao.getEngolfo().getPavioSuperior()) != PavioSuperior.PAVIO67PORCENTO
							&& PavioSuperior.comparaPavioSuperior(
									operacao.getEngolfo().getPavioSuperior()) != PavioSuperior.PAVIO80PORCENTO
							&& PavioSuperior.comparaPavioSuperior(
									operacao.getEngolfo().getPavioSuperior()) != PavioSuperior.PAVIO90PORCENTO)

						engolfos.add(operacao.getEngolfo());
					operacoesEngolfo.add(operacao);

				}

			}
		});

		String inputFileValidacao = "resource\\ArquivoValidacaoEngolfo.txt";

		Double assertividade = 0.0;
		Double acerto = 0.0;
		Double total = 0.0;
		Double porcentagemFinal = 0.0;

		NeuralNetLoader loader = new NeuralNetLoader("resource\\RedeNeuralEngolfoPerfil" + perfil.getNome() + ".snet");
		NeuralNet nnet = loader.getNeuralNet();

		Monitor monitor = nnet.getMonitor();

		monitor.addNeuralNetListener(nnet);

		MemoryOutputSynapse memOut = new MemoryOutputSynapse();

		nnet.getOutputLayer().removeAllOutputs();
		nnet.getOutputLayer().addOutputSynapse(memOut);

		FileInputSynapse inputStream = new FileInputSynapse();

		inputStream = (FileInputSynapse) nnet.getInputLayer().getAllInputs().get(0);

		inputStream.setAdvancedColumnSelector("1,2,3,4,5,6,7,8,9,10,11,12,13,14");
		inputStream.setInputFile(new File(inputFileValidacao));

		nnet.getOutputLayer().addInputSynapse(inputStream);

		nnet.getMonitor().setTrainingPatterns(141);
		nnet.getMonitor().setTotCicles(1);
		nnet.getMonitor().setLearning(false);
		nnet.go();

		while (nnet.isRunning()) {

			for (int i = 0; i < operacoesEngolfo.size() - 3; i++) {
				double[] pattern = memOut.getNextPattern();

				if (pattern[0] < 0.5 && pattern[1] > 0.5) {
					if (operacoesEngolfo.get(i).isStart()) {
						total += 1.0;
						if (operacoesEngolfo.get(i).getPrimeiroAlvoAtingido()) {
							acerto += 1.0;
							porcentagemFinal += operacoesEngolfo.get(i).getPorcentagemOperacaoFinal();
						} else {
							porcentagemFinal -= operacoesEngolfo.get(i).getPorcentagemOperacaoFinal();
						}
					}
				} else if (pattern[0] < 0.5 && pattern[1] < 0.5) {
					if (operacoesEngolfo.get(i).isStart()) {
						total += 1.0;
						if (operacoesEngolfo.get(i).getPrimeiroAlvoAtingido()) {
							acerto += 1.0;
							porcentagemFinal += operacoesEngolfo.get(i).getPorcentagemOperacaoFinal();
						} else {
							porcentagemFinal -= operacoesEngolfo.get(i).getPorcentagemOperacaoFinal();
						}
					}
				}

			}

		}

		assertividade = (acerto / total) * 100;

		System.out.println();
		System.out.println("__________________________________________________________________________");
		System.out.println();
		System.out.println();
		System.out.println("Perfil: " + perfil.getNome());
		System.out.println();
		System.out.println("Assertividade de " + assertividade + "%");
		System.out.println();
		System.out.println("Total de operacoes no periodo de 01/01/2021 até 27/07/2021: " + total.intValue());
		System.out.println();
		System.out.println("Porcentagem Final No Periodo: " + porcentagemFinal + "%");
		System.out.println();
		System.out.println();
		System.out.println("__________________________________________________________________________");
		System.out.println();

		nnet.stop();

	}

	public static void testaRedeNeuralTresSoldadosNaPratica(Perfil perfil) {

		LocalDate data = LocalDate.parse("2021-01-01");

		ArrayList<Operacao> operacoes = OperacaoService.getOperacoesUltimoAno(MIN, MAX, data);
		ArrayList<TresSoldados> tresSoldados = new ArrayList<TresSoldados>();
		ArrayList<Operacao> operacoesTresSoldados = new ArrayList<Operacao>();

		operacoes.stream().forEach(operacao -> {
			
			TresSoldados tres = operacao.getTresSoldados();
			if (Padroes.comparaPadrao(operacao.getPadrao()) == Padroes.TRESSOLDADOSDEALTA
					|| Padroes.comparaPadrao(operacao.getPadrao()) == Padroes.TRESSOLDADOSDEBAIXA) {

				if (PavioSuperior.comparaPavioSuperior(tres.getPavioSuperiorPrimeiroCandle()) == PavioSuperior.SEMPAVIO
						|| PavioSuperior
								.comparaPavioSuperior(tres.getPavioSuperiorPrimeiroCandle()) == PavioSuperior.PAVIO5PORCENTO
						|| PavioSuperior.comparaPavioSuperior(tres.getPavioSuperiorPrimeiroCandle()) == PavioSuperior.PAVIO10PORCENTO
						|| PavioSuperior.comparaPavioSuperior(tres.getPavioSuperiorPrimeiroCandle()) == PavioSuperior.PAVIO33PORCENTO) {

					if (PavioInferior.comparaPavioInferior(tres.getPavioInferiorPrimeiroCandle()) == PavioInferior.SEMPAVIO
							|| PavioInferior.comparaPavioInferior(tres.getPavioInferiorPrimeiroCandle()) == PavioInferior.PAVIO5PORCENTO
							|| PavioInferior.comparaPavioInferior(tres.getPavioInferiorPrimeiroCandle()) == PavioInferior.PAVIO10PORCENTO
							|| PavioInferior.comparaPavioInferior(tres.getPavioInferiorPrimeiroCandle()) == PavioInferior.PAVIO33PORCENTO) {

						if (PavioSuperior
								.comparaPavioSuperior(tres.getPavioSuperiorTerceiroCandle()) == PavioSuperior.SEMPAVIO
								|| PavioSuperior.comparaPavioSuperior(tres.getPavioSuperiorTerceiroCandle()) == PavioSuperior.PAVIO5PORCENTO
								|| PavioSuperior.comparaPavioSuperior(tres.getPavioSuperiorTerceiroCandle()) == PavioSuperior.PAVIO10PORCENTO
								|| PavioSuperior.comparaPavioSuperior(tres.getPavioSuperiorTerceiroCandle()) == PavioSuperior.PAVIO33PORCENTO) {

							if (PavioInferior
									.comparaPavioInferior(tres.getPavioInferiorTerceiroCandle()) == PavioInferior.SEMPAVIO
									|| PavioInferior.comparaPavioInferior(tres.getPavioInferiorTerceiroCandle()) == PavioInferior.PAVIO5PORCENTO
									|| PavioInferior.comparaPavioInferior(tres.getPavioInferiorTerceiroCandle()) == PavioInferior.PAVIO10PORCENTO
									|| PavioInferior.comparaPavioInferior(tres.getPavioInferiorTerceiroCandle()) == PavioInferior.PAVIO33PORCENTO) {
								
								tresSoldados.add(operacao.getTresSoldados());
								operacoesTresSoldados.add(operacao);
								
							}
						}
					}
				}
				
			}

		});

		String inputFileValidacao = "resource\\ArquivoValidacaoTresSoldados.txt";

		Double assertividade = 0.0;
		Double acerto = 0.0;
		Double total = 0.0;
		Double porcentagemFinal = 0.0;

		NeuralNetLoader loader = new NeuralNetLoader("resource\\RedeNeuralTresSoldadosPerfil" + perfil.getNome() + ".snet");
		NeuralNet nnet = loader.getNeuralNet();

		Monitor monitor = nnet.getMonitor();

		monitor.addNeuralNetListener(nnet);

		MemoryOutputSynapse memOut = new MemoryOutputSynapse();

		nnet.getOutputLayer().removeAllOutputs();
		nnet.getOutputLayer().addOutputSynapse(memOut);

		FileInputSynapse inputStream = new FileInputSynapse();

		inputStream = (FileInputSynapse) nnet.getInputLayer().getAllInputs().get(0);

		inputStream.setAdvancedColumnSelector("1,2,3,4,5,6,7,8,9,10");
		inputStream.setInputFile(new File(inputFileValidacao));

		nnet.getOutputLayer().addInputSynapse(inputStream);

		nnet.getMonitor().setTrainingPatterns(87);
		nnet.getMonitor().setTotCicles(1);
		nnet.getMonitor().setLearning(false);
		nnet.go();

		while (nnet.isRunning()) {

			for (int i = 0; i < operacoesTresSoldados.size(); i++) {
				double[] pattern = memOut.getNextPattern();

				if (pattern[0] < 0.5 && pattern[1] > 0.5) {
					if (operacoesTresSoldados.get(i).isStart()) {
						total += 1.0;
						if (operacoesTresSoldados.get(i).getPrimeiroAlvoAtingido()) {
							acerto += 1.0;
							porcentagemFinal += operacoesTresSoldados.get(i).getPorcentagemOperacaoFinal();
						} else {
							porcentagemFinal -= operacoesTresSoldados.get(i).getPorcentagemOperacaoFinal();
						}
					}
				} else if (pattern[0] < 0.5 && pattern[1] < 0.5) {
					if (operacoesTresSoldados.get(i).isStart()) {
						total += 1.0;
						if (operacoesTresSoldados.get(i).getPrimeiroAlvoAtingido()) {
							acerto += 1.0;
							porcentagemFinal += operacoesTresSoldados.get(i).getPorcentagemOperacaoFinal();
						} else {
							porcentagemFinal -= operacoesTresSoldados.get(i).getPorcentagemOperacaoFinal();
						}
					}
				}

			}

		}

		assertividade = (acerto / total) * 100;

		System.out.println();
		System.out.println("__________________________________________________________________________");
		System.out.println();
		System.out.println();
		System.out.println("Perfil: " + perfil.getNome());
		System.out.println();
		System.out.println("Assertividade de " + assertividade + "%");
		System.out.println();
		System.out.println("Total de operacoes no periodo de 01/01/2021 até 27/07/2021: " + total.intValue());
		System.out.println();
		System.out.println("Porcentagem Final No Periodo: " + porcentagemFinal + "%");
		System.out.println();
		System.out.println();
		System.out.println("__________________________________________________________________________");
		System.out.println();

		nnet.stop();

	}

	public static void realizaTreinamentoRedeNeuralTresSoldados(Perfil perfil) throws IOException {

		Double acerto = 0.0;

		ArrayList<Integer> valoresValidacaoManter = new ArrayList<>();
		ArrayList<Integer> valoresValidacaoDirecao = new ArrayList<>();

		String inputFile = "resource\\ArquivoTreinamentoTresSoldadosPerfil" + perfil.getNome() + ".txt";

		String outputFile = "resource\\ArquivoTreinamentoTresSoldadosSaidaPerfil" + perfil.getNome() + ".txt";

		String saveEngolfo = "resource\\RedeNeuralTresSoldadosPerfil" + perfil.getNome() + ".snet";

		LinearLayer input = new LinearLayer();

		SigmoidLayer hidden = new SigmoidLayer();

		SigmoidLayer output = new SigmoidLayer();

		input.setLayerName("input");

		hidden.setLayerName("hidden");

		output.setLayerName("output");

		input.setRows(10);
		hidden.setRows(20);
		output.setRows(2);

		FullSynapse synapse_IH = new FullSynapse();

		FullSynapse synapse_HO = new FullSynapse();

		synapse_IH.setName("IH");

		synapse_HO.setName("HO");

		input.addOutputSynapse(synapse_IH);
		hidden.addInputSynapse(synapse_IH);

		hidden.addOutputSynapse(synapse_HO);
		output.addInputSynapse(synapse_HO);

		FileInputSynapse inputStream = new FileInputSynapse();
		inputStream.setAdvancedColumnSelector("1,2,3,4,5,6,7,8,9,10");
		inputStream.setInputFile(new File(inputFile));

		input.addInputSynapse(inputStream);

		TeachingSynapse trainer = new TeachingSynapse();

		FileInputSynapse samples = new FileInputSynapse();

		samples.setInputFile(new File(inputFile));

		samples.setAdvancedColumnSelector("11,12");

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
		input.setMonitor(monitor);
		hidden.setMonitor(monitor);
		output.setMonitor(monitor);

		monitor.addNeuralNetListener(nnet);

		monitor.setTrainingPatterns(458);
		monitor.setTotCicles(4000);
		monitor.setLearning(true);
		nnet.go();

		while (nnet.isRunning()) {

		}

		MemoryOutputSynapse memOut = new MemoryOutputSynapse();

		output.removeAllOutputs();

		output.addOutputSynapse(memOut);

		nnet.getMonitor().setTotCicles(1);

		nnet.getMonitor().setLearning(false);
		nnet.go();

		FileReader arquivo;
		try {

			arquivo = new FileReader(inputFile);
			BufferedReader leitura = new BufferedReader(arquivo);
			String linha = leitura.readLine();

			while (linha != null) {
				char manter = linha.charAt(20);
				valoresValidacaoManter.add(Character.getNumericValue(manter));
				char direcao = linha.charAt(22);
				valoresValidacaoDirecao.add(Character.getNumericValue(direcao));

				linha = leitura.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < valoresValidacaoManter.size(); i++) {
			double[] pattern = memOut.getNextPattern();

			if (pattern[0] > 0.5 && valoresValidacaoManter.get(i).intValue() == 1) {
				if (pattern[1] > 0.5 && valoresValidacaoDirecao.get(i).intValue() == 1) {
					acerto += 1.0;
				} else if (pattern[1] < 0.5 && valoresValidacaoDirecao.get(i).intValue() == 0) {
					acerto += 1.0;
				}
			} else if (pattern[0] < 0.5 && valoresValidacaoManter.get(i).intValue() == 0) {
				if (pattern[1] > 0.5 && valoresValidacaoDirecao.get(i).intValue() == 1) {
					acerto += 1.0;
				} else if (pattern[1] < 0.5 && valoresValidacaoDirecao.get(i).intValue() == 0) {
					acerto += 1.0;
				}
			}

		}

		Double assertividade = (acerto / 458) * 100;

		System.out.println();
		System.out.println("Assertividade da Rede Neural = " + assertividade + "%");
		System.out.println();

		if (assertividade > 97.0) {
			System.out.println();
			System.out.println("Salvando Rede Neural Tres Soldados...");
			System.out.println();
			saveNeuralNet(saveEngolfo, nnet);
		}

		System.out.println();
		System.out.println("Finalizando...");
		System.out.println();

		nnet.stop();

	}

	public static void realizaTreinamentoRedeNeuralEngolfo(Perfil perfil) throws IOException {

		Double acerto = 0.0;

		ArrayList<Integer> valoresValidacaoManter = new ArrayList<>();
		ArrayList<Integer> valoresValidacaoDirecao = new ArrayList<>();

		String inputFile = "resource\\ArquivoTreinamentoEngolfoPerfil" + perfil.getNome() + ".txt";

		String outputFile = "resource\\ArquivoTreinamentoEngolfoSaidaPerfil" + perfil.getNome() + ".txt";

		String saveEngolfo = "resource\\RedeNeuralEngolfoPerfil" + perfil.getNome() + ".snet";

		LinearLayer input = new LinearLayer();

		SigmoidLayer hidden = new SigmoidLayer();

		SigmoidLayer output = new SigmoidLayer();

		input.setLayerName("input");

		hidden.setLayerName("hidden");

		output.setLayerName("output");

		input.setRows(14);
		hidden.setRows(15);
		output.setRows(2);

		FullSynapse synapse_IH = new FullSynapse();

		FullSynapse synapse_HO = new FullSynapse();

		synapse_IH.setName("IH");

		synapse_HO.setName("HO");

		input.addOutputSynapse(synapse_IH);
		hidden.addInputSynapse(synapse_IH);

		hidden.addOutputSynapse(synapse_HO);
		output.addInputSynapse(synapse_HO);

		FileInputSynapse inputStream = new FileInputSynapse();
		inputStream.setAdvancedColumnSelector("1,2,3,4,5,6,7,8,9,10,11,12,13,14");
		inputStream.setInputFile(new File(inputFile));

		input.addInputSynapse(inputStream);

		TeachingSynapse trainer = new TeachingSynapse();

		FileInputSynapse samples = new FileInputSynapse();

		samples.setInputFile(new File(inputFile));

		samples.setAdvancedColumnSelector("15,16");

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
		input.setMonitor(monitor);
		hidden.setMonitor(monitor);
		output.setMonitor(monitor);

		monitor.addNeuralNetListener(nnet);

		monitor.setTrainingPatterns(4000);
		monitor.setTotCicles(2000);
		monitor.setLearning(true);
		nnet.go();

		while (nnet.isRunning()) {

		}

		MemoryOutputSynapse memOut = new MemoryOutputSynapse();

		output.removeAllOutputs();

		output.addOutputSynapse(memOut);

		nnet.getMonitor().setTotCicles(1);

		nnet.getMonitor().setLearning(false);
		nnet.go();

		FileReader arquivo;
		try {

			arquivo = new FileReader(inputFile);
			BufferedReader leitura = new BufferedReader(arquivo);
			String linha = leitura.readLine();

			while (linha != null) {
				char manter = linha.charAt(28);
				valoresValidacaoManter.add(Character.getNumericValue(manter));
				char direcao = linha.charAt(30);
				valoresValidacaoDirecao.add(Character.getNumericValue(direcao));

				linha = leitura.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < valoresValidacaoManter.size(); i++) {
			double[] pattern = memOut.getNextPattern();

			if (pattern[0] > 0.5 && valoresValidacaoManter.get(i).intValue() == 1) {
				if (pattern[1] > 0.5 && valoresValidacaoDirecao.get(i).intValue() == 1) {
					acerto += 1.0;
				} else if (pattern[1] < 0.5 && valoresValidacaoDirecao.get(i).intValue() == 0) {
					acerto += 1.0;
				}
			} else if (pattern[0] < 0.5 && valoresValidacaoManter.get(i).intValue() == 0) {
				if (pattern[1] > 0.5 && valoresValidacaoDirecao.get(i).intValue() == 1) {
					acerto += 1.0;
				} else if (pattern[1] < 0.5 && valoresValidacaoDirecao.get(i).intValue() == 0) {
					acerto += 1.0;
				}
			}

		}

		Double assertividade = (acerto / 4000.0) * 100;

		System.out.println();
		System.out.println("Assertividade da Rede Neural = " + assertividade + "%");
		System.out.println();

		if (assertividade > 99.0) {
			System.out.println();
			System.out.println("Salvando Rede Neural Engolfo...");
			System.out.println();
			saveNeuralNet(saveEngolfo, nnet);
		}

		System.out.println();
		System.out.println("Finalizando...");
		System.out.println();

		nnet.stop();

	}

	public static void realizaTreinamentoRedeNeuralPiercingLine(Perfil perfil) throws IOException {

		Double acerto = 0.0;

		ArrayList<Integer> valoresValidacaoManter = new ArrayList<>();
		ArrayList<Integer> valoresValidacaoDirecao = new ArrayList<>();

		String inputFile = "resource\\ArquivoTreinamentoPiercingLinePerfil" + perfil.getNome() + ".txt";

		String outputFile = "resource\\ArquivoTreinamentoPiercingLineSaidaPerfil" + perfil.getNome() + ".txt";

		String savePiercingLine = "resource\\RedeNeuralPiercingLinePerfil" + perfil.getNome() + ".snet";

		LinearLayer input = new LinearLayer();

		SigmoidLayer hidden = new SigmoidLayer();

		SigmoidLayer output = new SigmoidLayer();

		input.setLayerName("input");

		hidden.setLayerName("hidden");

		output.setLayerName("output");

		input.setRows(7);
		hidden.setRows(8);
		output.setRows(2);

		FullSynapse synapse_IH = new FullSynapse();

		FullSynapse synapse_HO = new FullSynapse();

		synapse_IH.setName("IH");

		synapse_HO.setName("HO");

		input.addOutputSynapse(synapse_IH);
		hidden.addInputSynapse(synapse_IH);

		hidden.addOutputSynapse(synapse_HO);
		output.addInputSynapse(synapse_HO);

		FileInputSynapse inputStream = new FileInputSynapse();
		inputStream.setAdvancedColumnSelector("1,2,3,4,5,6,7");
		inputStream.setInputFile(new File(inputFile));

		input.addInputSynapse(inputStream);

		TeachingSynapse trainer = new TeachingSynapse();

		FileInputSynapse samples = new FileInputSynapse();

		samples.setInputFile(new File(inputFile));

		samples.setAdvancedColumnSelector("8,9");

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
		input.setMonitor(monitor);
		hidden.setMonitor(monitor);
		output.setMonitor(monitor);

		monitor.addNeuralNetListener(nnet);

		monitor.setTrainingPatterns(128);
		monitor.setTotCicles(2000);
		monitor.setLearning(true);
		nnet.go();

		while (nnet.isRunning()) {

		}

		MemoryOutputSynapse memOut = new MemoryOutputSynapse();

		output.removeAllOutputs();

		output.addOutputSynapse(memOut);

		nnet.getMonitor().setTotCicles(1);

		nnet.getMonitor().setLearning(false);
		nnet.go();

		FileReader arquivo;
		try {

			arquivo = new FileReader(inputFile);
			BufferedReader leitura = new BufferedReader(arquivo);
			String linha = leitura.readLine();

			while (linha != null) {
				char manter = linha.charAt(14);
				valoresValidacaoManter.add(Character.getNumericValue(manter));
				char direcao = linha.charAt(16);
				valoresValidacaoDirecao.add(Character.getNumericValue(direcao));

				linha = leitura.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < valoresValidacaoManter.size(); i++) {
			double[] pattern = memOut.getNextPattern();

			if (pattern[0] > 0.5 && valoresValidacaoManter.get(i).intValue() == 1) {
				if (pattern[1] > 0.5 && valoresValidacaoDirecao.get(i).intValue() == 1) {
					acerto += 1.0;
				} else if (pattern[1] < 0.5 && valoresValidacaoDirecao.get(i).intValue() == 0) {
					acerto += 1.0;
				}
			} else if (pattern[0] < 0.5 && valoresValidacaoManter.get(i).intValue() == 0) {
				if (pattern[1] > 0.5 && valoresValidacaoDirecao.get(i).intValue() == 1) {
					acerto += 1.0;
				} else if (pattern[1] < 0.5 && valoresValidacaoDirecao.get(i).intValue() == 0) {
					acerto += 1.0;
				}
			}

		}

		Double assertividade = (acerto / 128.0) * 100;

		System.out.println();
		System.out.println("Assertividade da Rede Neural = " + assertividade + "%");
		System.out.println();

		if (assertividade.compareTo(100.0) == 0) {
			System.out.println();
			System.out.println("Salvando Rede Neural Piercing Line e Dark Cloud...");
			System.out.println();
			saveNeuralNet(savePiercingLine, nnet);
		}

		System.out.println();
		System.out.println("Finalizando...");
		System.out.println();

		nnet.stop();

	}

	public static void realizaTreinamentoRedeNeuralMartelo(Perfil perfil) throws IOException {

		Double acerto = 0.0;

		ArrayList<Integer> valoresValidacao = new ArrayList<>();

		String inputFile = "resource\\ArquivoTreinamentoMarteloPerfil" + perfil.getNome() + ".txt";

		String outputFile = "resource\\ArquivoTreinamentoMarteloSaidaPerfil" + perfil.getNome() + ".txt";

		String saveMartelo = "resource\\RedeNeuralMarteloPerfil" + perfil.getNome() + ".snet";

		LinearLayer input = new LinearLayer();

		SigmoidLayer hidden = new SigmoidLayer();

		SigmoidLayer output = new SigmoidLayer();

		input.setLayerName("input");

		hidden.setLayerName("hidden");

		output.setLayerName("output");

		input.setRows(8);
		hidden.setRows(8);
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
		inputStream.setAdvancedColumnSelector("1,2,3,4,5,6,7,8");
		inputStream.setInputFile(new File(inputFile));

		input.addInputSynapse(inputStream);

		TeachingSynapse trainer = new TeachingSynapse();

		FileInputSynapse samples = new FileInputSynapse();

		samples.setInputFile(new File(inputFile));

		samples.setAdvancedColumnSelector("9");

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
		input.setMonitor(monitor);
		hidden.setMonitor(monitor);
		output.setMonitor(monitor);

		monitor.addNeuralNetListener(nnet);

		monitor.setTrainingPatterns(144);
		monitor.setTotCicles(10000);
		monitor.setLearning(true);
		nnet.go();

		while (nnet.isRunning()) {

		}

		MemoryOutputSynapse memOut = new MemoryOutputSynapse();

		output.removeAllOutputs();

		output.addOutputSynapse(memOut);

		nnet.getMonitor().setTotCicles(1);

		nnet.getMonitor().setLearning(false);
		nnet.go();

		FileReader arquivo;
		try {

			arquivo = new FileReader(inputFile);
			BufferedReader leitura = new BufferedReader(arquivo);
			String linha = leitura.readLine();

			while (linha != null) {
				char valor = linha.charAt(16);

				valoresValidacao.add(Character.getNumericValue(valor));

				linha = leitura.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < valoresValidacao.size(); i++) {
			double[] pattern = memOut.getNextPattern();

			if (pattern[0] > 0.5 && valoresValidacao.get(i).intValue() == 1) {
				acerto += 1.0;
			} else if (pattern[0] < 0.5 && valoresValidacao.get(i).intValue() == 0) {
				acerto += 1.0;
			}

		}

		Double assertividade = (acerto / 144.0) * 100;

		System.out.println();
		System.out.println("Assertividade da Rede Neural = " + assertividade + "%");
		System.out.println();

		if (assertividade.compareTo(100.0) == 0) {
			saveNeuralNet(saveMartelo, nnet);
		}

		nnet.stop();

	}

	private static void saveNeuralNet(String fileName, NeuralNet nnet) {
		try {

			FileOutputStream stream = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(stream);
			out.writeObject(nnet);
			out.close();

		} catch (Exception excp) {
			excp.printStackTrace();
		}
	}

	private static boolean condicaoParaCompraMartelo(Double assertividadeComPeso, Perfil perfil) {
		return assertividadeComPeso.compareTo(perfil.getValor()) == 0 || assertividadeComPeso > perfil.getValor();
	}

	private static boolean condicaoParaPiercingLine(Double assertividadeComPeso, Perfil perfil) {
		return assertividadeComPeso.compareTo(perfil.getValor()) == 0 || assertividadeComPeso > perfil.getValor();
	}

	private static boolean condicaoParaMargemSegurancaEngolfo(Double assertividadeComPeso, Perfil perfil) {
		return assertividadeComPeso.compareTo(perfil.getValor()) == 0 || assertividadeComPeso > perfil.getValor();
	}

	private static boolean condicaoParaMargemSegurancaTresSoldados(Double assertividadeComPeso, Perfil perfil) {
		return assertividadeComPeso.compareTo(perfil.getValor()) == 0 || assertividadeComPeso > perfil.getValor();
	}

	private static double calculaAssertividadeComPesoMartelo(EstatisticaMartelo estatisticaMartelo) {
		return (estatisticaMartelo.getAssertividadeGeral() + (estatisticaMartelo.getAssertividadeUltimosCincoAnos() * 2)
				+ (estatisticaMartelo.getAssertividadeUltimoAno() * 3)) / 6;
	}

	private static double calculaAssertividadeComPesoPiercingLine(EstatisticaPiercingLine estatisticaPiercingLine) {
		return (estatisticaPiercingLine.getAssertividadeGeralPrimeiroAlvo()
				+ (estatisticaPiercingLine.getAssertividadeUltimosCincoAnosPrimeiroAlvo() * 2)
				+ (estatisticaPiercingLine.getAssertividadeUltimoAnoPrimeiroAlvo() * 3)) / 6;
	}

	private static double calculaAssertividadeComPesoEngolfo(EstatisticaEngolfo estatisticaEngolfo) {
		return (estatisticaEngolfo.getAssertividadeGeralPrimeiroAlvo()
				+ (estatisticaEngolfo.getAssertividadeUltimosCincoAnosPrimeiroAlvo() * 2)
				+ (estatisticaEngolfo.getAssertividadeUltimoAnoPrimeiroAlvo() * 3)) / 6;
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

	private static boolean condicaoParaEngolfoDeBaixa(InfoCandle primeiroCandle, InfoCandle segundoCandle) {
		if (primeiroCandle != null && segundoCandle != null) {
			return segundoCandle.getFechamento() < primeiroCandle.getMinima()
					&& segundoCandle.getAbertura() > primeiroCandle.getMaxima();
		}
		return false;
	}

	private static boolean condicaoParaEngolfoDeAlta(InfoCandle primeiroCandle, InfoCandle segundoCandle) {
		if (primeiroCandle != null && segundoCandle != null) {
			return segundoCandle.getFechamento() > primeiroCandle.getMaxima()
					&& segundoCandle.getAbertura() < primeiroCandle.getMinima();
		}
		return false;
	}

	private static boolean verificaCandleParado(InfoCandle infoCandle) {
		return infoCandle.getFechamento().compareTo(infoCandle.getAbertura()) == 0
				&& infoCandle.getMaxima().compareTo(infoCandle.getAbertura()) == 0
				&& infoCandle.getMinima().compareTo(infoCandle.getAbertura()) == 0;
	}

	private static VariacaoPreco classificaVariacaoPreco(Double variacao) {

		if (variacao > 1 && variacao < 2 || variacao.compareTo(1.0) == 0) {
			return VariacaoPreco.ATE2VEZES;
		} else if (variacao > 2 && variacao < 3 || variacao.compareTo(2.0) == 0) {
			return VariacaoPreco.DE2A3VEZES;
		} else if (variacao > 3 && variacao < 4 || variacao.compareTo(3.0) == 0) {
			return VariacaoPreco.DE3A4VEZES;
		} else if (variacao > 4 && variacao < 5 || variacao.compareTo(4.0) == 0) {
			return VariacaoPreco.DE4A5VEZES;
		} else if (variacao > 5 || variacao.compareTo(5.0) == 0) {
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

		Monitor mon = (Monitor) e.getSource();

		// Imprimir o erro a cada 200 ciclos de treinamento
		if (mon.getCurrentCicle() % 200 == 0) {
			// Imprimir o ciclo de treinamento atual, bem como o erro global da rede neural
			System.out.println(mon.getCurrentCicle() + " epochs remaining - RMSE = " + mon.getGlobalError());
		}

	}

	@Override
	public void netStoppedError(NeuralNetEvent e, String error) {
		// TODO Auto-generated method stub

	}

}
