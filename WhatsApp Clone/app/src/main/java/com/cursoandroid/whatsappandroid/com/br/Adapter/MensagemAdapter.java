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
import com.cursoandroid.whatsappandroid.com.br.helper.Preferencias;
import com.cursoandroid.whatsappandroid.com.br.modelo.Mensagem;

import java.util.ArrayList;
import java.util.List;

public class MensagemAdapter extends ArrayAdapter<Mensagem> {


    private Context context;
    private ArrayList<Mensagem> mensagens;

    public MensagemAdapter(Context c, ArrayList<Mensagem> objects) {
        super(c, 0, objects);
        this.context = c;
        this.mensagens = objects;
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {

        View view = null;

        //Verifica se a lista esta preenchida
        if(mensagens != null){

            //Recupera dados do usuario remetente
            Preferencias preferencias = new Preferencias(context);
            String idUsuarioRemetente = preferencias.getIdentificador();

            //Inicializa objeito para montagem do layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //Recuperar mensagem
            Mensagem mensagem = mensagens.get(position);

            //Monta view a partir do xml
            if(idUsuarioRemetente.equals(mensagem.getIdUsuario())){
                view  = inflater.inflate(R.layout.item_mensagem_direita,parent,false);
            }else{
                view  = inflater.inflate(R.layout.item_mensagem_esquerda,parent,false);
            }


            //Recuperar elemento para exibição
            TextView textoMensagem = view.findViewById(R.id.tv_mensagem);
            textoMensagem.setText(mensagem.getMensagem());

        }

        return view;
    }
}
