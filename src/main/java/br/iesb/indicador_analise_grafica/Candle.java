package br.iesb.indicador_analise_grafica;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.persistence.*;

import com.sun.istack.NotNull;

@Entity
@Table(name="CANDLE", schema="indicadordeanalisetecnicacomia")
public class Candle {
	
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
    @Id
	private Long ID;

	
	@OneToOne(mappedBy = "candle")
	private InfoCandle infoCandle;
	
	LocalDate data;
	//DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
	
	
	private String papel;
	
	@Column(name="abertura")
	double abertura; 
	
	@Column(name="fechamento")
	double fechamento; 
	
	@Column(name="maxima")
	double maxima; 
	
	@Column(name="minima")
	double minima; 
	
	@Column(name="volume")
	double volume;
	
	public Candle() {
		
	}
	
		
	public Candle(Date date, String abertura, String maxima,
				String minima, String fechamento, String volume, String papel) {
		
		this.abertura = Double.parseDouble(abertura)/100;
		this.fechamento = Double.parseDouble(fechamento)/100;
		this.maxima = Double.parseDouble(maxima)/100;
		this.minima = Double.parseDouble(minima)/100;
		this.volume = Double.parseDouble(volume)/100;
		this.data = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		this.papel = papel;
		
	}
	
	public LocalDate getDate() {
		return data;
	}

	public String getPapel() {
		return papel;
	}

	public Long getID() {
		return ID;
	}

	
}
