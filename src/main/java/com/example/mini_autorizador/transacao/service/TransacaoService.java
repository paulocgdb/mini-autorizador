package com.example.mini_autorizador.transacao.service;

import com.example.mini_autorizador.cartao.model.Cartao;
import com.example.mini_autorizador.cartao.repository.CartaoRepository;
import com.example.mini_autorizador.transacao.dto.TransacaoDTO;
import com.example.mini_autorizador.transacao.exception.RegraAutorizacaoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransacaoService {
    private final CartaoRepository cartaoRepository;
    private final ValidadorTransacao validadorTransacao;

    public TransacaoService(CartaoRepository cartaoRepository, ValidadorTransacao validadorTransacao) {
        this.cartaoRepository = cartaoRepository;
        this.validadorTransacao = validadorTransacao;
    }

    public ResponseEntity<Object> processarTransacao(TransacaoDTO transacaoDTO) {
        return cartaoRepository.findByNumeroCartaoForUpdate(transacaoDTO.getNumeroCartao())
                .<ResponseEntity<Object>>map(cartao -> {
                    try {
                        validadorTransacao.validar(transacaoDTO, cartao);
                        return processarTransacaoComSucesso(cartao, transacaoDTO);
                    } catch (RegraAutorizacaoException ex) {
                        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body((Object) ex.getMessage());
                    }
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body((Object) "CARTAO_INEXISTENTE"));
    }

    private ResponseEntity<Object> processarTransacaoComSucesso(Cartao cartao, TransacaoDTO transacaoDTO) {
        cartao.setSaldo(cartao.getSaldo().subtract(transacaoDTO.getValor()));
        cartaoRepository.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).body("OK");
    }
}
