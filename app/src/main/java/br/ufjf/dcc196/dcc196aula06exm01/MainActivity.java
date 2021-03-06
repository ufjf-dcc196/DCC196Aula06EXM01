package br.ufjf.dcc196.dcc196aula06exm01;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button btnInserir;
    private Button btnListar;
    private RecyclerView rclLivros;
    private BibliotecaDbHelper dbHelper;
    private LivroAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new BibliotecaDbHelper(getApplicationContext());

        rclLivros = (RecyclerView) findViewById(R.id.rclLivros);
        rclLivros.setLayoutManager(new LinearLayoutManager(this));


        adapter = new LivroAdapter(getCursorLivrosPos1950());
        adapter.setOnLivroClickListener(new LivroAdapter.OnLivroClickListener() {
            @Override
            public void onLivroClick(View livroView, int position) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String select = BibliotecaContract.Livro.COLUMN_NAME_TITULO+" = ?";
                ContentValues values = new ContentValues();
                TextView txtTitulo = (TextView) livroView.findViewById(R.id.txt_livro_titulo);
                String[] selectParam = {txtTitulo.getText().toString()};
                values.put(BibliotecaContract.Livro.COLUMN_NAME_ANO, 2018);

                int numUp = db.update(BibliotecaContract.Livro.TABLE_NAME, values,select,selectParam);
                Log.i("DBINFO", "UPDATE "+numUp+" ano: " + txtTitulo.getText().toString());
                adapter.setCursor(getCursorLivrosPos1950());
                adapter.notifyDataSetChanged();

            }
        });
        adapter.setOnLivroLongClickListener(new LivroAdapter.OnLivroLongClickListener() {
            @Override
            public void onLivroLongClick(View livroView, int position) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String select = BibliotecaContract.Livro.COLUMN_NAME_TITULO+" = ?";
                TextView txtTitulo = (TextView) livroView.findViewById(R.id.txt_livro_titulo);

                String[] selectArgs = {txtTitulo.getText().toString()};
                db.delete(BibliotecaContract.Livro.TABLE_NAME,select,selectArgs);
                Log.i("DBINFO", "DEL titulo: " + txtTitulo.getText().toString());
                adapter.setCursor(getCursorLivrosPos1950());
                adapter.notifyItemRemoved(position);

            }
        });
        rclLivros.setAdapter(adapter);



        final Random rnd = new Random();
        btnInserir = (Button) findViewById(R.id.btnInserir);
        btnInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues valores = new ContentValues();
                valores.put(BibliotecaContract.Livro.COLUMN_NAME_TITULO, "Livro "+rnd.nextInt(1000));
                valores.put(BibliotecaContract.Livro.COLUMN_NAME_AUTOR, "Autor "+rnd.nextInt(1000));
                valores.put(BibliotecaContract.Livro.COLUMN_NAME_ANO, 1900+rnd.nextInt(118));
                long id = db.insert(BibliotecaContract.Livro.TABLE_NAME,null, valores);
                Log.i("DBINFO", "registro criado com id: "+id);
                adapter.setCursor(getCursorLivrosPos1950());
                adapter.notifyDataSetChanged();
            }
        });
        btnListar = (Button) findViewById(R.id.btnListar);
        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = getCursorLivrosPos1950();
                cursor.moveToPosition(-1);
                while(cursor.moveToNext()) {
                    int idxTitulo = cursor.getColumnIndexOrThrow(BibliotecaContract.Livro.COLUMN_NAME_TITULO);
                    int idxAutor = cursor.getColumnIndexOrThrow(BibliotecaContract.Livro.COLUMN_NAME_AUTOR);
                    int idxAno = cursor.getColumnIndexOrThrow(BibliotecaContract.Livro.COLUMN_NAME_ANO);
                    String titulo = cursor.getString(idxTitulo);
                    String autor = cursor.getString(idxAutor);
                    Integer ano = cursor.getInt(idxAno);
                    Log.i("DBINFO", "titulo: " + titulo+" autor: "+autor+" ano:"+ ano);
                }
            }
        });
    }

    private Cursor getCursorLivrosPos1950() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] visao = {
                BibliotecaContract.Livro.COLUMN_NAME_TITULO,
                BibliotecaContract.Livro.COLUMN_NAME_AUTOR,
                BibliotecaContract.Livro.COLUMN_NAME_ANO
        };
        String restricoes = BibliotecaContract.Livro.COLUMN_NAME_ANO + " > ?";
        String[] params = {"1950"};
        String sort = BibliotecaContract.Livro.COLUMN_NAME_ANO+ " DESC";
        return db.query(BibliotecaContract.Livro.TABLE_NAME, visao,restricoes,params,null,null, sort);
    }
}
