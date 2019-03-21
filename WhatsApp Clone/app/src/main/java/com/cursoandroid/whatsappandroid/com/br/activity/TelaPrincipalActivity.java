package com.cursoandroid.whatsappandroid.com.br.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cursoandroid.whatsappandroid.com.br.Adapter.TabAdpter;
import com.cursoandroid.whatsappandroid.com.br.R;
import com.cursoandroid.whatsappandroid.com.br.config.ConfiguracaoFirebase;
import com.cursoandroid.whatsappandroid.com.br.helper.Base64Custom;
import com.cursoandroid.whatsappandroid.com.br.helper.Preferencias;
import com.cursoandroid.whatsappandroid.com.br.helper.SlidingTabLayout;
import com.cursoandroid.whatsappandroid.com.br.modelo.Contato;
import com.cursoandroid.whatsappandroid.com.br.modelo.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class TelaPrincipalActivity extends AppCompatActivity {

    private Button botaoSair;
    private FirebaseAuth autenticacao;
    private Toolbar toolbar;

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private String identificadorContato;
    private DatabaseReference firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("WhatsApp");
        setSupportActionBar(toolbar);

        slidingTabLayout = findViewById(R.id.stl_tabs);
        viewPager = findViewById(R.id.vp_pagina);

        //Configurar sliding tabs
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this,R.color.colorAccent));

        //Configurar adapter
        TabAdpter tabAdpter = new TabAdpter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdpter);

        slidingTabLayout.setViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_sair:
                deslogarUsuario();
                return true;
            case R.id.item_configuracoes:
                Toast.makeText(TelaPrincipalActivity.this,"Você clicou em Configurações!",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_adicionar:
                abrirCadastroContato();
                Toast.makeText(TelaPrincipalActivity.this,"Você clicou em Adicionar Pessoa!",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_pesquisa:
                Toast.makeText(TelaPrincipalActivity.this,"Você clicou em Pesquisar!",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void abrirCadastroContato(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(TelaPrincipalActivity.this);

        alertDialog.setTitle("Novo Contato");
        alertDialog.setMessage("E-mail do usuário");
        alertDialog.setCancelable(false);

        final EditText editText = new EditText(TelaPrincipalActivity.this);
        alertDialog.setView(editText);

        alertDialog.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String emailContato = editText.getText().toString();

                //Validar se email foi digitado
                if(emailContato.isEmpty()){
                    Toast.makeText(TelaPrincipalActivity.this,"Preencha o e-mail!",Toast.LENGTH_SHORT).show();
                }else {

                    //Verificar se o usuario ja esta cadastrado no app
                    identificadorContato = Base64Custom.codificarBase64(emailContato);

                    //Recuperar instancia Firebase
                    firebase = ConfiguracaoFirebase.getFirebase();
                    firebase = firebase.child("usuarios").child(identificadorContato);

                    firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.getValue() != null){

                                //Recuperar dados do contato a ser adicionado
                                Usuario usuarioContato = dataSnapshot.getValue(Usuario.class);

                                //Recuperar identificador usuario logado (base64)
                                Preferencias preferencias = new Preferencias(TelaPrincipalActivity.this);
                                String identificadorUsuarioLogado = preferencias.getIdentificador();

                                firebase = ConfiguracaoFirebase.getFirebase();
                                firebase = firebase
                                        .child("contatos")
                                        .child(identificadorUsuarioLogado)
                                        .child(identificadorContato);

                                Contato contato = new Contato();
                                contato.setIdentificadorUsuario(identificadorContato);
                                contato.setEmail(usuarioContato.getEmail());
                                contato.setNome(usuarioContato.getNome());

                                firebase.setValue(contato);

                            }else {
                                Toast.makeText(TelaPrincipalActivity.this,"Usuario não possui cadastro.",Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }

            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.create();
        alertDialog.show();

    }

    public void deslogarUsuario(){
        autenticacao.signOut();
        Intent intent = new Intent(TelaPrincipalActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
