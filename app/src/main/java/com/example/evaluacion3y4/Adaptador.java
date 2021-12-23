package com.example.evaluacion3y4;

import android.content.Intent;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> {
    public ArrayList<Noticias> noticias;

    public Adaptador(ArrayList<Noticias>noticias){this.noticias = noticias;}

    @NonNull
    @Override
    public Adaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_tarjeta, parent, false);
        return new ViewHolder(view).enlaceAdaptador(this);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador.ViewHolder holder, int position) {
        holder.foto.setImageResource(R.drawable.ic_launcher_background);
        holder.titulo.setText(noticias.get(position).getTitulo());
        holder.descripcion.setText(noticias.get(position).getDescipcion());
        holder.latitud.setText(noticias.get(position).getLatitud().toString());
        holder.longitud.setText(noticias.get(position).getLongitud().toString());



    }

    @Override
    public int getItemCount() {
        return noticias.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView foto;
        private TextView titulo,descripcion,latitud, longitud;
        private Button botonMapa, botonElimina;
        private Noticias noticias;
        private Adaptador adaptador;
        private MapaSave mapas;

        public ViewHolder(View itemView){
            super(itemView);
            foto=itemView.findViewById(R.id.foto);
            titulo=itemView.findViewById(R.id.titulo);
            descripcion=itemView.findViewById(R.id.texto);
            latitud=itemView.findViewById(R.id.latitud);
            longitud=itemView.findViewById(R.id.longitud);
            botonMapa=itemView.findViewById(R.id.btnMapa);
            botonElimina=itemView.findViewById(R.id.botonEliminar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            botonElimina.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adaptador.noticias.remove(getAdapterPosition());
                    adaptador.notifyItemRemoved(getAdapterPosition());
                    adaptador.notifyItemRangeChanged(getAdapterPosition(), adaptador.noticias.size());

                }
            });

            botonMapa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mapas = new MapaSave(latitud.getText().toString(),longitud.getText().toString());

                    Intent intent =new Intent(view.getContext(),MapsGuardado.class);
                    intent.putExtra("latitudes",mapas);
                    view.getContext().startActivity(intent);


                }
            });

        }

        public ViewHolder enlaceAdaptador(Adaptador e){
            this.adaptador = e;
            return this;
        }
    }
}
