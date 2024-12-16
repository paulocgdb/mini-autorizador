package com.example.mini_autorizador.cartao.strategy;

import com.example.mini_autorizador.cartao.model.Cartao;
import com.example.mini_autorizador.transacao.dto.TransacaoDTO;
import com.example.mini_autorizador.transacao.exception.RegraAutorizacaoException;
import org.springframework.stereotype.Component;

@Component
public class RegraSaldo implements RegraAutorizacao {

    @Override
    public void validar(TransacaoDTO transacao, Cartao cartao) {
        if (cartao.getSaldo().compareTo(transacao.getValor()) < 0) {
            throw new RegraAutorizacaoException("Saldo insuficiente.");
        }
    }
}
