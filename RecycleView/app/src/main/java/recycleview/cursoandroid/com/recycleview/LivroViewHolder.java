package recycleview.cursoandroid.com.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class LivroViewHolder extends RecyclerView.ViewHolder {

    final TextView nome;
    final TextView descricao;
    final TextView preco;
    final TextView autor;

    public LivroViewHolder(View view){
        super(view);
        nome = view.findViewById(R.id.item_livro_nome);
        descricao = view.findViewById(R.id.item_livro_desc);
        autor = view.findViewById(R.id.item_livro_autor);
        preco = view.findViewById(R.id.item_livro_preco);

    }

}
