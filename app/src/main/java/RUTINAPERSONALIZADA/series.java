package RUTINAPERSONALIZADA;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class series implements Parcelable {

    private String peso;
    private String repeticiones;
    private String numeroSerie;

    public series(String numeroSerie, String peso, String repeticiones, String fecha) {}


    public series(String peso, String repeticiones, String numeroSerie) {
        this.peso = peso;
        this.repeticiones = repeticiones;
        this.numeroSerie = numeroSerie;
    }

    protected series(Parcel in) {
        peso = in.readString();
        repeticiones = in.readString();
        numeroSerie = in.readString();
    }

    public static final Creator<series> CREATOR = new Creator<series>() {
        @Override
        public series createFromParcel(Parcel in) {
            return new series(in);
        }

        @Override
        public series[] newArray(int size) {
            return new series[size];
        }
    };

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(String repeticiones) {
        this.repeticiones = repeticiones;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(peso);
        parcel.writeString(repeticiones);
        parcel.writeString(numeroSerie);
    }
}