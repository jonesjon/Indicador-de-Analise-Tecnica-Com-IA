package br.iesb.indicador_analise_grafica.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.iesb.indicador_analise_grafica.PiercingLine;

@Repository
public interface PiercingLineRepository extends CrudRepository<PiercingLine, Long> {

}
