package com.example.mini_autorizador.cartao.strategy;

import com.example.mini_autorizador.cartao.model.Cartao;
import com.example.mini_autorizador.transacao.dto.TransacaoDTO;

public interface RegraAutorizacao {
    void validar(TransacaoDTO transacao, Cartao cartao);
}
