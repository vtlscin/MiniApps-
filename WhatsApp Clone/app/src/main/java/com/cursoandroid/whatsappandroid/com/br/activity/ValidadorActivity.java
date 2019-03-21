package com.cursoandroid.whatsappandroid.com.br.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cursoandroid.whatsappandroid.com.br.R;
import com.cursoandroid.whatsappandroid.com.br.helper.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;

public class ValidadorActivity extends Activity {

    private Button validar;
    private EditText textoValidacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);

        validar = findViewById(R.id.botaoValidar);
        textoValidacao = findViewById(R.id.edit_cod_validacao);

        SimpleMaskFormatter simpleMaskCodigoValidacao = new SimpleMaskFormatter("NNNN");
        MaskTextWatcher mascaraCodigoValidacao = new MaskTextWatcher(textoValidacao,simpleMaskCodigoValidacao);

        textoValidacao.addTextChangedListener(mascaraCodigoValidacao);

        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferencias preferencias = new Preferencias(ValidadorActivity.this);
                HashMap<String,String> usuario = preferencias.getDadosUsuarios();

                String tokenDigitado = textoValidacao.getText().toString();
                String tokenGerado = usuario.get("token");

                if(tokenDigitado.equals(tokenGerado)){
                    Toast.makeText(ValidadorActivity.this,"Token Validado",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ValidadorActivity.this,"Token Invalido",Toast.LENGTH_SHORT).show();
                }



            }
        });


    }
}
