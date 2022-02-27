package br.iesb.indicador_analise_grafica.padroes;

import javax.persistence.*;

import br.iesb.indicador_analise_grafica.Operacao;
import br.iesb.indicador_analise_grafica_enum.PadroesEnum;

@Entity
@Table(name="BEBE_ABANDONADO")
@PrimaryKeyJoinColumn(name = "ID")
public class BebeAbandonado extends Padrao{
	
	@Column
	private Boolean primeiroCandleMarubozu;
	
	@Column
	private Boolean segundoCandleDoji;
	
	@Column
	private Boolean terceiroCandleMarubozu;
	
	/*@OneToOne
    @JoinColumns({@JoinColumn(name="dat"), @JoinColumn(name="nomeDoPapel"), @JoinColumn(name="padrao")})
	private Operacao operacao = null;*/

	public BebeAbandonado() {
		
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

	/*public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}*/
	
	
	
}
