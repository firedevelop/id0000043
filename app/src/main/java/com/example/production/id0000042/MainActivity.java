package com.example.production.id0000042;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private final boolean CANCELAR_SI_MAS_DE_100_IMAGENES = false;

    private final String TAG_LOG = "test";

    private TextView TV_mensaje;
<
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TV_mensaje = (TextView) findViewById(R.id.TextView_mensajesAlUsuario);

        Button B_probarHacerDosCosasALaVez = (Button) findViewById(R.id.button_probarComoPodemosHacerOtraCosa);
        B_probarHacerDosCosasALaVez.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "...Haciendo otra cosa el usuario sobre el hilo PRINCIPAL a la vez que carga...", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        new DescargarImagenesDeInternetEnOtroHilo(CANCELAR_SI_MAS_DE_100_IMAGENES).execute(" Estos Strings van a variableNoUsada que no usaremos en este ejemplo y podiamos haber declarado como Void "," si lo necesitaramos podemos cambiar el String por otro tipo de datos "," y podemos añadir más de 4 datos que los de este ejemplo, todos los que necesitemos "," y recuerda que se usan como un array, para acceder en concreto a este usaríamos variableNoUsada[3] ");
    }
    private class DescargarImagenesDeInternetEnOtroHilo extends AsyncTask <String, Float, Integer> {
        private boolean cancelarSiHayMas100Archivos;
        private ProgressBar miBarraDeProgreso;

        public DescargarImagenesDeInternetEnOtroHilo(boolean cancelarSiHayMas100Archivos) {
            this.cancelarSiHayMas100Archivos = cancelarSiHayMas100Archivos;
        }

        @Override
        protected void onPreExecute() {
            TV_mensaje.setText("ANTES de EMPEZAR la descarga. Hilo PRINCIPAL");
            miBarraDeProgreso = (ProgressBar) findViewById(R.id.progressBar_indicador);
        }

        @Override
        protected Integer doInBackground(String... variableNoUsada) {

            int cantidadImagenesDescargadas = 0;
            float progreso = 0.0f;

            while (!isCancelled() && cantidadImagenesDescargadas<2000){
                cantidadImagenesDescargadas++;
                try {
                    Thread.sleep((long) (Math.random()*10));
                } catch (InterruptedException e) {
                    cancel(true);
                    e.printStackTrace();
                }

                progreso+=0.5;
                publishProgress(progreso);
                if (cancelarSiHayMas100Archivos && cantidadImagenesDescargadas>100){
                    cancel(true);
                }
            }
            return cantidadImagenesDescargadas;
        }

        @Override
        protected void onProgressUpdate(Float... porcentajeProgreso) {
            TV_mensaje.setText("Progreso descarga: "+porcentajeProgreso[0]+"%. Hilo PRINCIPAL");
            miBarraDeProgreso.setProgress( Math.round(porcentajeProgreso[0]) );
        }
        @Override
        protected void onPostExecute(Integer cantidadProcesados) {
            TV_mensaje.setText("DESPUÉS de TERMINAR la descarga. Se han descarcado "+cantidadProcesados+" imágenes. Hilo PRINCIPAL");
            TV_mensaje.setTextColor(Color.GREEN);
        }
        @Override
        protected void onCancelled (Integer cantidadProcesados) {
            TV_mensaje.setText("DESPUÉS de CANCELAR la descarga. Se han descarcado "+cantidadProcesados+" imágenes. Hilo PRINCIPAL");
            TV_mensaje.setTextColor(Color.RED);
        }
    }
}