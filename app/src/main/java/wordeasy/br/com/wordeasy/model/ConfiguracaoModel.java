package wordeasy.br.com.wordeasy.model;

import android.app.Activity;
import android.content.Context;

import wordeasy.br.com.wordeasy.interfaces.model.ModelOperacaoConfiguracao;
import wordeasy.br.com.wordeasy.interfaces.presenter.RetornoPresenteOperacaoConfiguracao;
import wordeasy.br.com.wordeasy.view.dao.repositorio.ConfiguracaoRepositorio;
import wordeasy.br.com.wordeasy.view.dominio.Configuracao;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;
import wordeasy.br.com.wordeasy.view.util.Utilitario;

public class ConfiguracaoModel implements ModelOperacaoConfiguracao{

    private RetornoPresenteOperacaoConfiguracao mPresente;
    private ConfiguracaoRepositorio configuracaoRepositorio;

    public ConfiguracaoModel(RetornoPresenteOperacaoConfiguracao mPresente) {
        this.mPresente = mPresente;
        configuracaoRepositorio = new ConfiguracaoRepositorio();
    }

    @Override
    public void insereConfiguracao(Configuracao configuracao) {
        try {
            configuracaoRepositorio.create(configuracao);
            mPresente.criarAlarme(configuracao);
            mPresente.onConfiguracaoCriada(configuracao);
        } catch (Exception e) {
           mPresente.onError(""+e);
        }
    }

    @Override
    public void getConguracao(Usuario usuario) {

        Configuracao configuracao = getConfiguracaoAuxiliar(usuario);

        //se menor que < 1 significa que ainda nao cradastrou nenhuma configuracao
        if(configuracao.getUsuarioId() < 1) {
            configuracao.setItensPorSessaoRevisao(5);
            configuracao.setItensPorSessaoEstudo(5);
            configuracao.setHora(13);
            configuracao.setMinuto(00);
            configuracao.setUsuarioId(usuario.getId());
            configuracao.setAtivo(true);

            try {
                configuracaoRepositorio.create(configuracao);
                mPresente.criarAlarme(configuracao);
            } catch (Exception e) {
                mPresente.onError(""+e);
            }
            mPresente.onConfiguracaoUsuarioAtualRecuperada(configuracao);
        }
        //significa que tem alguma configuracao e preenche o obj com os dados
        else {
            mPresente.onConfiguracaoUsuarioAtualRecuperada(configuracao);
        }
    }

    @Override
    public Usuario getUsuarioAtual(Context context) {
        return Utilitario.getSharedPreferenceUsuario((Activity) context);
    }

    private Configuracao getConfiguracaoAuxiliar(Usuario usuario) {
        Configuracao config = null;
        try {
            config = configuracaoRepositorio.getConfiguracao(usuario.getId());
            mPresente.onConfiguracaoUsuarioAtualRecuperada(config);
        } catch (Exception e) {
            mPresente.onError(""+e);
        }
        return  config;
    }
}
