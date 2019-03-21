package com.cursoandroid.whatsappandroid.com.br.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cursoandroid.whatsappandroid.com.br.R;
import com.cursoandroid.whatsappandroid.com.br.helper.Permissao;
import com.cursoandroid.whatsappandroid.com.br.helper.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Random;

public class LoginActivity extends Activity {

    private EditText telefone;
    private EditText pais;
    private EditText estado;
    private EditText nome;
    private Button cadastrar;
    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_PHONE_STATE
    };
    private final String NOME_ARQUIVO = "whatsapp-preferencias";
    private final int MODE = 0;
    private ValueEventListener valueEventListenerUsuario;
    private DatabaseReference firebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Permissao.validaPermissoes(1,this,permissoesNecessarias);

        //Recuperando Ids
        telefone = findViewById(R.id.edit_telefone);
        pais = findViewById(R.id.edit_Pais);
        estado = findViewById(R.id.edit_Estado);
        nome = findViewById(R.id.edit_Nome);
        cadastrar = findViewById(R.id.botaoCadastrar);

        //Colocando mascara para telefone
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("NNNNN-NNNN");
        MaskTextWatcher maskTelefone = new MaskTextWatcher(telefone,simpleMaskTelefone);
        telefone.addTextChangedListener(maskTelefone);

        //Colocando mascara para pais
        SimpleMaskFormatter simpleMaskPais = new SimpleMaskFormatter("+NN");
        MaskTextWatcher maskPais = new MaskTextWatcher(pais,simpleMaskPais);
        pais.addTextChangedListener(maskPais);

        //Colocando mascara para estado
        SimpleMaskFormatter simpleMaskEstado = new SimpleMaskFormatter("NN");
        MaskTextWatcher maskEstado = new MaskTextWatcher(estado,simpleMaskEstado);
        estado.addTextChangedListener(maskEstado);


        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nomeUsuario = nome.getText().toString();
                String telefoneCompleto = pais.getText().toString() +
                                          estado.getText().toString()+
                                          telefone.getText().toString();

                String telefoneSemFormatacao = telefoneCompleto.replace("+","");
                telefoneSemFormatacao = telefoneSemFormatacao.replace("-","");


                //Gerar token
                Random randomico = new Random();
                int numeroRandomico = randomico.nextInt(9999 - 1000) + 1000;
                String token = String.valueOf(numeroRandomico); // converter numeroRandomico para String
                String mensagemEnvio = "WhatsApp Código de Confirmação: " + token;

                //Log.i("TOKEN","T:" + token);

                //Salvar os dados para validação
                Preferencias preferencias = new Preferencias(LoginActivity.this);
                preferencias.salvarUsuarioPreferencias(nomeUsuario,telefoneSemFormatacao,token);

                //Envio de sms
                 boolean enviadoSMS = enviaSMS("+" + telefoneSemFormatacao,mensagemEnvio );

                 if( enviadoSMS){


                     try {
                         Intent intent = new Intent(LoginActivity.this,ValidadorActivity.class);
                         startActivity(intent);
                     }catch (Exception e){
                         e.printStackTrace();
                     }
                     finish();

                 }else{
                      Toast.makeText(LoginActivity.this,"Problema ao enviar SMS",Toast.LENGTH_SHORT).show();

                 }



                /*
                HashMap<String,String> usuario = preferencias.getDadosUsuarios();
                Log.i("TOKEN", "T: " + usuario.get("nome") + " FONE: " + usuario.get("telefone"));
                */
            }
        });


    }


    //Envio de SMS
    private boolean enviaSMS(String telefone, String mensagem){
        try{

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone,null,mensagem,null,null);
            Toast.makeText(LoginActivity.this,"Mensagem enviada",Toast.LENGTH_SHORT).show();
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){

        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        for(int resultado : grantResults){

            if(resultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }

        }

    }

    public void alertaValidacaoPermissao(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões negadas");
        builder.setMessage("Para utilizar esse app, é necessário aceitar as permissões");

        builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

}


