package br.iesb.indicador_analise_grafica.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.iesb.indicador_analise_grafica.Martelo;
import br.iesb.indicador_analise_grafica.primary_key.MarteloPK;

@Repository
public interface MarteloRepository extends CrudRepository<Martelo, MarteloPK> {
	
	

}
