package br.com.caelum.eats;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("monolito") // modificado
interface PedidoRestClient {
	
	@PutMapping("/pedidos/{pedidoId}/pago")
	void avisaQueFoiPago(@PathVariable("pedidoId") Long pedidoId);
	
}
