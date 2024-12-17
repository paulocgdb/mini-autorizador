package com.example.mini_autorizador.cartao.service;

import com.example.mini_autorizador.cartao.model.Cartao;
import com.example.mini_autorizador.cartao.repository.CartaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class CartaoService {
    private final CartaoRepository cartaoRepository;

    public CartaoService(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    public ResponseEntity<Cartao> criarCartao(Cartao cartao) {
        return cartaoRepository.findById(cartao.getNumeroCartao())
                .map(cartaoExistente -> ResponseEntity
                        .status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .body(cartaoExistente))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(cartaoRepository.save(cartao)));
    }

    public ResponseEntity<BigDecimal> obterSaldo(String numeroCartao) {
        return cartaoRepository.findById(numeroCartao)
                .map(cartao -> ResponseEntity.ok(cartao.getSaldo()))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
