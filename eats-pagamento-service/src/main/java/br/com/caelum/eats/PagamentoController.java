package br.com.caelum.eats;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/pagamentos", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@AllArgsConstructor
@Slf4j
class PagamentoController {

	private PagamentoRepository pagamentos;
	private PedidoRestClient pedidos;
	
	@GetMapping("/{id}")
	public Resource<PagamentoDto> detalha(@PathVariable Long id) {
		Pagamento pagamento = pagamentos.findById(id).orElseThrow(ResourceNotFoundException::new);
		
		List<Link> links = new ArrayList<>();
		
		Link self = linkTo(methodOn(PagamentoController.class).detalha(id)).withSelfRel();
		links.add(self);
		
		if (Pagamento.Status.CRIADO.equals(pagamento.getStatus())) {
			Link confirma = linkTo(methodOn(PagamentoController.class).confirma(id)).withRel("confirma");
			links.add(confirma);
			
			Link cancela = linkTo(methodOn(PagamentoController.class).cancela(id)).withRel("cancela");
			links.add(cancela);
		}
		
		PagamentoDto dto = new PagamentoDto(pagamento);
		Resource<PagamentoDto> resource = new Resource<>(dto, links);
		
		return resource;
	}
	
	@PostMapping
	public ResponseEntity<Resource<PagamentoDto>> cria(@RequestBody Pagamento pagamento,
													   UriComponentsBuilder uriBuilder) {
		pagamento.setStatus(Pagamento.Status.CRIADO);
		Pagamento salvo = pagamentos.save(pagamento);
		URI path = uriBuilder.path("/pagamentos/{id}").buildAndExpand(salvo.getId()).toUri();
		PagamentoDto dto = new PagamentoDto(salvo);
		
		Long id = salvo.getId();
		
		List<Link> links = new ArrayList<>();
		
		Link self = linkTo(methodOn(PagamentoController.class).detalha(id)).withSelfRel();
		links.add(self);
		
		Link confirma = linkTo(methodOn(PagamentoController.class).confirma(id)).withRel("confirma");
		links.add(confirma);
		
		Link cancela = linkTo(methodOn(PagamentoController.class).cancela(id)).withRel("cancela");
		links.add(cancela);
		
		Resource<PagamentoDto> resource = new Resource<>(dto, links);
		return ResponseEntity.created(path).body(resource);
	}
	
	@PutMapping("/{id}")
	public Resource<PagamentoDto> confirma(@PathVariable Long id) {
		Pagamento pagamento = pagamentos.findById(id).orElseThrow(ResourceNotFoundException::new);
		pagamento.setStatus(Pagamento.Status.CONFIRMADO);
		pagamentos.save(pagamento);
		
		Long pedidoId = pagamento.getPedidoId();
		pedidos.avisaQueFoiPago(pedidoId);
		
		List<Link> links = new ArrayList<>();
		
		Link self = linkTo(methodOn(PagamentoController.class).detalha(id)).withSelfRel();
		links.add(self);
		
		PagamentoDto dto = new PagamentoDto(pagamento);
		Resource<PagamentoDto> resource = new Resource<>(dto, links);
		
		return resource;
	}
	
	@DeleteMapping("/{id}")
	public Resource<PagamentoDto> cancela(@PathVariable Long id) {
		Pagamento pagamento = pagamentos.findById(id).orElseThrow(ResourceNotFoundException::new);
		pagamento.setStatus(Pagamento.Status.CANCELADO);
		pagamentos.save(pagamento);
		
		List<Link> links = new ArrayList<>();
		
		Link self = linkTo(methodOn(PagamentoController.class).detalha(id)).withSelfRel();
		links.add(self);
		
		PagamentoDto dto = new PagamentoDto(pagamento);
		Resource<PagamentoDto> resource = new Resource<>(dto, links);
		
		return resource;
	}

}