package br.iesb.indicador_analise_grafica.repository;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.iesb.indicador_analise_grafica.Operacao;
import br.iesb.indicador_analise_grafica.primary_key.OperacaoPK;

@Repository
public interface OperacaoRepository extends CrudRepository<Operacao, OperacaoPK> {

	@Query(value = "select * from OPERACAO where precoEntrada > ?1 and precoEntrada < ?2", nativeQuery = true)
	ArrayList<Operacao> findByOperacoesPossiveis(Double min, Double max);
	
	@Query(value = "select count(*) from OPERACAO;", nativeQuery = true)
	int findCountOperacao();

	
	
	
}
