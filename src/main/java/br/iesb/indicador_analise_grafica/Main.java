package br.iesb.indicador_analise_grafica;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Jdbc;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Controller;


@SpringBootApplication
public class Main {

	public static void main(String[] args) throws IOException, SQLException {

		SpringApplication.run(Main.class, args);
		long start = System.currentTimeMillis();

		// PopularBanco.inserirDados();

		// PreencherPapeisOperaveis.preencher();
		// TreinamentoRedeNeural.realizaTreinamentoProcurandoPadroesEmPapeisOperaveis();
		// TreinamentoRedeNeural.confereAlvosDasOperacoesPossiveis();

		long elapsedTime = (System.currentTimeMillis() - start);
		System.out.println("Tempo gasto parar rodar: (segundos) " + elapsedTime / 1000F);

		List<TipoCandle> tipos = TipoCandle.retornaTipos();
		List<PavioSuperior> pavioS = PavioSuperior.getPavioSuperiorMartelo();
		List<PavioInferior> pavioInf = PavioInferior.getPavioInferiorMartelo();
		List<VolumeAcimaMedia20> vol = VolumeAcimaMedia20.getListVolumeAcimaMedia20();
		List<PrecoAcimaMedia200> precos = PrecoAcimaMedia200.getListPrecoAcimaMedia200();

		ArrayList<Possibilidade> possibilidades = new ArrayList<Possibilidade>();

		tipos.stream().forEach(tipo -> {
			pavioS.stream().forEach(pavioSuperior -> {
				pavioInf.stream().forEach(pavioInferior -> {
					vol.stream().forEach(volume -> {
						precos.stream().forEach(preco -> {
							System.out.println(tipo.getTipo() + "||" + pavioSuperior.getDescricao() + "||"
									+ pavioInferior.getDescricao() + "||" + volume.getValor() + "||" + preco.getValor()
									+ "\n\n");
							Possibilidade possibilidade = new Possibilidade(tipo, pavioSuperior, pavioInferior, volume,
									preco);
							possibilidades.add(possibilidade);

						});
					});
				});
			});
		});

		
		queryDeTodasPossibilidades(possibilidades);
		
		System.out.println(possibilidades.size());

		Connection conexao = ConexaoBancoDeDados.getConexaoMySQL();

		Statement st = conexao.createStatement();

		ResultSet rs = st.executeQuery("select count(*) as abc from BEBE_ABANDONADO;");

		while (rs.next()) {
			System.out.println(rs.getInt("abc"));
		}

	}

	private static void queryDeTodasPossibilidades(ArrayList<Possibilidade> todasPossibilidades) {

		System.out.println("\n\nQueries de diferentes combinações\n\n");

		String queryComeco = "select count(*) as abc from MARTELO where ";
		
		final String tipoCandle = " tipoCandle = ";
		final String pavioInferior = " pavioInferior = ";
		final String pavioSuperior = " pavioSuperior = ";
		final String volumeAcimaMedia20 = " volumeAcimaMedia20 = ";
		final String marteloAcimaMedia200 = " marteloAcimaMedia200 = ";
		
		final String AND_BANCO = " AND ";
		
		final String close = ";";

		todasPossibilidades.stream().forEach(p -> {

			String queryFinal = queryComeco;

			if (p.tipoCandle != TipoCandle.NULL) {
				queryFinal += tipoCandle;
				queryFinal += p.tipoCandle.getTipo();
			}
			
			if (p.pavioSuperior != PavioSuperior.NULL) {
				queryFinal += AND_BANCO;
				queryFinal += pavioSuperior;
				queryFinal += p.pavioSuperior.getDescricao();
			}

			if (p.pavioInferior != PavioInferior.NULL) {
				queryFinal += AND_BANCO;
				queryFinal += pavioInferior;
				queryFinal += p.pavioInferior.getDescricao();
			}
			
			if (p.volumeAcimaMedia20 != VolumeAcimaMedia20.NULL) {
				queryFinal += AND_BANCO;
				queryFinal += volumeAcimaMedia20;
				queryFinal += p.volumeAcimaMedia20.getValor();
			}
			
			if (p.precoAcimaMedia200 != PrecoAcimaMedia200.NULL) {
				queryFinal += AND_BANCO;
				queryFinal += marteloAcimaMedia200;
				queryFinal += p.precoAcimaMedia200.getValor();
			}

			queryFinal += close;
			System.out.println(queryFinal);
		});
	}

}
