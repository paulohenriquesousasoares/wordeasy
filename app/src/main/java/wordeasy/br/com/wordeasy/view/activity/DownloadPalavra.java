package wordeasy.br.com.wordeasy.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import wordeasy.br.com.wordeasy.R;

public class DownloadPalavra extends AppCompatActivity {

    private Toolbar mtoolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dowload);
        inicializarViews();

        ImageView imgBaixaItermediaria = (ImageView) findViewById(R.id.imgEstudadaOK_intermediaria);
        imgBaixaItermediaria.setEnabled(false);

    }

    private void inicializarViews(){
        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        mtoolbar.setTitle(R.string.download);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
