package wordeasy.br.com.wordeasy.model;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import wordeasy.br.com.wordeasy.view.dominio.Palavra;
import wordeasy.br.com.wordeasy.interfaces.model.ModelOperacaoPalavra;
import wordeasy.br.com.wordeasy.interfaces.presenter.RetornoPresenteOperacaoPalavra;
import wordeasy.br.com.wordeasy.view.servico.PalavraServico;
import wordeasy.br.com.wordeasy.view.util.Constantes;

public class PalavraModel implements ModelOperacaoPalavra {

    private RetornoPresenteOperacaoPalavra mPresent;
    private PalavraServico palavraServico;

    public PalavraModel(RetornoPresenteOperacaoPalavra mPresent, Context context){
        this.mPresent = mPresent;
        palavraServico = new PalavraServico(context);
    }

    @Override
    public void inserePalavra(Palavra palavra, int tipoOperacao) {

        String result = operacaCrudPalavra(palavra,Constantes.CADASTRANDO,true);

        if( result.equals(Constantes.OK) )
            mPresent.onPalavraCadastrada(palavra);
        else {
            if(result.equals("Palavra já existe"))
                mPresent.onError(result,Constantes.CADASTRANDO);
            else
                 mPresent.onCampoNaoPreenchido(result);
        }

    }

    @Override
    public boolean alteraPalavra(Palavra palavra, boolean validarCampo) {

        String result =  operacaCrudPalavra(palavra, Constantes.ALTERANDO,validarCampo);
        boolean retorno = false;

        if( result.equals(Constantes.OK) ) {
            mPresent.onPalavraAlterada(palavra);
            retorno = true;
        }
        else {
            mPresent.onError(result,Constantes.ALTERANDO);
        }
        return retorno;
    }

    private String operacaCrudPalavra(Palavra palavra, int operacao, boolean validarCampo) {

        String retorno = "";
        String novaPalavra="";

        if(validarCampo)
            novaPalavra = palavraServico.getByNome(palavra.getUsuarioId(), palavra.getPalavraEmIngles());
        else
            novaPalavra = Constantes.OK;

        //Se é uma nova palavra
        if(novaPalavra.equals(Constantes.OK)) {

            String result = palavraServico.validaPalavra(palavra);
            if (result.equals(Constantes.OK)) {

                try {
                    palavra.setIndicePalavra(palavra.getPalavraEmIngles().substring(0, 1));
                    if(operacao ==  Constantes.ALTERANDO){
                        palavraServico.altera(palavra);
                    }
                    else if(operacao == Constantes.CADASTRANDO) {
                        palavraServico.create(palavra);
                    }
                    retorno = Constantes.OK;
                } catch (Exception e) {
                    retorno = ""+e;
                }
            } else {

                if(operacao ==  Constantes.ALTERANDO)
                    retorno = "Favor preencha o campo.";
                else if(operacao == Constantes.CADASTRANDO)
                    retorno = result;
            }
        }

        //Se não é uma nova palavra
        else {
            if(novaPalavra.equals("existe"))
                retorno = "Palavra já existe";
            else
                retorno = ""+novaPalavra;
        }
        return  retorno;
    }
}
