package dadosactivity.cursoandroid.com.dadosacitvity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button botao;
    private Button botao1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botao = findViewById(R.id.botaoId);
        botao1 = findViewById(R.id.botao1Id);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SegundaActivity.class);
                intent.putExtra("nome","Vinicius Thiago");

                startActivity(intent);
            }
        });

        botao1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SegundaActivity.class));
            }
        });

    }
}
