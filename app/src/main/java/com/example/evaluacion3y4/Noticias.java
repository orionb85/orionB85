package com.example.evaluacion3y4;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;

public class Noticias implements Serializable {
    private String titulo;
    private String descipcion;
    private Double latitud;
    private Double longitud;
    //private Bitmap foto;
    private String Urifoto;

    public Noticias(){
        titulo="";
        descipcion="";
        latitud=0.0;
        longitud=0.0;
        //foto=null;
        Urifoto="";

    }

    public Noticias(String titulo, String descipcion, Double latitud, Double longitud, String Urifoto){
        this.titulo=titulo;
        this.descipcion=descipcion;
        this.latitud=latitud;
        this.longitud=longitud;
        //this.foto=foto;
        this.Urifoto=Urifoto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescipcion() {
        return descipcion;
    }

    public void setDescipcion(String descipcion) {
        this.descipcion = descipcion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    /*public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }*/

    public String getUrifoto() {
        return Urifoto;
    }

    public void setUrifoto(String urifoto) {
        Urifoto = urifoto;
    }
}
