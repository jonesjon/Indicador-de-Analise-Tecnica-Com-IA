package br.iesb.indicador_analise_grafica.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.iesb.indicador_analise_grafica.padroes.Marubozu;
import br.iesb.indicador_analise_grafica.primary_key.MarubozuPK;


@Repository
public interface MarubozuRepository extends CrudRepository<Marubozu, MarubozuPK> {
	

}
