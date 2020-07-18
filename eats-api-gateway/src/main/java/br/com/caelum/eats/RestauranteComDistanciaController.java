package br.com.caelum.eats;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
class RestauranteComDistanciaController {
	
	private RestauranteRestClient restaurantes;
	private DistanciaRestClient distancias;
	
	@GetMapping("/restaurantes-com-distancia/{cep}/restaurante/{restauranteId}")
	public Map<String, Object> porCepEIdComDistancia(@PathVariable("cep") String cep,
													 @PathVariable("restauranteId") Long restauranteId) {
		Map<String, Object> dadosRestaurante = restaurantes.porId(restauranteId);
		Map<String, Object> dadosDistancia = distancias.porCepEId(cep, restauranteId);
		dadosRestaurante.putAll(dadosDistancia);
		return dadosRestaurante;
	}
}
