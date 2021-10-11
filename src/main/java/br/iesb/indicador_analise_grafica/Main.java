package br.iesb.indicador_analise_grafica;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Controller;

@SpringBootApplication
public class Main {

	public static void main(String[] args) throws IOException {

		SpringApplication.run(Main.class, args);
		long start = System.currentTimeMillis();
		
		//PopularBanco.inserirDados();
		
		//PreencherPapeisOperaveis.preencher();
		//TreinamentoRedeNeural.realizaTreinamentoProcurandoPadroesEmPapeisOperaveis();
		//TreinamentoRedeNeural.confereAlvosDasOperacoesPossiveis();

		long elapsedTime = (System.currentTimeMillis() - start);
		System.out.println("Tempo gasto parar rodar: (segundos) " + elapsedTime / 1000F);
		
		List<TipoCandle> tipos = TipoCandle.retornaTipos();
		List<PavioSuperior> pavioS = PavioSuperior.getPavioSuperiorMartelo();
		List<PavioInferior> pavioInf = PavioInferior.getPavioInferiorMartelo();
		List<VolumeAcimaMedia20> vol = VolumeAcimaMedia20.getListVolumeAcimaMedia20();
		List<PrecoAcimaMedia200> precos = PrecoAcimaMedia200.getListPrecoAcimaMedia200();
		
		ArrayList<Possibilidade> possibilidades = new ArrayList<Possibilidade>();
		
		tipos.stream().forEach(tipo -> {
			pavioS.stream().forEach(pavioSuperior ->{
				pavioInf.stream().forEach(pavioInferior -> {
					vol.stream().forEach(volume -> {
						precos.stream().forEach(preco -> {
							System.out.println(tipo.getTipo() + "||" + pavioSuperior.getDescricao() + "||" + pavioInferior.getDescricao() + "||" + volume.getValor() + "||" + preco.getValor() + "\n\n");
							Possibilidade possibilidade = new Possibilidade(tipo, pavioSuperior, pavioInferior, volume, preco);
							possibilidades.add(possibilidade);
							
						});
					});
				});
			});
		});
		
		System.out.println(possibilidades.size());
		
		//Connection c = DriverManager.getConnection("");
		
	}

}
