package com.example.evaluacion3y4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evaluacion3y4.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.GoogleMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String[] permisos = { Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};


    private Ubicacion ubicacion;
    private int cont;

    private final int ACTIVITY_CAMARA = 50;
    private final int ACTIVITY_GALERIA = 60;
    private TextView ubica,ubicaLo,titulo,descrip,uri;
    private ImageView foto;
    private Bitmap bitmap;
    private Adaptador adaptador;
    private ArrayList<Noticias> noticias;
    //private Noticias noticias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        adaptador=new Adaptador(noticias);
        ubica=findViewById(R.id.ubica);
        ubicaLo=findViewById(R.id.ubicaLo);
        titulo=findViewById(R.id.titulo);
        descrip=findViewById(R.id.descripcion);
        foto = findViewById(R.id.foto);
        uri=findViewById(R.id.uri);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            requestPermissions(permisos,100);
        }
        bitmap=null;
        noticias = new ArrayList<Noticias>();
    }

    public void BuscarUbicacion(View view){
        Intent intent =new Intent(this, MapsActivity.class);
        startActivityForResult(intent,200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200){
            if(resultCode == RESULT_OK){
                Ubicacion ubicacion = (Ubicacion) data.getSerializableExtra("ubicacion");
                ubicaLo.setText(""+ubicacion.GetLongigut());
                ubica.setText(""+ubicacion.GetLatitud());
            }
        }
        switch (requestCode)
        {
            case ACTIVITY_CAMARA:
                if(resultCode == RESULT_OK){
                    bitmap = (Bitmap) data.getExtras().get("data");
                    foto.setImageBitmap(bitmap);
                }
                break;
            case ACTIVITY_GALERIA:
                if(resultCode == RESULT_OK){
                    Uri ruta = data.getData();
                    foto.setImageURI(ruta);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100){
            if(!(grantResults[0]== PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(this, "Se necesita permiso de cámara", Toast.LENGTH_LONG).show();
            }

            if(!(grantResults[1] == PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(this, "Se necesita permiso de lectura de memoria", Toast.LENGTH_LONG).show();
            }

            if(!(grantResults[2] == PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(this, "Se necesita permiso de escritura de memoria", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void TomarFoto(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,ACTIVITY_CAMARA);
    }

    public void GuardarFoto(View view){
        if(bitmap != null){

            File archivoFoto = null;
            OutputStream streamSalida = null;

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                ContentResolver resolver = getContentResolver();
                ContentValues values = new ContentValues();

                String nombreArchivo = System.currentTimeMillis()+"_fotoPrueba";

                values.put(MediaStore.Images.Media.DISPLAY_NAME, nombreArchivo);
                values.put(MediaStore.Images.Media.MIME_TYPE, "Image/jpg");
                values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyApp");
                values.put(MediaStore.Images.Media.IS_PENDING, 1);

                Uri coleccion = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
                Uri fotoUri = resolver.insert(coleccion, values);
                String rut=fotoUri.toString();
                uri.setText(rut);

                try{
                    streamSalida = resolver.openOutputStream(fotoUri);
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }

                values.clear();
                values.put(MediaStore.Images.Media.IS_PENDING, 0);
                resolver.update(fotoUri, values, null, null);
            } else {

                String ruta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
                String nombreArchivo = System.currentTimeMillis()+"_fotoPrueba.jpg";
                archivoFoto = new File(ruta, nombreArchivo);
                uri.setText(ruta+"/"+nombreArchivo);

                try{
                    streamSalida = new FileOutputStream(archivoFoto);
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }

            }

            boolean fotoOk = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, streamSalida);

            if(fotoOk){
                Toast.makeText(this, "Foto Guardada!", Toast.LENGTH_SHORT).show();
            }

            if(streamSalida != null){
                try{
                    streamSalida.flush();
                    streamSalida.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }

            if(archivoFoto != null){
                MediaScannerConnection.scanFile(this, new String[]{archivoFoto.toString()}, null, null);
            }


        } else {
            Toast.makeText(this, "Primero debe tomar una foto antes de usar esta opción", Toast.LENGTH_SHORT).show();
        }
    }

    public void GuardarNoticia(View view){
        //noticias.add (new Noticias(titulo.getText().toString(),descrip.getText().toString(),Double.parseDouble(ubica.getText().toString()),Double.parseDouble(ubicaLo.getText().toString()),foto.getDrawingCache(),Uri.parse(bitmap.toString())));

        String tit=titulo.getText().toString();
        String des=descrip.getText().toString();
        Double lat=Double.parseDouble(ubica.getText().toString());
        Double lon=Double.parseDouble(ubicaLo.getText().toString());
        String ft=uri.getText().toString();
        //noticias.add (new Noticias(tit,des,lat,lon,ft));
        noticias.add(new Noticias(tit,des,lat,lon,ft));
        //Toast.makeText(this,""+tit+" "+des+" "+lat+" "+lon+" "+ft,Toast.LENGTH_LONG).show();

        //startActivity(intent);
    }

    public void MostrarLista(View view){
        Intent intent= new Intent(this, Listado.class);
        intent.putExtra("noticias", noticias);
        startActivity(intent);
    }

}