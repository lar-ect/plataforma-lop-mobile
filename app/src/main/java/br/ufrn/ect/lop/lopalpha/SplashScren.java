package br.ufrn.ect.lop.lopalpha;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashScren extends AppCompatActivity implements Runnable{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_scren);
        Handler handler = new Handler(); // Instanciando o handler
        handler.postDelayed(this,3000); // Definindo um time de 3 segundos para iniciar a tela de login.
    }

    @Override
    public void run() {
        Log.v("SPLASH","Splash sendo executada.");
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        //Iniciando a activity da tela de login.
        finish(); // finalizando a activity atual.
    }
}
