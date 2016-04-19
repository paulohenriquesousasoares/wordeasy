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

        Log.i("TAG",String.valueOf(mDataset.size()) );

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

       //int color = Color.argb(255,new Random().nextInt(255),new Random().nextInt(255),new Random().nextInt(255));
        holder.containerRadius.setBackgroundDrawable(new
                ColorDrawable(Utilitario.getColor(mDataset.get(position).getIndicePalavra())));
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public  class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView palavraEmIngles;
        TextView palavraTraducaoUm;
        TextView serial;
        ImageView imgFavoritos;
        RelativeLayout containerRadius;

        public DataObjectHolder(final View itemView) {
            super(itemView);

            palavraEmIngles = (TextView) itemView.findViewById(R.id.txtPalavraEmIngles);
            palavraTraducaoUm = (TextView) itemView.findViewById(R.id.txtTraducaoUm);
            serial = (TextView) itemView.findViewById(R.id.serial);
            imgFavoritos = (ImageView) itemView.findViewById(R.id.img_favorite);
            containerRadius = (RelativeLayout) itemView.findViewById(R.id.container_radius);

            itemView.setOnClickListener(this);
            imgFavoritos.setOnClickListener(this);
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

    public void addFavorito(int position, View view) {

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        dataObjectHolder.imgFavoritos.setImageResource(R.drawable.ic_favorite_black_24dp);

        //TODO = alterar este objeto na base de dados como favorito true
        mDataset.get(position).setFavorito(true);
        notifyItemChanged(position);
    }

    public void removeDosFavoritos(int position, View view) {
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        dataObjectHolder.imgFavoritos.setImageResource(R.drawable.ic_favorite_border_black_24dp);

        //TODO = alterar este objeto na base de dados como favorito true
        mDataset.get(position).setFavorito(false);
        notifyItemChanged(position);
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
                            mDataset.get(position).isFavorito());
    }
}
