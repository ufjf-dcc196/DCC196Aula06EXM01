package br.ufjf.dcc196.dcc196aula06exm01;

import android.provider.BaseColumns;

public final class BibliotecaContract {
    public final class Livro implements BaseColumns {
        public final static String TABLE_NAME = "Livro";
        public static final String COLUMN_NAME_ID = "_ID";
        public final static String COLUMN_NAME_TITULO = "titulo";
        public static final String COLUMN_NAME_AUTOR = "autor";
        public static final String COLUMN_NAME_ANO = "ano";
        public final static String CREATE_LIVRO  = "CREATE TABLE "+Livro.TABLE_NAME+" ("
                + Livro._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Livro.COLUMN_NAME_TITULO+ " TEXT, "
                + Livro.COLUMN_NAME_AUTOR+ " TEXT,"
                + Livro.COLUMN_NAME_ANO+ " INTEGER"
                +")";
        public final static String DROP_LIVRO = "DROP TABLE IF EXISTS "+Livro.TABLE_NAME;
    }
}
