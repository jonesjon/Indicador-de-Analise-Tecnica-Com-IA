package br.iesb.indicador_analise_grafica.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.iesb.indicador_analise_grafica.TresSoldados;

@Repository
public interface TresSoldadosRepository extends CrudRepository<TresSoldados, Long>{

}
