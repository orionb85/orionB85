package com.example.evaluacion3y4;

import java.io.Serializable;

public class Ubicacion implements Serializable {
    private double latitud, longitud;

    public Ubicacion(){
        this.latitud = 0;
        this.longitud = 0;
    }

    public Ubicacion(double latitud, double longitud){
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public double GetLatitud(){
        return this.latitud;
    }

    public double GetLongigut(){
        return this.longitud;
    }

    public String GetLatitudLongitud(){
        return latitud+", "+longitud;
    }
}
