package com.cardbox.cardbox.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cardbox.cardbox.Enums.TipoDeIntegracaoEnum;
import com.cardbox.cardbox.Models.CadastroTransportadoraModel;
import com.cardbox.cardbox.Models.TransportadoraModel;
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

	public List<CadastroTransportadoraModel> calculaMelhorPrazo(TransportadoraModel model) {
		
		String tipoTransporte = model.getTipoDeTransporte();
		
		

		if (TipoDeIntegracaoEnum.AEREO.getDescricao().equals(tipoTransporte)) {
			
			List<CadastroTransportadoraModel> listaTansporteAereo = cadastroTransportadoraRepository.findAllByTransporteAereo(Boolean.TRUE);
			
			Long minimo = 0l;
			
			List<CadastroTransportadoraModel> listaAuxTransporteAereo = new ArrayList<>();
			
			for(CadastroTransportadoraModel resultadoTransporteAereo :  listaTansporteAereo) {
				
				int contador = 0;
				
				CadastroTransportadoraModel dadosPersistTransporteAereo = new CadastroTransportadoraModel();
				
				dadosPersistTransporteAereo.setId(resultadoTransporteAereo.getId());
				dadosPersistTransporteAereo.setNome(resultadoTransporteAereo.getNome());
				dadosPersistTransporteAereo.setTempoMedioPorKmAereo(resultadoTransporteAereo.getTempoMedioPorKmAereo());
				dadosPersistTransporteAereo.setValorPorKmAereo(resultadoTransporteAereo.getValorPorKmAereo());
				
				
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
						return listaAuxTransporteAereo;
					} else {
						
						List<CadastroTransportadoraModel> listaAuxEmpateTransporteAereo = new ArrayList<>();
						
						for (CadastroTransportadoraModel resultadoEmpateTransporteAereo : listaAuxTransporteAereo) {
							
							int contadorEmpate = 0;
							
							CadastroTransportadoraModel dadosEmpatePersistTransporteAereo = new CadastroTransportadoraModel();
							
							dadosEmpatePersistTransporteAereo.setId(resultadoEmpateTransporteAereo.getId());
							dadosEmpatePersistTransporteAereo.setNome(resultadoEmpateTransporteAereo.getNome());
							dadosEmpatePersistTransporteAereo.setTempoMedioPorKmAereo(resultadoEmpateTransporteAereo.getTempoMedioPorKmAereo());
							dadosEmpatePersistTransporteAereo.setValorPorKmAereo(resultadoEmpateTransporteAereo.getValorPorKmAereo());
							
							if(minimoEmpate == 0.0 || minimoEmpate > resultadoEmpateTransporteAereo.getValorPorKmAereo()) {
								
								listaAuxEmpateTransporteAereo.clear();
								listaAuxEmpateTransporteAereo.add(dadosEmpatePersistTransporteAereo);
								minimoEmpate = resultadoEmpateTransporteAereo.getValorPorKmAereo();
								contadorEmpate++;
							
							}	else if (minimoEmpate.equals(resultadoEmpateTransporteAereo.getValorPorKmAereo())  && contadorEmpate == 0) {
								
								listaAuxTransporteAereo.add(dadosEmpatePersistTransporteAereo);
								minimoEmpate = resultadoEmpateTransporteAereo.getValorPorKmAereo();
							}
							
						}
						
						
						return listaAuxTransporteAereo;
					}
				
//			return listaAuxTransporteAereo;
			
		}
		
		return null;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
//public List<CadastroTransportadoraModel> calculaMelhorPrazo(TransportadoraModel model) {
//		
//		String tipoTransporte = model.getTipoDeTransporte();
//
//		if (TipoDeIntegracaoEnum.AEREO.getDescricao().equals(tipoTransporte)) {
//			
//			List<CadastroTransportadoraModel> cadastro = cadastroTransportadoraRepository.findAllByTransporteAereo(Boolean.TRUE);
//			
//			Double minimo = 0.0;
//			
//			List<CadastroTransportadoraModel> reto = new ArrayList<>();
//			
//			for(CadastroTransportadoraModel ret :  cadastro) {
//				
//				CadastroTransportadoraModel r = new CadastroTransportadoraModel();
//				
//				if(minimo == 0.0 || minimo > ret.getTempoMedioPorKmAereo()) {
//					
//					
//					
//					minimo = ret.getTempoMedioPorKmAereo();
//					r.setId(ret.getId());
//					r.setNome(ret.getNome());
//					r.setTempoMedioPorKmAereo(ret.getTempoMedioPorKmAereo());
//					reto.add(r);
//					
//					if(r.getTempoMedioPorKmAereo() > minimo) {
//						reto.remove(r);
//					}
//			
//				} 
//				
//				
//			}
//			
//			return reto;
//		}
//		
//		return null;
//		
//	}
	
	
//	r.setId(ret.getId());
//	r.setNome(ret.getNome());
//	r.setTempoMedioPorKmAereo(ret.getTempoMedioPorKmAereo());
//	reto.add(r);

}