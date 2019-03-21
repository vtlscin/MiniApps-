package sharedpreferences.cursoandroid.com.sharedpreferences;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {


    private EditText editText;
    private Button botao;
    private TextView textoExibicao;

    private static final String ARQUIVO_PREFERENCIA = "ArquivoPreferencia";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextId);
        textoExibicao = findViewById(R.id.textoExibicaoId);
        botao = findViewById(R.id.botaoId);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Criando SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if(editText.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this,"Por favor, preencher o nome",Toast.LENGTH_SHORT).show();
                }else{
                    editor.putString("nome",editText.getText().toString());
                    editor.commit();
                    
                }

            }
        });

            //Recuperar os dados salvos
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);
        if(sharedPreferences.contains("nome")){
            String nomeUsuario = sharedPreferences.getString("nome","usuario nao defindo");
            textoExibicao.setText("Olá, " + nomeUsuario);
        }else{
            textoExibicao.setText("Olá, usuário não defindo");
        }

    }
}
