package com.example.mini_autorizador.transacao.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransacaoDTO {
    private String numeroCartao;
    private String senhaCartao;
    private BigDecimal valor;
}
