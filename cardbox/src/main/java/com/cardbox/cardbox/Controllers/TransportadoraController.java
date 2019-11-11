package com.cardbox.cardbox.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import com.cardbox.cardbox.Models.CadastroTransportadoraModel;
import com.cardbox.cardbox.Models.TransportadoraModel;
import com.cardbox.cardbox.Models.TransportadoraRetorno;
import com.cardbox.cardbox.Service.TransportadoraService;

@RestController
@RequestMapping("/transportadora")
public class TransportadoraController {

	@Autowired
	private TransportadoraService transportadoraService;
	

	@RequestMapping(value = ("/{origem}/{destino}/{distancia}/{tipodetransporte}/{prioridade}"), method = RequestMethod.GET)
	public ResponseEntity<List<TransportadoraRetorno>> find (@PathVariable String origem,
									 				 @PathVariable String destino,
								 				 	 @PathVariable Long distancia,
								 				 	 @PathVariable String tipodetransporte,
								 				 	 @PathVariable String prioridade) {

		TransportadoraModel model = new TransportadoraModel(origem, destino, distancia, tipodetransporte, prioridade);
		
		List<TransportadoraRetorno> ret = transportadoraService.calculaMelhorPrazoValor(model);
		
		return ResponseEntity.ok().body(ret);
		
		
	}
	
//	@RequestMapping(value = ("/{origem}/{destino}/{distancia}/{tipodetransporte}/{prioridade}"), method = RequestMethod.GET)
//	public ResponseEntity<List<CadastroTransportadoraModel>> find (@PathVariable String origem,
//									 				 @PathVariable String destino,
//								 				 	 @PathVariable Long distancia,
//								 				 	 @PathVariable String tipodetransporte,
//								 				 	 @PathVariable String prioridade) {
//
//		TransportadoraModel model = new TransportadoraModel(origem, destino, distancia, tipodetransporte, prioridade);
//		
//		List<CadastroTransportadoraModel> ret = transportadoraService.calculaMelhorPrazo(model);
//		
//		return ResponseEntity.ok().body(ret);
//		
//		
//	}
	
//	@RequestMapping(value = "/{model}", method=RequestMethod.POST)
//	public void salvar (@PathVariable Integer id, 
//						@PathVariable String destino) {
//		Optional<TransportadoraModel> model = transportadoraService.buscarTransportadora(id);
//		model.get().setDestino(destino);
//		transportadoraService.salvarDados(model);
//
//	}
}