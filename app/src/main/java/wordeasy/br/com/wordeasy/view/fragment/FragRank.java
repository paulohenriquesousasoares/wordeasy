package wordeasy.br.com.wordeasy.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import wordeasy.br.com.wordeasy.R;

/**
 * Created by paulo on 28/12/2015.
 */
public class FragRank extends Fragment {

    public FragRank() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.rank, container, false);

        return  view;
    }

}