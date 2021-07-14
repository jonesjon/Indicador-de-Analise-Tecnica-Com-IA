package br.iesb.indicador_analise_grafica.service;

import br.iesb.indicador_analise_grafica.model.Teste;

public interface ITesteService {

	Teste getTesteById(Long id);
	 
	/*
    Teste getBookByDescription(String descricao);
 
    Teste saveBook(Teste b);
     
    void deleteBook(Teste b);*/
}
