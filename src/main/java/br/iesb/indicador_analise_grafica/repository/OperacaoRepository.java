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

	@Query(value = "select * from OPERACAO where precoEntrada > ?1 and precoEntrada < ?2 and nomeDoPapel = ?3 order by dat asc", nativeQuery = true)
	ArrayList<Operacao> findByOperacoesPossiveis(Double min, Double max, String nomeDoPapel);
	
	@Query(value = "select count(*) from OPERACAO where precoEntrada > ?1 and precoEntrada < ?2", nativeQuery = true)
	int findCountOperacoesPossiveis(Double min, Double max);
	
	@Query(value = "select distinct(nomeDoPapel) from OPERACAO where precoEntrada > ?1 and precoEntrada < ?2", nativeQuery = true)
	ArrayList<String> findDistinctNomeDosPapeisOperacoesPossiveis(Double min, Double max);
	
	@Query(value = "select count(*) from OPERACAO o inner join MARTELO m "
						+ "on o.nomeDoPapel = m.nomeDopapel and  o.dat = m.dat and o.padrao = m.padrao "
							+ "where o.start = 1 and tipo = ?1 and pavioSuperior = ?2 and pavioInferior = ?3 "
								+ "and volumeAcimaMedia20 = ?4 and marteloAcimaMedia200 = ?5 and o.dat < 2021-01-01", nativeQuery = true)
	int countMarteloEspecificoIniciadoGeral(String tipo, String pavioSuperior, String pavioInferior, String volumeAcimaMedia20, String marteloAcimaMedia200);
	
	
	
	
	
}
