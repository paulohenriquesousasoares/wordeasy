package wordeasy.br.com.wordeasy.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
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

        mtoolbar.setTitle(R.string.app_name);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setupViewPager(viewPager);

        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.branco));
        tabLayout.setupWithViewPager(viewPager);
        getPalavrasEstudadas();
    }

    public   ArrayList<Palavra> getPalavrasEstudadas() {

        PalavraRepositorio palavraRepositorio = new PalavraRepositorio();
        try {
            palavras  = palavraRepositorio.get();
        } catch (Exception e) {
            Mensagem.toast(MainActivity.this, ""+e).show();
        }
        return palavras;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragInicial(), getResources().getString(R.string.frag_Inicial));
        adapter.addFragment(new FragDificil(), getResources().getString(R.string.frag_dificil));
        viewPager.setAdapter(adapter);
    }

    private void criaMenuDrawer(Bundle savedInstanceState) {

        Usuario user =  Utilitario.getSharedPreferenceUsuario(MainActivity.this);

        CreateMenuDrawable draweMenu = new CreateMenuDrawable();
        draweMenu.header(this, savedInstanceState,user.getNome(),user.getEmail());
        navigationDrawerLeft = draweMenu.drawerMenu(this, mtoolbar, savedInstanceState,-1);
        draweMenu.navigationItem(this, navigationDrawerLeft);

        navigationDrawerLeft.setOnDrawerItemClickListener(this);
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
            mAdapter = new MyRecyclerViewAdapter(palavras, MainActivity.this);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setRecycleViewOnClickListenerHack(MainActivity.this);
            String ordenacaoTipo = ordenaCrescente ? "AZ" : "ZA";
            Mensagem.snackbar("Ordenado de " +  ordenacaoTipo ,findViewById(R.id.main)).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void myOnClickListener(View v, int position) {

        Palavra palavra =   mAdapter.getPalavraSelecionada(position);

        Intent it = new Intent(this, PalavrasDetalhesActivity.class);
        it.putExtra("id", palavra.getId());
        it.putExtra("ingles", palavra.getPalavraEmIngles());
        it.putExtra("portugues", palavra.getPalavraEmPortugues());
        it.putExtra("indice", palavra.getIndicePalavra());
        it.putExtra("favorito", palavra.isFavorito());
        it.putExtra("acertos", palavra.getQtdAcertos() );
        it.putExtra("erros", palavra.getQtdErros());
        it.putExtra("vezesEstudou", palavra.getQtdVezesEstudou());

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
            Mensagem.toast(MainActivity.this,"OnLongPress").show();
    }

    //Click MenuDrawable
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
        if(position ==  Constantes.ACTIVITY_CADASTRAR) {

            Intent intent = new Intent(MainActivity.this,CadastrarUsuarioActivity.class);
            intent.putExtra(Usuario.ID,true);
            //startActivity(intent);
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

