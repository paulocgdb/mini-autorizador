package com.example.mini_autorizador.cartao.strategy;

import com.example.mini_autorizador.cartao.model.Cartao;
import com.example.mini_autorizador.transacao.dto.TransacaoDTO;
import com.example.mini_autorizador.transacao.exception.RegraAutorizacaoException;
import org.springframework.stereotype.Component;

@Component
public class RegraSenha implements RegraAutorizacao {

    @Override
    public void validar(TransacaoDTO transacao, Cartao cartao) {
        if (!cartao.getSenha().equals(transacao.getSenhaCartao())) {
            throw new RegraAutorizacaoException("Senha incorreta.");
        }
    }
}
