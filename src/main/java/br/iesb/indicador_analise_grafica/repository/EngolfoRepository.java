package br.iesb.indicador_analise_grafica.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.iesb.indicador_analise_grafica.padroes.Engolfo;

@Repository
public interface EngolfoRepository extends CrudRepository<Engolfo, Long>{

	
	
}
