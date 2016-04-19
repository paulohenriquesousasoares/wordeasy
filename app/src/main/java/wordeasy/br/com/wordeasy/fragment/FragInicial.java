package wordeasy.br.com.wordeasy.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;

import butterknife.Bind;
import wordeasy.br.com.wordeasy.activity.EstudarActivity;

import wordeasy.br.com.wordeasy.activity.CadastrarNovaPalavraActivity;
import wordeasy.br.com.wordeasy.activity.PalavrasDetalhesActivity;
import wordeasy.br.com.wordeasy.R;
import wordeasy.br.com.wordeasy.activity.RevisaoActivity;
import wordeasy.br.com.wordeasy.adapter.MyRecyclerViewAdapter;
import wordeasy.br.com.wordeasy.dao.repositorio.PalavraRepositorio;
import wordeasy.br.com.wordeasy.interfaces.RecycleViewOnclickListener;
import wordeasy.br.com.wordeasy.dominio.Palavra;
import wordeasy.br.com.wordeasy.util.Mensagem;
import wordeasy.br.com.wordeasy.util.Utilitario;


public class FragInicial extends Fragment  implements
        RecycleViewOnclickListener, View.OnClickListener{

    @Bind(R.id.toolbar) Toolbar toolbar;

    private MyRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public RecyclerView mRecyclerView;
    private FloatingActionButton floatButtonCadastrar,floatButtonEstudar,floatButtonRevisao;
    private SwipeRefreshLayout swipeRefreshLayout;


    public FragInicial() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {

        //TRANSITION
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setSharedElementExitTransition(new ChangeBounds());
        }

        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.inicial, container, false);

        floatButtonCadastrar = (FloatingActionButton) view.findViewById(R.id.fab1);
        floatButtonEstudar = (FloatingActionButton) view.findViewById(R.id.fab2);
        floatButtonRevisao =(FloatingActionButton) view.findViewById(R.id.fab4);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);

       carregaPalavra();

        floatButtonCadastrar.setOnClickListener(this);
        floatButtonEstudar.setOnClickListener(this);
        floatButtonRevisao.setOnClickListener(this);
        mAdapter.setRecycleViewOnClickListenerHack(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                carregaPalavra();
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primary_dark));
            }
        });

        return  view;
    }

    public void carregaPalavra() {

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(Utilitario.getPalavras(), getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void myOnClickListener(View v, int position) {

        Palavra palavra =   mAdapter.getPalavraSelecionada(position);

        Intent it = new Intent(getActivity(), PalavrasDetalhesActivity.class);

        it.putExtra("id", palavra.getId());
        it.putExtra("ingles", palavra.getPalavraEmIngles());
        it.putExtra("portugues", palavra.getPalavraEmPortugues());
        it.putExtra("indice", palavra.getIndicePalavra());
        it.putExtra("favorito", palavra.isFavorito());
        it.putExtra("acertos", palavra.getQtdAcertos());
        it.putExtra("erros", palavra.getQtdErros());
        it.putExtra("vezesEstudou", palavra.getQtdVezesEstudou());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            View ingles = v.findViewById(R.id.txtPalavraEmIngles);
            View traducao = v.findViewById(R.id.txtTraducaoUm);

            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), Pair.create(ingles, "elemento1"));

           getActivity().startActivity(it, options.toBundle());
        }
        else {
            startActivity(it);
        }
    }


    @Override
    public void myOnLongPressClickListener(View v, int position) {
        Mensagem.toast(getActivity(), "OnLongPress").show();
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if(id == floatButtonCadastrar.getId()) {
            startActivity(new Intent(getActivity(), CadastrarNovaPalavraActivity.class));
        }
        else if(id == floatButtonEstudar.getId()) {
            startActivity(new Intent(getActivity(), EstudarActivity.class));
        }
        else if(id == floatButtonRevisao.getId()) {
            startActivity(new Intent(getActivity(), RevisaoActivity.class));
        }

    }
}