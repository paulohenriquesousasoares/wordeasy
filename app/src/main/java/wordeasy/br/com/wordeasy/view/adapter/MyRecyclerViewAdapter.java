package wordeasy.br.com.wordeasy.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import wordeasy.br.com.wordeasy.R;
import wordeasy.br.com.wordeasy.interfaces.RecycleViewOnclickListener;
import wordeasy.br.com.wordeasy.view.dominio.Palavra;
import wordeasy.br.com.wordeasy.view.util.Utilitario;

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

//        if(mDataset.get(position).isNaoEstudar())
//            holder.naoEstudarMaisSelecionado.setText("nao estudar mais");
//        else
//            holder.naoEstudarMaisSelecionado.setText("");

        if(mDataset.get(position).isCardPersonalizado())
            holder.cardPersonalizadoSelecionado.setText("Adicionado card personalizado");
        else
            holder.cardPersonalizadoSelecionado.setText("");


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
        private TextView cardPersonalizadoSelecionado;
        private TextView serial;
        private RelativeLayout containerRadius;

        public DataObjectHolder(final View itemView) {
            super(itemView);

            palavraEmIngles = (TextView) itemView.findViewById(R.id.txtPalavraEmIngles);
            palavraTraducaoUm = (TextView) itemView.findViewById(R.id.txtTraducao);
            serial = (TextView) itemView.findViewById(R.id.serial);
            cardPersonalizadoSelecionado = (TextView) itemView.findViewById(R.id.txtCardPersonalizadoItem);
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

    public  void removerItemJaSei(int position) {

        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    public  void alterarObjetoCardPersonalizado(int position) {

        if(mDataset.get(position).isCardPersonalizado()) {
            mDataset.get(position).setCardPersonalizado(false);
            mDataset.get(position).setCardPersonalizadoSelecionado("");
        }
        else {
            mDataset.get(position).setCardPersonalizado(true);
            mDataset.get(position).setCardPersonalizadoSelecionado("Adicionado ao card personalizado");

        }
        notifyDataSetChanged();
    }

    public void itensInserido(Palavra palavra , int position) {

        mDataset.add(palavra);

        notifyDataSetChanged();
    }

    public void notifyObjetoAlterado(Palavra palavra,int positon) {
        mDataset.get(positon).setPalavraEmIngles(palavra.getPalavraEmIngles());
        mDataset.get(positon).setPalavraEmPortugues(palavra.getPalavraEmPortugues());
        mDataset.get(positon).setIndicePalavra(palavra.getPalavraEmIngles().substring(0,1));
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
        palavra.setNaoEstudarMaisSelecionado(mDataset.get(position).getNaoEstudarMaisSelecionado());
        palavra.setCardPersonalizadoSelecionado(mDataset.get(position).getCardPersonalizadoSelecionado());
        return palavra;
    }
}
