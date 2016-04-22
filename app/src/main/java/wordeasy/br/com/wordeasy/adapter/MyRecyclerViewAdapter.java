package wordeasy.br.com.wordeasy.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import me.drakeet.materialdialog.MaterialDialog;
import wordeasy.br.com.wordeasy.R;
import wordeasy.br.com.wordeasy.interfaces.RecycleViewOnclickListener;
import wordeasy.br.com.wordeasy.dominio.Palavra;
import wordeasy.br.com.wordeasy.util.Utilitario;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.DataObjectHolder> {

    private ArrayList<Palavra> mDataset;
    private static RecycleViewOnclickListener recycleViewOnclickListener;
    private MaterialDialog mMaterialDialog;
    private Context context;
    private LayoutInflater gLayoutInflater;

    public MyRecyclerViewAdapter(ArrayList<Palavra> palavras, Context context) {
        this.mDataset = palavras;
        this.context = context;
        gLayoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_palavra, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        holder.palavraEmIngles.setText(mDataset.get(position).getPalavraEmIngles());
        holder.palavraTraducaoUm.setText(mDataset.get(position).getPalavraEmPortugues());
        holder.serial.setText("" + mDataset.get(position).getIndicePalavra());

        String indicePalavraAtual = mDataset.get(position).getIndicePalavra();
        Utilitario.getColor(indicePalavraAtual, holder.containerRadius);
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public  class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView palavraEmIngles;
        private TextView palavraTraducaoUm;
        private TextView serial;
        private RelativeLayout containerRadius;

        public DataObjectHolder(final View itemView) {
            super(itemView);

            palavraEmIngles = (TextView) itemView.findViewById(R.id.txtPalavraEmIngles);
            palavraTraducaoUm = (TextView) itemView.findViewById(R.id.txtTraducao);
            serial = (TextView) itemView.findViewById(R.id.serial);
            containerRadius = (RelativeLayout) itemView.findViewById(R.id.container_radius);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(recycleViewOnclickListener != null) {
                recycleViewOnclickListener.myOnClickListener(v,getPosition());
            }
        }
    }

    public void setRecycleViewOnClickListenerHack(RecycleViewOnclickListener r){
        recycleViewOnclickListener = r;
    }


    /*  =====================================================================================================
            METODOS
        =====================================================================================================
    */

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    public  void exibirDialog(int position, View v, Palavra palavra) {

        mMaterialDialog = new MaterialDialog(v.getContext())
                .setTitle(palavra.getPalavraEmIngles())
                .setMessage(
                                "Tradução um : " + palavra.getPalavraEmPortugues()
                )
                .setPositiveButton("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                })
                .setNegativeButton("Fechar", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });
        mMaterialDialog.show();
    }

    public  Palavra getPalavraSelecionada(int position){

        return new  Palavra(mDataset.get(position).getIndicePalavra(),
                            mDataset.get(position).getPalavraEmIngles(),
                            mDataset.get(position).getPalavraEmPortugues(),
                            mDataset.get(position).isFavorito(),
                            mDataset.get(position).getQtdErros(),
                            mDataset.get(position).getQtdAcertos(),
                            mDataset.get(position).getQtdVezesEstudou());
    }
}
