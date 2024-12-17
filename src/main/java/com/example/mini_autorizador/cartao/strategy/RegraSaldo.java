package com.example.mini_autorizador.cartao.strategy;

import com.example.mini_autorizador.cartao.model.Cartao;
import com.example.mini_autorizador.transacao.dto.TransacaoDTO;
import com.example.mini_autorizador.transacao.exception.RegraAutorizacaoException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RegraSaldo implements RegraAutorizacao {

    @Override
    public void validar(TransacaoDTO transacao, Cartao cartao) {
        Optional.of(cartao)
                .filter(c -> c.getSaldo().compareTo(transacao.getValor()) >= 0)
                .orElseThrow(() -> new RegraAutorizacaoException("SALDO_INSUFICIENTE"));
    }
}
