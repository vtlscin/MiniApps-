package dialog.cursoandroid.com.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button botao;
    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botao = findViewById(R.id.botaoId);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Criar alert Dialog
                dialog = new AlertDialog.Builder(MainActivity.this);

                //configurar o titulo
                dialog.setTitle("Titulo da dialog");

                //configurar a mensagem
                dialog.setMessage("Mensagem da dialog");

                dialog.setCancelable(false);

                dialog.setIcon(android.R.drawable.ic_delete);

                //configurar botoes
                dialog.setNegativeButton("Não",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),"Pressionado o botao não",Toast.LENGTH_SHORT).show();
                            }
                        });
                //Botao positivo
                dialog.setPositiveButton("Sim",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this,"Pressionou o botao sim",Toast.LENGTH_SHORT).show();
                            }
                        });
                dialog.create();
                dialog.show();
            }

        });

    }
}
