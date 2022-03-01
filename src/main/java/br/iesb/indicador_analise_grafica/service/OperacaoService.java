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
import br.iesb.indicador_analise_grafica_enum.PavioInferior;
import br.iesb.indicador_analise_grafica_enum.PavioSuperior;
import br.iesb.indicador_analise_grafica_enum.Perfuracao;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia20;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia200;
import br.iesb.indicador_analise_grafica_enum.PrecoAcimaMedia8;
import br.iesb.indicador_analise_grafica_enum.TipoCandle;
import br.iesb.indicador_analise_grafica_enum.VariacaoPreco;
import br.iesb.indicador_analise_grafica_enum.VolumeAcimaMedia20;

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

	public static Double contaPiercingLineEspecificoInciado(TipoCandle tipoCandle, VolumeAcimaMedia20 vol,
			Perfuracao perf, PrecoAcimaMedia8 preco8, PrecoAcimaMedia20 preco20, PrecoAcimaMedia200 preco200) {

		return operacaoRepository.countPiercingLineEspecificoIniciadoGeral(tipoCandle.getTipo(), vol.getValor(),
				perf.getMin(), perf.getMax(), preco8.getValor(), preco20.getValor(), preco200.getValor());

	}

	public static Double contaPiercingLineEspecificoInciadoNaoChegouAlvo(TipoCandle tipoCandle, VolumeAcimaMedia20 vol,
			Perfuracao perf, PrecoAcimaMedia8 preco8, PrecoAcimaMedia20 preco20, PrecoAcimaMedia200 preco200) {

		return operacaoRepository.countPiercingLineEspecificoIniciadoGeralNaoChegouAlvo(tipoCandle.getTipo(),
				vol.getValor(), perf.getMin(), perf.getMax(), preco8.getValor(), preco20.getValor(),
				preco200.getValor());

	}

	public static Double contaPiercingLineEspecificoInciadoUltimosCincoAnos(TipoCandle tipoCandle,
			VolumeAcimaMedia20 vol, Perfuracao perf, PrecoAcimaMedia8 preco8, PrecoAcimaMedia20 preco20,
			PrecoAcimaMedia200 preco200) {

		return operacaoRepository.countPiercingLineEspecificoIniciadoGeralUltimosCincoAnos(tipoCandle.getTipo(),
				vol.getValor(), perf.getMin(), perf.getMax(), preco8.getValor(), preco20.getValor(),
				preco200.getValor());

	}

	public static Double contaPiercingLineEspecificoInciadoUltimosCincoAnosNaoChegouAlvo(TipoCandle tipoCandle,
			VolumeAcimaMedia20 vol, Perfuracao perf, PrecoAcimaMedia8 preco8, PrecoAcimaMedia20 preco20,
			PrecoAcimaMedia200 preco200) {

		return operacaoRepository.countPiercingLineEspecificoIniciadoGeralUltimosCincoAnosNaoChegouAlvo(
				tipoCandle.getTipo(), vol.getValor(), perf.getMin(), perf.getMax(), preco8.getValor(),
				preco20.getValor(), preco200.getValor());

	}

	public static Double contaPiercingLineEspecificoInciadoUltimoAno(TipoCandle tipoCandle, VolumeAcimaMedia20 vol,
			Perfuracao perf, PrecoAcimaMedia8 preco8, PrecoAcimaMedia20 preco20, PrecoAcimaMedia200 preco200) {

		return operacaoRepository.countPiercingLineEspecificoIniciadoGeralUltimoAno(tipoCandle.getTipo(),
				vol.getValor(), perf.getMin(), perf.getMax(), preco8.getValor(), preco20.getValor(),
				preco200.getValor());

	}

	public static Double contaPiercingLineEspecificoInciadoUltimoAnoNaoChegouAlvo(TipoCandle tipoCandle,
			VolumeAcimaMedia20 vol, Perfuracao perf, PrecoAcimaMedia8 preco8, PrecoAcimaMedia20 preco20,
			PrecoAcimaMedia200 preco200) {

		return operacaoRepository.countPiercingLineEspecificoIniciadoGeralUltimoAnoNaoChegouAlvo(tipoCandle.getTipo(),
				vol.getValor(), perf.getMin(), perf.getMax(), preco8.getValor(), preco20.getValor(),
				preco200.getValor());

	}

	public static Double contaEngolfoEspecificoInciado(TipoCandle tipoCandle, PavioSuperior pavioSuperior,
			PavioInferior pavioInferior, VolumeAcimaMedia20 vol, PrecoAcimaMedia8 preco8, PrecoAcimaMedia20 preco20,
			PrecoAcimaMedia200 preco200, VariacaoPreco variacao) {

		return operacaoRepository.countEngolfoEspecificoIniciadoGeral(tipoCandle.getTipo(),
				pavioSuperior.getDescricao(), pavioInferior.getDescricao(), vol.getValor(), preco8.getValor(),
				preco20.getValor(), preco200.getValor(), variacao.getDescricao());

	}

	public static Double contaEngolfoEspecificoInciadoNaoChegouAlvo(TipoCandle tipoCandle, PavioSuperior pavioSuperior,
			PavioInferior pavioInferior, VolumeAcimaMedia20 vol, PrecoAcimaMedia8 preco8, PrecoAcimaMedia20 preco20,
			PrecoAcimaMedia200 preco200, VariacaoPreco variacao) {

		return operacaoRepository.countEngolfoEspecificoIniciadoGeralNaoChegouAlvo(tipoCandle.getTipo(),
				pavioSuperior.getDescricao(), pavioInferior.getDescricao(), vol.getValor(), preco8.getValor(),
				preco20.getValor(), preco200.getValor(), variacao.getDescricao());

	}

	public static Double contaEngolfoEspecificoInciadoUltimosCincoAnos(TipoCandle tipoCandle,
			PavioSuperior pavioSuperior, PavioInferior pavioInferior, VolumeAcimaMedia20 vol, PrecoAcimaMedia8 preco8,
			PrecoAcimaMedia20 preco20, PrecoAcimaMedia200 preco200, VariacaoPreco variacao) {

		return operacaoRepository.countEngolfoEspecificoIniciadoUltimosCincoAnos(tipoCandle.getTipo(),
				pavioSuperior.getDescricao(), pavioInferior.getDescricao(), vol.getValor(), preco8.getValor(),
				preco20.getValor(), preco200.getValor(), variacao.getDescricao());

	}

	public static Double contaEngolfoEspecificoInciadoUltimosCincoAnosNaoChegouAlvo(TipoCandle tipoCandle,
			PavioSuperior pavioSuperior, PavioInferior pavioInferior, VolumeAcimaMedia20 vol, PrecoAcimaMedia8 preco8,
			PrecoAcimaMedia20 preco20, PrecoAcimaMedia200 preco200, VariacaoPreco variacao) {

		return operacaoRepository.countEngolfoEspecificoIniciadoUltimosCincoAnosNaoChegouAlvo(tipoCandle.getTipo(),
				pavioSuperior.getDescricao(), pavioInferior.getDescricao(), vol.getValor(), preco8.getValor(),
				preco20.getValor(), preco200.getValor(), variacao.getDescricao());

	}

	public static Double contaEngolfoEspecificoInciadoUltimoAno(TipoCandle tipoCandle, PavioSuperior pavioSuperior,
			PavioInferior pavioInferior, VolumeAcimaMedia20 vol, PrecoAcimaMedia8 preco8, PrecoAcimaMedia20 preco20,
			PrecoAcimaMedia200 preco200, VariacaoPreco variacao) {

		return operacaoRepository.countEngolfoEspecificoIniciadoUltimoAno(tipoCandle.getTipo(),
				pavioSuperior.getDescricao(), pavioInferior.getDescricao(), vol.getValor(), preco8.getValor(),
				preco20.getValor(), preco200.getValor(), variacao.getDescricao());

	}

	public static Double contaEngolfoEspecificoInciadoUltimoAnoNaoChegouAlvo(TipoCandle tipoCandle,
			PavioSuperior pavioSuperior, PavioInferior pavioInferior, VolumeAcimaMedia20 vol, PrecoAcimaMedia8 preco8,
			PrecoAcimaMedia20 preco20, PrecoAcimaMedia200 preco200, VariacaoPreco variacao) {

		return operacaoRepository.countEngolfoEspecificoIniciadoUltimoAnoNaoChegouAlvo(tipoCandle.getTipo(),
				pavioSuperior.getDescricao(), pavioInferior.getDescricao(), vol.getValor(), preco8.getValor(),
				preco20.getValor(), preco200.getValor(), variacao.getDescricao());

	}

	public static Double contaTresSoldadosEspecificoIniciadoGeral(PadroesEnum padrao,
			PavioSuperior pavioSuperiorPrimeiroCandle, PavioInferior pavioInferiorPrimeiroCandle,
			PavioSuperior pavioSuperiorTerceiroCandle, PavioInferior pavioInferiorTerceiroCandle,
			PrecoAcimaMedia200 preco200) {

		return operacaoRepository.countTresSoldadosEspecificoIniciadoGeral(padrao.getDescricao(),
				pavioSuperiorPrimeiroCandle.getDescricao(), pavioInferiorPrimeiroCandle.getDescricao(),
				pavioSuperiorTerceiroCandle.getDescricao(), pavioInferiorTerceiroCandle.getDescricao(), preco200.getValor());

	}
	
	public static Double contaTresSoldadosEspecificoIniciadoGeralNaoChegouAlvo(PadroesEnum padrao,
			PavioSuperior pavioSuperiorPrimeiroCandle, PavioInferior pavioInferiorPrimeiroCandle,
			PavioSuperior pavioSuperiorTerceiroCandle, PavioInferior pavioInferiorTerceiroCandle,
			PrecoAcimaMedia200 preco200) {

		return operacaoRepository.countTresSoldadosEspecificoIniciadoGeralNaoChegouAlvo(padrao.getDescricao(),
				pavioSuperiorPrimeiroCandle.getDescricao(), pavioInferiorPrimeiroCandle.getDescricao(),
				pavioSuperiorTerceiroCandle.getDescricao(), pavioInferiorTerceiroCandle.getDescricao(), preco200.getValor());

	}
	
	public static Double contaTresSoldadosEspecificoIniciadoUltimosCincoAnos(PadroesEnum padrao,
			PavioSuperior pavioSuperiorPrimeiroCandle, PavioInferior pavioInferiorPrimeiroCandle,
			PavioSuperior pavioSuperiorTerceiroCandle, PavioInferior pavioInferiorTerceiroCandle,
			PrecoAcimaMedia200 preco200) {

		return operacaoRepository.countTresSoldadosEspecificoIniciadoUltimosCincoAnos(padrao.getDescricao(),
				pavioSuperiorPrimeiroCandle.getDescricao(), pavioInferiorPrimeiroCandle.getDescricao(),
				pavioSuperiorTerceiroCandle.getDescricao(), pavioInferiorTerceiroCandle.getDescricao(), preco200.getValor());

	}
	
	public static Double contaTresSoldadosEspecificoIniciadoUltimosCincoAnosNaoChegouAlvo(PadroesEnum padrao,
			PavioSuperior pavioSuperiorPrimeiroCandle, PavioInferior pavioInferiorPrimeiroCandle,
			PavioSuperior pavioSuperiorTerceiroCandle, PavioInferior pavioInferiorTerceiroCandle,
			PrecoAcimaMedia200 preco200) {

		return operacaoRepository.countTresSoldadosEspecificoIniciadoUltimosCincoAnosNaoChegouAlvo(padrao.getDescricao(),
				pavioSuperiorPrimeiroCandle.getDescricao(), pavioInferiorPrimeiroCandle.getDescricao(),
				pavioSuperiorTerceiroCandle.getDescricao(), pavioInferiorTerceiroCandle.getDescricao(), preco200.getValor());

	}
	
	public static Double contaTresSoldadosEspecificoIniciadoUltimoAno(PadroesEnum padrao,
			PavioSuperior pavioSuperiorPrimeiroCandle, PavioInferior pavioInferiorPrimeiroCandle,
			PavioSuperior pavioSuperiorTerceiroCandle, PavioInferior pavioInferiorTerceiroCandle,
			PrecoAcimaMedia200 preco200) {

		return operacaoRepository.countTresSoldadosEspecificoIniciadoUltimoAno(padrao.getDescricao(),
				pavioSuperiorPrimeiroCandle.getDescricao(), pavioInferiorPrimeiroCandle.getDescricao(),
				pavioSuperiorTerceiroCandle.getDescricao(), pavioInferiorTerceiroCandle.getDescricao(), preco200.getValor());

	}
	
	public static Double contaTresSoldadosEspecificoIniciadoUltimoAnoNaoChegouAlvo(PadroesEnum padrao,
			PavioSuperior pavioSuperiorPrimeiroCandle, PavioInferior pavioInferiorPrimeiroCandle,
			PavioSuperior pavioSuperiorTerceiroCandle, PavioInferior pavioInferiorTerceiroCandle,
			PrecoAcimaMedia200 preco200) {

		return operacaoRepository.countTresSoldadosEspecificoIniciadoUltimoAnoNaoChegouAlvo(padrao.getDescricao(),
				pavioSuperiorPrimeiroCandle.getDescricao(), pavioInferiorPrimeiroCandle.getDescricao(),
				pavioSuperiorTerceiroCandle.getDescricao(), pavioInferiorTerceiroCandle.getDescricao(), preco200.getValor());

	}

}
