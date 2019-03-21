package com.cursoandroid.fragments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button botao1;
    private Button botao2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botao1 = findViewById(R.id.botaoFrag1);
        botao2 = findViewById(R.id.botaoFrag2);

        if(savedInstanceState == null){
            //adicionar o fragmento inicial
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frameContainerId, new NovoFragment())
                    .commit();

        }

        botao1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameContainerId,new NovoFragment()).commit();
            }
        });

        botao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameContainerId, new SegundoFragment()).commit();
            }
        });

    }
}
