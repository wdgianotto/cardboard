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

	private int i;
	
	public Optional<TransportadoraModel> buscarTransportadora(Integer id) {
		return transportadoraRepository.findById(id);
	
	}
	
	public void salvarDados(TransportadoraModel transportadora) {
		
		transportadoraRepository.save(transportadora);
		
	}
	
	public void excluirDados(TransportadoraModel transportadora) {
		
		transportadoraRepository.delete(transportadora);
		
	}

public List<TransportadoraRetorno> calculaMelhorPrazo(TransportadoraModel model) {
	
	String tipoTransporte = model.getTipoDeTransporte();
	
	

	if (TipoDeIntegracaoEnum.AEREO.getDescricao().equals(tipoTransporte)) {
		
			List<CadastroTransportadoraModel> listaTansporteAereo = cadastroTransportadoraRepository.findAllByTransporteAereo(Boolean.TRUE);
			List<TransportadoraRetorno> listaAuxTransporteAereoRetorno = new ArrayList<>();
			List<CadastroTransportadoraModel> listaAuxTransporteAereo = new ArrayList<>();
		
			Long minimo = 0l;
		
		for(CadastroTransportadoraModel resultadoTransporteAereo :  listaTansporteAereo) {
			
			int contador = 0;
			
			
			CadastroTransportadoraModel dadosPersistTransporteAereo = new CadastroTransportadoraModel();
			
			dadosPersistTransporteAereo.setId(resultadoTransporteAereo.getId());
			dadosPersistTransporteAereo.setNome(resultadoTransporteAereo.getNome());
			dadosPersistTransporteAereo.setTempoMedioPorKmAereo(resultadoTransporteAereo.getTempoMedioPorKmAereo());
			dadosPersistTransporteAereo.setTempoMedioPorKmTerrestre(resultadoTransporteAereo.getTempoMedioPorKmTerrestre());
			dadosPersistTransporteAereo.setTransporteAereo(resultadoTransporteAereo.isTransporteAereo());
			dadosPersistTransporteAereo.setTransporteTerrestre(resultadoTransporteAereo.isTransporteTerrestre());
			dadosPersistTransporteAereo.setValorPorKmAereo(resultadoTransporteAereo.getValorPorKmAereo());
			dadosPersistTransporteAereo.setValorPorKmTerrestre(resultadoTransporteAereo.getValorPorKmTerrestre());

			if(minimo == 0.0 || minimo > resultadoTransporteAereo.getTempoMedioPorKmAereo()) {
			
				listaAuxTransporteAereo.clear();
				listaAuxTransporteAereo.add(dadosPersistTransporteAereo);
				minimo = resultadoTransporteAereo.getTempoMedioPorKmAereo();
				contador++;
			
			}	else if (minimo == resultadoTransporteAereo.getTempoMedioPorKmAereo() && contador == 0) {
				
				listaAuxTransporteAereo.add(dadosPersistTransporteAereo);
				minimo = resultadoTransporteAereo.getTempoMedioPorKmAereo();
			}
			
		}
				Double minimoEmpate = 0.0;
		
				if (listaAuxTransporteAereo.size() == 1) {
					
					TransportadoraRetorno dadosPersistTransporteAereoRetorno = new TransportadoraRetorno();
					
					dadosPersistTransporteAereoRetorno.setNome(listaAuxTransporteAereo.get(0).getNome());
					dadosPersistTransporteAereoRetorno.setTempo((Double.valueOf(model.getDistancia()) / listaAuxTransporteAereo.get(0).getTempoMedioPorKmAereo()) * 60);
					dadosPersistTransporteAereoRetorno.setValor((model.getDistancia() * listaAuxTransporteAereo.get(0).getValorPorKmAereo()) / 10);
					listaAuxTransporteAereoRetorno.add(dadosPersistTransporteAereoRetorno);
					
					return listaAuxTransporteAereoRetorno;
					
				} else {
					
					List<CadastroTransportadoraModel> listaAuxEmpateTransporteAereo = new ArrayList<>();
					
					for (CadastroTransportadoraModel resultadoEmpateTransporteAereo : listaAuxTransporteAereo) {
						
						int contadorEmpate = 0;
						
						CadastroTransportadoraModel dadosEmpatePersistTransporteAereo = new CadastroTransportadoraModel();
						
						
						dadosEmpatePersistTransporteAereo.setId(resultadoEmpateTransporteAereo.getId());
						dadosEmpatePersistTransporteAereo.setNome(resultadoEmpateTransporteAereo.getNome());
						dadosEmpatePersistTransporteAereo.setTempoMedioPorKmAereo(resultadoEmpateTransporteAereo.getTempoMedioPorKmAereo());
						dadosEmpatePersistTransporteAereo.setTempoMedioPorKmTerrestre(resultadoEmpateTransporteAereo.getTempoMedioPorKmTerrestre());
						dadosEmpatePersistTransporteAereo.setTransporteAereo(resultadoEmpateTransporteAereo.isTransporteAereo());
						dadosEmpatePersistTransporteAereo.setTransporteTerrestre(resultadoEmpateTransporteAereo.isTransporteTerrestre());
						dadosEmpatePersistTransporteAereo.setValorPorKmAereo(resultadoEmpateTransporteAereo.getValorPorKmAereo());
						dadosEmpatePersistTransporteAereo.setValorPorKmTerrestre(resultadoEmpateTransporteAereo.getValorPorKmTerrestre());
						
						if(minimoEmpate == 0.0 || minimoEmpate > resultadoEmpateTransporteAereo.getValorPorKmAereo()) {
							
							listaAuxEmpateTransporteAereo.clear();
							listaAuxEmpateTransporteAereo.add(dadosEmpatePersistTransporteAereo);
							minimoEmpate = resultadoEmpateTransporteAereo.getValorPorKmAereo();
							contadorEmpate++;
						
						}	else if (minimoEmpate.equals(resultadoEmpateTransporteAereo.getValorPorKmAereo())  && contadorEmpate == 0) {
							
							listaAuxEmpateTransporteAereo.add(dadosEmpatePersistTransporteAereo);
							minimoEmpate = resultadoEmpateTransporteAereo.getValorPorKmAereo();
						}
						
					}
					
					List<TransportadoraRetorno> listaAuxEmpateTransporteAereoRetorno = new ArrayList<>();
					
					for (CadastroTransportadoraModel resultadoEmpateValorTransporteAereo : listaAuxEmpateTransporteAereo) {
					
						TransportadoraRetorno dadosEmpatePersistTransporteAereoRetorno = new TransportadoraRetorno();
						
						dadosEmpatePersistTransporteAereoRetorno.setNome(resultadoEmpateValorTransporteAereo.getNome());
						dadosEmpatePersistTransporteAereoRetorno.setTempo((Double.valueOf(model.getDistancia()) / resultadoEmpateValorTransporteAereo.getTempoMedioPorKmAereo()) * 60);
						dadosEmpatePersistTransporteAereoRetorno.setValor((model.getDistancia() * resultadoEmpateValorTransporteAereo.getValorPorKmAereo()) / 10);
						listaAuxEmpateTransporteAereoRetorno.add(dadosEmpatePersistTransporteAereoRetorno);
						
					}
					
					return listaAuxEmpateTransporteAereoRetorno;
				}

		
	}
	
	if (TipoDeIntegracaoEnum.TERRESTRE.getDescricao().equals(tipoTransporte)) {
		
		List<CadastroTransportadoraModel> listaTansporteTerrestre = cadastroTransportadoraRepository.findAllByTransporteTerrestre(Boolean.TRUE);
		List<TransportadoraRetorno> listaAuxTransporteTerrestreRetorno = new ArrayList<>();
		List<CadastroTransportadoraModel> listaAuxTransporteTerrestre = new ArrayList<>();
	
		Long minimo = 0l;
	
	for(CadastroTransportadoraModel resultadoTransporteTerrestre :  listaTansporteTerrestre) {
		
		int contador = 0;
		
		
		CadastroTransportadoraModel dadosPersistTransporteTerrestre = new CadastroTransportadoraModel();
		
		dadosPersistTransporteTerrestre.setId(resultadoTransporteTerrestre.getId());
		dadosPersistTransporteTerrestre.setNome(resultadoTransporteTerrestre.getNome());
		dadosPersistTransporteTerrestre.setTempoMedioPorKmAereo(resultadoTransporteTerrestre.getTempoMedioPorKmAereo());
		dadosPersistTransporteTerrestre.setTempoMedioPorKmTerrestre(resultadoTransporteTerrestre.getTempoMedioPorKmTerrestre());
		dadosPersistTransporteTerrestre.setTransporteAereo(resultadoTransporteTerrestre.isTransporteAereo());
		dadosPersistTransporteTerrestre.setTransporteTerrestre(resultadoTransporteTerrestre.isTransporteTerrestre());
		dadosPersistTransporteTerrestre.setValorPorKmAereo(resultadoTransporteTerrestre.getValorPorKmAereo());
		dadosPersistTransporteTerrestre.setValorPorKmTerrestre(resultadoTransporteTerrestre.getValorPorKmTerrestre());

		if(minimo == 0.0 || minimo > resultadoTransporteTerrestre.getTempoMedioPorKmTerrestre()) {
		
			listaAuxTransporteTerrestre.clear();
			listaAuxTransporteTerrestre.add(dadosPersistTransporteTerrestre);
			minimo = resultadoTransporteTerrestre.getTempoMedioPorKmTerrestre();
			contador++;
		
		}	else if (minimo == resultadoTransporteTerrestre.getTempoMedioPorKmTerrestre() && contador == 0) {
			
			listaAuxTransporteTerrestre.add(dadosPersistTransporteTerrestre);
			minimo = resultadoTransporteTerrestre.getTempoMedioPorKmTerrestre();
		}
		
	}
			Double minimoEmpate = 0.0;
	
			if (listaAuxTransporteTerrestre.size() == 1) {
				
				TransportadoraRetorno dadosPersistTransporteTerrestreRetorno = new TransportadoraRetorno();
				
				dadosPersistTransporteTerrestreRetorno.setNome(listaAuxTransporteTerrestre.get(0).getNome());
				dadosPersistTransporteTerrestreRetorno.setTempo((Double.valueOf(model.getDistancia()) / listaAuxTransporteTerrestre.get(0).getTempoMedioPorKmTerrestre()) * 60);
				dadosPersistTransporteTerrestreRetorno.setValor((model.getDistancia() * listaAuxTransporteTerrestre.get(0).getValorPorKmTerrestre()) / 10);
				listaAuxTransporteTerrestreRetorno.add(dadosPersistTransporteTerrestreRetorno);
				
				return listaAuxTransporteTerrestreRetorno;
				
			} else {
				
				List<CadastroTransportadoraModel> listaAuxEmpateTransporteTerrestre = new ArrayList<>();
				
				for (CadastroTransportadoraModel resultadoEmpateTransporteTerrestre : listaAuxTransporteTerrestre) {
					
					int contadorEmpate = 0;
					
					CadastroTransportadoraModel dadosEmpatePersistTransporteTerrestre = new CadastroTransportadoraModel();
					
					
					dadosEmpatePersistTransporteTerrestre.setId(resultadoEmpateTransporteTerrestre.getId());
					dadosEmpatePersistTransporteTerrestre.setNome(resultadoEmpateTransporteTerrestre.getNome());
					dadosEmpatePersistTransporteTerrestre.setTempoMedioPorKmAereo(resultadoEmpateTransporteTerrestre.getTempoMedioPorKmAereo());
					dadosEmpatePersistTransporteTerrestre.setTempoMedioPorKmTerrestre(resultadoEmpateTransporteTerrestre.getTempoMedioPorKmTerrestre());
					dadosEmpatePersistTransporteTerrestre.setTransporteAereo(resultadoEmpateTransporteTerrestre.isTransporteAereo());
					dadosEmpatePersistTransporteTerrestre.setTransporteTerrestre(resultadoEmpateTransporteTerrestre.isTransporteTerrestre());
					dadosEmpatePersistTransporteTerrestre.setValorPorKmAereo(resultadoEmpateTransporteTerrestre.getValorPorKmAereo());
					dadosEmpatePersistTransporteTerrestre.setValorPorKmTerrestre(resultadoEmpateTransporteTerrestre.getValorPorKmTerrestre());
					
					if(minimoEmpate == 0.0 || minimoEmpate > resultadoEmpateTransporteTerrestre.getValorPorKmTerrestre()) {
						
						listaAuxEmpateTransporteTerrestre.clear();
						listaAuxEmpateTransporteTerrestre.add(dadosEmpatePersistTransporteTerrestre);
						minimoEmpate = resultadoEmpateTransporteTerrestre.getValorPorKmTerrestre();
						contadorEmpate++;
					
					}	else if (minimoEmpate.equals(resultadoEmpateTransporteTerrestre.getValorPorKmTerrestre())  && contadorEmpate == 0) {
						
						listaAuxEmpateTransporteTerrestre.add(dadosEmpatePersistTransporteTerrestre);
						minimoEmpate = resultadoEmpateTransporteTerrestre.getValorPorKmTerrestre();
					}
					
				}
				
				List<TransportadoraRetorno> listaAuxEmpateTransporteTerrestreRetorno = new ArrayList<>();
				
				for (CadastroTransportadoraModel resultadoEmpateValorTransporteTerrestre : listaAuxEmpateTransporteTerrestre) {
				
					TransportadoraRetorno dadosEmpatePersistTransporteTerrestreRetorno = new TransportadoraRetorno();
					
					dadosEmpatePersistTransporteTerrestreRetorno.setNome(resultadoEmpateValorTransporteTerrestre.getNome());
					dadosEmpatePersistTransporteTerrestreRetorno.setTempo((Double.valueOf(model.getDistancia()) / resultadoEmpateValorTransporteTerrestre.getTempoMedioPorKmAereo()) * 60);
					dadosEmpatePersistTransporteTerrestreRetorno.setValor((model.getDistancia() * resultadoEmpateValorTransporteTerrestre.getValorPorKmAereo()) / 10);
					listaAuxEmpateTransporteTerrestreRetorno.add(dadosEmpatePersistTransporteTerrestreRetorno);
					
				}
				
				return listaAuxEmpateTransporteTerrestreRetorno;
			}

	
}
	
if (TipoDeIntegracaoEnum.EMPTY.getDescricao().equals(tipoTransporte)) {
		
		List<CadastroTransportadoraModel> listaTansporteAll = cadastroTransportadoraRepository.findAll();
		List<TransportadoraRetorno> listaAuxTransporteAllRetorno = new ArrayList<>();
		List<CadastroTransportadoraModel> listaAuxTransporteAll = new ArrayList<>();
	
		Long minimo = 0l;
	
	for(CadastroTransportadoraModel resultadoTransporteAll :  listaTansporteAll) {
		
		int contador = 0;
		
		
		CadastroTransportadoraModel dadosPersistTransporteAll = new CadastroTransportadoraModel();
		
		dadosPersistTransporteAll.setId(resultadoTransporteAll.getId());
		dadosPersistTransporteAll.setNome(resultadoTransporteAll.getNome());
		dadosPersistTransporteAll.setTempoMedioPorKmAereo(resultadoTransporteAll.getTempoMedioPorKmAereo());
		dadosPersistTransporteAll.setTempoMedioPorKmTerrestre(resultadoTransporteAll.getTempoMedioPorKmTerrestre());
		dadosPersistTransporteAll.setTransporteAereo(resultadoTransporteAll.isTransporteAereo());
		dadosPersistTransporteAll.setTransporteTerrestre(resultadoTransporteAll.isTransporteTerrestre());
		dadosPersistTransporteAll.setValorPorKmAereo(resultadoTransporteAll.getValorPorKmAereo());
		dadosPersistTransporteAll.setValorPorKmTerrestre(resultadoTransporteAll.getValorPorKmTerrestre());

		if(minimo == 0.0 || minimo > resultadoTransporteAll.getTempoMedioPorKmTerrestre()) {
		
			listaAuxTransporteAll.clear();
			listaAuxTransporteAll.add(dadosPersistTransporteAll);
			minimo = resultadoTransporteAll.getTempoMedioPorKmTerrestre();
			contador++;
		
		}	else if (minimo == resultadoTransporteAll.getTempoMedioPorKmTerrestre() && contador == 0) {
			
			listaAuxTransporteAll.add(dadosPersistTransporteAll);
			minimo = resultadoTransporteAll.getTempoMedioPorKmTerrestre();
		}
		
	}
			Double minimoEmpate = 0.0;
	
			if (listaAuxTransporteAll.size() == 1) {
				
				TransportadoraRetorno dadosPersistTransporteTerrestreRetorno = new TransportadoraRetorno();
				
				dadosPersistTransporteTerrestreRetorno.setNome(listaAuxTransporteAll.get(0).getNome());
				dadosPersistTransporteTerrestreRetorno.setTempo((Double.valueOf(model.getDistancia()) / listaAuxTransporteAll.get(0).getTempoMedioPorKmTerrestre()) * 60);
				dadosPersistTransporteTerrestreRetorno.setValor((model.getDistancia() * listaAuxTransporteAll.get(0).getValorPorKmTerrestre()) / 10);
				listaAuxTransporteAllRetorno.add(dadosPersistTransporteTerrestreRetorno);
				
				return listaAuxTransporteAllRetorno;
				
			} else {
				
				List<CadastroTransportadoraModel> listaAuxEmpateTransporteTerrestre = new ArrayList<>();
				
				for (CadastroTransportadoraModel resultadoEmpateTransporteTerrestre : listaAuxTransporteAll) {
					
					int contadorEmpate = 0;
					
					CadastroTransportadoraModel dadosEmpatePersistTransporteTerrestre = new CadastroTransportadoraModel();
					
					
					dadosEmpatePersistTransporteTerrestre.setId(resultadoEmpateTransporteTerrestre.getId());
					dadosEmpatePersistTransporteTerrestre.setNome(resultadoEmpateTransporteTerrestre.getNome());
					dadosEmpatePersistTransporteTerrestre.setTempoMedioPorKmAereo(resultadoEmpateTransporteTerrestre.getTempoMedioPorKmAereo());
					dadosEmpatePersistTransporteTerrestre.setTempoMedioPorKmTerrestre(resultadoEmpateTransporteTerrestre.getTempoMedioPorKmTerrestre());
					dadosEmpatePersistTransporteTerrestre.setTransporteAereo(resultadoEmpateTransporteTerrestre.isTransporteAereo());
					dadosEmpatePersistTransporteTerrestre.setTransporteTerrestre(resultadoEmpateTransporteTerrestre.isTransporteTerrestre());
					dadosEmpatePersistTransporteTerrestre.setValorPorKmAereo(resultadoEmpateTransporteTerrestre.getValorPorKmAereo());
					dadosEmpatePersistTransporteTerrestre.setValorPorKmTerrestre(resultadoEmpateTransporteTerrestre.getValorPorKmTerrestre());
					
					if(minimoEmpate == 0.0 || minimoEmpate > resultadoEmpateTransporteTerrestre.getValorPorKmTerrestre()) {
						
						listaAuxEmpateTransporteTerrestre.clear();
						listaAuxEmpateTransporteTerrestre.add(dadosEmpatePersistTransporteTerrestre);
						minimoEmpate = resultadoEmpateTransporteTerrestre.getValorPorKmTerrestre();
						contadorEmpate++;
					
					}	else if (minimoEmpate.equals(resultadoEmpateTransporteTerrestre.getValorPorKmTerrestre())  && contadorEmpate == 0) {
						
						listaAuxEmpateTransporteTerrestre.add(dadosEmpatePersistTransporteTerrestre);
						minimoEmpate = resultadoEmpateTransporteTerrestre.getValorPorKmTerrestre();
					}
					
				}
				
				List<TransportadoraRetorno> listaAuxEmpateTransporteTerrestreRetorno = new ArrayList<>();
				
				for (CadastroTransportadoraModel resultadoEmpateValorTransporteTerrestre : listaAuxEmpateTransporteTerrestre) {
				
					TransportadoraRetorno dadosEmpatePersistTransporteTerrestreRetorno = new TransportadoraRetorno();
					
					dadosEmpatePersistTransporteTerrestreRetorno.setNome(resultadoEmpateValorTransporteTerrestre.getNome());
					dadosEmpatePersistTransporteTerrestreRetorno.setTempo((Double.valueOf(model.getDistancia()) / resultadoEmpateValorTransporteTerrestre.getTempoMedioPorKmAereo()) * 60);
					dadosEmpatePersistTransporteTerrestreRetorno.setValor((model.getDistancia() * resultadoEmpateValorTransporteTerrestre.getValorPorKmAereo()) / 10);
					listaAuxEmpateTransporteTerrestreRetorno.add(dadosEmpatePersistTransporteTerrestreRetorno);
					
				}
				
				return listaAuxEmpateTransporteTerrestreRetorno;
			}

	
}
	return null;
	
}

}