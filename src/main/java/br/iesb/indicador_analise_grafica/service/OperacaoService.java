package br.iesb.indicador_analise_grafica.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.iesb.indicador_analise_grafica.Operacao;
import br.iesb.indicador_analise_grafica.repository.OperacaoRepository;
import br.iesb.indicador_analise_grafica_enum.PadroesEnum;
import br.iesb.indicador_analise_grafica_enum.PavioInferiorEnum;
import br.iesb.indicador_analise_grafica_enum.PavioSuperiorEnum;
import br.iesb.indicador_analise_grafica_enum.PerfuracaoEnum;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia20Enum;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia200Enum;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia8Enum;
import br.iesb.indicador_analise_grafica_enum.TipoCandleEnum;
import br.iesb.indicador_analise_grafica_enum.VariacaoPrecoEnum;
import br.iesb.indicador_analise_grafica_enum.VolumeAcimaMedia20Enum;

@Service
public class OperacaoService {

	@Autowired
	private OperacaoRepository operacao;
	private static OperacaoRepository operacaoRepository;

	@PostConstruct
	public void getOperacaoRepository() {
		operacaoRepository = operacao;
	}
	
	public static List<Operacao> operacoesPorAno(LocalDate data){
		return operacaoRepository.findByDatGreaterThanEqual(data);//  operacoesPorAno(data);
	}

	public static void adicionaOperacao(Operacao operacao) {
		operacaoRepository.save(operacao);
	}

	public static ArrayList<Operacao> getOperacoes() {
		return (ArrayList<Operacao>) operacaoRepository.findAll();
	}

	public static ArrayList<Operacao> getOperacoesPossiveis(Double min, Double max, String nomeDoPapel) {
		return (ArrayList<Operacao>) operacaoRepository.findByOperacoesPossiveis(min, max, nomeDoPapel);
	}

	public static int getQtdOperacoesPossiveis(Double min, Double max) {
		return operacaoRepository.findCountOperacoesPossiveis(min, max);
	}

	public static ArrayList<String> getAllPapeisOperacoesPossiveis(Double min, Double max) {
		return operacaoRepository.findDistinctNomeDosPapeisOperacoesPossiveis(min, max);
	}

	public static Double contaMarteloEspecificoInciado(String tipo, String pavioSuperior, String pavioInferior,
			int volumeAcimaMedia20, int marteloAcimaMedia200) {

		return operacaoRepository.countMarteloEspecificoIniciadoGeral(tipo, pavioSuperior, pavioInferior,
				volumeAcimaMedia20, marteloAcimaMedia200);
	}

	public static Double contaMarteloEspecificoInciadoNaoChegouAlvo(String tipo, String pavioSuperior,
			String pavioInferior, int volumeAcimaMedia20, int marteloAcimaMedia200) {

		return operacaoRepository.countMarteloEspecificoIniciadoGeralNaoChegouAlvo(tipo, pavioSuperior, pavioInferior,
				volumeAcimaMedia20, marteloAcimaMedia200);
	}

	public static Double contaMarteloEspecificoInciadoUltimosCincoAnos(String tipo, String pavioSuperior,
			String pavioInferior, int volumeAcimaMedia20, int marteloAcimaMedia200) {

		return operacaoRepository.countMarteloEspecificoIniciadoUltimosCincoAnos(tipo, pavioSuperior, pavioInferior,
				volumeAcimaMedia20, marteloAcimaMedia200);
	}

	public static Double contaMarteloEspecificoInciadoUltimosCincoAnosNaoChegouAlvo(String tipo, String pavioSuperior,
			String pavioInferior, int volumeAcimaMedia20, int marteloAcimaMedia200) {

		return operacaoRepository.countMarteloEspecificoIniciadoUltimosCincoAnosNaoChegouAlvo(tipo, pavioSuperior,
				pavioInferior, volumeAcimaMedia20, marteloAcimaMedia200);
	}

	public static Double contaMarteloEspecificoInciadoUltimoAno(String tipo, String pavioSuperior, String pavioInferior,
			int volumeAcimaMedia20, int marteloAcimaMedia200) {

		return operacaoRepository.countMarteloEspecificoIniciadoUltimoAno(tipo, pavioSuperior, pavioInferior,
				volumeAcimaMedia20, marteloAcimaMedia200);
	}

	public static Double contaMarteloEspecificoInciadoUltimoAnoNaoChegouAlvo(String tipo, String pavioSuperior,
			String pavioInferior, int volumeAcimaMedia20, int marteloAcimaMedia200) {

		return operacaoRepository.countMarteloEspecificoIniciadoUltimoAnoNaoChegouAlvo(tipo, pavioSuperior,
				pavioInferior, volumeAcimaMedia20, marteloAcimaMedia200);
	}

	public static ArrayList<Operacao> getOperacoesUltimoAno(Double min, Double max, LocalDate data) {
		return operacaoRepository.findByOperacoesPossiveisUltimoAno(min, max, data);
	}

	public static Double contaPiercingLineEspecificoInciado(TipoCandleEnum tipoCandle, VolumeAcimaMedia20Enum vol,
			PerfuracaoEnum perf, PrecoAcimaMedia8Enum preco8, PrecoAcimaMedia20Enum preco20, PrecoAcimaMedia200Enum preco200) {

		return operacaoRepository.countPiercingLineEspecificoIniciadoGeral(tipoCandle.getTipo(), vol.getValor(),
				perf.getMin(), perf.getMax(), preco8.getValor(), preco20.getValor(), preco200.getValor());

	}

	public static Double contaPiercingLineEspecificoInciadoNaoChegouAlvo(TipoCandleEnum tipoCandle, VolumeAcimaMedia20Enum vol,
			PerfuracaoEnum perf, PrecoAcimaMedia8Enum preco8, PrecoAcimaMedia20Enum preco20, PrecoAcimaMedia200Enum preco200) {

		return operacaoRepository.countPiercingLineEspecificoIniciadoGeralNaoChegouAlvo(tipoCandle.getTipo(),
				vol.getValor(), perf.getMin(), perf.getMax(), preco8.getValor(), preco20.getValor(),
				preco200.getValor());

	}

	public static Double contaPiercingLineEspecificoInciadoUltimosCincoAnos(TipoCandleEnum tipoCandle,
			VolumeAcimaMedia20Enum vol, PerfuracaoEnum perf, PrecoAcimaMedia8Enum preco8, PrecoAcimaMedia20Enum preco20,
			PrecoAcimaMedia200Enum preco200) {

		return operacaoRepository.countPiercingLineEspecificoIniciadoGeralUltimosCincoAnos(tipoCandle.getTipo(),
				vol.getValor(), perf.getMin(), perf.getMax(), preco8.getValor(), preco20.getValor(),
				preco200.getValor());

	}

	public static Double contaPiercingLineEspecificoInciadoUltimosCincoAnosNaoChegouAlvo(TipoCandleEnum tipoCandle,
			VolumeAcimaMedia20Enum vol, PerfuracaoEnum perf, PrecoAcimaMedia8Enum preco8, PrecoAcimaMedia20Enum preco20,
			PrecoAcimaMedia200Enum preco200) {

		return operacaoRepository.countPiercingLineEspecificoIniciadoGeralUltimosCincoAnosNaoChegouAlvo(
				tipoCandle.getTipo(), vol.getValor(), perf.getMin(), perf.getMax(), preco8.getValor(),
				preco20.getValor(), preco200.getValor());

	}

	public static Double contaPiercingLineEspecificoInciadoUltimoAno(TipoCandleEnum tipoCandle, VolumeAcimaMedia20Enum vol,
			PerfuracaoEnum perf, PrecoAcimaMedia8Enum preco8, PrecoAcimaMedia20Enum preco20, PrecoAcimaMedia200Enum preco200) {

		return operacaoRepository.countPiercingLineEspecificoIniciadoGeralUltimoAno(tipoCandle.getTipo(),
				vol.getValor(), perf.getMin(), perf.getMax(), preco8.getValor(), preco20.getValor(),
				preco200.getValor());

	}

	public static Double contaPiercingLineEspecificoInciadoUltimoAnoNaoChegouAlvo(TipoCandleEnum tipoCandle,
			VolumeAcimaMedia20Enum vol, PerfuracaoEnum perf, PrecoAcimaMedia8Enum preco8, PrecoAcimaMedia20Enum preco20,
			PrecoAcimaMedia200Enum preco200) {

		return operacaoRepository.countPiercingLineEspecificoIniciadoGeralUltimoAnoNaoChegouAlvo(tipoCandle.getTipo(),
				vol.getValor(), perf.getMin(), perf.getMax(), preco8.getValor(), preco20.getValor(),
				preco200.getValor());

	}

	public static Double contaEngolfoEspecificoInciado(TipoCandleEnum tipoCandle, PavioSuperiorEnum pavioSuperior,
			PavioInferiorEnum pavioInferior, VolumeAcimaMedia20Enum vol, PrecoAcimaMedia8Enum preco8, PrecoAcimaMedia20Enum preco20,
			PrecoAcimaMedia200Enum preco200, VariacaoPrecoEnum variacao) {

		return operacaoRepository.countEngolfoEspecificoIniciadoGeral(tipoCandle.getTipo(),
				pavioSuperior.getDescricao(), pavioInferior.getDescricao(), vol.getValor(), preco8.getValor(),
				preco20.getValor(), preco200.getValor(), variacao.getDescricao());

	}

	public static Double contaEngolfoEspecificoInciadoNaoChegouAlvo(TipoCandleEnum tipoCandle, PavioSuperiorEnum pavioSuperior,
			PavioInferiorEnum pavioInferior, VolumeAcimaMedia20Enum vol, PrecoAcimaMedia8Enum preco8, PrecoAcimaMedia20Enum preco20,
			PrecoAcimaMedia200Enum preco200, VariacaoPrecoEnum variacao) {

		return operacaoRepository.countEngolfoEspecificoIniciadoGeralNaoChegouAlvo(tipoCandle.getTipo(),
				pavioSuperior.getDescricao(), pavioInferior.getDescricao(), vol.getValor(), preco8.getValor(),
				preco20.getValor(), preco200.getValor(), variacao.getDescricao());

	}

	public static Double contaEngolfoEspecificoInciadoUltimosCincoAnos(TipoCandleEnum tipoCandle,
			PavioSuperiorEnum pavioSuperior, PavioInferiorEnum pavioInferior, VolumeAcimaMedia20Enum vol, PrecoAcimaMedia8Enum preco8,
			PrecoAcimaMedia20Enum preco20, PrecoAcimaMedia200Enum preco200, VariacaoPrecoEnum variacao) {

		return operacaoRepository.countEngolfoEspecificoIniciadoUltimosCincoAnos(tipoCandle.getTipo(),
				pavioSuperior.getDescricao(), pavioInferior.getDescricao(), vol.getValor(), preco8.getValor(),
				preco20.getValor(), preco200.getValor(), variacao.getDescricao());

	}

	public static Double contaEngolfoEspecificoInciadoUltimosCincoAnosNaoChegouAlvo(TipoCandleEnum tipoCandle,
			PavioSuperiorEnum pavioSuperior, PavioInferiorEnum pavioInferior, VolumeAcimaMedia20Enum vol, PrecoAcimaMedia8Enum preco8,
			PrecoAcimaMedia20Enum preco20, PrecoAcimaMedia200Enum preco200, VariacaoPrecoEnum variacao) {

		return operacaoRepository.countEngolfoEspecificoIniciadoUltimosCincoAnosNaoChegouAlvo(tipoCandle.getTipo(),
				pavioSuperior.getDescricao(), pavioInferior.getDescricao(), vol.getValor(), preco8.getValor(),
				preco20.getValor(), preco200.getValor(), variacao.getDescricao());

	}

	public static Double contaEngolfoEspecificoInciadoUltimoAno(TipoCandleEnum tipoCandle, PavioSuperiorEnum pavioSuperior,
			PavioInferiorEnum pavioInferior, VolumeAcimaMedia20Enum vol, PrecoAcimaMedia8Enum preco8, PrecoAcimaMedia20Enum preco20,
			PrecoAcimaMedia200Enum preco200, VariacaoPrecoEnum variacao) {

		return operacaoRepository.countEngolfoEspecificoIniciadoUltimoAno(tipoCandle.getTipo(),
				pavioSuperior.getDescricao(), pavioInferior.getDescricao(), vol.getValor(), preco8.getValor(),
				preco20.getValor(), preco200.getValor(), variacao.getDescricao());

	}

	public static Double contaEngolfoEspecificoInciadoUltimoAnoNaoChegouAlvo(TipoCandleEnum tipoCandle,
			PavioSuperiorEnum pavioSuperior, PavioInferiorEnum pavioInferior, VolumeAcimaMedia20Enum vol, PrecoAcimaMedia8Enum preco8,
			PrecoAcimaMedia20Enum preco20, PrecoAcimaMedia200Enum preco200, VariacaoPrecoEnum variacao) {

		return operacaoRepository.countEngolfoEspecificoIniciadoUltimoAnoNaoChegouAlvo(tipoCandle.getTipo(),
				pavioSuperior.getDescricao(), pavioInferior.getDescricao(), vol.getValor(), preco8.getValor(),
				preco20.getValor(), preco200.getValor(), variacao.getDescricao());

	}

	public static Double contaTresSoldadosEspecificoIniciadoGeral(PadroesEnum padrao,
			PavioSuperiorEnum pavioSuperiorPrimeiroCandle, PavioInferiorEnum pavioInferiorPrimeiroCandle,
			PavioSuperiorEnum pavioSuperiorTerceiroCandle, PavioInferiorEnum pavioInferiorTerceiroCandle,
			PrecoAcimaMedia200Enum preco200) {

		return operacaoRepository.countTresSoldadosEspecificoIniciadoGeral(padrao.getDescricao(),
				pavioSuperiorPrimeiroCandle.getDescricao(), pavioInferiorPrimeiroCandle.getDescricao(),
				pavioSuperiorTerceiroCandle.getDescricao(), pavioInferiorTerceiroCandle.getDescricao(), preco200.getValor());

	}
	
	public static Double contaTresSoldadosEspecificoIniciadoGeralNaoChegouAlvo(PadroesEnum padrao,
			PavioSuperiorEnum pavioSuperiorPrimeiroCandle, PavioInferiorEnum pavioInferiorPrimeiroCandle,
			PavioSuperiorEnum pavioSuperiorTerceiroCandle, PavioInferiorEnum pavioInferiorTerceiroCandle,
			PrecoAcimaMedia200Enum preco200) {

		return operacaoRepository.countTresSoldadosEspecificoIniciadoGeralNaoChegouAlvo(padrao.getDescricao(),
				pavioSuperiorPrimeiroCandle.getDescricao(), pavioInferiorPrimeiroCandle.getDescricao(),
				pavioSuperiorTerceiroCandle.getDescricao(), pavioInferiorTerceiroCandle.getDescricao(), preco200.getValor());

	}
	
	public static Double contaTresSoldadosEspecificoIniciadoUltimosCincoAnos(PadroesEnum padrao,
			PavioSuperiorEnum pavioSuperiorPrimeiroCandle, PavioInferiorEnum pavioInferiorPrimeiroCandle,
			PavioSuperiorEnum pavioSuperiorTerceiroCandle, PavioInferiorEnum pavioInferiorTerceiroCandle,
			PrecoAcimaMedia200Enum preco200) {

		return operacaoRepository.countTresSoldadosEspecificoIniciadoUltimosCincoAnos(padrao.getDescricao(),
				pavioSuperiorPrimeiroCandle.getDescricao(), pavioInferiorPrimeiroCandle.getDescricao(),
				pavioSuperiorTerceiroCandle.getDescricao(), pavioInferiorTerceiroCandle.getDescricao(), preco200.getValor());

	}
	
	public static Double contaTresSoldadosEspecificoIniciadoUltimosCincoAnosNaoChegouAlvo(PadroesEnum padrao,
			PavioSuperiorEnum pavioSuperiorPrimeiroCandle, PavioInferiorEnum pavioInferiorPrimeiroCandle,
			PavioSuperiorEnum pavioSuperiorTerceiroCandle, PavioInferiorEnum pavioInferiorTerceiroCandle,
			PrecoAcimaMedia200Enum preco200) {

		return operacaoRepository.countTresSoldadosEspecificoIniciadoUltimosCincoAnosNaoChegouAlvo(padrao.getDescricao(),
				pavioSuperiorPrimeiroCandle.getDescricao(), pavioInferiorPrimeiroCandle.getDescricao(),
				pavioSuperiorTerceiroCandle.getDescricao(), pavioInferiorTerceiroCandle.getDescricao(), preco200.getValor());

	}
	
	public static Double contaTresSoldadosEspecificoIniciadoUltimoAno(PadroesEnum padrao,
			PavioSuperiorEnum pavioSuperiorPrimeiroCandle, PavioInferiorEnum pavioInferiorPrimeiroCandle,
			PavioSuperiorEnum pavioSuperiorTerceiroCandle, PavioInferiorEnum pavioInferiorTerceiroCandle,
			PrecoAcimaMedia200Enum preco200) {

		return operacaoRepository.countTresSoldadosEspecificoIniciadoUltimoAno(padrao.getDescricao(),
				pavioSuperiorPrimeiroCandle.getDescricao(), pavioInferiorPrimeiroCandle.getDescricao(),
				pavioSuperiorTerceiroCandle.getDescricao(), pavioInferiorTerceiroCandle.getDescricao(), preco200.getValor());

	}
	
	public static Double contaTresSoldadosEspecificoIniciadoUltimoAnoNaoChegouAlvo(PadroesEnum padrao,
			PavioSuperiorEnum pavioSuperiorPrimeiroCandle, PavioInferiorEnum pavioInferiorPrimeiroCandle,
			PavioSuperiorEnum pavioSuperiorTerceiroCandle, PavioInferiorEnum pavioInferiorTerceiroCandle,
			PrecoAcimaMedia200Enum preco200) {

		return operacaoRepository.countTresSoldadosEspecificoIniciadoUltimoAnoNaoChegouAlvo(padrao.getDescricao(),
				pavioSuperiorPrimeiroCandle.getDescricao(), pavioInferiorPrimeiroCandle.getDescricao(),
				pavioSuperiorTerceiroCandle.getDescricao(), pavioInferiorTerceiroCandle.getDescricao(), preco200.getValor());

	}

}
