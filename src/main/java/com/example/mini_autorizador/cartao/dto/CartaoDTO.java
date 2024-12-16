package com.example.mini_autorizador.cartao.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoDTO {
    private String numeroCartao;
    private String senha;
    private BigDecimal saldo;
}
