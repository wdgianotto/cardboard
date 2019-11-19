package com.cardbox.cardbox.Service;

import java.util.ArrayList;
import java.util.List;

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

	String PRAZO = "Prazo";
	String VALOR = "Valor";


	public List<TransportadoraRetorno> calculaMelhorPrazoValor(TransportadoraModel model) {

		List<TransportadoraRetorno> listaAuxTransporteRetorno = new ArrayList<>();
		List<CadastroTransportadoraModel> listaAuxTransporte = new ArrayList<>();
		String tipoTransporte = model.getTipoDeTransporte();

		if (TipoDeIntegracaoEnum.TERRESTRE.getDescricao().equals(tipoTransporte)) {
			List<CadastroTransportadoraModel> listaTansporte = cadastroTransportadoraRepository
					.findAllByTransporteTerrestre(Boolean.TRUE);

			listaAuxTransporte = calculo(listaTansporte, model, model.getPrioridade());
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
													  TransportadoraModel dadosRequisicao, String prioridade) {

		List<CadastroTransportadoraModel> retorno = new ArrayList<>();
		List<CadastroTransportadoraModel> listaAuxTransporte = new ArrayList<>();
		String prioridadeTransportadora = prioridade;

		Double valor = 0D;
		Double minimo = 0D;


		for (CadastroTransportadoraModel resultadoAuxTransporte : resultadoTransporte) {

			if (PRAZO.equals(prioridadeTransportadora)) {
				valor = verificaRequisicaoPrazo(resultadoAuxTransporte, dadosRequisicao);
			} else if ((VALOR.equals(prioridadeTransportadora))) {
				valor = verificaRequisicaoValor(resultadoAuxTransporte, dadosRequisicao);
			}

			int contador = 0;

			CadastroTransportadoraModel dadosPersistTransporte = new CadastroTransportadoraModel();

			dadosPersistTransporte.setId(resultadoAuxTransporte.getId());
			dadosPersistTransporte.setNome(resultadoAuxTransporte.getNome());
			dadosPersistTransporte.setTempoMedioPorKmAereo(resultadoAuxTransporte.getTempoMedioPorKmAereo());
			dadosPersistTransporte.setTempoMedioPorKmTerrestre(resultadoAuxTransporte.getTempoMedioPorKmTerrestre());
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
		if (listaAuxTransporte.size() == 1 || prioridadeTransportadora != dadosRequisicao.getPrioridade()) {
			retorno = listaAuxTransporte;

		} else {
			retorno = desempate(listaAuxTransporte, dadosRequisicao, prioridadeTransportadora);
		}
			return retorno;
		}

	private List<CadastroTransportadoraModel> desempate(List<CadastroTransportadoraModel> resultadoTransporte,
													  TransportadoraModel dadosRequisicao, String prioridade) {

		List<CadastroTransportadoraModel> retorno = new ArrayList<>();
		List<CadastroTransportadoraModel> listaAuxTransporte = new ArrayList<>();

		if (PRAZO.equals(prioridade)) {
			prioridade = VALOR;
		} else {
			prioridade = PRAZO;
		}

		String prioridadeTransportadora = prioridade;

		Double valor = 0D;
		Double minimo = 0D;

		for (CadastroTransportadoraModel resultadoAuxTransporte : resultadoTransporte) {

			if (PRAZO.equals(prioridadeTransportadora)) {
				valor = verificaRequisicaoPrazo(resultadoAuxTransporte, dadosRequisicao);
			} else if ((VALOR.equals(prioridadeTransportadora))) {
				valor = verificaRequisicaoValor(resultadoAuxTransporte, dadosRequisicao);
			}

			int contador = 0;

			CadastroTransportadoraModel dadosPersistTransporte = new CadastroTransportadoraModel();

			dadosPersistTransporte.setId(resultadoAuxTransporte.getId());
			dadosPersistTransporte.setNome(resultadoAuxTransporte.getNome());
			dadosPersistTransporte.setTempoMedioPorKmAereo(resultadoAuxTransporte.getTempoMedioPorKmAereo());
			dadosPersistTransporte.setTempoMedioPorKmTerrestre(resultadoAuxTransporte.getTempoMedioPorKmTerrestre());
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
		return listaAuxTransporte;
	}

	private List<TransportadoraRetorno> retornoResultado(List<CadastroTransportadoraModel> listaAuxTransporte,
														 TransportadoraModel transportadoraList) {

		Double valor = 0D;
		Double tempo = 0D;

		List<TransportadoraRetorno> listaAuxTransporteRetorno = new ArrayList<>();

		for (CadastroTransportadoraModel aux : listaAuxTransporte) {

			TransportadoraRetorno dadosPersistTransporteRetorno = new TransportadoraRetorno();

				tempo = verificaRequisicaoPrazo(aux, transportadoraList);
				valor = verificaRequisicaoValor(aux, transportadoraList);

			dadosPersistTransporteRetorno.setNome(aux.getNome());
			dadosPersistTransporteRetorno.setTempo((Double.valueOf(transportadoraList.getDistancia()) / tempo) * 60);
			dadosPersistTransporteRetorno.setValor((transportadoraList.getDistancia() * valor) / 10);

			listaAuxTransporteRetorno.add(dadosPersistTransporteRetorno);

		}

		return listaAuxTransporteRetorno;

	}

	private Double verificaRequisicaoPrazo(CadastroTransportadoraModel listaAuxTransporte,
                                           TransportadoraModel transportadoraList) {
		String tipoTransporte = transportadoraList.getTipoDeTransporte();
		Double valor = 0D;

		if (TipoDeIntegracaoEnum.TERRESTRE.getDescricao().equals(tipoTransporte)) {
			valor = Double.valueOf(listaAuxTransporte.getTempoMedioPorKmTerrestre());
		}

//		if (TipoDeIntegracaoEnum.EMPTY.getDescricao().equals(tipoTransporte) && PRAZO.equals(transportadoraList.getPrioridade()) && listaAuxTransporte.getTempoMedioPorKmAereo() > 0) {
//			valor = Double.valueOf(listaAuxTransporte.getTempoMedioPorKmAereo());
//		} else {
//			valor = Double.valueOf(listaAuxTransporte.getTempoMedioPorKmTerrestre());
//		}

		if (TipoDeIntegracaoEnum.AEREO.getDescricao().equals(tipoTransporte)) {
			valor = Double.valueOf(listaAuxTransporte.getTempoMedioPorKmAereo());
		}

		return valor;
	}

	private Double verificaRequisicaoValor(CadastroTransportadoraModel listaAuxTransporte,
                                           TransportadoraModel transportadoraList) {
		String tipoTransporte = transportadoraList.getTipoDeTransporte();
		Double valor = 0D;

		if (TipoDeIntegracaoEnum.TERRESTRE.getDescricao().equals(tipoTransporte)) {
			valor = Double.valueOf(listaAuxTransporte.getValorPorKmTerrestre());
		}

//		if (TipoDeIntegracaoEnum.EMPTY.getDescricao().equals(tipoTransporte) && PRAZO.equals(transportadoraList.getPrioridade()) && listaAuxTransporte.getValorPorKmAereo() > 0) {
//			valor = Double.valueOf(listaAuxTransporte.getValorPorKmAereo());
//		} else {
//			valor = Double.valueOf(listaAuxTransporte.getValorPorKmTerrestre());
//		}

		if (TipoDeIntegracaoEnum.AEREO.getDescricao().equals(tipoTransporte)) {
			valor = Double.valueOf(listaAuxTransporte.getValorPorKmAereo());
		}

		return valor;
	}

}