package main.java.model;

import lombok.Getter;

@Getter
public enum EFonte {
    GLOBO(1, "Globo", "https://g1.globo.com"),
    INFO_MONEY(2, "Info Money", "https://www.infomoney.com.br");

    private final Integer valor;
    private final String label;
    private final String url;

    EFonte(Integer valor, String label, String url) {
        this.valor = valor;
        this.label = label;
        this.url = url;
    }
}
