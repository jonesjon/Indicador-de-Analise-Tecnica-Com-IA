package br.iesb.indicador_analise_grafica.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.iesb.indicador_analise_grafica.Doji;


@Repository
public interface DojiRepository extends CrudRepository<Doji, Long>{


}
