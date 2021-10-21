package com.example.tareasensegundoplano2122;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button buttonStart, buttonStop,buttonStart2,buttonStop2 ;
    TextView crono, crono2;
    Thread hilo;
    int contador = 0;
    int contador2  =0;
    private boolean hiloActivo, hiloActivo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonStart = findViewById(R.id.buttonStart);
        buttonStop = findViewById(R.id.buttonStop);
        crono = findViewById(R.id.textViewCrono);
        buttonStart2 = findViewById(R.id.buttonStart2);
        buttonStop2 = findViewById(R.id.buttonStop2);
        crono2 = findViewById(R.id.textViewCrono2);

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hiloActivo = false;
                hilo = null;
                //contador = 0; //para reseatar el contador.
            }
        });
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hiloActivo = true;
                if (hilo==null) {
                    hilo = new Thread() {
                        @Override
                        public void run() {
                            while (hiloActivo) {

                                int segundos = contador % 60;
                                int minutos = contador / 60;

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        crono.setText(String.format("%02d:%02d", minutos, segundos));
                                    }
                                });


                                Log.i("CRONO", minutos + ":" + segundos);
                                try {
                                    sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                contador++;
                            }
                        }
                    };
                    hilo.start();
                }


            }
        });

        buttonStart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hiloActivo2 = true;
                MiCronometro miCronometro = new MiCronometro(contador2, crono2);
                miCronometro.execute();
            }
        });
        buttonStop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hiloActivo2 = false;
            }
        });

    }

    private class MiCronometro extends AsyncTask<String, String, String>{
        int micontador;
        TextView textView;

        MiCronometro(int inicio, TextView tv){
            micontador = inicio;
            textView = tv;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            while (hiloActivo2) {
                int segundos = micontador % 60;
                int minutos = micontador / 60;
                String textoCrono = String.format("%02d:%02d", minutos, segundos);
                //Actualizar el textView!!
                publishProgress(textoCrono);
                micontador++;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            textView.setText(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            contador2 = micontador;
        }
    }
}