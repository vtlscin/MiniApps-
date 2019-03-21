package com.cursoandroid.whatsappandroid.com.br.activity;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.cursoandroid.whatsappandroid.com.br.Adapter.MensagemAdapter;
import com.cursoandroid.whatsappandroid.com.br.R;
import com.cursoandroid.whatsappandroid.com.br.config.ConfiguracaoFirebase;
import com.cursoandroid.whatsappandroid.com.br.helper.Base64Custom;
import com.cursoandroid.whatsappandroid.com.br.helper.Preferencias;
import com.cursoandroid.whatsappandroid.com.br.modelo.Conversa;
import com.cursoandroid.whatsappandroid.com.br.modelo.Mensagem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConversaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editMensagem;
    private ImageButton btMensagem;
    private DatabaseReference firebase;
    private ListView listView;
    private ArrayList<Mensagem> mensagens;
    private ArrayAdapter<Mensagem> adapter;
    private ValueEventListener eventListenerMensagem;
    private Mensagem mensagem;

    //dados do destinatario
    private String nomeUsuarioDestinatario;
    private String idUsuarioDestinatario;


    //dados do remetente
    private String idUsuarioRemetente;
    private String nomeUsuarioRemetente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);
        toolbar = findViewById(R.id.tb_conversa);
        editMensagem = findViewById(R.id.edit_mensagem);
        btMensagem = findViewById(R.id.bt_enviar);
        listView = findViewById(R.id.lv_conversas);


        //dados usuario logado
        Preferencias preferencias = new Preferencias(ConversaActivity.this);
        idUsuarioRemetente = preferencias.getIdentificador();
        nomeUsuarioRemetente = preferencias.getNome();

        Bundle extra = getIntent().getExtras();

        if(extra!=null){
            nomeUsuarioDestinatario = extra.getString("nome");
            String emailDestinario = extra.getString("email");
            idUsuarioDestinatario = Base64Custom.codificarBase64(emailDestinario);
        }

        //Configurar toolbar
        toolbar.setTitle(nomeUsuarioDestinatario);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        //Montar listView e adapter
        mensagens = new ArrayList<>();
        adapter = new MensagemAdapter(ConversaActivity.this,mensagens);
        listView.setAdapter(adapter);

        //Recuperar mensagens do Firebase
        firebase = ConfiguracaoFirebase.getFirebase()
                   .child("mensagem")
                   .child(idUsuarioRemetente)
                   .child(idUsuarioDestinatario);

        //Criar listener para mensagens
        eventListenerMensagem = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mensagens.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()){
                    mensagem = dados.getValue(Mensagem.class);
                    mensagens.add(mensagem);
                }

                adapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        firebase.addValueEventListener(eventListenerMensagem);

        //Enviar mensagem
        btMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoMensagem = editMensagem.getText().toString();
                if(textoMensagem.isEmpty()){
                    Toast.makeText(ConversaActivity.this,"Digite uma mensagem pra enviar!",Toast.LENGTH_SHORT).show();
                }else{

                    Mensagem mensagem = new Mensagem();
                    mensagem.setIdUsuario(idUsuarioRemetente);
                    mensagem.setMensagem(textoMensagem);

                    //salvando mensagem para o remetente
                    Boolean retornoMensagemRemetente = salvarMensagem(idUsuarioRemetente,idUsuarioDestinatario,mensagem);
                    if(!retornoMensagemRemetente){
                        Toast.makeText(ConversaActivity.this,
                                "Problema ao salvar mensagem, tente novamente!",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        //salvando mensagem para o destinatario
                        Boolean retornoMensagemDestinatario =salvarMensagem(idUsuarioDestinatario,idUsuarioRemetente,mensagem);
                        if(!retornoMensagemDestinatario){
                            Toast.makeText(ConversaActivity.this,
                                    "Problema ao salvar mensagem para o destinatariom, tente novamente!",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                        //salvando conversa para o rementente
                        Conversa conversa = new Conversa();
                        conversa.setIdUsuario(idUsuarioDestinatario);
                        conversa.setNome(nomeUsuarioDestinatario);
                        conversa.setMensagem(textoMensagem);
                        Boolean retornoConversaRemente = salvarConversa(idUsuarioRemetente,idUsuarioDestinatario,conversa);
                        if(!retornoConversaRemente){
                            Toast.makeText(ConversaActivity.this,
                                    "Problema ao salvar conversa , tente novamente!",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            //salvando conversa para destinatario
                            conversa = new Conversa();
                            conversa.setIdUsuario(idUsuarioRemetente);
                            conversa.setNome(nomeUsuarioRemetente);
                            conversa.setMensagem(textoMensagem);
                            Boolean retornoConversaDestinatario = salvarConversa(idUsuarioDestinatario,idUsuarioRemetente,conversa);
                            if(!retornoConversaDestinatario){
                                Toast.makeText(ConversaActivity.this,
                                        "Problema ao salvar conversa para o destinatario , tente novamente!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }


                    editMensagem.setText("");

                }
            }
        });


    }

    private boolean salvarMensagem(String idRementente,String idDestinatario,Mensagem mensagem){
        try{

            firebase = ConfiguracaoFirebase.getFirebase().child("mensagem");

            firebase.child(idRementente)
                    .child(idDestinatario)
                    .push()
                    .setValue(mensagem);

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private boolean salvarConversa(String idRementente, String idDestinatario, Conversa conversa){
        try {
            firebase = ConfiguracaoFirebase.getFirebase().child("conversas");
            firebase.child(idRementente)
                    .child(idDestinatario)
                    .setValue(conversa);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
