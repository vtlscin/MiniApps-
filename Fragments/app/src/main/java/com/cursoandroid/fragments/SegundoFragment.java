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
public class SegundoFragment extends Fragment {

    private Button botao;
    public SegundoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final FragmentManager fragmentManager = getFragmentManager();
        View view = inflater.inflate(R.layout.fragment_segundo, container, false);
        botao = view.findViewById(R.id.botaoId);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(),"Mudou o fragment!!",Toast.LENGTH_SHORT).show();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.frameContainerId,new NovoFragment())
                        .commit();
            }
        });
        return view;
    }

}
