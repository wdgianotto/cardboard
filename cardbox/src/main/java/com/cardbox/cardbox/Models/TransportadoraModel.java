package com.cardbox.cardbox.Models;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity
@Table(name = "pesquisas")
public class TransportadoraModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "origem")
	private String origem;
	
	@Column(name = "destino")
	private String destino;
	
	@Column(name = "distancia")
	private Long distancia;
	
	//nullable
	@Column(name = "tipodetransporte")
	private String tipoDeTransporte;
	
	@Column(name = "prioridade")
	private String prioridade;

	public TransportadoraModel(String origem, String destino, Long distancia, String tipoDeTransporte,
			String prioridade) {
		super();
		this.origem = origem;
		this.destino = destino;
		this.distancia = distancia;
		this.tipoDeTransporte = tipoDeTransporte;
		this.prioridade = prioridade;
	}

	public TransportadoraModel() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public Long getDistancia() {
		return distancia;
	}

	public void setDistancia(Long distancia) {
		this.distancia = distancia;
	}

	public String getTipoDeTransporte() {
		return tipoDeTransporte;
	}

	public void setTipoDeTransporte(String tipoDeTransporte) {
		this.tipoDeTransporte = tipoDeTransporte;
	}

	public String getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}


	
	
}
