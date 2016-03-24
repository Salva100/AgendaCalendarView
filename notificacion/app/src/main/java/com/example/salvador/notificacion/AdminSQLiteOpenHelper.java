package com.example.salvador.notificacion;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class AdminSQLiteOpenHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "agenda.db";
    public static final String TABLA_PERSONAS = "calendario";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOMBRE = "titulo";
    public static final String COLUMN_CONFERENCISTA = "conferencista";
    public static final String COLUMN_DESCRIP = "descripcion";
    public static final String COLUMN_LUGAR = "lugar";
    public static final String COLUMN_FECHA = "fecha";
    public static final String COLUMN_HORA = "hora";



    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLA_PERSONAS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRE + " TEXT, " +
                COLUMN_CONFERENCISTA + " TEXT, " +
                COLUMN_DESCRIP + " TEXT, " +
                COLUMN_LUGAR + " TEXT, " +
                COLUMN_FECHA + " TEXT, " +
                COLUMN_HORA + " TEXT " +
                ");";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_PERSONAS);
        onCreate(db);
    }

    //Añade un nuevo Row a la Base de Datos

    public void addEvento(String titulo, String conferencista, String descri, String lugar, String fecha, String hr) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, titulo);
        values.put(COLUMN_CONFERENCISTA, conferencista);
        values.put(COLUMN_DESCRIP, descri);
        values.put(COLUMN_LUGAR, lugar);
        values.put(COLUMN_FECHA, fecha);
        values.put(COLUMN_HORA, hr);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLA_PERSONAS, null, values);
        db.close();

    }



    // Borrar una persona de la Base de Datos

    public void borrarPersona(int persona_id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLA_PERSONAS + " WHERE " + COLUMN_ID + " = " + persona_id + ";");
        db.close();
    }

    //listar por id

    public Cursor personabyid(int id){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLA_PERSONAS + " WHERE " + COLUMN_ID + " = " + id + ";";
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
        }

        return c;
    }

    //listar a todas las personas
    public Cursor listarpersonas(String dia,Integer mes){
        String mesNom="";
        switch (mes){
            case 0:
                mesNom="Enero";
                break;
            case 1:
                mesNom="Febrero";
                break;
            case 2:
                mesNom="Marzo";
                break;
            case 3:
                mesNom="Abril";
                break;
            case 4:
                mesNom="Mayo";
                break;
            case 5:
                mesNom="Junio";
                break;
            case 6:
                mesNom="Julio";
                break;
            case 7:
                mesNom="Agosto";
                break;
            case 8:
                mesNom="Septiembre";
                break;
            case 9:
                mesNom="Octubre";
                break;
            case 10:
                mesNom="Noviembre";
                break;
            case 11:
                mesNom="Diciembre";
                break;


        }
        SQLiteDatabase db = getReadableDatabase();
        String query = ("SELECT * FROM " + TABLA_PERSONAS + " WHERE "+COLUMN_FECHA+" LIKE '"+dia+"____"+mesNom+"%' ORDER BY "+ COLUMN_HORA +" ASC;");
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
        }

        return c;
    }

}




