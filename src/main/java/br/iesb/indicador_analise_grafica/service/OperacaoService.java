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
	
	public static ArrayList<Operacao> getOperacoes(){
		return  (ArrayList<Operacao>) operacaoRepository.findAll();
	}
	
	public static ArrayList<Operacao> getOperacoesPossiveis(Double min, Double max){
		return  (ArrayList<Operacao>) operacaoRepository.findByOperacoesPossiveis(min, max);
	}
	
	public static int getQtdOperacoes() {
		return operacaoRepository.findCountOperacao();
	}
	
}
