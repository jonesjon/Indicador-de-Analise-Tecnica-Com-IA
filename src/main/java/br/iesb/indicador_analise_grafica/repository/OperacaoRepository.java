package br.iesb.indicador_analise_grafica.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.iesb.indicador_analise_grafica.Operacao;

@Repository
public interface OperacaoRepository extends CrudRepository<Operacao, Long> {

	@Query(value = "select * from OPERACAO where precoEntrada > ?1 and precoEntrada < ?2 and nomeDoPapel = ?3 order by dat asc", nativeQuery = true)
	ArrayList<Operacao> findByOperacoesPossiveis(Double min, Double max, String nomeDoPapel);
	
	@Query(value = "select * FROM OPERACAO where o.nomeDoPapel = ?1", nativeQuery = true)
	ArrayList<Operacao> findByOperacoes(String nomeDoPapel);
	
	@Query(value = "select count(*) from OPERACAO where precoEntrada > ?1 and precoEntrada < ?2", nativeQuery = true)
	int findCountOperacoesPossiveis(Double min, Double max);
	
	@Query(value = "select distinct(nomeDoPapel) from OPERACAO where precoEntrada > ?1 and precoEntrada < ?2", nativeQuery = true)
	ArrayList<String> findDistinctNomeDosPapeisOperacoesPossiveis(Double min, Double max);
	

	
	
	
}
