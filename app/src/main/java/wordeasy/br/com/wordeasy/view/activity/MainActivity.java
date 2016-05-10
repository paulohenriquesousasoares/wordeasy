package wordeasy.br.com.wordeasy.view.activity;

;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.Bind;
import butterknife.ButterKnife;
import wordeasy.br.com.wordeasy.R;
import wordeasy.br.com.wordeasy.interfaces.presenter.PresenteOperacaoMain;
import wordeasy.br.com.wordeasy.interfaces.view.ViewOperacaoRequisitaMain;
import wordeasy.br.com.wordeasy.presenter.MainPresenter;
import wordeasy.br.com.wordeasy.view.adapter.MyRecyclerViewAdapter;
import wordeasy.br.com.wordeasy.view.adapter.ViewPagerAdapter;
import wordeasy.br.com.wordeasy.view.dao.repositorio.PalavraRepositorio;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;
import wordeasy.br.com.wordeasy.view.fragment.FragRank;
import wordeasy.br.com.wordeasy.view.fragment.FragInicial;
import wordeasy.br.com.wordeasy.interfaces.RecycleViewOnclickListener;
import wordeasy.br.com.wordeasy.view.dominio.Palavra;
import wordeasy.br.com.wordeasy.view.util.Constantes;
import wordeasy.br.com.wordeasy.view.util.Mensagem;
import wordeasy.br.com.wordeasy.view.util.Utilitario;

public class MainActivity extends AppCompatActivity   implements
        RecycleViewOnclickListener,
        Drawer.OnDrawerItemClickListener,
        ViewOperacaoRequisitaMain {

    private Toolbar mtoolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MenuItem item1;
    private  RecyclerView mRecyclerView;
    private int positionPage = 0;
    private boolean ordenaCrescente;
    private MyRecyclerViewAdapter mAdapter;
    private ArrayList<Palavra> palavrasListaGlobal;
    private ArrayList<Palavra> palavrasListaGlobalAuxiliar;
    private Drawer.Result navigationDrawerLeft;
   // private AccountHeader.Result headerNavigationLeft;
    private Bundle savedInstanceState;
    private int posicaoAux;
    private TextView palavraSelecionadaAlterar;
    private PresenteOperacaoMain mPresente;
    private Usuario usuarioLogadoGlobal;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ButterKnife.bind(MainActivity.this);
        inicializarViewsTela();

        this.savedInstanceState = savedInstanceState;
        usuarioLogadoGlobal =  getUsuarioLogado();
        criaDrawerMenu(savedInstanceState);
        getPalavras(Constantes.TAKE_ALL_PALAVRAS);
    }

    //metodos usados nos frgaments
    public ArrayList<Palavra> getAllPalvrasAtual(){
        return palavrasListaGlobal;
    }
    public ArrayList<Palavra> getAllPalavraAux() {
        return palavrasListaGlobalAuxiliar;
    }


   //metodos desta tela

    @Override
    public void inicializarViewsTela() {
        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);

        mtoolbar.setTitle(R.string.app_name);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setupViewPager(viewPager);

        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.branco));
        tabLayout.setupWithViewPager(viewPager);

        try{
            mPresente = new MainPresenter(this, MainActivity.this);
        }
        catch (Exception e){
            Mensagem.snackbar(""+e,findViewById(R.id.main)).show();
        }
    }

    public  void getPalavras(int tipoBuscado) {
        mPresente.getAllPalavras(usuarioLogadoGlobal.getId(), tipoBuscado);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragInicial(), getResources().getString(R.string.frag_Inicial));
        //adapter.addFragment(new FragRank(), getResources().getString(R.string.frag_rank));
        viewPager.setAdapter(adapter);
    }

    public void preencheRecycleview(ArrayList<Palavra> palavrasL,RecyclerView recyclerView, MyRecyclerViewAdapter adapter) {

        mAdapter = new MyRecyclerViewAdapter(palavrasL, MainActivity.this);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setRecycleViewOnClickListenerHack(MainActivity.this);
    }

    private Usuario getUsuarioLogado() {
       return Utilitario.getSharedPreferenceUsuario(MainActivity.this);
    }

    @Override
    public void criaDrawerMenu(Bundle savedInstanceState) {
        mPresente.criaMenu(MainActivity.this, savedInstanceState, usuarioLogadoGlobal.getNome(), usuarioLogadoGlobal.getEmail(), mtoolbar, navigationDrawerLeft);
    }

    @Override
    public void aplicaListenerClickMenu(Drawer.Result navigationDrawerLeft) {
        navigationDrawerLeft.setOnDrawerItemClickListener(this);
    }

    @Override
    public void preencheListaWithAllPalavras(ArrayList<Palavra> palavras, int qualOpcaoCarregou) {

         if(qualOpcaoCarregou == Constantes.TAKE_ALL_PALAVRAS)
            this.palavrasListaGlobal = palavras;
        else
           this.palavrasListaGlobalAuxiliar = palavras;
    }

    @Override
    public void trocaLabelToolbar(String titulo) {
        mtoolbar.setSubtitle(titulo);
    }

    @Override
    public String alteraObjetoClicado(Palavra palavra, String textoClicado, int positionOpcaoClicado) {

        PalavraRepositorio palavraRepositorio = new PalavraRepositorio(MainActivity.this);
        String retorno = "";

        try {
                if ( positionOpcaoClicado == 0) {

                    palavra.setNaoEstudar(palavra.isNaoEstudar() == Constantes.TRUE  ? Constantes.FALSE : Constantes.TRUE);
                    retorno =  verificaSeEstaNaoEstudarMais(textoClicado);

                }
                else if(positionOpcaoClicado == 1 ) {

                    palavra.setCardPersonalizado(palavra.isCardPersonalizado() == Constantes.TRUE ? Constantes.FALSE: Constantes.TRUE);
                    retorno =  verificaCardPersonalizado(textoClicado);
                }

                palavraRepositorio.alteraPalavra(palavra);

        } catch (Exception e) {
            Mensagem.toast(MainActivity.this,""+e).show();
        }
        return  retorno;

    }

    private String verificaSeEstaNaoEstudarMais(String texto) {
        if(texto.equals("Voltar a estudar"))
            return  "Não estudar mais";
        else
            return  "Voltar a estudar";
    }

    private String verificaCardPersonalizado(String texto) {
        if(texto.equals("Adicionar ao card personalizado"))
            return  "Adicionado ao card personalizado";
        else
            return  "Adicionar ao card personalizado";
    }


    //listener

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //outState = navigationDrawerLeft.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchView sv = new SearchView(this);
        sv.setOnQueryTextListener(new SearchFiltro());
        sv.setIconifiedByDefault(false);
        sv.setIconified(false);
        sv.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.searchview) + "</font>"));
        sv.clearFocus();

        item1 = menu.findItem(R.id.ic_search);
        item1.setIcon(R.drawable.ic_search_white_24dp);

        item1.setActionView(sv);

        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.ic_orderby) {
            ordenaCrescente=!ordenaCrescente;
            Collections.sort(palavrasListaGlobal, new Comparator<Palavra>() {
                @Override
                public int compare(Palavra o1, Palavra o2) {

                    Palavra p1 = (Palavra) o1;
                    Palavra p2 = (Palavra) o2;

                    if(ordenaCrescente)
                        return p1.getPalavraEmIngles().compareToIgnoreCase(p2.getPalavraEmIngles());
                    else
                        return p2.getPalavraEmIngles().compareToIgnoreCase(p1.getPalavraEmIngles());
                }
            });

            mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
            preencheRecycleview(palavrasListaGlobal,mRecyclerView,mAdapter);
            String ordenacaoTipo = ordenaCrescente ? "AZ" : "ZA";
            Mensagem.snackbar("Ordenado de " + ordenacaoTipo, findViewById(R.id.main)).show();
        }
        //menu varios
        else if(id == R.id.addCardPersonalizadas) {

            try {

                mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
                getPalavras(Constantes.TAKE_ALL_CARD_PERSONALIZADO);

                preencheRecycleview(palavrasListaGlobalAuxiliar,mRecyclerView,mAdapter);
                trocaLabelToolbar("Card personalizado");
            } catch (Exception e) {
                Mensagem.toast(MainActivity.this,""+e).show();
            }
        }
        else if(id == R.id.naoEstudar) {

            try {

                mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
                //mPresente.getAllPalavras(usuarioLogadoGlobal.getId(),Constantes.TAKE_ALL_NAO_ESTUDAR);
                getPalavras(Constantes.TAKE_ALL_NAO_ESTUDAR);
                preencheRecycleview(palavrasListaGlobalAuxiliar, mRecyclerView, mAdapter);
                trocaLabelToolbar("Já sei");

            } catch (Exception e) {
                Mensagem.toast(MainActivity.this,""+e).show();
            }

        }
        else if(id == R.id.todas) {
            mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
            mPresente.getAllPalavras(usuarioLogadoGlobal.getId(),Constantes.TAKE_ALL_PALAVRAS);
            preencheRecycleview(palavrasListaGlobal, mRecyclerView, mAdapter);
            trocaLabelToolbar("Todas");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void myOnClickListener(View v, int position) {

        Palavra palavra =   mAdapter.getPalavraSelecionada(position);

        Intent it = new Intent(this, PalavrasDetalhesActivity.class);
        it.putExtra(Palavra.ID,palavra);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            View ingles = v.findViewById(R.id.txtPalavraEmIngles);
            //View traducao = v.findViewById(R.id.txtTraducaoUm);

            ActivityOptionsCompat options =  ActivityOptionsCompat.makeSceneTransitionAnimation(this, Pair.create(ingles, "elemento1"));

            this.startActivity(it, options.toBundle());
        }
        else {
            startActivity(it);
        }
    }

    @Override
    public void myOnLongPressClickListener(View v, int position) {

        posicaoAux = position;

        LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.long_press, null);

        final Palavra palavra =   mAdapter.getPalavraSelecionada(posicaoAux);
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

        //get as views
        palavraSelecionadaAlterar = (TextView) view.findViewById(R.id.txtPalavraSelecionada);
        ListView lst = (ListView) view.findViewById(R.id.lstOpcoes);

        palavraSelecionadaAlterar.setText(palavra.getPalavraEmIngles());

        //PREENCHE O ADAPTER DO LISTVIEW
        ArrayList<String>  opcoes = new ArrayList<String>();
        String[] itens = getResources().getStringArray(R.array.onlong_press_itens);

        //verifica o estado do objeto naoEstudaMais
        for (int i=0;i<itens.length;i++) {

            if(i == 0 && palavra.isNaoEstudar() == Constantes.TRUE)
                opcoes.add("Voltar a estudar");
            else if(i ==  1 && palavra.isCardPersonalizado() == Constantes.TRUE)
                opcoes.add("Adicionado ao card personalizado");
            else
                opcoes.add(itens[i]);
        }

        lst.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, opcoes));

        alert.setView(view);
        final AlertDialog dialog  = alert.create();
        dialog.show();

        //CLIQUE ITEM NO LISTVIEW
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String itemTextoClicado = ((TextView) view).getText().toString();
                String result =  alteraObjetoClicado(palavra, itemTextoClicado, position);
                ((TextView) view).setText(result);

                String msgToast = "";
                if(position == 0){
                    mAdapter.removerItemJaSei(posicaoAux);
                    msgToast = palavra.isNaoEstudar() == Constantes.TRUE ? "Adicionado ao nao estudar mais." :  "Removido do nao estudar mais.";
                }
                else if(position ==  1) {
                    mAdapter.alterarObjetoCardPersonalizado(posicaoAux);
                    msgToast = palavra.isCardPersonalizado() == Constantes.TRUE ?  "Adicionado ao card pesonalizado." : "Removido do card pesonalizado.";
                }
                else if(position == 2) {
                    ((TextView) view).setText("Alterar palavra");

                    Intent it = new Intent(MainActivity.this, CadastrarNovaPalavraActivity.class);
                    it.putExtra(Palavra.ID, palavra);
                    startActivityForResult(it, 3);
                }
                if(position < 2 )
                    Mensagem.toast(MainActivity.this,msgToast).show();

                dialog.dismiss();
            }

        });
    }

    //Click MenuDrawer
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {

        if(position == Constantes.CONFIGURACAO) {
            startActivity(new Intent(MainActivity.this, ConfiguracaoActivity.class));
        }
        else if(position ==  Constantes.ACTIVITY_CADASTRAR) {

            Intent intent = new Intent(MainActivity.this,CadastrarUsuarioActivity.class);
            intent.putExtra(Usuario.ID,true);
            startActivityForResult(intent, 1);
        }
        else if(position == Constantes.ENCERRRAR_SESSAO) {
            AlertDialog.Builder alert =   Mensagem.alertDialog(MainActivity.this, "Encerrar sessão", "Deseja realmente sair ?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Utilitario.deletaSharedPreferenceUsuario(MainActivity.this);
                            startActivity(new Intent(MainActivity.this, SplashActivity.class));
                            finish();
                        }
                    }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Se alterou os dados do usuario entra aqui e carrega o menu atualizado
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){

                boolean estaAlterando = data.getBooleanExtra(Palavra .ID,true);
                if(estaAlterando) {
                    usuarioLogadoGlobal =  getUsuarioLogado();
                    criaDrawerMenu(savedInstanceState);
                }
            }
        }

        //se salvou uma nova palavra quando sair da tela cai aqui e adiciona as palvras na lista geral de
        if(resultCode == 2) {
            ArrayList<Palavra> novasPalavras = (ArrayList<Palavra>) data.getExtras().getSerializable(Palavra.ID);

            for(Palavra p : novasPalavras){
                Palavra palavra = new Palavra();
                palavra.setId(p.getId());
                palavra.setPalavraEmIngles(p.getPalavraEmIngles());
                palavra.setPalavraEmPortugues(p.getPalavraEmPortugues());
                palavra.setCardPersonalizado(Constantes.FALSE);
                palavra.setNaoEstudar(Constantes.FALSE);
                palavra.setIndicePalavra(p.getIndicePalavra());
                palavra.setUsuarioId(p.getUsuarioId());
                palavra.setQtdErros(p.getQtdErros());
                palavra.setQtdAcertos(p.getQtdAcertos());
                palavra.setQtdVezesEstudou(p.getQtdVezesEstudou());
                mAdapter.itensInserido(palavra,posicaoAux);
            }
        }

        if(resultCode == 3) {
            if(data != null) {
                if(data.getExtras().getSerializable(Palavra.ID) !=null){
                    Palavra  palavraAlterada = (Palavra) data.getExtras().getSerializable(Palavra.ID);
                    mAdapter.notifyObjetoAlterado(palavraAlterada,posicaoAux);
                    palavraSelecionadaAlterar.setText(palavraAlterada.getPalavraEmIngles());
                }
            }
        }
    }

    @Override
    public void showToast(String message) { }

    @Override
    public void showSnackBar(String message) {
        Mensagem.snackbar(message,findViewById(R.id.main)).show();
    }


    //CLASS SearchFiltro
    private class SearchFiltro implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {

            if(palavrasListaGlobal != null) {

                ArrayList<Palavra> mListaAux = new ArrayList<Palavra>();

                for (int i = 0; i < palavrasListaGlobal.size(); i++) {
                    if (palavrasListaGlobal.get(i).getPalavraEmIngles().toLowerCase().contains(newText.toLowerCase())) {
                        mListaAux.add(palavrasListaGlobal.get(i));
                    }
                }

                if (mListaAux.size() > -1) {
                    mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
                    mAdapter = new MyRecyclerViewAdapter(mListaAux, MainActivity.this);
                    mRecyclerView.setAdapter(mAdapter);
                }
                mAdapter.setRecycleViewOnClickListenerHack(MainActivity.this);
            }
            return  false;
        }
    }

}

