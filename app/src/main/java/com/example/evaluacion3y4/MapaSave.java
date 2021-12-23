package com.example.evaluacion3y4;

import java.io.Serializable;

public class MapaSave implements Serializable {

    private String latitud;
    private String longitud;

    public MapaSave(){
        this.latitud="";
        this.longitud="";

    }
    public MapaSave(String latitud, String longitud){
        this.longitud=longitud;
        this.latitud=latitud;

    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
