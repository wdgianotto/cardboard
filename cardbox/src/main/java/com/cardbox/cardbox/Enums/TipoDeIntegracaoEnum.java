package com.cardbox.cardbox.Enums;

public enum TipoDeIntegracaoEnum {

	AEREO("Aereo"), TERRESTRE("Terrestre"), AEREO_TERRESTRE("Aereo Terrestre"), EMPTY("Empty");
	
	private String descricao;

	private TipoDeIntegracaoEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
