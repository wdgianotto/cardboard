package com.cardbox.cardbox.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cardbox.cardbox.Enums.TipoDeIntegracaoEnum;
import com.cardbox.cardbox.Models.CadastroTransportadoraModel;
import com.cardbox.cardbox.Models.TransportadoraModel;
import com.cardbox.cardbox.Models.TransportadoraRetorno;
import com.cardbox.cardbox.Repository.CadastroTransportadoraRepository;
import com.cardbox.cardbox.Repository.TransportadoraRepository;

@Service
public class TransportadoraService {

	@Autowired
	private TransportadoraRepository transportadoraRepository;

	@Autowired
	private CadastroTransportadoraRepository cadastroTransportadoraRepository;




	public List<TransportadoraRetorno> calculaMelhorPrazoValor(TransportadoraModel model) {

		List<TransportadoraRetorno> listaAuxTransporteRetorno = new ArrayList<>();
		List<CadastroTransportadoraModel> listaAuxTransporte = new ArrayList<>();
		String tipoTransporte = model.getTipoDeTransporte();

		if (TipoDeIntegracaoEnum.TERRESTRE.getDescricao().equals(tipoTransporte)) {
			List<CadastroTransportadoraModel> listaTansporte = cadastroTransportadoraRepository
					.findAllByTransporteTerrestre(Boolean.TRUE);

			listaAuxTransporte = calculo(listaTansporte, model,  model.getPrioridade());
			listaAuxTransporteRetorno = retornoResultado(listaAuxTransporte, model);
			return listaAuxTransporteRetorno;
			
		}

		if (TipoDeIntegracaoEnum.EMPTY.getDescricao().equals(tipoTransporte)) {
			List<CadastroTransportadoraModel> listaTansporte = cadastroTransportadoraRepository.findAll();

			listaAuxTransporte = calculo(listaTansporte, model, model.getPrioridade());
			listaAuxTransporteRetorno = retornoResultado(listaAuxTransporte, model);
			return listaAuxTransporteRetorno;
		}

		if (TipoDeIntegracaoEnum.AEREO.getDescricao().equals(tipoTransporte)) {
			List<CadastroTransportadoraModel> listaTansporte = cadastroTransportadoraRepository
					.findAllByTransporteAereo(Boolean.TRUE);

			listaAuxTransporte = calculo(listaTansporte, model, model.getPrioridade());
			listaAuxTransporteRetorno = retornoResultado(listaAuxTransporte, model);
			return listaAuxTransporteRetorno;
		}

		return null;
	}

	private List<CadastroTransportadoraModel> calculo(List<CadastroTransportadoraModel> resultadoTransporte,
			TransportadoraModel dadosRequisição, String prioridade) {
		
		
		List<CadastroTransportadoraModel> listaAuxTransporte = new ArrayList<>();
		String prioridadeTransportadora = prioridade;
		
		Double valor = 0D;
		Double minimo = 0D;

		for (CadastroTransportadoraModel resultadoAuxTransporte : resultadoTransporte) {

			if ("Prazo".equals(prioridadeTransportadora)) {
				valor = verificaRequisiçãoPrazo(resultadoAuxTransporte, dadosRequisição);
			} else if (("Valor".equals(prioridadeTransportadora))) {
				valor = verificaRequisiçãoValor(resultadoAuxTransporte, dadosRequisição);
			}

			int contador = 0;

			CadastroTransportadoraModel dadosPersistTransporte = new CadastroTransportadoraModel();

			dadosPersistTransporte.setId(resultadoAuxTransporte.getId());
			dadosPersistTransporte.setNome(resultadoAuxTransporte.getNome());
			dadosPersistTransporte.setTempoMedioPorKmAereo(resultadoAuxTransporte.getTempoMedioPorKmAereo());
			dadosPersistTransporte
					.setTempoMedioPorKmTerrestre(resultadoAuxTransporte.getTempoMedioPorKmTerrestre());
			dadosPersistTransporte.setTransporteAereo(resultadoAuxTransporte.isTransporteAereo());
			dadosPersistTransporte.setTransporteTerrestre(resultadoAuxTransporte.isTransporteTerrestre());
			dadosPersistTransporte.setValorPorKmAereo(resultadoAuxTransporte.getValorPorKmAereo());
			dadosPersistTransporte.setValorPorKmTerrestre(resultadoAuxTransporte.getValorPorKmTerrestre());

			if (minimo == 0.0 || minimo > valor) {

				listaAuxTransporte.clear();
				listaAuxTransporte.add(dadosPersistTransporte);
				minimo = valor;
				contador++;

			} else if (minimo.equals(valor) && contador == 0) {

				listaAuxTransporte.add(dadosPersistTransporte);
				minimo = valor;
			}

		}
		
		if (listaAuxTransporte.size() == 1 || prioridadeTransportadora != dadosRequisição.getPrioridade()) {

		} else {
			if ("Prazo".equals(prioridadeTransportadora)) {
				prioridadeTransportadora = "Valor";
			} else {
				prioridadeTransportadora = "Prazo";
			}
			calculo(listaAuxTransporte, dadosRequisição, prioridadeTransportadora);
		}
		return listaAuxTransporte;

	}

	private List<TransportadoraRetorno> retornoResultado (List<CadastroTransportadoraModel> listaAuxTransporte, TransportadoraModel transportadoraList){
			
			Double valor = 0D;
			

			List<TransportadoraRetorno> listaAuxTransporteRetorno = new ArrayList<>();
		
			for (CadastroTransportadoraModel aux : listaAuxTransporte) {
			
				TransportadoraRetorno dadosPersistTransporteRetorno = new TransportadoraRetorno();
				
				if ("Prazo".equals(transportadoraList.getPrioridade())) {
					valor = verificaRequisiçãoPrazo(aux, transportadoraList);
				} else if ("Valor".equals(transportadoraList.getPrioridade())) {
					valor = verificaRequisiçãoValor(aux, transportadoraList);
				}
				
			dadosPersistTransporteRetorno.setNome(aux.getNome());
			dadosPersistTransporteRetorno.setTempo((Double.valueOf(transportadoraList.getDistancia())
					/ valor) * 60);
			dadosPersistTransporteRetorno
					.setValor((transportadoraList.getDistancia() * valor) / 10);
			
			listaAuxTransporteRetorno.add(dadosPersistTransporteRetorno);
			
			
			}
			
			return listaAuxTransporteRetorno;

		}

	private Double verificaRequisiçãoPrazo(CadastroTransportadoraModel listaAuxTransporte,
			TransportadoraModel transportadoraList) {
		String tipoTransporte = transportadoraList.getTipoDeTransporte();
		Double valor = 0D;

		if (TipoDeIntegracaoEnum.TERRESTRE.getDescricao().equals(tipoTransporte)) {
			valor = Double.valueOf(listaAuxTransporte.getTempoMedioPorKmTerrestre());
		}

		if (TipoDeIntegracaoEnum.EMPTY.getDescricao().equals(tipoTransporte)) {
			valor = Double.valueOf(listaAuxTransporte.getTempoMedioPorKmAereo());
		}

		if (TipoDeIntegracaoEnum.AEREO.getDescricao().equals(tipoTransporte)) {
			valor = Double.valueOf(listaAuxTransporte.getTempoMedioPorKmAereo());
		}

		return valor;
	}

	private Double verificaRequisiçãoValor(CadastroTransportadoraModel listaAuxTransporte,
			TransportadoraModel transportadoraList) {
		String tipoTransporte = transportadoraList.getTipoDeTransporte();
		Double valor = 0D;

		if (TipoDeIntegracaoEnum.TERRESTRE.getDescricao().equals(tipoTransporte)) {
			valor = Double.valueOf(listaAuxTransporte.getValorPorKmTerrestre());
		}

		if (TipoDeIntegracaoEnum.EMPTY.getDescricao().equals(tipoTransporte)) {
			valor = Double.valueOf(listaAuxTransporte.getValorPorKmTerrestre());
		}

		if (TipoDeIntegracaoEnum.AEREO.getDescricao().equals(tipoTransporte)) {
			valor = Double.valueOf(listaAuxTransporte.getValorPorKmAereo());
		}

		return valor;
	}

}