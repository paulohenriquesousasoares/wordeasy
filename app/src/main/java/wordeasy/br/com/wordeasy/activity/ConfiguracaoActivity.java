package wordeasy.br.com.wordeasy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import java.util.Calendar;
import butterknife.Bind;
import butterknife.ButterKnife;
import wordeasy.br.com.wordeasy.R;
import wordeasy.br.com.wordeasy.dao.repositorio.ConfiguracaoRepositorio;
import wordeasy.br.com.wordeasy.dao.repositorio.UsuarioRepositorio;
import wordeasy.br.com.wordeasy.dominio.Configuracao;
import wordeasy.br.com.wordeasy.dominio.Usuario;
import wordeasy.br.com.wordeasy.util.Mensagem;
import wordeasy.br.com.wordeasy.util.Utilitario;


public class ConfiguracaoActivity extends AppCompatActivity implements
       View.OnClickListener{

    @Bind(R.id.spinner_itens_por_estudo) Spinner spinnerItensPorEstudo;
    @Bind(R.id.idSpinnerRevisao) Spinner spinnerItensPorRevisao;
    @Bind(R.id.idCheckLigado) CheckBox checkBoxLigado;
    @Bind(R.id.imgDataLembre) ImageView imgSelecionaDateLembrete;
    @Bind(R.id.toolbar)  Toolbar mtoolbar;
    @Bind(R.id.idLembreteDiarioLabel) TextView lembrete;

    private int itensPorEstudo;
    private int itensPorRevisao;
    private  Configuracao  configuracao;
    private UsuarioRepositorio usuarioRepositorio;
    private ConfiguracaoRepositorio configuracaoRepositorio;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracao);
        ButterKnife.bind(this);
        initiView();

        //LISTENER
        spinnerItensPorEstudo.setOnItemSelectedListener(itensPorEstudoClique);
        spinnerItensPorRevisao.setOnItemSelectedListener(itensPorRevisaoClique);
        lembrete.setOnClickListener(this);
        checkBoxLigado.setOnClickListener(this);
        imgSelecionaDateLembrete.setOnClickListener(this);

        Usuario usuario = Utilitario.getSharedPreferenceUsuario(ConfiguracaoActivity.this);

        //verifica se este usuario tem alguma configuracao ja cadastrada
       Configuracao config = getConfiguracao();

        //se menor que -1 significa que ainda nao cradastrou nenhuma configuracao
        if(config.getUsuarioId() < 1) {
            configuracao.setItensPorSessaoRevisao(5);
            configuracao.setItensPorSessaoEstudo(5);
            configuracao.setHora(13);
            configuracao.setMinuto(00);
            configuracao.setUsuarioId(usuario.getId());
            configuracao.setAtivo( true );
            salvaConfiguracao(configuracao);
        }
        //significa que tem alguma configuracao e preenche o obj com os dados
        else {
            configuracao = config;
            spinnerItensPorEstudo.setSelection(getPositionItemSelectedSpinner(configuracao.getItensPorSessaoEstudo()));
            spinnerItensPorRevisao.setSelection(getPositionItemSelectedSpinner(configuracao.getItensPorSessaoRevisao()));
        }

        //verifica se o alarme  esta ativo o
        if(config.isAtivo()) {
            checkBoxLigado.setChecked(true);
            setLabelLembrete(configuracao);
        }
        else
            checkBoxLigado.setChecked(false);

        checkBoxLigado.setText(checkBoxLigado.isChecked() ? "Ligado" : "Desligado");
    }


      /*===================================================================================================================
            METODOS
       =================================================================================================================*/
    void initiView() {

        usuarioRepositorio = new UsuarioRepositorio();
        configuracaoRepositorio = new ConfiguracaoRepositorio();
        configuracao = new Configuracao();


        mtoolbar.setTitle(R.string.appConfiguraca);
        setSupportActionBar(mtoolbar);
        mtoolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void setLabelLembrete(Configuracao configuracao) {
        lembrete.setText("Será lembrado todo dia as " + configuracao.getHora() + ":" + configuracao.getMinuto());
    }

    public int getPositionItemSelectedSpinner(int valorItens) {

        int position = 0;

        if(valorItens == 5)
            position = 0;
        else if(valorItens == 10)
            position = 1;
        else if(valorItens == 15)
            position = 2;
        else if(valorItens == 20)
            position = 3;

        return  position;
    }

    private void salvaConfiguracao(Configuracao configuracao) {
        ConfiguracaoRepositorio configuracaoRepositorio = new ConfiguracaoRepositorio();
        try {
            configuracaoRepositorio.create(configuracao);
            Utilitario.criaAlarme(ConfiguracaoActivity.this, configuracao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Configuracao getConfiguracao() {
        Configuracao config = null;
        try {
            config = configuracaoRepositorio.getConfiguracao(Utilitario.getSharedPreferenceUsuario(ConfiguracaoActivity.this).getId());
        } catch (Exception e) {
            Mensagem.toast(ConfiguracaoActivity.this,"Error ao recuperar as configurações."+e);
        }
        return  config;
    }


    /*===================================================================================================================
            LISTENER
       =================================================================================================================*/


    //CLIQUE SPINNER ITEM_POR_ESTUDO  SELECT ITEM
    public AdapterView.OnItemSelectedListener itensPorEstudoClique = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            itensPorEstudo = Integer.parseInt(parent.getItemAtPosition(position).toString());
            configuracao.setItensPorSessaoEstudo(itensPorEstudo);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    //CLIQUE SPINNER ITEM_POR_REVISA  SELECT ITEM
    public AdapterView.OnItemSelectedListener itensPorRevisaoClique = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            itensPorRevisao = Integer.parseInt(parent.getItemAtPosition(position).toString());
            configuracao.setItensPorSessaoRevisao(itensPorRevisao);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId =  item.getItemId();
        if(itemId == R.id.ic_salvar) {
            salvaConfiguracao(configuracao);
            Mensagem.snackbar("Configuração realizada com sucesso.",findViewById(R.id.idRl_lembrete_actConfig)).show();
        }
        else {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.config_menu, menu);
        return true;
    }

    //CLIQUE LISTENER GERAL
    @Override
    public void onClick(View v) {

        int id = v.getId();
        if(id == R.id.idCheckLigado) {

            if(!checkBoxLigado.isChecked()) {
                Utilitario.destroyAlarme(ConfiguracaoActivity.this);
                lembrete.setText("");
            }
            else {
                configuracao =  getConfiguracao();
                Utilitario.criaAlarme(ConfiguracaoActivity.this, configuracao);
                setLabelLembrete(configuracao);
                checkBoxLigado.setText(checkBoxLigado.isChecked() ? "Ligado" : "Desligado");
            }
        }
        else if(id ==  R.id.imgDataLembre) {
            Calendar now = Calendar.getInstance();
            TimePickerDialog timePickerDialog = TimePickerDialog.newInstance
            (
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                            configuracao.setHora(hourOfDay);
                            configuracao.setMinuto(minute);
                            lembrete.setText("Será lembrado todo dia as " + hourOfDay + ":" + minute);
                            checkBoxLigado.setChecked(true);
                        }
                    },
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    now.get(Calendar.SECOND),
                    true
            );
            timePickerDialog.show(getFragmentManager(), "timerDialog");
        }
    }
}
