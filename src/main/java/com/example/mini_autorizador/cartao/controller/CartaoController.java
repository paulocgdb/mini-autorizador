package com.example.mini_autorizador.cartao.controller;

import com.example.mini_autorizador.cartao.model.Cartao;
import com.example.mini_autorizador.cartao.service.CartaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {
    private final CartaoService cartaoService;

    public CartaoController(CartaoService cartaoService) {
        this.cartaoService = cartaoService;
    }

    @PostMapping
    public ResponseEntity<Object> criarCartao(@RequestBody Cartao cartao) {
        return cartaoService.criarCartao(cartao);
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<BigDecimal> obterSaldo(@PathVariable String numeroCartao) {
        return cartaoService.obterSaldo(numeroCartao);
    }
}
