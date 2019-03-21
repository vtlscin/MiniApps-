package com.cursoandroid.whatsappandroid.com.br.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cursoandroid.whatsappandroid.com.br.Adapter.ContatoAdapter;
import com.cursoandroid.whatsappandroid.com.br.R;
import com.cursoandroid.whatsappandroid.com.br.activity.ConversaActivity;
import com.cursoandroid.whatsappandroid.com.br.config.ConfiguracaoFirebase;
import com.cursoandroid.whatsappandroid.com.br.helper.Preferencias;
import com.cursoandroid.whatsappandroid.com.br.modelo.Contato;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContatosFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<Contato> contatos;
    private DatabaseReference referencia;
    private ValueEventListener valueEventListenerContatos;

    public ContatosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        referencia.addValueEventListener(valueEventListenerContatos);
    }

    @Override
    public void onStop() {
        super.onStop();
        referencia.removeEventListener(valueEventListenerContatos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        //Instanciar objetos
        contatos = new ArrayList<>();


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contatos, container, false);

        //Montar lista
        listView = view.findViewById(R.id.lv_contatos);
        /*adapter = new ArrayAdapter(
            getActivity(),
            R.layout.lista_contato,
            contatos
        );*/
        adapter = new ContatoAdapter(getActivity(),contatos);
        listView.setAdapter(adapter);

        //Recuperar contatos do firebase
        Preferencias preferencias = new Preferencias(getActivity());
        String identificadorUsuarioLogado = preferencias.getIdentificador();
        referencia = ConfiguracaoFirebase.getFirebase()
                        .child("contatos")
                        .child(identificadorUsuarioLogado);
        //Listener para recuperar contatos
        valueEventListenerContatos = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Limpar lista
                contatos.clear();

                //Listar contatos
                for(DataSnapshot dados : dataSnapshot.getChildren()){
                    Contato contato = dados.getValue(Contato.class);
                    contatos.add(contato);

                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), ConversaActivity.class);

                //recuperar dados a serem passados
                Contato contato = contatos.get(position);

                //enviando dados para conversa activity
                intent.putExtra("nome",contato.getNome());
                intent.putExtra("email",contato.getEmail());
                startActivity(intent);

            }
        });

        return view;

    }

}
