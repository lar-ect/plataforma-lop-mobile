package br.ufrn.ect.lop.lopalpha;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashScren extends AppCompatActivity implements Runnable{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_scren);
        Handler handler = new Handler(); // Instanciando o handler
        handler.postDelayed(this,4000); // Definindo um time de 3 segundos para iniciar a tela de login.
    }

    @Override
    public void run() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        final String nome = pref.getString("nome", null);
        final String email = pref.getString("email", null);
        final String matricula = pref.getString("matricula", null);
        final String idSessao = pref.getString("idSessao", null);
        final User usuario = new User();
        if(idSessao!=null){
            String url = getString(R.string.host) + "/api/v1/session-status";
            String contentType = "application/x-www-form-urlencoded; charset=UTF-8";
            final ConexaoDAO conexaoDAO = new ConexaoDAO(this,url);
            conexaoDAO.setParams("id",idSessao);
            conexaoDAO.setHeader("Content-Type",contentType);
            conexaoDAO.executeRequest(Request.Method.POST, new ConexaoDAO.VolleyCallback() {
                @Override
                public void getResponse(String response) {
                    try {
                        JSONObject jObj = new JSONObject(response);
                        boolean status = jObj.getBoolean("status");
                        if(status){
                            usuario.setNome(nome);
                            usuario.setEmail(email);
                            usuario.setMatricula(matricula);
                            usuario.setIdSessao(idSessao);
                            startActivity(new Intent(getApplicationContext(),TelaPrincipalActivity.class).putExtra("Usuario",(Parcelable) usuario));
                            finish();
                        }else{
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    }
                }
            });
        }else{
            Log.v("SPLASH","Splash sendo executada.");
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            //Iniciando a activity da tela de login.
            finish(); // finalizando a activity atual.
        }
    }
}
