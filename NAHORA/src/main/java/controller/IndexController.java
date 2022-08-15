package main.java.controller;

import lombok.Getter;
import lombok.Setter;
import main.java.model.EFonte;
import main.java.model.Noticia;
import main.java.util.UtilScraping;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named
@RequestScoped
@Getter
@Setter
public class IndexController {
    private String fieldAssunto;
    private List<EFonte> fieldFonte;

    public EFonte[] listaFonte() {
        return EFonte.values();
    }

    public void buscarNoticia() {
        try {
            final OkHttpClient client = new OkHttpClient.Builder().build();
            final Request request = new Request.Builder()
                    .url(UtilScraping.buscarUrl(getFieldAssunto(), getFieldFonte().get(0)))
                    .build();

            final Response response = client.newCall(request).execute();
            String content = response.body().string();

            extrairNoticia(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void extrairNoticia(String html) {
        List<Noticia> listaNoticia = new ArrayList<>();

        html = extrairConteudoHTML("<ul class=\"results__list\">", "</ul>", html);
        String[] listaLI = html.split("<li class=\"widget widget--card");

        for (String htmlLI : listaLI) {
            htmlLI = htmlLI.replace("\"widget--navigational__description\"", "\"widget--info__description\"");


            Noticia noticia = new Noticia();
            noticia.setTitulo(extrairConteudoHTML("", "", htmlLI));

            noticia.setSubTitulo(extrairConteudoHTML("", "", htmlLI));

            noticia.setAutor(extrairConteudoHTML("", "", htmlLI));

            noticia.setPrevia(extrairConteudoHTML("<p class=\"widget--info__description\">", "</p>", htmlLI));
            noticia.setPrevia(noticia.getPrevia().replaceAll("<[^>]*>", "").replace("\n", "").replace("\t", ""));

            noticia.setDataPublicacao(extrairConteudoHTML("", "", htmlLI));

            noticia.setUrl(extrairConteudoHTML("<a href=\"", "\"", htmlLI));

            listaNoticia.add(noticia);


        }

    }

    private String extrairConteudoHTML(String tagInicio, String tagFim, String html) {
        Matcher matcher = null;
        boolean encontrado = false;
        try {
            Pattern pattern = Pattern.compile(tagInicio.concat("(.*?)").concat(tagFim), Pattern.DOTALL);
            matcher = pattern.matcher(html);
            encontrado = matcher.find();
        } catch (Exception e) {
            System.out.println("Falha ao percorrer arvore de tags HTML");
        }
        return encontrado ? matcher.group(1) : "";
    }
}
