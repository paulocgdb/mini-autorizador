package com.example.mini_autorizador.cartao.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
public class Cartao {
    @Id
    @Column(name = "numero_cartao", nullable = false, length = 16)
    private String numeroCartao;

    @Column(name = "senha", nullable = false, length = 10)
    private String senha;

    @Column(name = "saldo", nullable = false, precision = 10, scale = 2)
    private BigDecimal saldo = BigDecimal.valueOf(500.00);
}
