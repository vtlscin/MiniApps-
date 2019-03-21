package com.cursoandroid.whatsappandroid.com.br.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cursoandroid.whatsappandroid.com.br.R;
import com.cursoandroid.whatsappandroid.com.br.config.ConfiguracaoFirebase;
import com.cursoandroid.whatsappandroid.com.br.helper.Base64Custom;
import com.cursoandroid.whatsappandroid.com.br.helper.Preferencias;
import com.cursoandroid.whatsappandroid.com.br.modelo.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends Activity {

    private EditText textoEmail;
    private EditText textoSenha;
    private Button botaoLogar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;
    private ValueEventListener valueEventListenerUsuario;
    private DatabaseReference firebase;
    private String identificadorUsuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verificarUsuarioLogado();

        textoEmail = findViewById(R.id.textoEmailId);
        textoSenha = findViewById(R.id.textoSenhaId);
        botaoLogar = findViewById(R.id.botaoLogarId);

        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textoEmail.getText().toString().equals("") || textoSenha.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this,"Email ou senha estao vazios", Toast.LENGTH_SHORT).show();
                }else{
                    usuario = new Usuario();
                    usuario.setEmail(textoEmail.getText().toString());
                    usuario.setSenha(textoSenha.getText().toString());
                    validarLogin();
                }
            }
        });

    }

    private void verificarUsuarioLogado(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if(autenticacao.getCurrentUser() != null ){
            abrirTelaPrincipal();
        }
    }

    private void validarLogin(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    identificadorUsuarioLogado = Base64Custom.codificarBase64(usuario.getEmail());

                    firebase = ConfiguracaoFirebase.getFirebase()
                            .child("usuarios")
                            .child(identificadorUsuarioLogado);

                    valueEventListenerUsuario = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Usuario usuarioRecuperado = dataSnapshot.getValue(Usuario.class);
                            Preferencias preferencias = new Preferencias(MainActivity.this);
                            preferencias.salvarDados(identificadorUsuarioLogado,usuarioRecuperado.getNome());

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    };
                    firebase.addListenerForSingleValueEvent(valueEventListenerUsuario);


                    abrirTelaPrincipal();
                    Toast.makeText(MainActivity.this,"Sucesso ao fazer login!",Toast.LENGTH_SHORT).show();
                }else{
                    String erro = "";
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        erro = "Email errado ou nao existe";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erro = "A senha esta incorreta" ;
                    } catch (Exception e) {
                        erro = "Ao fazer login";
                        e.printStackTrace();
                    }

                    Toast.makeText(MainActivity.this,"Erro: " + erro,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void abrirTelaPrincipal(){
        Intent intent = new Intent(MainActivity.this,TelaPrincipalActivity.class);
        startActivity(intent);
        finish();
    }

    public void abrirCadastroUsuario(View view){

        Intent intent = new Intent(MainActivity.this,cadastroUsuarioActivity.class);
        startActivity(intent);

    }


}
