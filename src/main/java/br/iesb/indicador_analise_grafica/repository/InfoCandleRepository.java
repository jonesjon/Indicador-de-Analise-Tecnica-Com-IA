package br.iesb.indicador_analise_grafica.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.iesb.indicador_analise_grafica.InfoCandle;
import br.iesb.indicador_analise_grafica.InfoCandlePK;

@Repository
public interface InfoCandleRepository extends CrudRepository<InfoCandle, InfoCandlePK>{

	List<InfoCandle> nomeDoPapel(String nomeDoPapel);
	
}
