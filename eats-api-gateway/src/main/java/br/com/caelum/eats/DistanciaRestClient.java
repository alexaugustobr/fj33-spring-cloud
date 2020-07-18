package br.com.caelum.eats;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
class DistanciaRestClient {
	
	private RestTemplate restTemplate;
	private String distanciaServiceUrl;
	
	DistanciaRestClient(RestTemplate restTemplate,
						@Value("${zuul.routes.distancia.url}") String distanciaServiceUrl) {
		this.restTemplate = restTemplate;
		this.distanciaServiceUrl = distanciaServiceUrl;
	}
	
	Map<String,Object> porCepEId(String cep, Long restauranteId) {
		String url = distanciaServiceUrl + "/restaurantes/" + cep + "/restaurante/" + restauranteId;
		return restTemplate.getForObject(url, Map.class);
	}
	
}
