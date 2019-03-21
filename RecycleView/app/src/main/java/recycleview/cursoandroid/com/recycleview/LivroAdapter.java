package recycleview.cursoandroid.com.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

public class LivroAdapter extends RecyclerView.Adapter {
    private List<Livro> livros;
    private Context context;

    public LivroAdapter(List<Livro> livros,Context context){
        this.livros = livros;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_livro,parent,false);
        LivroViewHolder holder = new LivroViewHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder,int position){
        final LivroViewHolder holder = (LivroViewHolder) viewHolder;
        final Livro livro = livros.get(position);
        holder.nome.setText(livro.getNomeAutor());
        holder.autor.setText(livro.getNomeAutor());
        holder.descricao.setText(livro.getDescricao());
        holder.preco.setText(Double.toString(livro.getPreco()));
        holder.nome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,livro.getNomeLivro(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount(){
        return livros.size();
    }
}
