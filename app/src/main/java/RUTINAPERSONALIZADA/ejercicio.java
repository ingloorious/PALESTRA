package RUTINAPERSONALIZADA;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ejercicio implements Parcelable {
    private String nombreEjercicio;
    private List<series> seriesList;

    public ejercicio(String nombreEjercicio, List<series> seriesList) {
        this.nombreEjercicio = nombreEjercicio;
        this.seriesList = seriesList;
    }

    public ejercicio() {}

    protected ejercicio(Parcel in) {
        nombreEjercicio = in.readString();
        seriesList = in.createTypedArrayList(series.CREATOR);
    }

    public static final Creator<ejercicio> CREATOR = new Creator<ejercicio>() {
        @Override
        public ejercicio createFromParcel(Parcel in) {
            return new ejercicio(in);
        }

        @Override
        public ejercicio[] newArray(int size) {
            return new ejercicio[size];
        }
    };

    public String getNombreEjercicio() {
        return nombreEjercicio;
    }

    public void setNombreEjercicio(String nombreEjercicio) {
        this.nombreEjercicio = nombreEjercicio;
    }

    public List<series> getSeriesList() {
        return seriesList;
    }

    public void setSeriesList(List<series> seriesList) {
        this.seriesList = seriesList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nombreEjercicio);
        parcel.writeTypedList(seriesList); // Escribir la lista de series
    }

    public void agregarSerie(series nuevaSerie) {
        seriesList.add(nuevaSerie);
    }
}
