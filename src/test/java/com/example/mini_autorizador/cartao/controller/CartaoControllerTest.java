package com.example.mini_autorizador.cartao.controller;

import com.example.mini_autorizador.cartao.model.Cartao;
import com.example.mini_autorizador.cartao.service.CartaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CartaoControllerTest {

    @Mock
    private CartaoService cartaoService;

    @InjectMocks
    private CartaoController cartaoController;

    private Cartao cartao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cartao = new Cartao();
        cartao.setNumeroCartao("1234567890123456");
        cartao.setSenha("1234");
        cartao.setSaldo(BigDecimal.valueOf(500.00));
    }

    @Test
    void criar_cartao_com_sucesso() {
        when(cartaoService.criarCartao(any(Cartao.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(cartao));

        ResponseEntity<Cartao> response = cartaoController.criarCartao(cartao);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cartao, response.getBody());
    }

    @Test
    void criar_cartao_existente() {
        when(cartaoService.criarCartao(any(Cartao.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(cartao));

        ResponseEntity<Cartao> response = cartaoController.criarCartao(cartao);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(cartao, response.getBody());
    }

    @Test
    void obter_saldo_com_sucesso() {
        when(cartaoService.obterSaldo("1234567890123456"))
                .thenReturn(ResponseEntity.ok(BigDecimal.valueOf(500.00)));

        ResponseEntity<BigDecimal> response = cartaoController.obterSaldo("1234567890123456");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(BigDecimal.valueOf(500.00), response.getBody());
    }

    @Test
    void obter_saldo_cartao_inexistente() {
        when(cartaoService.obterSaldo("1234567890123456"))
                .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

        ResponseEntity<BigDecimal> response = cartaoController.obterSaldo("1234567890123456");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}