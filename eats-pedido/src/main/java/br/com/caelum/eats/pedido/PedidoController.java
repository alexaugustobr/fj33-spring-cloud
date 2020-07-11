package br.com.caelum.eats.pedido;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
class PedidoController {

	private PedidoRepository pedidos;

	@GetMapping("/pedidos")
	List<PedidoDto> lista() {
		return pedidos.findAll().stream()
				.map(pedido -> new PedidoDto(pedido)).collect(Collectors.toList());
	}


	@GetMapping("/pedidos/{id}")
	PedidoDto porId(@PathVariable("id") Long id) {
		Pedido pedido = pedidos.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		return new PedidoDto(pedido);
	}

	@PostMapping("/pedidos")
	PedidoDto adiciona(@RequestBody Pedido pedido) {
		pedido.setDataHora(LocalDateTime.now());
		pedido.setStatus(Pedido.Status.REALIZADO);
		pedido.getItens().forEach(item -> item.setPedido(pedido));
		pedido.getEntrega().setPedido(pedido);
		Pedido salvo = pedidos.save(pedido);
		return new PedidoDto(salvo);
	}

	@PutMapping("/pedidos/{id}/status")
	PedidoDto atualizaStatus(@RequestBody Pedido pedido) {
		pedidos.atualizaStatus(pedido.getStatus(), pedido);
		return new PedidoDto(pedido);
	}

	@GetMapping("/parceiros/restaurantes/{restauranteId}/pedidos/pendentes")
	List<PedidoDto> pendentes(@PathVariable("restauranteId") Long restauranteId) {
		return pedidos.doRestauranteSemOsStatus(restauranteId, Arrays.asList(Pedido.Status.REALIZADO, Pedido.Status.ENTREGUE)).stream()
				.map(pedido -> new PedidoDto(pedido)).collect(Collectors.toList());
	}
	
	@PutMapping("/pedidos/{id}/pago")
	void pago(@PathVariable("id") Long id) {
		Pedido pedido = pedidos.porIdComItens(id);
		if (pedido == null) {
			throw new ResourceNotFoundException();
		}
		pedido.setStatus(Pedido.Status.PAGO);
		pedidos.atualizaStatus(Pedido.Status.PAGO, pedido);
	}

}
