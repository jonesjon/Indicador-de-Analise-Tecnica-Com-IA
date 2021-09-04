package br.iesb.indicador_analise_grafica.service;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.iesb.indicador_analise_grafica.Operacao;
import br.iesb.indicador_analise_grafica.repository.OperacaoRepository;

@Service
@Transactional
public class OperacaoService {
	
	@Autowired
	private OperacaoRepository operacaoRepository;
	
	public void adicionaOperacao(Operacao operacao) {
		operacaoRepository.save(operacao);
	}
	
	public ArrayList<Operacao> getOperacoes(){
		return  (ArrayList<Operacao>) operacaoRepository.findAll();
	}
	
	public ArrayList<Operacao> getOperacoesPossiveis(Double min, Double max, String nomeDoPapel){
		return  (ArrayList<Operacao>) operacaoRepository.findByOperacoesPossiveis(min, max, nomeDoPapel);
	}
	
	@Transactional
	public ArrayList<Operacao> getAllOperacoesPossiveis(String nomeDoPapel){
		ArrayList<Operacao> operacoes = operacaoRepository.findByOperacoes(nomeDoPapel);
		
		for(int i=0; i<operacoes.size(); i++) {
			System.out.println(operacoes.get(i).getInfoCandle());
		}
		
		return operacoes; 
	}
	
	public int getQtdOperacoesPossiveis(Double min, Double max) {
		return operacaoRepository.findCountOperacoesPossiveis(min, max);
	}
	
	public ArrayList<String> getAllPapeisOperacoesPossiveis(Double min, Double max){
		return operacaoRepository.findDistinctNomeDosPapeisOperacoesPossiveis(min, max);
	}
	
}
