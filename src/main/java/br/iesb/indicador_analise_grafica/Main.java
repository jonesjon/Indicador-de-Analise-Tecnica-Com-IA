package br.iesb.indicador_analise_grafica;

import java.io.IOException;

import javax.activation.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@ComponentScan(basePackages = "br.iesb.indicador_analise_grafica")
@Configuration
public class Main {

	public static void main(String[] args) throws IOException {
		
		SpringApplication.run(Main.class, args);
		long start = System.currentTimeMillis();
		
		TreinamentoRedeNeural trn = new TreinamentoRedeNeural();
		
		//PopularBanco.inserirDados();
		
		//PreencherPapeisOperaveis.preencher();
		trn.realizaTreinamentoProcurandoPadroesEmPapeisOperaveis();
		trn.confereAlvosDasOperacoesPossiveis();

		long elapsedTime = (System.currentTimeMillis() - start);
		System.out.println("Tempo gasto parar rodar: (segundos) " + elapsedTime / 1000F);

	}

}
