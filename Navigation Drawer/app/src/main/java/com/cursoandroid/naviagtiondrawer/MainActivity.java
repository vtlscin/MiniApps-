package com.cursoandroid.naviagtiondrawer;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cursoandroid.naviagtiondrawer.Teste.Main2Activity;

public class MainActivity extends AppCompatActivity {

    private TextView logo;
    private Button login;
    private Button botao2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo = findViewById(R.id.logoId);
        login = findViewById(R.id.loginId);
        botao2 = findViewById(R.id.botao2);

        Typeface font = Typeface.createFromAsset(getAssets(),"WatercolorDemo.ttf");
        logo.setTypeface(font);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NavigationDrawer.class);
                startActivity(intent);
            }
        });

        botao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });


    }
}
