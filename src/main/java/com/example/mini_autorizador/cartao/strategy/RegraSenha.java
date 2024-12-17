package com.example.mini_autorizador.cartao.strategy;

import com.example.mini_autorizador.cartao.model.Cartao;
import com.example.mini_autorizador.transacao.dto.TransacaoDTO;
import com.example.mini_autorizador.transacao.exception.RegraAutorizacaoException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RegraSenha implements RegraAutorizacao {

    @Override
    public void validar(TransacaoDTO transacao, Cartao cartao) {
        Optional.ofNullable(cartao)
                .filter(c -> c.getSenha().equals(transacao.getSenhaCartao()))
                .orElseThrow(() -> new RegraAutorizacaoException("SENHA_INVALIDA"));
    }
}
