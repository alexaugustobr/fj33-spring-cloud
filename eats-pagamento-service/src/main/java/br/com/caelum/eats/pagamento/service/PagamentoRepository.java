package br.com.caelum.eats.pagamento.service;

import org.springframework.data.jpa.repository.JpaRepository;

interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

}
