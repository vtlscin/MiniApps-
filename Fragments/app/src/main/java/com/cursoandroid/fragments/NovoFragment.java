package com.cursoandroid.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class NovoFragment extends Fragment {

    private Button botao;

    public NovoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        //Obtendo a view do fragmento
        final FragmentManager fragmentManager = getFragmentManager();
        View view = inflater.inflate(R.layout.fragment_novo, container, false);
        botao = view.findViewById(R.id.botaoId);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(),"Mudou o fragment!!",Toast.LENGTH_SHORT).show();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.frameContainerId,new SegundoFragment())
                        .commit();
            }
        });
       return view;
    }

}
