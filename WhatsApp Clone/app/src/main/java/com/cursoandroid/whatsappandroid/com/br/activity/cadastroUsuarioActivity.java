package com.cursoandroid.whatsappandroid.com.br.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class cadastroUsuarioActivity extends Activity {

    private EditText cadastroNome;
    private EditText cadastroEmail;
    private EditText cadastroSenha;
    private Button botaoCadastrar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrousuario);

        cadastroNome = findViewById(R.id.editCadastroNomeId);
        cadastroEmail = findViewById(R.id.editCadastroEmailId);
        cadastroSenha = findViewById(R.id.editCadastroSenhaId);
        botaoCadastrar = findViewById(R.id.botaoCadastrarId);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cadastroNome.getText().toString().equals("") || cadastroSenha.getText().toString().equals("") || cadastroEmail.getText().toString().equals("")){
                    Toast.makeText(cadastroUsuarioActivity.this,"Por favor preencha todos os campos",Toast.LENGTH_SHORT).show();
                }else{
                    usuario = new Usuario();
                    usuario.setNome(cadastroNome.getText().toString());
                    usuario.setEmail(cadastroEmail.getText().toString());
                    usuario.setSenha(cadastroSenha.getText().toString());
                    cadastrarUsuario();
                }

            }
        });

    }

    private void cadastrarUsuario(){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(cadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(cadastroUsuarioActivity.this,"Sucesso ao cadastrar usuário",Toast.LENGTH_SHORT).show();

                    FirebaseUser usuarioFirebase = task.getResult().getUser();

                    String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                    usuario.setId(identificadorUsuario);
                    usuario.salvar();

                    Preferencias preferencias = new Preferencias(cadastroUsuarioActivity.this);
                    preferencias.salvarDados(identificadorUsuario,usuario.getNome());

                    abrirLoginUsuario();

                }else{

                    String erroExcecao = "";

                    try{
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        erroExcecao = "Digite uma senha mais forte, contendo mais caracteres e com letras e números!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "O e-mail digitado é invalido, digite um novo e-mail!";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExcecao = "Esse e-mail já está em uso no App!";
                    } catch (Exception e) {
                        erroExcecao = "Ao cadastrar usuário";
                        e.printStackTrace();
                    }

                    Toast.makeText(cadastroUsuarioActivity.this,"Erro: "+ erroExcecao,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void abrirLoginUsuario(){
        Intent intent = new Intent(cadastroUsuarioActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}
