package SQLITE;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class BBDD extends SQLiteOpenHelper {

    String respu;

    private static final String DATABASE_NAME = "datosUsuario.db";

    private static final int DATABASE_VERSION = 2;

    private static final String CREATE_TABLE = "CREATE TABLE usuarios ("+
            "id INTEGER PRIMARY KEY AUTOINCREMENT ," +
            "PESO INTEGER ," +
            "ALTURA FLOAT ," +
            "EDAD INTEGER ," +
            "SEXO VARCHAR ," +
            "ACTIVIDAD VARCHAR," +
            "OBJETIVO VARCHAR ," +
            "FRECUENCIA VARCHAR )";

    public BBDD (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);
    }

    public void InsertarDatos(int peso, float altura, int edad, String sexo, String objetivo, String actividad , String frecuencia) {
        SQLiteDatabase db = this.getWritableDatabase();



        ContentValues values = new ContentValues();
        values.put("PESO", peso);
        values.put("ALTURA", altura);
        values.put("EDAD", edad);
        values.put("SEXO", sexo);
        values.put("ACTIVIDAD", sexo);
        values.put("OBJETIVO", objetivo);
        values.put("FRECUENCIA", frecuencia);

        long rowId = db.insert("usuarios", null, values);

        if (rowId == -1) {


            respu = "error insertando los datos";
        } else {

            respu = "Se insertaron correctamente";
        }

        db.close();
    }

    public void limpiar () {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("usuarios" , null , null );
        db.close();

    }

    public boolean existeFila() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columnas = {"id"};

        String selection = "id = ?";
        String[] selectionArgs = { String.valueOf(1) };

        // Hacer la consulta a la base de datos
        Cursor cursor = db.query(
                "usuarios",
                columnas,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean existe = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return existe;
    }
}






