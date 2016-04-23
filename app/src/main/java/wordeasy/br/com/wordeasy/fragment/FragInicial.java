package wordeasy.br.com.wordeasy.fragment;

import android.content.Context;
import android.content.Intent;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

import butterknife.Bind;
import me.drakeet.materialdialog.MaterialDialog;
import wordeasy.br.com.wordeasy.activity.EstudarActivity;

import wordeasy.br.com.wordeasy.activity.CadastrarNovaPalavraActivity;
import wordeasy.br.com.wordeasy.activity.MainActivity;
import wordeasy.br.com.wordeasy.activity.PalavrasDetalhesActivity;
import wordeasy.br.com.wordeasy.R;
import wordeasy.br.com.wordeasy.activity.RevisaoActivity;
import wordeasy.br.com.wordeasy.adapter.MyRecyclerViewAdapter;
import wordeasy.br.com.wordeasy.interfaces.RecycleViewOnclickListener;
import wordeasy.br.com.wordeasy.dominio.Palavra;
import wordeasy.br.com.wordeasy.util.Mensagem;


public class FragInicial extends Fragment  implements RecycleViewOnclickListener, View.OnClickListener{

    private MyRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public RecyclerView mRecyclerView;
    private FloatingActionButton floatButtonCadastrar,floatButtonEstudar,floatButtonRevisao,floatButtonCardPersonalizado;
    private FloatingActionMenu floatingActionMenu;
    private SwipeRefreshLayout swipeRefreshLayout;
    private  ArrayList<Palavra> palavrasLista;
    private  MaterialDialog mMaterialDialog = null ;


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

        inicializaViews(view);

        if(savedInstanceState != null) {
            palavrasLista = (ArrayList<Palavra>) savedInstanceState.getSerializable(Palavra.ID);
        }
        else {
            //carrega pela primeira vez
            palavrasLista = ((MainActivity) getActivity()).getPalavrasEstudadas();
        }

        preencheRecycleView(palavrasLista);

        floatButtonCadastrar.setOnClickListener(this);
        floatButtonEstudar.setOnClickListener(this);
        floatButtonRevisao.setOnClickListener(this);
        floatButtonCardPersonalizado.setOnClickListener(this);
        mAdapter.setRecycleViewOnClickListenerHack(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                preencheRecycleView(palavrasLista);
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primary_dark));


            }
        });

        return  view;
    }

    private void inicializaViews(View view){
        floatButtonCadastrar = (FloatingActionButton) view.findViewById(R.id.fab1);
        floatButtonEstudar = (FloatingActionButton) view.findViewById(R.id.fab2);
        floatButtonRevisao =(FloatingActionButton) view.findViewById(R.id.fab4);
        floatButtonCardPersonalizado = (FloatingActionButton) view.findViewById(R.id.fab5);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        floatingActionMenu = (FloatingActionMenu) view.findViewById(R.id.float_menu);
    }

    public void preencheRecycleView(ArrayList<Palavra> palavrasLista) {

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(palavrasLista,getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void myOnClickListener(View v, int position) {

        Palavra palavra =   mAdapter.getPalavraSelecionada(position);

        Intent it = new Intent(getActivity(), PalavrasDetalhesActivity.class);
        it.putExtra(Palavra.ID, palavra);

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
    public void myOnLongPressClickListener(View v, int position)  {

        Palavra palavra =   mAdapter.getPalavraSelecionada(position);

        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.long_press, null);


        TextView palavraSelecionada = (TextView) view.findViewById(R.id.txtPalavraSelecionada);
        TextView explicacaoCard = (TextView) view.findViewById(R.id.txtexplicacaoCard);

        //seta a palavra selecionada
        palavraSelecionada.setText(palavra.getPalavraEmIngles());


        //explicaco click
        explicacaoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog = new MaterialDialog(getActivity())
                        .setTitle("Informação")
                        .setMessage("Adicionar uma palavra ao card personalizado, significa agendar palavras para que voçe " +
                                    "possa estudar mais tarde, geralmente usamos esta opção em palvras que achamos mais difícil :)")
                        .setPositiveButton("Entendi", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();
                            }
                        });
                mMaterialDialog.show();

            }
        });
        alert.setView(view);
        alert.show();
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
                Mensagem.alertDialog(getActivity(),"informação","É preciso ter pelo menos 5 palavras cadastradas para iniciar os estudos.").show();
            }

        }
        else if(id == floatButtonRevisao.getId()) {

            if(palavrasLista.size() > 4)
                startActivity(new Intent(getActivity(), RevisaoActivity.class));
            else {
//                AlertDialog.Builder alert = Mensagem.alertDialog(getActivity(), "Aviso", "è preciso ter pelo menos 5 palavras cadastradas para iniciar os estudos.");
//                AlertDialog alertDialog = alert.create();
//                alertDialog.show();
                Mensagem.alertDialog(getActivity(),"informação","É preciso ter pelo menos 5 palavras cadastradas para iniciar os estudos.").show();
            }
        }
        else if(id == floatButtonCardPersonalizado.getId()) {
            Intent it = new Intent("CARD_PERSONALIZADO");
            startActivity(it);
        }
        floatingActionMenu.close(true);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Palavra.ID,palavrasLista);

    }
}