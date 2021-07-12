package br.iesb.indicador_analise_grafica.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.iesb.indicador_analise_grafica.InfoCandle;
import br.iesb.indicador_analise_grafica.InfoCandlePK;

@Repository
public interface InfoCandleRepository extends JpaRepository<InfoCandle, InfoCandlePK>{

	List<InfoCandle> nomeDoPapel(String nomeDoPapel);
	
}
