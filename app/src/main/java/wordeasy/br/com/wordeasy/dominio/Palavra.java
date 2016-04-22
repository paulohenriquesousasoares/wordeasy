package wordeasy.br.com.wordeasy.dominio;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Palavra extends RealmObject {

    public static final String  ID = "br.com.wordeasy.modelo.palavra";

    @PrimaryKey
    private long id;
    private String PalavraEmIngles;
    private String palavraEmPortugues;
    private String indicePalavra;
    private boolean favorito;
    private Usuario usuario;
    private int qtdErros;
    private int qtdAcertos;
    private int qtdVezesEstudou;

    public Palavra() {}

    public Palavra(String indicePalavra,String palavraEmIngles, String palavraEmPortugues,boolean favorito,int qtdErros,int qtdAcertos,int qtdVezesEstudou) {
        this.PalavraEmIngles = palavraEmIngles;
        this.palavraEmPortugues = palavraEmPortugues;
        this.indicePalavra = indicePalavra;
        this.favorito = favorito;
        this.qtdErros = qtdErros;
        this.qtdAcertos = qtdAcertos;
        this.qtdVezesEstudou = qtdVezesEstudou;
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
        return palavraEmPortugues;
    }

    public void setPalavraEmPortugues(String palavraEmPortugues) {
        this.palavraEmPortugues = palavraEmPortugues;
    }

    public String getIndicePalavra() {
        return indicePalavra;
    }

    public void setIndicePalavra(String indicePalavra) {
        this.indicePalavra = indicePalavra;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getQtdErros() {
        return qtdErros;
    }

    public void setQtdErros(int qtdErros) {
        this.qtdErros = qtdErros;
    }

    public int getQtdAcertos() {
        return qtdAcertos;
    }

    public void setQtdAcertos(int qtdAcertos) {
        this.qtdAcertos = qtdAcertos;
    }

    public int getQtdVezesEstudou() {
        return qtdVezesEstudou;
    }

    public void setQtdVezesEstudou(int qtdVezesEstudou) {
        this.qtdVezesEstudou = qtdVezesEstudou;
    }
}
