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
import com.cursoandroid.whatsappandroid.com.br.modelo.Conversa;

import java.util.ArrayList;
import java.util.List;

public class ConversaAdapter extends ArrayAdapter<Conversa> {


    private ArrayList<Conversa> conversas;
    private Context context;

    public ConversaAdapter(Context c, ArrayList<Conversa> objects) {
        super(c, 0, objects);
        this.context = c;
        this.conversas = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        //Verifica se a lista está preenchida
        if (conversas != null){

            //inicializar obejto para montagem da view
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //Monta view a partir do xml
            view = layoutInflater.inflate(R.layout.lista_conversas,parent,false);

            //Recuperar elemento para exibição
            TextView nome = view.findViewById(R.id.tv_titulo);
            TextView ultimaMensagem = view.findViewById(R.id.tv_subtitulo);

            Conversa conversa = conversas.get(position);
            nome.setText(conversa.getNome());
            ultimaMensagem.setText(conversa.getMensagem());

        }

        return view;
    }
}
