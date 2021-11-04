package br.iesb.indicador_analise_grafica.padroes;

import javax.persistence.*;

import br.iesb.indicador_analise_grafica.Operacao;
import br.iesb.indicador_analise_grafica_enum.PavioInferior;
import br.iesb.indicador_analise_grafica_enum.PavioSuperior;;

@Entity
@Table(name="TRES_SOLDADOS")
public class TresSoldados {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	
	@Column
	private String pavioSuperiorPrimeiroCandle;
	
	@Column
	private String pavioInferiorPrimeiroCandle;
	
	@Column
	private String pavioSuperiorSegundoCandle;
	
	@Column
	private String pavioInferiorSegundoCandle;
	
	@Column
	private String pavioSuperiorTerceiroCandle;
	
	@Column
	private String pavioInferiorTerceiroCandle;
	
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

	public String getPavioSuperiorPrimeiroCandle() {
		return pavioSuperiorPrimeiroCandle;
	}

	public void setPavioSuperiorPrimeiroCandle(String pavioSuperiorPrimeiroCandle) {
		this.pavioSuperiorPrimeiroCandle = pavioSuperiorPrimeiroCandle;
	}

	public String getPavioInferiorPrimeiroCandle() {
		return pavioInferiorPrimeiroCandle;
	}

	public void setPavioInferiorPrimeiroCandle(String pavioInferiorPrimeiroCandle) {
		this.pavioInferiorPrimeiroCandle = pavioInferiorPrimeiroCandle;
	}

	public String getPavioSuperiorSegundoCandle() {
		return pavioSuperiorSegundoCandle;
	}

	public void setPavioSuperiorSegundoCandle(String pavioSuperiorSegundoCandle) {
		this.pavioSuperiorSegundoCandle = pavioSuperiorSegundoCandle;
	}

	public String getPavioInferiorSegundoCandle() {
		return pavioInferiorSegundoCandle;
	}

	public void setPavioInferiorSegundoCandle(String pavioInferiorSegundoCandle) {
		this.pavioInferiorSegundoCandle = pavioInferiorSegundoCandle;
	}

	public String getPavioSuperiorTerceiroCandle() {
		return pavioSuperiorTerceiroCandle;
	}

	public void setPavioSuperiorTerceiroCandle(String pavioSuperiorTerceiroCandle) {
		this.pavioSuperiorTerceiroCandle = pavioSuperiorTerceiroCandle;
	}

	public String getPavioInferiorTerceiroCandle() {
		return pavioInferiorTerceiroCandle;
	}

	public void setPavioInferiorTerceiroCandle(String pavioInferiorTerceiroCandle) {
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
