package br.iesb.indicador_analise_grafica.repository;

import java.time.LocalDate;
import java.util.ArrayList;
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

	@Query(value = "select count(*) from OPERACAO o inner join TRES_SOLDADOS e on o.nomeDoPapel = e.nomeDopapel and  o.dat = e.dat and o.padrao = e.padrao "
			+ "where o.start = 1 and o.dat < '2021-01-01' and o.padrao = ?1 and pavioSuperiorPrimeiroCandle = ?2 and pavioInferiorPrimeiroCandle = ?3 "
			+ "and pavioSuperiorTerceiroCandle = ?4 and pavioInferiorTerceiroCandle = ?5 "
			+ "and precoAcimaMedia200 = ?6", nativeQuery = true)
	Double countTresSoldadosEspecificoIniciadoGeral(String padrao, String pavioSuperiorPrimeiroCandle,
			String pavioInferiorPrimeiroCandle, String pavioSuperiorTerceiroCandle, String pavioInferiorTerceiroCandle,
			int preco200);

	@Query(value = "select count(*) from OPERACAO o inner join TRES_SOLDADOS e on o.nomeDoPapel = e.nomeDopapel and  o.dat = e.dat and o.padrao = e.padrao "
			+ "where o.start = 1 and o.dat < '2021-01-01' and o.primeiroAlvoAtingido = 0 and o.padrao = ?1 and pavioSuperiorPrimeiroCandle = ?2 and pavioInferiorPrimeiroCandle = ?3 "
			+ "and pavioSuperiorTerceiroCandle = ?4 and pavioInferiorTerceiroCandle = ?5 "
			+ "and precoAcimaMedia200 = ?6", nativeQuery = true)
	Double countTresSoldadosEspecificoIniciadoGeralNaoChegouAlvo(String padrao, String pavioSuperiorPrimeiroCandle,
			String pavioInferiorPrimeiroCandle, String pavioSuperiorTerceiroCandle, String pavioInferiorTerceiroCandle,
			int preco200);

	@Query(value = "select count(*) from OPERACAO o inner join TRES_SOLDADOS e on o.nomeDoPapel = e.nomeDopapel and  o.dat = e.dat and o.padrao = e.padrao "
			+ "where o.start = 1 and o.dat < '2021-01-01' and o.dat >= '2016-01-01' and o.padrao = ?1 and pavioSuperiorPrimeiroCandle = ?2 and pavioInferiorPrimeiroCandle = ?3 "
			+ "and pavioSuperiorTerceiroCandle = ?4 and pavioInferiorTerceiroCandle = ?5 "
			+ "and precoAcimaMedia200 = ?6", nativeQuery = true)
	Double countTresSoldadosEspecificoIniciadoUltimosCincoAnos(String padrao, String pavioSuperiorPrimeiroCandle,
			String pavioInferiorPrimeiroCandle, String pavioSuperiorTerceiroCandle, String pavioInferiorTerceiroCandle,
			int preco200);

	@Query(value = "select count(*) from OPERACAO o inner join TRES_SOLDADOS e on o.nomeDoPapel = e.nomeDopapel and  o.dat = e.dat and o.padrao = e.padrao "
			+ "where o.start = 1 and o.dat < '2021-01-01' and o.dat >= '2016-01-01' and primeiroAlvoAtingido = 0 and o.padrao = ?1 and pavioSuperiorPrimeiroCandle = ?2 and pavioInferiorPrimeiroCandle = ?3 "
			+ "and pavioSuperiorTerceiroCandle = ?4 and pavioInferiorTerceiroCandle = ?5 "
			+ "and precoAcimaMedia200 = ?6", nativeQuery = true)
	Double countTresSoldadosEspecificoIniciadoUltimosCincoAnosNaoChegouAlvo(String padrao,
			String pavioSuperiorPrimeiroCandle, String pavioInferiorPrimeiroCandle, String pavioSuperiorTerceiroCandle,
			String pavioInferiorTerceiroCandle, int preco200);

	@Query(value = "select count(*) from OPERACAO o inner join TRES_SOLDADOS e on o.nomeDoPapel = e.nomeDopapel and  o.dat = e.dat and o.padrao = e.padrao "
			+ "where o.start = 1 and o.dat < '2021-01-01' and o.dat >= '2020-01-01' and o.padrao = ?1 and pavioSuperiorPrimeiroCandle = ?2 and pavioInferiorPrimeiroCandle = ?3 "
			+ "and pavioSuperiorTerceiroCandle = ?4 and pavioInferiorTerceiroCandle = ?5 "
			+ "and precoAcimaMedia200 = ?6", nativeQuery = true)
	Double countTresSoldadosEspecificoIniciadoUltimoAno(String padrao, String pavioSuperiorPrimeiroCandle,
			String pavioInferiorPrimeiroCandle, String pavioSuperiorTerceiroCandle, String pavioInferiorTerceiroCandle,
			int preco200);

	@Query(value = "select count(*) from OPERACAO o inner join TRES_SOLDADOS e on o.nomeDoPapel = e.nomeDopapel and  o.dat = e.dat and o.padrao = e.padrao "
			+ "where o.start = 1 and o.dat < '2021-01-01' and o.dat >= '2020-01-01' and primeiroAlvoAtingido = 0 and o.padrao = ?1 and pavioSuperiorPrimeiroCandle = ?2 and pavioInferiorPrimeiroCandle = ?3 "
			+ "and pavioSuperiorTerceiroCandle = ?4 and pavioInferiorTerceiroCandle = ?5 "
			+ "and precoAcimaMedia200 = ?6", nativeQuery = true)
	Double countTresSoldadosEspecificoIniciadoUltimoAnoNaoChegouAlvo(String padrao, String pavioSuperiorPrimeiroCandle,
			String pavioInferiorPrimeiroCandle, String pavioSuperiorTerceiroCandle, String pavioInferiorTerceiroCandle,
			int preco200);

	@Query(value = "select count(*) from OPERACAO o inner join ENGOLFO e on o.nomeDoPapel = e.nomeDopapel and  o.dat = e.dat and o.padrao = e.padrao "
			+ "where o.start = 1 and o.dat < '2021-01-01' and tipo = ?1 and pavioSuperior = ?2 and pavioInferior = ?3 and volumeAcimaMedia20 = ?4 "
			+ "and acimaMedia8 = ?5 and acimaMedia20 = ?6 and acimaMedia200 = ?7 and variacao = ?8", nativeQuery = true)
	Double countEngolfoEspecificoIniciadoGeral(String tipo, String pavioSuperior, String pavioInferior,
			int volumeAcimaMedia20, int acimaMedia8, int acimaMedia20, int acimaMedia200, String variacao);

	@Query(value = "select count(*) from OPERACAO o inner join ENGOLFO e on o.nomeDoPapel = e.nomeDopapel and  o.dat = e.dat and o.padrao = e.padrao "
			+ "where o.start = 1 and o.dat < '2021-01-01' and primeiroAlvoAtingido = 0 and tipo = ?1 and pavioSuperior = ?2 and pavioInferior = ?3 and volumeAcimaMedia20 = ?4 "
			+ "and acimaMedia8 = ?5 and acimaMedia20 = ?6 and acimaMedia200 = ?7 and variacao = ?8", nativeQuery = true)
	Double countEngolfoEspecificoIniciadoGeralNaoChegouAlvo(String tipo, String pavioSuperior, String pavioInferior,
			int volumeAcimaMedia20, int acimaMedia8, int acimaMedia20, int acimaMedia200, String variacao);

	@Query(value = "select count(*) from OPERACAO o inner join ENGOLFO e on o.nomeDoPapel = e.nomeDopapel and  o.dat = e.dat and o.padrao = e.padrao "
			+ "where o.start = 1 and o.dat < '2021-01-01' and o.dat >= '2016-01-01' and tipo = ?1 and pavioSuperior = ?2 and pavioInferior = ?3 and volumeAcimaMedia20 = ?4 "
			+ "and acimaMedia8 = ?5 and acimaMedia20 = ?6 and acimaMedia200 = ?7 and variacao = ?8", nativeQuery = true)
	Double countEngolfoEspecificoIniciadoUltimosCincoAnos(String tipo, String pavioSuperior, String pavioInferior,
			int volumeAcimaMedia20, int acimaMedia8, int acimaMedia20, int acimaMedia200, String variacao);

	@Query(value = "select count(*) from OPERACAO o inner join ENGOLFO e on o.nomeDoPapel = e.nomeDopapel and  o.dat = e.dat and o.padrao = e.padrao "
			+ "where o.start = 1 and o.dat < '2021-01-01' and o.dat >= '2016-01-01' and primeiroAlvoAtingido = 0 and tipo = ?1 and pavioSuperior = ?2 and pavioInferior = ?3 and volumeAcimaMedia20 = ?4 "
			+ "and acimaMedia8 = ?5 and acimaMedia20 = ?6 and acimaMedia200 = ?7 and variacao = ?8", nativeQuery = true)
	Double countEngolfoEspecificoIniciadoUltimosCincoAnosNaoChegouAlvo(String tipo, String pavioSuperior,
			String pavioInferior, int volumeAcimaMedia20, int acimaMedia8, int acimaMedia20, int acimaMedia200,
			String variacao);

	@Query(value = "select count(*) from OPERACAO o inner join ENGOLFO e on o.nomeDoPapel = e.nomeDopapel and  o.dat = e.dat and o.padrao = e.padrao "
			+ "where o.start = 1 and o.dat < '2021-01-01' and o.dat >= '2020-01-01' and tipo = ?1 and pavioSuperior = ?2 and pavioInferior = ?3 and volumeAcimaMedia20 = ?4 "
			+ "and acimaMedia8 = ?5 and acimaMedia20 = ?6 and acimaMedia200 = ?7 and variacao = ?8", nativeQuery = true)
	Double countEngolfoEspecificoIniciadoUltimoAno(String tipo, String pavioSuperior, String pavioInferior,
			int volumeAcimaMedia20, int acimaMedia8, int acimaMedia20, int acimaMedia200, String variacao);

	@Query(value = "select count(*) from OPERACAO o inner join ENGOLFO e on o.nomeDoPapel = e.nomeDopapel and  o.dat = e.dat and o.padrao = e.padrao "
			+ "where o.start = 1 and o.dat < '2021-01-01' and o.dat >= '2020-01-01' and primeiroAlvoAtingido = 0 and tipo = ?1 and pavioSuperior = ?2 and pavioInferior = ?3 and volumeAcimaMedia20 = ?4 "
			+ "and acimaMedia8 = ?5 and acimaMedia20 = ?6 and acimaMedia200 = ?7 and variacao = ?8", nativeQuery = true)
	Double countEngolfoEspecificoIniciadoUltimoAnoNaoChegouAlvo(String tipo, String pavioSuperior, String pavioInferior,
			int volumeAcimaMedia20, int acimaMedia8, int acimaMedia20, int acimaMedia200, String variacao);

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
	Double countPiercingLineEspecificoIniciadoGeralNaoChegouAlvo(String tipo, int volumeAcimaMedia20, int perfMinima,
			int perfMaxima, int precoAcimaMedia8, int precoAcimaMedia20, int precoAcimaMedia200);

	@Query(value = "select count(*) from OPERACAO o inner join PIERCING_LINE p "
			+ "on o.nomeDoPapel = p.nomeDopapel and  o.dat = p.dat and o.padrao = p.padrao "
			+ "where start = 1 and p.tipo = ?1 and volumeAcimaMedia20 = ?2 "
			+ "and perfuracao >= ?3 and perfuracao < ?4 and precoAcimaMedia8 = ?5 and precoAcimaMedia20 = ?6 "
			+ "and precoAcimaMedia200 = ?7 and o.dat < '2021-01-01' and o.dat >= '2016-01-01'", nativeQuery = true)
	Double countPiercingLineEspecificoIniciadoGeralUltimosCincoAnos(String tipo, int volumeAcimaMedia20, int perfMinima,
			int perfMaxima, int precoAcimaMedia8, int precoAcimaMedia20, int precoAcimaMedia200);

	@Query(value = "select count(*) from OPERACAO o inner join PIERCING_LINE p "
			+ "on o.nomeDoPapel = p.nomeDopapel and  o.dat = p.dat and o.padrao = p.padrao "
			+ "where start = 1 and primeiroAlvoAtingido = 0 and p.tipo = ?1 and volumeAcimaMedia20 = ?2 "
			+ "and perfuracao >= ?3 and perfuracao < ?4 and precoAcimaMedia8 = ?5 and precoAcimaMedia20 = ?6 "
			+ "and precoAcimaMedia200 = ?7 and o.dat < '2021-01-01' and o.dat >= '2016-01-01'", nativeQuery = true)
	Double countPiercingLineEspecificoIniciadoGeralUltimosCincoAnosNaoChegouAlvo(String tipo, int volumeAcimaMedia20,
			int perfMinima, int perfMaxima, int precoAcimaMedia8, int precoAcimaMedia20, int precoAcimaMedia200);

	@Query(value = "select count(*) from OPERACAO o inner join PIERCING_LINE p "
			+ "on o.nomeDoPapel = p.nomeDopapel and  o.dat = p.dat and o.padrao = p.padrao "
			+ "where start = 1 and p.tipo = ?1 and volumeAcimaMedia20 = ?2 "
			+ "and perfuracao >= ?3 and perfuracao < ?4 and precoAcimaMedia8 = ?5 and precoAcimaMedia20 = ?6 "
			+ "and precoAcimaMedia200 = ?7 and o.dat < '2021-01-01' and o.dat >= '2020-01-01'", nativeQuery = true)
	Double countPiercingLineEspecificoIniciadoGeralUltimoAno(String tipo, int volumeAcimaMedia20, int perfMinima,
			int perfMaxima, int precoAcimaMedia8, int precoAcimaMedia20, int precoAcimaMedia200);

	@Query(value = "select count(*) from OPERACAO o inner join PIERCING_LINE p "
			+ "on o.nomeDoPapel = p.nomeDopapel and  o.dat = p.dat and o.padrao = p.padrao "
			+ "where start = 1 and primeiroAlvoAtingido = 0 and p.tipo = ?1 and volumeAcimaMedia20 = ?2 "
			+ "and perfuracao >= ?3 and perfuracao < ?4 and precoAcimaMedia8 = ?5 and precoAcimaMedia20 = ?6 "
			+ "and precoAcimaMedia200 = ?7 and o.dat < '2021-01-01' and o.dat >= '2020-01-01'", nativeQuery = true)
	Double countPiercingLineEspecificoIniciadoGeralUltimoAnoNaoChegouAlvo(String tipo, int volumeAcimaMedia20,
			int perfMinima, int perfMaxima, int precoAcimaMedia8, int precoAcimaMedia20, int precoAcimaMedia200);

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
