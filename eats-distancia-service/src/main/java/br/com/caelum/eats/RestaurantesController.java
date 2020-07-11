package br.com.caelum.eats;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@AllArgsConstructor
@Slf4j
class RestaurantesController {
	
	private RestauranteRepository restaurantes;
	
	@PostMapping("/restaurantes")
	ResponseEntity<Restaurante> adiciona(@RequestBody Restaurante restaurante, UriComponentsBuilder uriBuilder) {
		log.info("Insere novo restaurante: " + restaurante);
		Restaurante salvo = restaurantes.insert(restaurante);
		UriComponents uriComponents = uriBuilder.path("/restaurantes/{id}").buildAndExpand(salvo.getId());
		URI uri = uriComponents.toUri();
		return ResponseEntity.created(uri).contentType(MediaType.APPLICATION_JSON).body(salvo);
	}
	
	@PutMapping("/restaurantes/{id}")
	Restaurante atualiza(@PathVariable("id") Long id, @RequestBody Restaurante restaurante) {
		if (!restaurantes.existsById(id)) {
			throw new ResourceNotFoundException();
		}
		log.info("Atualiza restaurante: " + restaurante);
		return restaurantes.save(restaurante);
	}
	
}
