package wordeasy.br.com.wordeasy.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.Bind;
import wordeasy.br.com.wordeasy.activity.EstudarActivity;

import wordeasy.br.com.wordeasy.activity.CadastrarNovaPalavraActivity;
import wordeasy.br.com.wordeasy.activity.MainActivity;
import wordeasy.br.com.wordeasy.activity.PalavrasDetalhesActivity;
import wordeasy.br.com.wordeasy.R;
import wordeasy.br.com.wordeasy.activity.RevisaoActivity;
import wordeasy.br.com.wordeasy.adapter.MyRecyclerViewAdapter;
import wordeasy.br.com.wordeasy.dominio.MessageEvent;
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
    private FloatingActionMenu floatingActionMenu;
    private SwipeRefreshLayout swipeRefreshLayout;
    private  ArrayList<Palavra> palavrasLista;


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
        floatingActionMenu = (FloatingActionMenu) view.findViewById(R.id.float_menu);

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

        palavrasLista = ((MainActivity) getActivity()).getPalavrasEstudadas();

        mAdapter = new MyRecyclerViewAdapter(palavrasLista,getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void myOnClickListener(View v, int position) {

        Palavra palavra =   mAdapter.getPalavraSelecionada(position);

        Intent it = new Intent(getActivity(), PalavrasDetalhesActivity.class);
        it.putExtra(Palavra.ID,palavra);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View ingles = v.findViewById(R.id.txtPalavraEmIngles);
            View indicePalavraContainer = v.findViewById(R.id.serial);

            ActivityOptionsCompat options =  ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                            Pair.create(ingles, "elemento1"),
                            Pair.create(ingles, "elemento2"));

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

            if(palavrasLista.size() > 4)
                startActivity(new Intent(getActivity(), EstudarActivity.class));
            else {
                AlertDialog.Builder alert = Mensagem.alertDialog(getActivity(), "Aviso", "è preciso ter pelo menos 5 palavras cadastradas para iniciar os estudos.");
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }

        }
        else if(id == floatButtonRevisao.getId()) {

            if(palavrasLista.size() > 4)
                startActivity(new Intent(getActivity(), RevisaoActivity.class));
            else {
                AlertDialog.Builder alert = Mensagem.alertDialog(getActivity(), "Aviso", "è preciso ter pelo menos 5 palavras cadastradas para iniciar os estudos.");
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        }
        floatingActionMenu.close(true);
    }

}