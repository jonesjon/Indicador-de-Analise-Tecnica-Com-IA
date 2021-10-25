package br.iesb.indicador_analise_grafica.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.iesb.indicador_analise_grafica.InfoCandle;
import br.iesb.indicador_analise_grafica.primary_key.InfoCandlePK;

@Repository
public interface InfoCandleRepository extends CrudRepository<InfoCandle, InfoCandlePK> {

	@Query(value = "select * from INFO_CANDLE where nomeDoPapel = ?1 order by dat asc", nativeQuery = true)
	ArrayList<InfoCandle> findByNomeDoPapel(String nomeDoPapel);

	int countByNomeDoPapel(String name);

	@Query(value = "select * from INFO_CANDLE Where nomeDoPapel = ?1 order by dat desc limit 199;", nativeQuery = true)
	ArrayList<InfoCandle> findByListForMediaMovel(String papel);
	
	@Query(value = "select distinct nomeDoPapel from INFO_CANDLE where nomeDoPapel > ?1 order by nomeDoPapel asc limit ?2", nativeQuery = true)
	ArrayList<String> findByListForAllPapeis(String nomeDoPapel, int limit);
	
	@Query(value = "select * from INFO_CANDLE where dat > ?1 and nomeDoPapel = ?2 limit 500;", nativeQuery = true)
	ArrayList<InfoCandle> findByDatAfterAndNomeDoPapel(LocalDate dat, String nomeDoPapel);

	@Query(value = "select * from INFO_CANDLE where dat < ?1 and nomeDoPapel = ?2  order by dat desc limit ?3", nativeQuery = true)
	ArrayList<InfoCandle> findByVerificaGraficoContinuo(LocalDate data, String nomeDoPapel, int limit);

	@Query(value = "select count(distinct(nomeDoPapel)) from INFO_CANDLE;", nativeQuery = true)
	int findQtdPapeis();

	@Query(value = "select * from INFO_CANDLE Where nomeDoPapel = ?1 and dat <= ?2 order by dat desc limit ?3", nativeQuery = true)
	ArrayList<InfoCandle> findByUltimosCandles(String nomeDoPapel, LocalDate dat, int limit);

}
