package listview.cursoandroid.com.listview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    private ListView listaItens;
    private String[] itens = {
            "Angra dos Reis","Caldas novas",
            "Campos do jordão","Costa do Sauipe",
            "Ilhéus","Porto Seguro","Rio de Janeiro",
            "Tiradentes","Praga","Santiago","Zurigus",
            "Caribe","Buenos Aires","Budapest","Cancun",
            "Aruaba"
    };
    private String[] teste1 = {"Vinicius","Thiago","Leite"};
    private String[] teste2 = {"123","456","789"};
    private String[] chaves = {"Nome","Nome1"};
    private int[] ids = {android.R.id.text1,android.R.id.text2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Map<String, String>> listaDupla = new ArrayList<Map<String, String>>();
        for(int i=0; i< teste1.length; i++){
            Map<String,String> m = new HashMap<String, String>();
            m.put("Nome",teste1[i]);
            m.put("Nome1",teste2[i]);
            listaDupla.add(m);
        }

        SimpleAdapter sa = new SimpleAdapter(this,listaDupla,android.R.layout.simple_list_item_2,chaves,ids);
        listaItens = findViewById(R.id.listViewId);
        listaItens.setAdapter(sa);
       /*
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
            getApplicationContext(),//contexto
            android.R.layout.simple_list_item_1,//layout para exibir itens
            android.R.id.text1,//id do textView do layout que foi escolhido
            itens
        );

        listaItens.setAdapter(adaptador);

        listaItens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int codigoPosicao = position;
                String valorClicado = listaItens.getItemAtPosition(codigoPosicao).toString();
                Toast.makeText(MainActivity.this, valorClicado, Toast.LENGTH_SHORT).show();
            }
        });
        */

    }
}
