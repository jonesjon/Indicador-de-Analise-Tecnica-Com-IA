package br.iesb.indicador_analise_grafica.model;


import org.springframework.data.repository.CrudRepository;

public interface TesteRepository extends CrudRepository<Teste, Long> {
	
	Teste getTestById(Long id);
	
	
}

/*
public interface ITesteRepository {

	Teste getBookById(Long id);
	 
    Teste getBookByDescription(String descricao);
 
    Teste saveBook(Teste b);
     
    void deleteBook(Teste b);
}*/
