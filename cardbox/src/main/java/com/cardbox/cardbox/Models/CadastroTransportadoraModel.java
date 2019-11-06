package com.cardbox.cardbox.Models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transportadora")
public class CadastroTransportadoraModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "id")
	private Long id;
	
	@Column (name = "nome")
	private String nome;
	
	@Column (name = "tempomedioporkmaereo")
	private Long tempoMedioPorKmAereo;
	
	@Column (name = "tempomedioporkmterrestre")
	private Long tempoMedioPorKmTerrestre;
	
	@Column (name = "transporteaereo")
	private Boolean transporteAereo;
	
	@Column (name = "transporteterrestre")
	private Boolean transporteTerrestre;
	
	@Column (name = "valorporkmaereo")
	private Double valorPorKmAereo;
	
	@Column  (name = "valorporkmterrestre")
	private Double valorPorKmTerrestre;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getTempoMedioPorKmAereo() {
		return tempoMedioPorKmAereo;
	}

	public void setTempoMedioPorKmAereo(Long tempoMedioPorKmAereo) {
		this.tempoMedioPorKmAereo = tempoMedioPorKmAereo;
	}

	public Long getTempoMedioPorKmTerrestre() {
		return tempoMedioPorKmTerrestre;
	}

	public void setTempoMedioPorKmTerrestre(Long tempoMedioPorKmTerrestre) {
		this.tempoMedioPorKmTerrestre = tempoMedioPorKmTerrestre;
	}

	public boolean isTransporteAereo() {
		return transporteAereo;
	}

	public void setTransporteAereo(boolean transporteAereo) {
		this.transporteAereo = transporteAereo;
	}

	public boolean isTransporteTerrestre() {
		return transporteTerrestre;
	}

	public void setTransporteTerrestre(boolean transporteTerrestre) {
		this.transporteTerrestre = transporteTerrestre;
	}

	public Double getValorPorKmAereo() {
		return valorPorKmAereo;
	}

	public void setValorPorKmAereo(Double valorPorKmAereo) {
		this.valorPorKmAereo = valorPorKmAereo;
	}

	public Double getValorPorKmTerrestre() {
		return valorPorKmTerrestre;
	}

	public void setValorPorKmTerrestre(Double valorPorKmTerrestre) {
		this.valorPorKmTerrestre = valorPorKmTerrestre;
	}

	public CadastroTransportadoraModel(String nome, Long tempoMedioPorKmAereo,
			Long tempoMedioPorKmTerrestre, boolean transporteAereo, boolean transporteTerrestre,
			Double valorPorKmAereo, Double valorPorKmTerrestre) {
		super();
		this.nome = nome;
		this.tempoMedioPorKmAereo = tempoMedioPorKmAereo;
		this.tempoMedioPorKmTerrestre = tempoMedioPorKmTerrestre;
		this.transporteAereo = transporteAereo;
		this.transporteTerrestre = transporteTerrestre;
		this.valorPorKmAereo = valorPorKmAereo;
		this.valorPorKmTerrestre = valorPorKmTerrestre;
	}

	public CadastroTransportadoraModel() {
		super();
	}

	
	
}
