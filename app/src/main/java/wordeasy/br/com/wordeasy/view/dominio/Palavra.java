package wordeasy.br.com.wordeasy.view.dominio;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Palavra  implements Serializable {

    public static final String  ID = "br.com.wordeasy.modelo.palavra";

    private long id;
    private String palavraEmIngles;
    private String palavraEmPortugues;
    private String indicePalavra;
    private long usuarioId;
    private int qtdErros;
    private int qtdAcertos;
    private int qtdVezesEstudou;
    private int cardPersonalizado;
    private int naoEstudar;

    //usadas no recycleview quando estiver cardPersonalizado e naoEstudar como true para serem exibidas na lista
    private String CardPersonalizadoSelecionado;
    private String NaoEstudarMaisSelecionado;

//    public Palavra() {}

//    public Palavra(String indicePalavra,String palavraEmIngles, String palavraEmPortugues,boolean favorito,int qtdErros,int qtdAcertos,int qtdVezesEstudou,Usuario usuario) {
//        this.palavraEmIngles = palavraEmIngles;
//        this.palavraEmPortugues = palavraEmPortugues;
//        this.indicePalavra = indicePalavra;
//        this.qtdErros = qtdErros;
//        this.qtdAcertos = qtdAcertos;
//        this.qtdVezesEstudou = qtdVezesEstudou;
//        this.Usuario = usuario;
//    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getPalavraEmIngles() {
        return palavraEmIngles;
    }
    public void setPalavraEmIngles(String palavraEmIngles) {
        this.palavraEmIngles = palavraEmIngles;
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

    public long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
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
    public void setQtdVezesEstudou(int qtdVezesEstudou) {this.qtdVezesEstudou = qtdVezesEstudou;}

    public int isCardPersonalizado() {return cardPersonalizado;}
    public void setCardPersonalizado(int cardPersonalizado) {  this.cardPersonalizado = cardPersonalizado; }

    public int isNaoEstudar() {
        return naoEstudar;
    }
    public void setNaoEstudar(int naoEstudar) {
        this.naoEstudar = naoEstudar;
    }

    public String getCardPersonalizadoSelecionado() { return CardPersonalizadoSelecionado; }
    public void setCardPersonalizadoSelecionado(String cardPersonalizadoSelecionado) {CardPersonalizadoSelecionado = cardPersonalizadoSelecionado;}

    public String getNaoEstudarMaisSelecionado() {return NaoEstudarMaisSelecionado;}
    public void setNaoEstudarMaisSelecionado(String naoEstudarMaisSelecionado) {NaoEstudarMaisSelecionado = naoEstudarMaisSelecionado;}
}
