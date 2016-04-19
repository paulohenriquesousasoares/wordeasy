package wordeasy.br.com.wordeasy.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wordeasy.br.com.wordeasy.R;


public class FragFavoritos extends Fragment  {

    public FragFavoritos() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.one, container, false);


        return view;
    }

}