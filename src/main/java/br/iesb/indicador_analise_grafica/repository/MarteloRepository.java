package br.iesb.indicador_analise_grafica.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.iesb.indicador_analise_grafica.padroes.Martelo;

@Repository
public interface MarteloRepository extends CrudRepository<Martelo, Long> {
	
	@Query(value = "select * from MARTELO where dat >= '2021-01-01'", nativeQuery = true)
	ArrayList<Martelo> getMartelosUltimoAno();

}
