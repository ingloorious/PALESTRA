package RUTINAPERSONALIZADA;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class Rutina implements Parcelable {
    private String id; // El ID del documento de Firestore
    private String nombre; // Nombre de la rutina
    private String fecha; // Fecha en que se hizo la rutina
    private List<ejercicio> ejercicios; // Lista de ejercicios relacionados con esta rutina

    // Constructor vacío necesario para Firestore
    public Rutina() {
    }

    // Constructor que inicializa los campos
    public Rutina(String nombre, String fecha, List<ejercicio> ejercicios) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.ejercicios = ejercicios;
    }

    protected Rutina(Parcel in) {
        id = in.readString();
        nombre = in.readString();
        fecha = in.readString();
        ejercicios = in.createTypedArrayList(ejercicio.CREATOR);
    }

    public static final Creator<Rutina> CREATOR = new Creator<Rutina>() {
        @Override
        public Rutina createFromParcel(Parcel in) {
            return new Rutina(in);
        }

        @Override
        public Rutina[] newArray(int size) {
            return new Rutina[size];
        }
    };

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<ejercicio> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(List<ejercicio> ejercicios) {
        this.ejercicios = ejercicios;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(nombre);
        parcel.writeString(fecha);
        parcel.writeTypedList(ejercicios);
    }
}
