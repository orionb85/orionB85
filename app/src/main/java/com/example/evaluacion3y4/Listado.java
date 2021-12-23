package com.example.evaluacion3y4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class Listado extends AppCompatActivity {
    private RecyclerView recicler;
    private ArrayList<Noticias> noticias;
    private Adaptador adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

       // noticias = new ArrayList<Noticias>();
         noticias = (ArrayList<Noticias>) getIntent().getSerializableExtra("noticias");
        //GuardaNoticias();
        adaptador = new Adaptador(noticias);
        recicler=findViewById(R.id.reciclerNoticias);
        recicler.setLayoutManager(new LinearLayoutManager(this));
        recicler.setAdapter(adaptador);
        //
        //
    }
    //private void GuardaNoticias(){
        //noticias.add(new )
    //}
}