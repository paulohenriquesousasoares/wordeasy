package wordeasy.br.com.wordeasy.dominio;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Palavra extends RealmObject implements Serializable {

    public static final String  ID = "br.com.wordeasy.modelo.palavra";

    @PrimaryKey
    private long id;
    private String PalavraEmIngles;
    private String PalavraEmPortugues;
    private String IndicePalavra;
    private boolean Favorito;
    private Usuario Usuario;
    private int QtdErros;
    private int QtdAcertos;
    private int QtdVezesEstudou;
    private boolean CardPersonalizado;
    private boolean NaoEstudar;

    public Palavra() {}

    public Palavra(String indicePalavra,String palavraEmIngles, String palavraEmPortugues,boolean favorito,int qtdErros,int qtdAcertos,int qtdVezesEstudou,Usuario usuario) {
        this.PalavraEmIngles = palavraEmIngles;
        this.PalavraEmPortugues = palavraEmPortugues;
        this.IndicePalavra = indicePalavra;
        this.Favorito = favorito;
        this.QtdErros = qtdErros;
        this.QtdAcertos = qtdAcertos;
        this.QtdVezesEstudou = qtdVezesEstudou;
        this.Usuario = usuario;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getPalavraEmIngles() {
        return PalavraEmIngles;
    }
    public void setPalavraEmIngles(String palavraEmIngles) {
        PalavraEmIngles = palavraEmIngles;
    }

    public String getPalavraEmPortugues() {
        return PalavraEmPortugues;
    }
    public void setPalavraEmPortugues(String palavraEmPortugues) {
        this.PalavraEmPortugues = palavraEmPortugues;
    }

    public String getIndicePalavra() {
        return IndicePalavra;
    }
    public void setIndicePalavra(String indicePalavra) {
        this.IndicePalavra = indicePalavra;
    }

    public boolean isFavorito() {
        return Favorito;
    }
    public void setFavorito(boolean favorito) {
        this.Favorito = favorito;
    }

    public Usuario getUsuario() {
        return Usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.Usuario = usuario;
    }

    public int getQtdErros() {
        return QtdErros;
    }
    public void setQtdErros(int qtdErros) {
        this.QtdErros = qtdErros;
    }

    public int getQtdAcertos() {
        return QtdAcertos;
    }
    public void setQtdAcertos(int qtdAcertos) {
        this.QtdAcertos = qtdAcertos;
    }

    public int getQtdVezesEstudou() {
        return QtdVezesEstudou;
    }
    public void setQtdVezesEstudou(int qtdVezesEstudou) {this.QtdVezesEstudou = qtdVezesEstudou;}

    public boolean isCardPersonalizado() {return CardPersonalizado;}

    public void setCardPersonalizado(boolean cardPersonalizado) {
        CardPersonalizado = cardPersonalizado;
    }

    public boolean isNaoEstudar() {
        return NaoEstudar;
    }

    public void setNaoEstudar(boolean naoEstudar) {
        NaoEstudar = naoEstudar;
    }
}
