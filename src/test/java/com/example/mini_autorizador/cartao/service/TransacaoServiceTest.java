package com.example.mini_autorizador.cartao.service;

import com.example.mini_autorizador.cartao.model.Cartao;
import com.example.mini_autorizador.cartao.repository.CartaoRepository;
import com.example.mini_autorizador.transacao.dto.TransacaoDTO;
import com.example.mini_autorizador.transacao.exception.RegraAutorizacaoException;
import com.example.mini_autorizador.transacao.service.TransacaoService;
import com.example.mini_autorizador.transacao.service.ValidadorTransacao;
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

class TransacaoServiceTest {
    @Mock
    private CartaoRepository cartaoRepository;

    @Mock
    private ValidadorTransacao validadorTransacao;

    @InjectMocks
    private TransacaoService transacaoService;

    private Cartao cartao;
    private TransacaoDTO transacaoDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cartao = new Cartao();
        cartao.setNumeroCartao("1234567890123456");
        cartao.setSenha("1234");
        cartao.setSaldo(BigDecimal.valueOf(500.00));

        transacaoDTO = new TransacaoDTO();
        transacaoDTO.setNumeroCartao("1234567890123456");
        transacaoDTO.setSenhaCartao("1234");
        transacaoDTO.setValor(BigDecimal.valueOf(100.00));
    }

    @Test
    void processar_transacao_com_sucesso() {
        when(cartaoRepository.findByNumeroCartaoForUpdate("1234567890123456"))
                .thenReturn(Optional.of(cartao));

        ResponseEntity<Object> response = transacaoService.processarTransacao(transacaoDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("OK", response.getBody());

        assertEquals(BigDecimal.valueOf(400.00), cartao.getSaldo());
        verify(cartaoRepository, times(1)).save(cartao);
    }

    @Test
    void processar_transacao_cartao_inexistente() {
        when(cartaoRepository.findByNumeroCartaoForUpdate("1234567890123456"))
                .thenReturn(Optional.empty());

        ResponseEntity<Object> response = transacaoService.processarTransacao(transacaoDTO);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals("CARTAO_INEXISTENTE", response.getBody());

        verify(cartaoRepository, never()).save(any(Cartao.class));
    }

    @Test
    void processar_transacao_com_erro_de_validacao() {
        when(cartaoRepository.findByNumeroCartaoForUpdate("1234567890123456"))
                .thenReturn(Optional.of(cartao));

        doThrow(new RegraAutorizacaoException("SALDO_INSUFICIENTE"))
                .when(validadorTransacao).validar(transacaoDTO, cartao);

        ResponseEntity<Object> response = transacaoService.processarTransacao(transacaoDTO);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals("SALDO_INSUFICIENTE", response.getBody());

        verify(cartaoRepository, never()).save(any(Cartao.class));
    }
}

