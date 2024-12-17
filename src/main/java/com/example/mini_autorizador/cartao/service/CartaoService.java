package com.example.mini_autorizador.cartao.service;

import com.example.mini_autorizador.cartao.model.Cartao;
import com.example.mini_autorizador.cartao.repository.CartaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
public class CartaoService {
    private final CartaoRepository cartaoRepository;

    public CartaoService(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    public ResponseEntity<Object> criarCartao(Cartao cartao) {
        return Optional.of(cartao)
                .filter(c -> !cartaoRepository.existsById(c.getNumeroCartao()))
                .map(c -> ResponseEntity.status(HttpStatus.CREATED).body((Object) cartaoRepository.save(c)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body((Object) "CARTAO_EXISTENTE"));
    }

    public ResponseEntity<BigDecimal> obterSaldo(String numeroCartao) {
        return cartaoRepository.findById(numeroCartao)
                .map(cartao -> ResponseEntity.ok(cartao.getSaldo()))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
