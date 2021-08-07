package br.iesb.indicador_analise_grafica;

import java.io.IOException;

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
		
		TreinamentoRedeNeural.realizaTreinamentoProcurandoPadroes();
		TreinamentoRedeNeural.confereAlvosDasOperacoes();

		long elapsedTime = (System.currentTimeMillis() - start);
		System.out.println("Tempo gasto parar rodar: (segundos) " + elapsedTime / 1000F);

	}

}
