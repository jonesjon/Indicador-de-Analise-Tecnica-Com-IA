package br.iesb.indicador_analise_grafica.repository;

import br.iesb.indicador_analise_grafica.model.Teste;

public interface ITesteRepository {

	Teste getBookById(Long id);
	 
    Teste getBookByDescription(String descricao);
 
    Teste saveBook(Teste b);
     
    void deleteBook(Teste b);
}
