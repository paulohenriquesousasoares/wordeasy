package wordeasy.br.com.wordeasy.model;

import wordeasy.br.com.wordeasy.view.dao.repositorio.UsuarioRepositorio;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;
import wordeasy.br.com.wordeasy.interfaces.model.ModelOperacaoLogin;
import wordeasy.br.com.wordeasy.interfaces.presenter.RetornoPresenterOperacaoLogin;

public class LoginModel implements ModelOperacaoLogin{

    private RetornoPresenterOperacaoLogin mPresenter;

    public LoginModel(RetornoPresenterOperacaoLogin mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void entrar(String email, String password) {

        UsuarioRepositorio usuarioRepositorio = new UsuarioRepositorio();
        try {
            Usuario user =  usuarioRepositorio.getByUserEmailAndSenha(email,password);
            mPresenter.onLogado(user);
        } catch (Exception e) {
            mPresenter.onError(""+e);
        }
    }
}
