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

	@Query(value = "select * from OPERACAO where precoEntrada > ?1 and precoEntrada < ?2 and dat >= ?3 order by dat asc", nativeQuery = true)
	ArrayList<Operacao> findByOperacoesPossiveisUltimoAno(Double min, Double max, LocalDate data);

	@Query(value = "select count(*) from OPERACAO where precoEntrada > ?1 and precoEntrada < ?2", nativeQuery = true)
	int findCountOperacoesPossiveis(Double min, Double max);

	@Query(value = "select distinct(nomeDoPapel) from OPERACAO where precoEntrada > ?1 and precoEntrada < ?2", nativeQuery = true)
	ArrayList<String> findDistinctNomeDosPapeisOperacoesPossiveis(Double min, Double max);

	@Query(value = "select count(*) from OPERACAO o inner join PIERCING_LINE p "
			+ "on o.nomeDoPapel = p.nomeDopapel and  o.dat = p.dat and o.padrao = p.padrao "
			+ "where start = 1 and p.tipo = ?1 and volumeAcimaMedia20 = ?2 "
			+ "and perfuracao >= ?3 and perfuracao < ?4 and precoAcimaMedia8 = ?5 and precoAcimaMedia20 = ?6 "
			+ "and precoAcimaMedia200 = ?7 and o.dat < '2021-01-01'", nativeQuery = true)
	Double countPiercingLineEspecificoIniciadoGeral(String tipo, int volumeAcimaMedia20, int perfMinima, int perfMaxima,
			int precoAcimaMedia8, int precoAcimaMedia20, int precoAcimaMedia200);
	
	@Query(value = "select count(*) from OPERACAO o inner join PIERCING_LINE p "
			+ "on o.nomeDoPapel = p.nomeDopapel and  o.dat = p.dat and o.padrao = p.padrao "
			+ "where start = 1 and primeiroAlvoAtingido = 0 and p.tipo = ?1 and volumeAcimaMedia20 = ?2 "
			+ "and perfuracao >= ?3 and perfuracao < ?4 and precoAcimaMedia8 = ?5 and precoAcimaMedia20 = ?6 "
			+ "and precoAcimaMedia200 = ?7 and o.dat < '2021-01-01'", nativeQuery = true)
	Double countPiercingLineEspecificoIniciadoGeralNaoChegouAlvo(String tipo, int volumeAcimaMedia20, int perfMinima, int perfMaxima,
			int precoAcimaMedia8, int precoAcimaMedia20, int precoAcimaMedia200);
	
	@Query(value = "select count(*) from OPERACAO o inner join PIERCING_LINE p "
			+ "on o.nomeDoPapel = p.nomeDopapel and  o.dat = p.dat and o.padrao = p.padrao "
			+ "where start = 1 and p.tipo = ?1 and volumeAcimaMedia20 = ?2 "
			+ "and perfuracao >= ?3 and perfuracao < ?4 and precoAcimaMedia8 = ?5 and precoAcimaMedia20 = ?6 "
			+ "and precoAcimaMedia200 = ?7 and o.dat < '2021-01-01' and o.dat >= '2016-01-01'", nativeQuery = true)
	Double countPiercingLineEspecificoIniciadoGeralUltimosCincoAnos(String tipo, int volumeAcimaMedia20, int perfMinima, int perfMaxima,
			int precoAcimaMedia8, int precoAcimaMedia20, int precoAcimaMedia200);
	
	@Query(value = "select count(*) from OPERACAO o inner join PIERCING_LINE p "
			+ "on o.nomeDoPapel = p.nomeDopapel and  o.dat = p.dat and o.padrao = p.padrao "
			+ "where start = 1 and primeiroAlvoAtingido = 0 and p.tipo = ?1 and volumeAcimaMedia20 = ?2 "
			+ "and perfuracao >= ?3 and perfuracao < ?4 and precoAcimaMedia8 = ?5 and precoAcimaMedia20 = ?6 "
			+ "and precoAcimaMedia200 = ?7 and o.dat < '2021-01-01' and o.dat >= '2016-01-01'", nativeQuery = true)
	Double countPiercingLineEspecificoIniciadoGeralUltimosCincoAnosNaoChegouAlvo(String tipo, int volumeAcimaMedia20, int perfMinima, int perfMaxima,
			int precoAcimaMedia8, int precoAcimaMedia20, int precoAcimaMedia200);
	
	@Query(value = "select count(*) from OPERACAO o inner join PIERCING_LINE p "
			+ "on o.nomeDoPapel = p.nomeDopapel and  o.dat = p.dat and o.padrao = p.padrao "
			+ "where start = 1 and p.tipo = ?1 and volumeAcimaMedia20 = ?2 "
			+ "and perfuracao >= ?3 and perfuracao < ?4 and precoAcimaMedia8 = ?5 and precoAcimaMedia20 = ?6 "
			+ "and precoAcimaMedia200 = ?7 and o.dat < '2021-01-01' and o.dat >= '2020-01-01'", nativeQuery = true)
	Double countPiercingLineEspecificoIniciadoGeralUltimoAno(String tipo, int volumeAcimaMedia20, int perfMinima, int perfMaxima,
			int precoAcimaMedia8, int precoAcimaMedia20, int precoAcimaMedia200);
	
	@Query(value = "select count(*) from OPERACAO o inner join PIERCING_LINE p "
			+ "on o.nomeDoPapel = p.nomeDopapel and  o.dat = p.dat and o.padrao = p.padrao "
			+ "where start = 1 and primeiroAlvoAtingido = 0 and p.tipo = ?1 and volumeAcimaMedia20 = ?2 "
			+ "and perfuracao >= ?3 and perfuracao < ?4 and precoAcimaMedia8 = ?5 and precoAcimaMedia20 = ?6 "
			+ "and precoAcimaMedia200 = ?7 and o.dat < '2021-01-01' and o.dat >= '2020-01-01'", nativeQuery = true)
	Double countPiercingLineEspecificoIniciadoGeralUltimoAnoNaoChegouAlvo(String tipo, int volumeAcimaMedia20, int perfMinima, int perfMaxima,
			int precoAcimaMedia8, int precoAcimaMedia20, int precoAcimaMedia200);

	@Query(value = "select count(*) from OPERACAO o inner join MARTELO m "
			+ "on o.nomeDoPapel = m.nomeDopapel and  o.dat = m.dat and o.padrao = m.padrao "
			+ "where o.start = 1 and tipo = ?1 and pavioSuperior = ?2 and pavioInferior = ?3 "
			+ "and volumeAcimaMedia20 = ?4 and marteloAcimaMedia200 = ?5 and o.dat < '2021-01-01'", nativeQuery = true)
	Double countMarteloEspecificoIniciadoGeral(String tipo, String pavioSuperior, String pavioInferior,
			int volumeAcimaMedia20, int marteloAcimaMedia200);

	@Query(value = "select count(*) from OPERACAO o inner join MARTELO m "
			+ "on o.nomeDoPapel = m.nomeDopapel and  o.dat = m.dat and o.padrao = m.padrao "
			+ "where o.start = 1 and o.primeiroAlvoAtingido = 0 and tipo = ?1 and pavioSuperior = ?2 and pavioInferior = ?3 "
			+ "and volumeAcimaMedia20 = ?4 and marteloAcimaMedia200 = ?5 and o.dat < '2021-01-01'", nativeQuery = true)
	Double countMarteloEspecificoIniciadoGeralNaoChegouAlvo(String tipo, String pavioSuperior, String pavioInferior,
			int volumeAcimaMedia20, int marteloAcimaMedia200);

	@Query(value = "select count(*) from OPERACAO o inner join MARTELO m "
			+ "on o.nomeDoPapel = m.nomeDopapel and  o.dat = m.dat and o.padrao = m.padrao "
			+ "where o.start = 1 and tipo = ?1 and pavioSuperior = ?2 and pavioInferior = ?3 "
			+ "and volumeAcimaMedia20 = ?4 and marteloAcimaMedia200 = ?5 and o.dat < '2021-01-01' and o.dat > '2016-01-01'", nativeQuery = true)
	Double countMarteloEspecificoIniciadoUltimosCincoAnos(String tipo, String pavioSuperior, String pavioInferior,
			int volumeAcimaMedia20, int marteloAcimaMedia200);

	@Query(value = "select count(*) from OPERACAO o inner join MARTELO m "
			+ "on o.nomeDoPapel = m.nomeDopapel and  o.dat = m.dat and o.padrao = m.padrao "
			+ "where o.start = 1 and o.primeiroAlvoAtingido = 0 and tipo = ?1 and pavioSuperior = ?2 and pavioInferior = ?3 "
			+ "and volumeAcimaMedia20 = ?4 and marteloAcimaMedia200 = ?5 and o.dat < '2021-01-01' and o.dat > '2016-01-01'", nativeQuery = true)
	Double countMarteloEspecificoIniciadoUltimosCincoAnosNaoChegouAlvo(String tipo, String pavioSuperior,
			String pavioInferior, int volumeAcimaMedia20, int marteloAcimaMedia200);

	@Query(value = "select count(*) from OPERACAO o inner join MARTELO m "
			+ "on o.nomeDoPapel = m.nomeDopapel and  o.dat = m.dat and o.padrao = m.padrao "
			+ "where o.start = 1 and tipo = ?1 and pavioSuperior = ?2 and pavioInferior = ?3 "
			+ "and volumeAcimaMedia20 = ?4 and marteloAcimaMedia200 = ?5 and o.dat < '2021-01-01' and o.dat >= '2020-01-01'", nativeQuery = true)
	Double countMarteloEspecificoIniciadoUltimoAno(String tipo, String pavioSuperior, String pavioInferior,
			int volumeAcimaMedia20, int marteloAcimaMedia200);

	@Query(value = "select count(*) from OPERACAO o inner join MARTELO m "
			+ "on o.nomeDoPapel = m.nomeDopapel and  o.dat = m.dat and o.padrao = m.padrao "
			+ "where o.start = 1 and o.primeiroAlvoAtingido = 0 and tipo = ?1 and pavioSuperior = ?2 and pavioInferior = ?3 "
			+ "and volumeAcimaMedia20 = ?4 and marteloAcimaMedia200 = ?5 and o.dat < '2021-01-01' and o.dat >= '2020-01-01'", nativeQuery = true)
	Double countMarteloEspecificoIniciadoUltimoAnoNaoChegouAlvo(String tipo, String pavioSuperior, String pavioInferior,
			int volumeAcimaMedia20, int marteloAcimaMedia200);

}
