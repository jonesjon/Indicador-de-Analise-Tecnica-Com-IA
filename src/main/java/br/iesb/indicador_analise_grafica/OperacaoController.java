package br.iesb.indicador_analise_grafica;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.iesb.indicador_analise_grafica.service.OperacaoService;

@RestController
public class OperacaoController {
	
	@GetMapping("/{ano}")
	public ResponseEntity<List<Operacao>> operacoesPorAno(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate ano){
		
		return ResponseEntity.ok().body(OperacaoService.operacoesPorAno(ano));
	}
	
	@GetMapping("/")
	public String operacao(){
		
		//List<Operacao> operacoes = OperacaoService.getOperacoes();
		
		return "OLAALO";
		
	}

}
