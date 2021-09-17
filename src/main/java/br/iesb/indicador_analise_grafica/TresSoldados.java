package br.iesb.indicador_analise_grafica;

import javax.persistence.*;;

@Entity
@Table(name="TRES_SOLDADOS")
public class TresSoldados {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	
	@Column
	private int pavioSuperiorPrimeiroCandle;
	
	@Column
	private int pavioInferiorPrimeiroCandle;
	
	@Column
	private int pavioSuperiorSegundoCandle;
	
	@Column
	private int pavioInferiorSegundoCandle;
	
	@Column
	private int pavioSuperiorTerceiroCandle;
	
	@Column
	private int pavioInferiorTerceiroCandle;
	
	@Column
	private Boolean precoAcimaMedia8;
	
	@Column
	private Boolean precoAcimaMedia20;
	
	@Column
	private Boolean precoAcimaMedia200;
	
	@Column
	private Boolean volumeAcimaMedia20;
	
	@OneToOne
    @JoinColumns({@JoinColumn(name="dat"), @JoinColumn(name="nomeDoPapel"), @JoinColumn(name="padrao")})
	private Operacao operacao = null;
	
	public TresSoldados() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getPavioSuperiorPrimeiroCandle() {
		return pavioSuperiorPrimeiroCandle;
	}

	public void setPavioSuperiorPrimeiroCandle(int pavioSuperiorPrimeiroCandle) {
		this.pavioSuperiorPrimeiroCandle = pavioSuperiorPrimeiroCandle;
	}

	public int getPavioInferiorPrimeiroCandle() {
		return pavioInferiorPrimeiroCandle;
	}

	public void setPavioInferiorPrimeiroCandle(int pavioInferiorPrimeiroCandle) {
		this.pavioInferiorPrimeiroCandle = pavioInferiorPrimeiroCandle;
	}

	public int getPavioSuperiorSegundoCandle() {
		return pavioSuperiorSegundoCandle;
	}

	public void setPavioSuperiorSegundoCandle(int pavioSuperiorSegundoCandle) {
		this.pavioSuperiorSegundoCandle = pavioSuperiorSegundoCandle;
	}

	public int getPavioInferiorSegundoCandle() {
		return pavioInferiorSegundoCandle;
	}

	public void setPavioInferiorSegundoCandle(int pavioInferiorSegundoCandle) {
		this.pavioInferiorSegundoCandle = pavioInferiorSegundoCandle;
	}

	public int getPavioSuperiorTerceiroCandle() {
		return pavioSuperiorTerceiroCandle;
	}

	public void setPavioSuperiorTerceiroCandle(int pavioSuperiorTerceiroCandle) {
		this.pavioSuperiorTerceiroCandle = pavioSuperiorTerceiroCandle;
	}

	public int getPavioInferiorTerceiroCandle() {
		return pavioInferiorTerceiroCandle;
	}

	public void setPavioInferiorTerceiroCandle(int pavioInferiorTerceiroCandle) {
		this.pavioInferiorTerceiroCandle = pavioInferiorTerceiroCandle;
	}

	public Boolean getPrecoAcimaMedia8() {
		return precoAcimaMedia8;
	}

	public void setPrecoAcimaMedia8(Boolean precoAcimaMedia8) {
		this.precoAcimaMedia8 = precoAcimaMedia8;
	}

	public Boolean getPrecoAcimaMedia20() {
		return precoAcimaMedia20;
	}

	public void setPrecoAcimaMedia20(Boolean precoAcimaMedia20) {
		this.precoAcimaMedia20 = precoAcimaMedia20;
	}

	public Boolean getPrecoAcimaMedia200() {
		return precoAcimaMedia200;
	}

	public void setPrecoAcimaMedia200(Boolean precoAcimaMedia200) {
		this.precoAcimaMedia200 = precoAcimaMedia200;
	}

	public Boolean getVolumeAcimaMedia20() {
		return volumeAcimaMedia20;
	}

	public void setVolumeAcimaMedia20(Boolean volumeAcimaMedia20) {
		this.volumeAcimaMedia20 = volumeAcimaMedia20;
	}

	public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}
	
	
}
