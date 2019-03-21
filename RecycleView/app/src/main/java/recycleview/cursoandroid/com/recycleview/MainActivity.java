package recycleview.cursoandroid.com.recycleview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private RecyclerView recicleView;
    List<Livro> livros = new ArrayList<Livro>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Livro livrinho = new Livro("O Jardim das Aflições","Carvalho, Olavo de", "Ótimo Livro", 40.00);
        livros.add(livrinho);
        livrinho = new Livro("1984","Orwell, George", "Ótimo Livro", 28.90);
        livros.add(livrinho);
        livrinho = new Livro("Admirável Mundo Novo","Orwell, George", "Ótimo Livro", 19.90);
        livros.add(livrinho);
        livrinho = new Livro("O Sol É Para Todos","Lee, Harper", "Ótimo Livro", 19.90);
        livros.add(livrinho);
        livrinho = new Livro("Como Ler Livros","Huxley, Aldous", "Ótimo Livro", 50.00);
        livros.add(livrinho);
        livrinho = new Livro("A Revolução Dos Bichos","Orwell, George", "Ótimo Livro", 19.90);
        livros.add(livrinho);

        recicleView =  findViewById(R.id.recyclerViewId);

        recicleView.setAdapter(new LivroAdapter(livros,this));

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false);

        recicleView.setLayoutManager(layout);

    }
}
