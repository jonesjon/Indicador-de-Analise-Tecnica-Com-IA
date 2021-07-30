package br.iesb.indicador_analise_grafica.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.iesb.indicador_analise_grafica.Operacao;
import br.iesb.indicador_analise_grafica.primary_key.OperacaoPK;

@Repository
public interface OperacaoRepository extends CrudRepository<Operacao, OperacaoPK> {
	
	

}
