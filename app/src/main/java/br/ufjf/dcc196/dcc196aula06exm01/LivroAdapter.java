package br.ufjf.dcc196.dcc196aula06exm01;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

class LivroAdapter extends RecyclerView.Adapter<LivroAdapter.ViewHolder>{
    private Cursor cursor;
    public LivroAdapter(Cursor c){
        cursor = c;
    }

    public void setCursor(Cursor c){
        cursor = c;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View livroView = inflater.inflate(R.layout.livro_layout, parent, false);
        ViewHolder holder = new ViewHolder(livroView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int idxTitulo = cursor.getColumnIndexOrThrow(BibliotecaContract.Livro.COLUMN_NAME_TITULO);
        int idxAutor = cursor.getColumnIndexOrThrow(BibliotecaContract.Livro.COLUMN_NAME_AUTOR);
        int idxAno = cursor.getColumnIndexOrThrow(BibliotecaContract.Livro.COLUMN_NAME_ANO);
        cursor.moveToPosition(position);
        holder.txtTitulo.setText(cursor.getString(idxTitulo));
        holder.txtAutor.setText(cursor.getString(idxAutor));
        holder.txtAno.setText(String.valueOf(cursor.getInt(idxAno)));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtTitulo;
        public TextView txtAutor;
        public TextView txtAno;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txt_livro_titulo);
            txtAutor = itemView.findViewById(R.id.txt_livro_autor);
            txtAno = itemView.findViewById(R.id.txt_livro_ano);
        }
    }
}
