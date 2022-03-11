package br.iesb.indicador_analise_grafica.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.iesb.indicador_analise_grafica.controle.Controle;

@Repository
public interface ControleRepository extends CrudRepository<Controle, Long>{

}
