package wordeasy.br.com.wordeasy.view.dominio;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Configuracao extends RealmObject {

    @PrimaryKey
    private long id;
    private int itensPorSessaoRevisao;
    private int itensPorSessaoEstudo;
    private int hora;
    private int minuto;
    private long usuarioId;
    private boolean ativo;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getItensPorSessaoRevisao() {
        return itensPorSessaoRevisao;
    }

    public void setItensPorSessaoRevisao(int itensPorSessaoRevisao) {
        this.itensPorSessaoRevisao = itensPorSessaoRevisao;
    }

    public int getItensPorSessaoEstudo() {
        return itensPorSessaoEstudo;
    }

    public void setItensPorSessaoEstudo(int itensPorSessaoEstudo) {
        this.itensPorSessaoEstudo = itensPorSessaoEstudo;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }


    public long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
