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

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;

import me.drakeet.materialdialog.MaterialDialog;
import wordeasy.br.com.wordeasy.R;
import wordeasy.br.com.wordeasy.interfaces.RecycleViewOnclickListener;
import wordeasy.br.com.wordeasy.dominio.Palavra;
import wordeasy.br.com.wordeasy.util.Utilitario;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.DataObjectHolder> {

    private ArrayList<Palavra> mDataset;
    private static RecycleViewOnclickListener recycleViewOnclickListener;
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


//        try{
//            YoYo.with(Techniques.FlipInX)
//                    .duration(700)
//                    .playOn(holder.itemView);
//        }
//        catch(Exception e){}
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public  class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
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
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(recycleViewOnclickListener != null) {
                recycleViewOnclickListener.myOnClickListener(v,getPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if(recycleViewOnclickListener != null) {
                recycleViewOnclickListener.myOnLongPressClickListener(v,getPosition());
            }
            return true;
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

    public  void alterarObjetoNaoEstudar(int position) {
       mDataset.get(position).setNaoEstudar(true);
        mDataset.get(position).setPalavraEmPortugues("this is a test.");
       notifyDataSetChanged();
    }

    public  void alterarObjetoCardPersonalizado(int position) {
        mDataset.get(position).setCardPersonalizado(true);
        notifyDataSetChanged();
    }


    public  Palavra getPalavraSelecionada(int position){

        Palavra palavra = new Palavra();
        palavra.setId(mDataset.get(position).getId());
        palavra.setPalavraEmIngles(mDataset.get(position).getPalavraEmIngles());
        palavra.setPalavraEmPortugues(mDataset.get(position).getPalavraEmPortugues());
        palavra.setIndicePalavra(mDataset.get(position).getIndicePalavra());
        palavra.setFavorito(mDataset.get(position).isFavorito());
        palavra.setUsuario(mDataset.get(position).getUsuario());
        palavra.setQtdErros(mDataset.get(position).getQtdErros());
        palavra.setQtdAcertos(mDataset.get(position).getQtdAcertos());
        palavra.setQtdVezesEstudou(mDataset.get(position).getQtdVezesEstudou());
        palavra.setCardPersonalizado(mDataset.get(position).isCardPersonalizado());
        palavra.setNaoEstudar(mDataset.get(position).isNaoEstudar());
        return palavra;
    }
}
