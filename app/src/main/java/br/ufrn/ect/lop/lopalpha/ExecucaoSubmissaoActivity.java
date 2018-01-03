package br.ufrn.ect.lop.lopalpha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ExecucaoSubmissaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execucao_submissao);
        Intent it = getIntent();
        String response = it.getStringExtra("response");
        try {
            JSONArray data = new JSONArray(response);
            TextView textView;
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
            JSONObject qData;
            for (int i =0; i<data.length();i++){
                qData = data.getJSONObject(i);
                textView = new TextView(this);
                Log.i("a", String.valueOf(qData.getString("saida").toString().trim().length()));
                Log.i("b", String.valueOf(qData.getString("saidaEsperada").toString().trim().length()));
                if(qData.getString("saida").toString().trim().equals(qData.getString("saidaEsperada").toString().trim())){
                    textView.setText("Caso "+(i+1)+" (correto)");
                }else{
                    textView.setText("Caso "+(i+1)+" (errado)");
                }
                linearLayout.addView(textView);
                JSONArray data1 = new JSONArray(qData.getString("entrada"));
                String uniao=" ";
                for (int j =0; j<data1.length();j++){
                    if(data1.length()!=j+1){
                        uniao+= data1.getString(j)+", ";

                    }else{
                        uniao+= data1.getString(j);
                    }
                }
                textView = new TextView(this);
                textView.setText(uniao);
                linearLayout.addView(textView);

                textView = new TextView(this);
                textView.setText("Saída");
                linearLayout.addView(textView);

                textView = new TextView(this);
                textView.setText(qData.getString("saida"));
                linearLayout.addView(textView);

                textView = new TextView(this);
                textView.setText("Saída esperada");
                linearLayout.addView(textView);

                textView = new TextView(this);
                textView.setText(qData.getString("saidaEsperada"));
                linearLayout.addView(textView);
                textView = new TextView(this);
                textView.setText("\n");
                linearLayout.addView(textView);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
