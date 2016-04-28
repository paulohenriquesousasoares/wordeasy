package wordeasy.br.com.wordeasy.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
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
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.ButterKnife;
import wordeasy.br.com.wordeasy.R;
import wordeasy.br.com.wordeasy.adapter.MyRecyclerViewAdapter;
import wordeasy.br.com.wordeasy.adapter.ViewPagerAdapter;
import wordeasy.br.com.wordeasy.dao.repositorio.PalavraRepositorio;
import wordeasy.br.com.wordeasy.dominio.Usuario;
import wordeasy.br.com.wordeasy.fragment.FragDificil;
import wordeasy.br.com.wordeasy.fragment.FragInicial;
import wordeasy.br.com.wordeasy.interfaces.RecycleViewOnclickListener;
import wordeasy.br.com.wordeasy.dominio.Palavra;
import wordeasy.br.com.wordeasy.util.Constantes;
import wordeasy.br.com.wordeasy.util.CreateMenuDrawable;
import wordeasy.br.com.wordeasy.util.Mensagem;
import wordeasy.br.com.wordeasy.util.Utilitario;

public class MainActivity extends AppCompatActivity   implements
        RecycleViewOnclickListener,
        Drawer.OnDrawerItemClickListener{

    private Toolbar mtoolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MenuItem item1;
    private int positionPage = 0;
    private boolean ordenaCrescente;
    private  RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mAdapter;
    private ArrayList<Palavra> palavras;
    private Drawer.Result navigationDrawerLeft;
    private AccountHeader.Result headerNavigationLeft;
    private Bundle savedInstanceState;
    private int posicaoAux;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);
        initiView();
        this.savedInstanceState = savedInstanceState;

        //menu drawer
        criaMenuDrawer(savedInstanceState);
    }


    /*==============================================================================================================
    *       METODOS
    * ==============================================================================================================*/

    private void initiView() {

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
        getPalavrasEstudadas();
    }

    public void alteraSubTituloToolbar(String subTitulo) {
        mtoolbar.setSubtitle(subTitulo);
    }

    public   ArrayList<Palavra> getPalavrasEstudadas() {

        PalavraRepositorio palavraRepositorio = new PalavraRepositorio();
        try {
            palavras  = palavraRepositorio.get(Utilitario.getSharedPreferenceUsuario(MainActivity.this).getId());
        } catch (Exception e) {
            Mensagem.toast(MainActivity.this, ""+e).show();
        }
        return palavras;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragInicial(), getResources().getString(R.string.frag_Inicial));
        adapter.addFragment(new FragDificil(), getResources().getString(R.string.frag_erradas));
        viewPager.setAdapter(adapter);
    }

    private void criaMenuDrawer(Bundle savedInstanceState) {

        Usuario user =  Utilitario.getSharedPreferenceUsuario(MainActivity.this);

        CreateMenuDrawable draweMenu = new CreateMenuDrawable();
        draweMenu.header(this, savedInstanceState, user.getNome(), user.getEmail());
        navigationDrawerLeft = draweMenu.drawerMenu(this, mtoolbar, savedInstanceState,-1);
        draweMenu.navigationItem(this, navigationDrawerLeft);

        navigationDrawerLeft.setOnDrawerItemClickListener(this);
    }

    public ArrayList<Palavra> getAllPalavras(){
        return palavras;
    }

    public void preencheRecycleview(ArrayList<Palavra> palavrasL,RecyclerView recyclerView, MyRecyclerViewAdapter adapter) {

        mAdapter = new MyRecyclerViewAdapter(palavrasL, MainActivity.this);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setRecycleViewOnClickListenerHack(MainActivity.this);
    }

    public String alteraObjetoClicado(Palavra palavra, String textoClicado, int positionOpcaoClicado) {
        try {

            PalavraRepositorio palavraRepositorio = new PalavraRepositorio();
            String retorno = "";

            if ( positionOpcaoClicado == 0) {

                palavra.setNaoEstudar(palavra.isNaoEstudar() ? false : true);
                retorno =  verificaSeEstaNaoEstudarMais(textoClicado);

            }
            else if(positionOpcaoClicado == 1 ) {

                palavra.setCardPersonalizado(palavra.isCardPersonalizado() ? false : true);
                retorno =  verificaCardPersonalizado(textoClicado);
            }

            palavraRepositorio.create(palavra);
            return retorno;

        } catch (Exception e) {Mensagem.toast(MainActivity.this,""+e).show();}
        return null;
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


    /*==============================================================================================================
    *      LISTENER
    * ==============================================================================================================*/

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = navigationDrawerLeft.saveInstanceState(outState);
//        outState = headerNavigationLeft.saveInstanceState(outState);
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

        PalavraRepositorio palavraRepositorio = new PalavraRepositorio();
        long idUser =  Utilitario.getSharedPreferenceUsuario(MainActivity.this).getId();

        int id = item.getItemId();

        if(id == R.id.ic_orderby) {
            ordenaCrescente=!ordenaCrescente;
            Collections.sort(palavras, new Comparator<Palavra>() {
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
            preencheRecycleview(palavras,mRecyclerView,mAdapter);
            String ordenacaoTipo = ordenaCrescente ? "AZ" : "ZA";
            Mensagem.snackbar("Ordenado de " + ordenacaoTipo, findViewById(R.id.main)).show();
        }
        //menu varios
        else if(id == R.id.addCardPersonalizadas) {

            try {

                mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
                ArrayList<Palavra> lista =  palavraRepositorio.getAllCardPersonalizado(idUser,0);
                preencheRecycleview(lista,mRecyclerView,mAdapter);
                alteraSubTituloToolbar("Card personalizado");

            } catch (Exception e) {
                Mensagem.toast(MainActivity.this,""+e).show();
            }
        }
        else if(id == R.id.naoEstudar) {

            try {

                mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
                ArrayList<Palavra> lista =  palavraRepositorio.getAllCardPersonalizado(idUser,1);
                preencheRecycleview(lista, mRecyclerView, mAdapter);
                alteraSubTituloToolbar("Já sei");

            } catch (Exception e) {
                Mensagem.toast(MainActivity.this,""+e).show();
            }

        }
        else if(id == R.id.todas) {
            preencheRecycleview(palavras,mRecyclerView,mAdapter);
            alteraSubTituloToolbar("Todas");
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

        lst.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, opcoes));

        alert.setView(view);
        alert.show();

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
                    msgToast = palavra.isNaoEstudar() ? "Adicionado ao nao estudar mais." :  "Removido do nao estudar mais.";
                }
                else if(position ==  1) {
                    mAdapter.alterarObjetoCardPersonalizado(posicaoAux);
                    msgToast = palavra.isCardPersonalizado() ?  "Adicionado ao card pesonalizado." : "Removido do card pesonalizado.";
                }
                else if(position == 2) {
                    ((TextView) view).setText("Alterar palavra");

                    Intent it = new Intent(MainActivity.this, CadastrarNovaPalavraActivity.class);
                    it.putExtra(Palavra.ID, palavra);
                    startActivityForResult(it, 3);
                }


                if(position < 2 )
                    Mensagem.toast(MainActivity.this,msgToast).show();
            }

        });
    }

    //Click MenuDrawer
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
        if(position ==  Constantes.ACTIVITY_CADASTRAR) {

            Intent intent = new Intent(MainActivity.this,CadastrarUsuarioActivity.class);
            intent.putExtra(Usuario.ID,true);
            startActivityForResult(intent,1);
        }
        else if(position == Constantes.ENCERRRAR_SESSAO) {
            Utilitario.deletaSharedPreferenceUsuario(MainActivity.this);
            startActivity(new Intent(MainActivity.this, SplashActivity.class));
            this.finish();
        }
        else if(position == Constantes.CONFIGURACAO) {
            startActivity(new Intent(MainActivity.this, ConfiguracaoActivity.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Se alterou os dados do usuario entra aqui e carrega o menu atualizado
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){

                String result = data.getStringExtra("result");
                if(result.equals("alterando")) {
                    criaMenuDrawer(savedInstanceState);
                }
            }
        }

        //se salvou uma nova palavra quando sair da tela cai aqui e adiciona as palvras na lista geral de palavras
        if(resultCode == 2) {
            ArrayList<Palavra> novasPalavras = (ArrayList<Palavra>) data.getExtras().getSerializable(Palavra.ID);

            for(Palavra p : novasPalavras){
                Palavra palavra = new Palavra();
                palavra.setId(p.getId());
                palavra.setPalavraEmIngles(p.getPalavraEmIngles());
                palavra.setPalavraEmPortugues(p.getPalavraEmPortugues());
                palavra.setCardPersonalizado(false);
                palavra.setNaoEstudar(false);
                palavra.setIndicePalavra(p.getIndicePalavra());
                palavra.setUsuario(p.getUsuario());
                palavra.setQtdErros(p.getQtdErros());
                palavra.setQtdAcertos(p.getQtdAcertos());
                palavra.setQtdVezesEstudou(p.getQtdVezesEstudou());
                mAdapter.itensInserido(palavra,posicaoAux);
                //palavras.add(palavra);

            }
        }

        if(resultCode == 3) {
            if(data != null) {
                if(data.getExtras().getSerializable(Palavra.ID) !=null){
                    Palavra  palavraAlterada = (Palavra) data.getExtras().getSerializable(Palavra.ID);
                    mAdapter.notifyObjetoAlterado(palavraAlterada,posicaoAux);
                }
            }
        }
    }

    //CLASS SearchFiltro
    private class SearchFiltro implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {

            if(palavras != null) {

                ArrayList<Palavra> mListaAux = new ArrayList<Palavra>();

                for (int i = 0; i < palavras.size(); i++) {
                    if (palavras.get(i).getPalavraEmIngles().toLowerCase().contains(newText.toLowerCase())) {
                        mListaAux.add(palavras.get(i));
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

