package br.com.caelum.eats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "restaurantes")
public class Restaurante {

	@Id
	private Long id;
	
	private String cep;

	private Boolean aprovado;
	
	private Long tipoDeCozinhaId;

}
