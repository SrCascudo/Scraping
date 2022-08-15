package main.java.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Noticia {
    private String titulo;
    private String subTitulo;
    private String autor;
    private String dataPublicacao;
    private String artigo;
    private String previa;
    private String url;
}
