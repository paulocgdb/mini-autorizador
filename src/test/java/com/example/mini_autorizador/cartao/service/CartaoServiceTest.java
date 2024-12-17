package com.example.mini_autorizador.cartao.service;

import com.example.mini_autorizador.cartao.model.Cartao;
import com.example.mini_autorizador.cartao.repository.CartaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CartaoServiceTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private CartaoService cartaoService;

    private Cartao cartao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartao = new Cartao();
        cartao.setNumeroCartao("1234567890123456");
        cartao.setSenha("1234");
        cartao.setSaldo(new BigDecimal("500.00"));
    }

    @Test
    void test_criar_cartao_cartao_nao_existente() {
        when(cartaoRepository.findById(cartao.getNumeroCartao())).thenReturn(Optional.empty());
        when(cartaoRepository.save(cartao)).thenReturn(cartao);

        ResponseEntity<Cartao> response = cartaoService.criarCartao(cartao);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cartao, response.getBody());
        verify(cartaoRepository, times(1)).findById(cartao.getNumeroCartao());
        verify(cartaoRepository, times(1)).save(cartao);
    }

    @Test
    void test_criar_cartao_cartao_existente() {
        when(cartaoRepository.findById(cartao.getNumeroCartao())).thenReturn(Optional.of(cartao));

        ResponseEntity<Cartao> response = cartaoService.criarCartao(cartao);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(cartao, response.getBody());
        verify(cartaoRepository, times(1)).findById(cartao.getNumeroCartao());
        verify(cartaoRepository, times(0)).save(any());
    }

    @Test
    void test_obter_saldo_cartao_existente() {
        when(cartaoRepository.findById(cartao.getNumeroCartao())).thenReturn(Optional.of(cartao));

        ResponseEntity<BigDecimal> response = cartaoService.obterSaldo(cartao.getNumeroCartao());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartao.getSaldo(), response.getBody());
        verify(cartaoRepository, times(1)).findById(cartao.getNumeroCartao());
    }

    @Test
    void test_obter_saldo_cartao_nao_existente() {
        when(cartaoRepository.findById(cartao.getNumeroCartao())).thenReturn(Optional.empty());

        ResponseEntity<BigDecimal> response = cartaoService.obterSaldo(cartao.getNumeroCartao());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(cartaoRepository, times(1)).findById(cartao.getNumeroCartao());
    }
}
