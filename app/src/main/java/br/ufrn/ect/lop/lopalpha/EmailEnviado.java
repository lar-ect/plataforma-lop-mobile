package br.ufrn.ect.lop.lopalpha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EmailEnviado extends AppCompatActivity {
    private TextView textRecuperar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_enviado);
        textRecuperar = (TextView) findViewById(R.id.textRecuperar);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String msg = bundle.getString("msg");
        textRecuperar.setText(msg);
    }
}
