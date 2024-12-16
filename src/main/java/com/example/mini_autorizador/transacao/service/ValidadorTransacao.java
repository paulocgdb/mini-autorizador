package com.example.mini_autorizador.transacao.service;

import com.example.mini_autorizador.cartao.model.Cartao;
import com.example.mini_autorizador.cartao.strategy.RegraAutorizacao;
import com.example.mini_autorizador.transacao.dto.TransacaoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidadorTransacao {
    private final List<RegraAutorizacao> regras;

    public ValidadorTransacao(List<RegraAutorizacao> regras) {
        this.regras = regras;
    }

    public void validar(TransacaoDTO transacao, Cartao cartao) {
        for (RegraAutorizacao regra : regras) {
            regra.validar(transacao, cartao);
        }
    }
}
