package br.iesb.indicador_analise_grafica.service;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.iesb.indicador_analise_grafica.InfoCandle;
import br.iesb.indicador_analise_grafica.Operacao;
import br.iesb.indicador_analise_grafica.repository.OperacaoRepository;

@Service
@Transactional
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
	
	public static ArrayList<Operacao> getOperacoesPossiveis(Double min, Double max, String nomeDoPapel){
		return  (ArrayList<Operacao>) operacaoRepository.findByOperacoesPossiveis(min, max, nomeDoPapel);
	}
	
	public static ArrayList<Operacao> getAllOperacoesPossiveis(String nomeDoPapel){
		ArrayList<Operacao> operacoes = operacaoRepository.findByOperacoes(nomeDoPapel);
		for(int i=0; i<operacoes.size(); i++) {
			operacoes.get(i).getInfoCandle();
		}
		return operacoes; 
	}
	
	public static int getQtdOperacoesPossiveis(Double min, Double max) {
		return operacaoRepository.findCountOperacoesPossiveis(min, max);
	}
	
	public static ArrayList<String> getAllPapeisOperacoesPossiveis(Double min, Double max){
		return operacaoRepository.findDistinctNomeDosPapeisOperacoesPossiveis(min, max);
	}
	
}
