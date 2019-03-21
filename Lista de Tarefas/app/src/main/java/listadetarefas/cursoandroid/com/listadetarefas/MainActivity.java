package listadetarefas.cursoandroid.com.listadetarefas;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    private ListView lista;
    private EditText tarefa;
    private Button botao;
    private EditText horario;
    private String[] chaves = {"tarefas","horarios"};
    private SQLiteDatabase bancoDados;
    private int [] ids = {android.R.id.text1,android.R.id.text2};
    private ArrayList<String> horarios;
    private ArrayList<String> tarefas;
    private int i = 0;
    private ArrayList<Integer> idRecuperado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tarefa = findViewById(R.id.tarefaId);
        botao = findViewById(R.id.botaoId);
        horario = findViewById(R.id.horarioId);
        lista = findViewById(R.id.listaId);

        bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);

        try {
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS tarefas (id INTEGER PRIMARY KEY AUTOINCREMENT, tarefa VARCHAR, horario VARCHAR)");
            //bancoDados.execSQL("DROP TABLE tarefas");
            //bancoDados.execSQL("INSERT INTO tarefas (tarefa,horario) VALUES ('teste','07:00')");
            //bancoDados.execSQL("INSERT INTO tarefas (tarefa,horaio) VALUES ('Tomar Café','07:40')");
            //bancoDados.execSQL("INSERT INTO tarefas (tarefa,horaio) VALUES ('Estudar Android','07:00')");
            horarios = new ArrayList<String>();
            tarefas = new ArrayList<String>();
            idRecuperado = new ArrayList<Integer>();

            botao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String pegarTarefa = tarefa.getText().toString();
                    String pegarHorario = horario.getText().toString();
                    salvarTarefa(pegarTarefa, pegarHorario);
                }
            });

            //Listar tarefas
            Adapter();

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.i("ITEM: ", position + " / " + idRecuperado.get(position));
                    removerTarefa(idRecuperado.get(position));
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    private void salvarTarefa (String tarefaPassada,String horarioPassado ){

        try{
            if(tarefaPassada.equals("")){
                Toast.makeText(MainActivity.this,"Digite uma tarefa",Toast.LENGTH_SHORT).show();
            }else{
                bancoDados.execSQL("INSERT INTO tarefas(tarefa,horario) VALUES ('" + tarefaPassada + "','" + horarioPassado + "')");
                Toast.makeText(MainActivity.this,"Tarefa salva com sucesso!",Toast.LENGTH_SHORT).show();
                Adapter();
                tarefa.setText("");
                horario.setText("");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void recuperarTarefas(){

        try{

            //Criando cursor
            Cursor cursor = bancoDados.rawQuery("SELECT * FROM tarefas ORDER BY id DESC ",null);

            int indiceId = cursor.getColumnIndex("id");
            int indiceTarefa = cursor.getColumnIndex("tarefa");
            int indiceHorario = cursor.getColumnIndex("horario");

            cursor.moveToFirst();

            while (cursor != null){

                Log.i("RESULTADO - id: ",cursor.getString(indiceId));
                Log.i("RESULTADO - tarefa: ",cursor.getString(indiceTarefa));
                Log.i("RESULTADO - horario: ",cursor.getString(indiceHorario));
                tarefas.add(cursor.getString(indiceTarefa));
                horarios.add(cursor.getString(indiceHorario));
                idRecuperado.add(Integer.parseInt(cursor.getString(indiceId)));
                Log.i("Id recuperado",+ Integer.parseInt(cursor.getString(indiceId)) + "");
                cursor.moveToNext();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void Adapter(){

        recuperarTarefas();
        List<Map<String,String>> listaDupla = new ArrayList<Map<String,String>>();
        while (i < tarefas.size()){
            Map<String,String> m = new HashMap<String, String>();
            m.put("tarefas", tarefas.get(i));
            m.put("horarios", horarios.get(i));
            listaDupla.add(m);
            i++;
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,listaDupla,android.R.layout.simple_list_item_2,chaves,ids);
        lista.setAdapter(simpleAdapter);

    }

    private void removerTarefa(Integer id){
        try{

            bancoDados.execSQL("DELETE FROM tarefas WHERE id="+id);
            Adapter();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
