package br.iesb.indicador_analise_grafica;

import java.io.Serializable;
import java.time.LocalDate;

import br.iesb.indicador_analise_grafica_enum.PadroesEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class OperacaoId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 626734219115263250L;

	private LocalDate dat;
	
	private String nomeDoPapel;
	
	private PadroesEnum padraoEnum;
}
