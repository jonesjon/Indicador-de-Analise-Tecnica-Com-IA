package br.iesb.indicador_analise_grafica.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.iesb.indicador_analise_grafica.InfoCandle;
import br.iesb.indicador_analise_grafica.primary_key.InfoCandlePK;

@Repository
public interface InfoCandleRepository extends CrudRepository<InfoCandle, InfoCandlePK> {

	ArrayList<InfoCandle> findByNomeDoPapel(String nomeDoPapel);

	int countByNomeDoPapel(String name);

	@Query(value = "select * from INFO_CANDLE Where nomeDoPapel = ?1 order by dat desc limit 199;", nativeQuery = true)
	ArrayList<InfoCandle> findByListForMediaMovel(String papel);
	
	@Query(value = "select distinct nomeDoPapel from INFO_CANDLE;", nativeQuery = true)
	ArrayList<String> findByListForAllPapeis();

//	@Query(name = "", nativeQuery = true)
//	List<InfoCandle> findByNomeDoPapel1(String nomeDoPapel);

}
