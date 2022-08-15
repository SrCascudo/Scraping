package main.java.util;

import main.java.model.EFonte;

public class UtilScraping {

    private static final String BUSCAR = "/busca/?q=";

    public static String buscarUrl(String assunto, EFonte fonte){
        return UtilScraping.construirURL(assunto, fonte);
    }

    private static String construirURL(String assunto, EFonte fonte){
        return fonte.getUrl().concat(BUSCAR).concat(assunto);
    }
}
