package wordeasy.br.com.wordeasy.view.activity;

import android.app.Activity;
import android.content.Context;
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
import wordeasy.br.com.wordeasy.interfaces.presenter.PresenterOperacaoConfiguracao;
import wordeasy.br.com.wordeasy.interfaces.view.ViewOperacaoRequisitaConfiguracao;
import wordeasy.br.com.wordeasy.presenter.ConfiguracaoPresenter;
import wordeasy.br.com.wordeasy.view.dominio.Configuracao;
import wordeasy.br.com.wordeasy.view.dominio.Usuario;
import wordeasy.br.com.wordeasy.view.util.Mensagem;
import wordeasy.br.com.wordeasy.view.util.Utilitario;


public class ConfiguracaoActivity extends AppCompatActivity
        implements View.OnClickListener , ViewOperacaoRequisitaConfiguracao{

    @Bind(R.id.spinner_itens_por_estudo) Spinner spinnerItensPorEstudo;
    @Bind(R.id.idSpinnerRevisao) Spinner spinnerItensPorRevisao;
    @Bind(R.id.idCheckLigado) CheckBox checkBoxLigado;
    @Bind(R.id.imgDataLembre) ImageView imgSelecionaDateLembrete;
    @Bind(R.id.toolbar)  Toolbar mtoolbar;
    @Bind(R.id.idLembreteDiarioLabel) TextView lembrete;

    private int itensPorEstudo;
    private int itensPorRevisao;
    private Configuracao configuracaoGlobal;
    private Usuario usuarioGlobal;

    private PresenterOperacaoConfiguracao mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar_configuracao);

        ButterKnife.bind(this);
        inicializarViewsTela();

        getUsuario(ConfiguracaoActivity.this);
        verificaSeUserTemConfiguracao(usuarioGlobal);
    }

    //metodo
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


   //listener

    @Override
    public void inicializarViewsTela() {
        configuracaoGlobal = new Configuracao();
        usuarioGlobal = new Usuario();

        mtoolbar.setTitle(R.string.appConfiguraca);
        setSupportActionBar(mtoolbar);
        mtoolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //listener
        spinnerItensPorEstudo.setOnItemSelectedListener(itensPorEstudoClique);
        spinnerItensPorRevisao.setOnItemSelectedListener(itensPorRevisaoClique);
        lembrete.setOnClickListener(this);
        checkBoxLigado.setOnClickListener(this);
        imgSelecionaDateLembrete.setOnClickListener(this);

        try{
            mPresenter = new ConfiguracaoPresenter(this);
        }
        catch (Exception e){
            Mensagem.toast(ConfiguracaoActivity.this, "Presenter error= " + e ).show();
        }
    }

    public AdapterView.OnItemSelectedListener itensPorEstudoClique = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            itensPorEstudo = Integer.parseInt(parent.getItemAtPosition(position).toString());
            configuracaoGlobal.setItensPorSessaoEstudo(itensPorEstudo);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public AdapterView.OnItemSelectedListener itensPorRevisaoClique = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            itensPorRevisao = Integer.parseInt(parent.getItemAtPosition(position).toString());
            configuracaoGlobal.setItensPorSessaoRevisao(itensPorRevisao);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId =  item.getItemId();
        if(itemId == R.id.ic_salvar) {
            configuracaoGlobal.setUsuarioId(usuarioGlobal.getId());
            configuracaoGlobal.setAtivo(true);
            mPresenter.criaConfiguracao(configuracaoGlobal);
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

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if(id == R.id.idCheckLigado) {

            if(!checkBoxLigado.isChecked()) {
                Utilitario.destroyAlarme(ConfiguracaoActivity.this);
                lembrete.setText("");
            }
            else {
                verificaSeUserTemConfiguracao(usuarioGlobal);
            }
        }
        else if(id ==  R.id.imgDataLembre) {
            Calendar now = Calendar.getInstance();
            TimePickerDialog timePickerDialog = TimePickerDialog.newInstance
            (
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                            configuracaoGlobal.setHora(hourOfDay);
                            configuracaoGlobal.setMinuto(minute);
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


    @Override
    public void verificaSeUserTemConfiguracao(Usuario usuario) {
        mPresenter.getConfiguracaoUsuarioAtual(usuario);
    }

    @Override
    public void criarAlarme(Configuracao configuracao) {
        Utilitario.criaAlarme(ConfiguracaoActivity.this, configuracao);
    }

    @Override
    public void preencheCampoConfiguracao(Configuracao configuracao) {

        if(configuracao.isAtivo()) {
            checkBoxLigado.setChecked(true);
            setLabelLembrete(configuracao);
        }
        else
            checkBoxLigado.setChecked(false);

        checkBoxLigado.setText(checkBoxLigado.isChecked() ? "Ligado" : "Desligado");

        spinnerItensPorEstudo.setSelection(getPositionItemSelectedSpinner(configuracao.getItensPorSessaoEstudo()));
        spinnerItensPorRevisao.setSelection(getPositionItemSelectedSpinner(configuracao.getItensPorSessaoRevisao()));
    }

    @Override
    public void getUsuario(Context context) {
        usuarioGlobal = mPresenter.getUsuairoAtual((Activity) context);
    }

    @Override
    public void showToast(String message) {
        Mensagem.toast(ConfiguracaoActivity.this,message).show();
    }

    @Override
    public void showSnackBar(String message) {
        Mensagem.snackbar(message,findViewById(R.id.idRl_lembrete_actConfig)).show();
    }
}
