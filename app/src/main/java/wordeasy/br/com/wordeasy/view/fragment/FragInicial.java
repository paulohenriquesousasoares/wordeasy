package wordeasy.br.com.wordeasy.view.fragment;

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
import android.transition.ChangeBounds;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

import wordeasy.br.com.wordeasy.interfaces.presenter.PresenteOperacaoMain;
import wordeasy.br.com.wordeasy.interfaces.view.ViewOperacaoRequisitaMain;
import wordeasy.br.com.wordeasy.presenter.MainPresenter;
import wordeasy.br.com.wordeasy.view.activity.EstudarActivity;

import wordeasy.br.com.wordeasy.view.activity.CadastrarNovaPalavraActivity;
import wordeasy.br.com.wordeasy.view.activity.MainActivity;
import wordeasy.br.com.wordeasy.view.activity.PalavrasDetalhesActivity;
import wordeasy.br.com.wordeasy.R;
import wordeasy.br.com.wordeasy.view.activity.RevisaoActivity;
import wordeasy.br.com.wordeasy.view.adapter.MyRecyclerViewAdapter;
import wordeasy.br.com.wordeasy.interfaces.RecycleViewOnclickListener;
import wordeasy.br.com.wordeasy.view.dominio.Palavra;
import wordeasy.br.com.wordeasy.view.util.Constantes;
import wordeasy.br.com.wordeasy.view.util.Mensagem;


public class FragInicial extends Fragment  implements RecycleViewOnclickListener, View.OnClickListener{

    private MyRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public RecyclerView mRecyclerView;
    private FloatingActionButton floatButtonCadastrar,floatButtonEstudar,floatButtonRevisao,floatButtonCardPersonalizado;
    private FloatingActionMenu floatingActionMenu;
    private SwipeRefreshLayout swipeRefreshLayout;
    private  ArrayList<Palavra> palavrasLista;
    private RelativeLayout rl;
    private TextView fechar, estudarCardPersonalizado, revisarCardPersonalizado;


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
//            palavrasLista = ((MainActivity) getActivity()).getPalavras();
             ((MainActivity) getActivity()).getPalavras(Constantes.TAKE_ALL_PALAVRAS);
             palavrasLista =  ((MainActivity) getActivity()).getAllPalvrasAtual();
        }

        preencheRecycleView(palavrasLista);

        floatButtonCadastrar.setOnClickListener(this);
        floatButtonEstudar.setOnClickListener(this);
        floatButtonRevisao.setOnClickListener(this);
        floatButtonCardPersonalizado.setOnClickListener(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                ((MainActivity) getActivity()).getPalavras(Constantes.TAKE_ALL_PALAVRAS);
                 preencheRecycleView(((MainActivity) getActivity()).getAllPalvrasAtual());
                ((MainActivity) getActivity()).trocaLabelToolbar("Todas");

                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primary_dark));}
        });
        return  view;
    }


    /*=========================================================================================================
            METODOS
    * ========================================================================================================*/
    private void inicializaViews(View view){
        floatButtonCadastrar = (FloatingActionButton) view.findViewById(R.id.fab1);
        floatButtonEstudar = (FloatingActionButton) view.findViewById(R.id.fab2);
        floatButtonRevisao =(FloatingActionButton) view.findViewById(R.id.fab4);
        floatButtonCardPersonalizado = (FloatingActionButton) view.findViewById(R.id.fabCardPersonalizado);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        floatingActionMenu = (FloatingActionMenu) view.findViewById(R.id.float_menu);
        rl = (RelativeLayout) view.findViewById(R.id.opcoes_menu_botton);
        fechar = (TextView) view.findViewById(R.id.txtFechar);
        estudarCardPersonalizado = (TextView) view.findViewById(R.id.txtEstudarCardPersonalizado);
        revisarCardPersonalizado = (TextView) view.findViewById(R.id.txtRevisaoCardPersonalizado);

    }

    public void preencheRecycleView(ArrayList<Palavra> palavrasLista) {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
       ((MainActivity)getActivity()).preencheRecycleview(palavrasLista,mRecyclerView,mAdapter);
    }



     /*=========================================================================================================
            LISTENER
    * ========================================================================================================*/


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
    public void myOnLongPressClickListener(View v, final int posicao)  {

        LayoutInflater layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.long_press, null);

        final Palavra palavra =   mAdapter.getPalavraSelecionada(posicao);
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        //get as views
        TextView palavraSelecionada = (TextView) view.findViewById(R.id.txtPalavraSelecionada);
        ListView lst = (ListView) view.findViewById(R.id.lstOpcoes);

        palavraSelecionada.setText(palavra.getPalavraEmIngles());

        //PREENCHE O ADAPTER DO LISTVIEW
        ArrayList<String>  opcoes = new ArrayList<String>();
        String[] itens = getResources().getStringArray(R.array.onlong_press_itens);

       //verifica o estado do objeto naoEstudaMais
        for (int i=0;i<itens.length;i++) {

            if(i == 0 && palavra.isNaoEstudar())
                opcoes.add("Voltar a estudar");
            else if(i ==  1 && palavra.isCardPersonalizado())
                opcoes.add("Adicionado ao card personalizado");
            else
                opcoes.add(itens[i]);
        }
        lst.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, opcoes));

        alert.setView(view);
        alert.show();



        //CLIQUE ITEM NO LISTVIEW
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String itemTextoClicado = ((TextView) view).getText().toString();

                String result = ((MainActivity)getActivity()).alteraObjetoClicado(palavra, itemTextoClicado, position);
                ((TextView) view).setText(result);

                String msgToast = "";
                if(position == 0){

                     mAdapter.removerItemJaSei(posicao);
                    if(palavra.isNaoEstudar())
                        msgToast =  "Adicionado ao nao estudar mais.";
                    else
                        msgToast =  "Removido do nao estudar mais.";
                }
                else if(position ==  1) {
                    mAdapter.alterarObjetoCardPersonalizado(posicao);
                    if(palavra.isCardPersonalizado())
                        msgToast =  "Adicionado ao card pesonalizado.";
                    else
                        msgToast =  "Removido do card pesonalizado.";
                }

                Mensagem.toast(getActivity(),msgToast).show();
            }

        });

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if(id == floatButtonCadastrar.getId()) {
            Intent it = new Intent(getActivity(),CadastrarNovaPalavraActivity.class);
            startActivityForResult(it,1);
        }
        else if(id == floatButtonEstudar.getId()) {

            if(palavrasLista.size() > 4)
                startActivity(new Intent(getActivity(), EstudarActivity.class));
            else {
                Mensagem.materialDialogAviso(getActivity(),
                 "Informação", "É preciso ter pelo menos 5 palavras cadastradas para iniciar os estudos.").show();
            }

        }
        else if(id == floatButtonRevisao.getId()) {

            if(palavrasLista.size() > 4)
                startActivity(new Intent(getActivity(), RevisaoActivity.class));
            else {
                Mensagem.materialDialogAviso(getActivity(), "Informação", "É preciso ter pelo menos 5 palavras cadastradas para iniciar os estudos.").show();
            }
        }
        else if(id == floatButtonCardPersonalizado.getId()) {

            rl.setVisibility(View.VISIBLE);

            try{
                YoYo.with(Techniques.SlideInUp).duration(700).playOn(rl);
            }catch(Exception e){}


            fechar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rl.setVisibility(View.GONE);
                }
            });

            estudarCardPersonalizado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((MainActivity) getActivity()).getPalavras(Constantes.TAKE_ALL_CARD_PERSONALIZADO);
                     ArrayList<Palavra> listPalavraAux = ((MainActivity) getActivity()).getAllPalavraAux();

                    if(listPalavraAux.size() >  4) {

                        Intent it = new Intent(getActivity(), EstudarActivity.class);
                        it.putExtra(Palavra.ID, Palavra.ID);
                        startActivity(it);
                        rl.setVisibility(View.GONE);
                    }
                    else {
                        Mensagem.materialDialogAviso(getActivity(),
                                "Informação", "É preciso ter pelo menos 5 palavras cadastradas para iniciar os estudos.").show();
                    }
                }
            });

            revisarCardPersonalizado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   ((MainActivity) getActivity()).getPalavras(Constantes.TAKE_ALL_CARD_PERSONALIZADO);
                    ArrayList<Palavra> listPalavraAux = ((MainActivity) getActivity()).getAllPalavraAux();

                    if(listPalavraAux.size() >  4) {

                        Intent it = new Intent(getActivity(), RevisaoActivity.class);
                        it.putExtra(Palavra.ID, Palavra.ID);
                        startActivity(it);
                        rl.setVisibility(View.GONE);
                    }
                    else {
                        Mensagem.materialDialogAviso(getActivity(),
                        "Informação", "É preciso ter pelo menos 5 palavras cadastradas para iniciar os estudos.").show();
                    }

                }
            });

        }
        floatingActionMenu.close(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Palavra.ID, palavrasLista);

    }
}