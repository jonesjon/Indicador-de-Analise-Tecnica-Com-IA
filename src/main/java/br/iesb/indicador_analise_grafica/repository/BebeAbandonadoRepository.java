package br.iesb.indicador_analise_grafica.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.iesb.indicador_analise_grafica.BebeAbandonado;

@Repository
public interface BebeAbandonadoRepository extends CrudRepository<BebeAbandonado, Long>{

}
