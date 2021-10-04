package br.iesb.indicador_analise_grafica;

import javax.persistence.*;

@Entity
@Table(name="BEBE_ABANDONADO")
public class BebeAbandonado {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	
	@Column
	private Boolean precoAcimaMedia8;
	
	@Column
	private Boolean precoAcimaMedia20;
	
	@Column
	private Boolean precoAcimaMedia200;
	
	@Column
	private Boolean volumeAcimaMedia20;
	
	@Column
	private Boolean primeiroCandleMarubozu;
	
	@Column
	private Boolean segundoCandleDoji;
	
	@Column
	private Boolean terceiroCandleMarubozu;
	
	@OneToOne
    @JoinColumns({@JoinColumn(name="dat"), @JoinColumn(name="nomeDoPapel"), @JoinColumn(name="padrao")})
	private Operacao operacao = null;

	public BebeAbandonado() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Boolean getPrimeiroCandleMarubozu() {
		return primeiroCandleMarubozu;
	}

	public void setPrimeiroCandleMarubozu(Boolean primeiroCandleMarubozu) {
		this.primeiroCandleMarubozu = primeiroCandleMarubozu;
	}

	public Boolean getSegundoCandleDoji() {
		return segundoCandleDoji;
	}

	public void setSegundoCandleDoji(Boolean segundoCandleDoji) {
		this.segundoCandleDoji = segundoCandleDoji;
	}

	public Boolean getTerceiroCandleMarubozu() {
		return terceiroCandleMarubozu;
	}

	public void setTerceiroCandleMarubozu(Boolean terceiroCandleMarubozu) {
		this.terceiroCandleMarubozu = terceiroCandleMarubozu;
	}

	public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}
	
	
	
}
