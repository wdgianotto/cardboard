package com.cardbox.cardbox.Repository;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import com.cardbox.cardbox.Models.TransportadoraModel;


@Repository
public interface TransportadoraRepository extends CrudRepository<TransportadoraModel, Integer>{

	public TransportadoraModel findByOrigem(String origem); 

	
	
}

