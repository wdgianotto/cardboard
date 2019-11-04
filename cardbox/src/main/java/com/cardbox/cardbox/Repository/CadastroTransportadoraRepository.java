package com.cardbox.cardbox.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cardbox.cardbox.Models.CadastroTransportadoraModel;


@Repository
public interface CadastroTransportadoraRepository extends CrudRepository<CadastroTransportadoraModel, Long>{

	public List<CadastroTransportadoraModel> findAllByTransporteAereo(Boolean t);
	
}
