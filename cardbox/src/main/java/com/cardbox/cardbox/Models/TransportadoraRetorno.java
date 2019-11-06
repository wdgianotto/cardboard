package com.cardbox.cardbox.Models;

public class TransportadoraRetorno {

	private Double valor;
	private String nome;
	private Double tempo;
	
	
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Double getTempo() {
		return tempo;
	}
	public void setTempo(Double tempo) {
		this.tempo = tempo;
	}
	public TransportadoraRetorno(Double valor, String nome, Double tempo) {
		super();
		this.valor = valor;
		this.nome = nome;
		this.tempo = tempo;
	}
	public TransportadoraRetorno() {
		super();
	}
	
	
		
}
