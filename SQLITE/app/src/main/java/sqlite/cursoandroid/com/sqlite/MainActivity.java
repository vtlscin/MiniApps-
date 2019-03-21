package sqlite.cursoandroid.com.sqlite;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);

            //table
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS pessoas( id INTEGER PRIMARY KEY AUTOINCREMENT, nome VARCHAR, idade INT(3))");
            //bancoDados.execSQL("DROP TABLE pessoas");

            //inserir dados
            bancoDados.execSQL("INSERT INTO pessoas (nome,idade) VALUES('Mariana', 10)");
            //bancoDados.execSQL("INSERT INTO pessoas (nome,idade) VALUES('Thiago', 50)");
            //bancoDados.execSQL("UPDATE pessoas SET idade = 28 WHERE id = 1");
            //bancoDados.execSQL("DELETE FROM pessoas WHERE id = 1");


            //Recuperando dados
            Cursor cursor = bancoDados.rawQuery("SELECT * FROM pessoas ", null);

            //Recuperando indice das colunas
            int indiceColunaNome = cursor.getColumnIndex("nome");
            int indiceColunaIdade = cursor.getColumnIndex("idade");
            int indiceColunaId = cursor.getColumnIndex("id");

            cursor.moveToFirst();

            while (cursor != null) {

                Log.i("RESULTADO - nome: ", cursor.getString(indiceColunaNome));
                Log.i("RESULTADO - idade: ", cursor.getString(indiceColunaIdade));
                Log.i("RESULTADO - id: ", cursor.getString(indiceColunaId));
                cursor.moveToNext();

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
