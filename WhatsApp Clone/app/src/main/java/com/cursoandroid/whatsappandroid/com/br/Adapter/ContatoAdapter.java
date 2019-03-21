package com.cursoandroid.whatsappandroid.com.br.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cursoandroid.whatsappandroid.com.br.R;
import com.cursoandroid.whatsappandroid.com.br.modelo.Contato;

import java.util.ArrayList;
import java.util.List;

public class ContatoAdapter extends ArrayAdapter<Contato> {

    private ArrayList<Contato> contatos;
    private Context context;

    public ContatoAdapter(Context c, ArrayList<Contato> objects) {
        super(c,0, objects);
        this.contatos = objects;
        this.context = c;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        //Verifica se a lista esta vazia
        if(contatos != null){

            //inicializar objeto para montagem da view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //Montar view a partir do xml
            view = inflater.inflate(R.layout.lista_contato,parent,false);

            //recupera elemento para exibiçao
            TextView nomeContato = view.findViewById(R.id.tv_nome);
            TextView emailContato = view.findViewById(R.id.tv_email);

            Contato contato = contatos.get(position);
            nomeContato.setText(contato.getNome());
            emailContato.setText(contato.getEmail());
        }

        return view;

    }
}
