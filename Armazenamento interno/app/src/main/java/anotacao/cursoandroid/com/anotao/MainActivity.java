package anotacao.cursoandroid.com.anotao;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends Activity {

    private EditText texto;
    private ImageView botao;
    private static final String NOME_ARQUIVO = "anotacao.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texto = findViewById(R.id.textoId);
        botao = findViewById(R.id.botaoSalvarId);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoDigitado = texto.getText().toString();
                gravarNoArquivo(textoDigitado);
                Toast.makeText(MainActivity.this,"Anotação salva com sucesso",Toast.LENGTH_SHORT).show();
            }
        });

        //Recuperar o que foi gravado
        if(lerArquivo()!= null){
            texto.setText(lerArquivo());
        }

    }

    private void gravarNoArquivo(String texto){

        try{

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(NOME_ARQUIVO,Context.MODE_PRIVATE));
            outputStreamWriter.write(texto);
            outputStreamWriter.close();

        }catch (IOException e){
            Log.v("MainActivity",e.toString());
        }

    }

    private String lerArquivo(){

        String resultado = "";

        try{
            //Abrir Arquivo
            InputStream arquivo = openFileInput(NOME_ARQUIVO);
            if(arquivo!=null){
                //ler o arquivo
                InputStreamReader inputStreamReader = new InputStreamReader(arquivo);

                //Gerar Buffer do arquivo lido
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                //Recuperar textos do arquivo
                String linhaArquivo = "";
                while ((linhaArquivo = bufferedReader.readLine()) != null){
                        resultado += linhaArquivo;
                }

                arquivo.close();
            }


        }catch (IOException e){
            Log.v("MainActivity",e.toString());
        }

        return resultado;

    }


}
