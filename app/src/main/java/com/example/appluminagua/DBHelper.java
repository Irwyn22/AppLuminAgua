package com.example.appluminagua;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Usuarios.db";
    private static final int DATABASE_VERSION = 3; // subimos versión para actualizar tabla

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE usuarios(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nombre TEXT, " +
                        "usuario TEXT UNIQUE, " +
                        "password TEXT)"
        );

        db.execSQL(
                "CREATE TABLE reportes(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nombre TEXT, " +
                        "calle1 TEXT, " +
                        "calle2 TEXT, " +
                        "colonia TEXT, " +
                        "cp TEXT, " +
                        "descripcion TEXT, " +
                        "imagenUri TEXT, " +
                        "tipo TEXT)"     // ← AGREGADO
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS reportes");

        onCreate(db);
    }

    // Insertar Usuarios
    public boolean insertarUsuario(String nombre, String usuario, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("usuario", usuario);
        values.put("password", password);

        long result = db.insert("usuarios", null, values);
        return result != -1;
    }

    // Verificar Login
    public boolean verificarUsuario(String usuario, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM usuarios WHERE usuario=? AND password=?",
                new String[]{usuario, password}
        );
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    // Insertar Reportes (AGREGADO TIPO)
    public boolean insertarReporte(
            String nombre,
            String calle1,
            String calle2,
            String colonia,
            String cp,
            String descripcion,
            String imagenUri,
            String tipo    // ← AGREGADO
    ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nombre", nombre);
        values.put("calle1", calle1);
        values.put("calle2", calle2);
        values.put("colonia", colonia);
        values.put("cp", cp);
        values.put("descripcion", descripcion);
        values.put("imagenUri", imagenUri);
        values.put("tipo", tipo);

        long result = db.insert("reportes", null, values);
        return result != -1;
    }
}

