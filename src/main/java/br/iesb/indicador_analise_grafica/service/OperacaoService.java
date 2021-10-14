package br.iesb.indicador_analise_grafica.service;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.iesb.indicador_analise_grafica.Operacao;
import br.iesb.indicador_analise_grafica.repository.OperacaoRepository;

@Service
public class OperacaoService {

	@Autowired
	private OperacaoRepository operacao;
	private static OperacaoRepository operacaoRepository;

	@PostConstruct
	public void getOperacaoRepository() {
		operacaoRepository = operacao;
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
	
	public static Double contaMarteloEspecificoInciadoNaoChegouAlvo(String tipo, String pavioSuperior, String pavioInferior,
			int volumeAcimaMedia20, int marteloAcimaMedia200) {

		return operacaoRepository.countMarteloEspecificoIniciadoGeralNaoChegouAlvo(tipo, pavioSuperior, pavioInferior,
				volumeAcimaMedia20, marteloAcimaMedia200);
	}
	
	public static Double contaMarteloEspecificoInciadoUltimosCincoAnos(String tipo, String pavioSuperior, String pavioInferior,
			int volumeAcimaMedia20, int marteloAcimaMedia200) {

		return operacaoRepository.countMarteloEspecificoIniciadoUltimosCincoAnos(tipo, pavioSuperior, pavioInferior,
				volumeAcimaMedia20, marteloAcimaMedia200);
	}
	
	public static Double contaMarteloEspecificoInciadoUltimosCincoAnosNaoChegouAlvo(String tipo, String pavioSuperior, String pavioInferior,
			int volumeAcimaMedia20, int marteloAcimaMedia200) {

		return operacaoRepository.countMarteloEspecificoIniciadoUltimosCincoAnosNaoChegouAlvo(tipo, pavioSuperior, pavioInferior,
				volumeAcimaMedia20, marteloAcimaMedia200);
	}
	
	public static Double contaMarteloEspecificoInciadoUltimoAno(String tipo, String pavioSuperior, String pavioInferior,
			int volumeAcimaMedia20, int marteloAcimaMedia200) {

		return operacaoRepository.countMarteloEspecificoIniciadoUltimoAno(tipo, pavioSuperior, pavioInferior,
				volumeAcimaMedia20, marteloAcimaMedia200);
	}
	
	public static Double contaMarteloEspecificoInciadoUltimoAnoNaoChegouAlvo(String tipo, String pavioSuperior, String pavioInferior,
			int volumeAcimaMedia20, int marteloAcimaMedia200) {

		return operacaoRepository.countMarteloEspecificoIniciadoUltimoAnoNaoChegouAlvo(tipo, pavioSuperior, pavioInferior,
				volumeAcimaMedia20, marteloAcimaMedia200);
	}

}
