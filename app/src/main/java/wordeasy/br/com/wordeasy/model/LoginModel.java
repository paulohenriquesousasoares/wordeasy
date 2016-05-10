package wordeasy.br.com.wordeasy.model;

import android.content.Context;

import wordeasy.br.com.wordeasy.view.dao.repositorio.UsuarioRepositorio;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;
import wordeasy.br.com.wordeasy.interfaces.model.ModelOperacaoLogin;
import wordeasy.br.com.wordeasy.interfaces.presenter.RetornoPresenterOperacaoLogin;

public class LoginModel implements ModelOperacaoLogin{

    private RetornoPresenterOperacaoLogin mPresenter;
    private UsuarioRepositorio usuarioRepositorio;

    public LoginModel(RetornoPresenterOperacaoLogin mPresenter, Context context) {
        this.mPresenter = mPresenter;
        usuarioRepositorio = new UsuarioRepositorio(context);

    }

    @Override
    public void entrar(String email, String password) {

        try {
            Usuario user =  usuarioRepositorio.getByUserEmailAndSenha(email,password);
            mPresenter.onLogado(user);
        } catch (Exception e) {
            mPresenter.onError(""+e);
        }
    }
}
