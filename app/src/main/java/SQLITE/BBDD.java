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

    // Este método es llamado cuando se crea la base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);  // Ejecuta el SQL para crear la tabla
    }

    // Este método es llamado cuando se actualiza la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);
    }

    public void InsertarDatos(int peso, float altura, int edad, String sexo, String objetivo, String actividad , String frecuencia) {
        // Open the database in writable mode
        SQLiteDatabase db = this.getWritableDatabase();


        // Create a ContentValues object to insert the data
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

        // Definir la columna que quieres verificar
        String[] columnas = {"id"};  // Solo la columna id, ya que solo estamos verificando la existencia

        // Definir la condición de búsqueda
        String selection = "id = ?";
        String[] selectionArgs = { String.valueOf(1) };  // El ID que quieres verificar

        // Hacer la consulta a la base de datos
        Cursor cursor = db.query(
                "usuarios",    // Nombre de la tabla
                columnas,      // Columnas que queremos recuperar
                selection,     // Condición de búsqueda
                selectionArgs, // Argumentos de la condición
                null,          // No agrupamos los resultados
                null,          // No ordenamos
                null           // No limitamos los resultados
        );

        boolean existe = cursor.getCount() > 0;  // Si el cursor tiene algún resultado, significa que la fila existe

        cursor.close();  // Cerrar el cursor
        db.close();      // Cerrar la base de datos

        return existe;   // Retorna true si existe, false si no existe
    }
}






