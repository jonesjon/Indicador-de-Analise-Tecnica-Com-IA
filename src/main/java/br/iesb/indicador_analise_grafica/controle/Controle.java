package br.iesb.indicador_analise_grafica.controle;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="controle")
@Data
public class Controle {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;

	@Column
	private LocalDate ultimaInsercaoInfoCandle;
	
	@Column
	private Boolean rodandoAtualizacaodiaria;
	
	@Column
	private LocalDate ultimaInsercaoMedia;
}
