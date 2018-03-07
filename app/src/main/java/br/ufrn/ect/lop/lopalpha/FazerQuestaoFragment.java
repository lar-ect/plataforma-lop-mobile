package br.ufrn.ect.lop.lopalpha;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.github.akshay_naik.texthighlighterapi.TextHighlighter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class FazerQuestaoFragment extends Fragment {
    private String titulo;
    private String id;
    private String enunciado;
    private TextView tTitulo;
    private Button executa,submeter;
    private EditText codeIDE;
    private SpannableString text;
    private Context context;
    private String codigo;
    private String lastValue = "";
    private int startChanged;
    private int beforeChanged;
    private int countChanged;

    public FazerQuestaoFragment() {
        // Required empty public constructor
    }
    public void setTitulo(String titulo){
        this.titulo = titulo;
    }
    public void setId(String id){
        this.id = id;
    }
    public void setEnunciado(String enunciado){
        this.enunciado = enunciado;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fazer_questao, container, false);
        tTitulo = v.findViewById(R.id.tituloEnunciado);
        tTitulo.setText(enunciado);
        codeIDE = (EditText) v.findViewById(R.id.codeIDE);
        executa = (Button) v.findViewById(R.id.executar);
        executa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = getString(R.string.host) + "/api/v1/executar/questao";
                String contentType = "application/x-www-form-urlencoded; charset=UTF-8";
                ConexaoDAO conexaoDAO = new ConexaoDAO(getContext(),url);
                conexaoDAO.setParams("id",id);
                conexaoDAO.setParams("codigo",codeIDE.getText().toString().replace("\n",""));
                conexaoDAO.setHeader("Content-Type",contentType);
                Log.i("msg",url);
                conexaoDAO.executeRequest(Request.Method.POST, new ConexaoDAO.VolleyCallback() {
                    @Override
                    public void getResponse(String response) {
                        try {
                            JSONArray data = new JSONArray(response);
                            Log.i("msg",response);
                            Intent it = new Intent(getContext(),ExecucaoSubmissaoActivity.class);
                            it.putExtra("response",response);
                            startActivity(it);
                        }catch (JSONException e){
                            Toast.makeText(context, "Problema na execução da questão.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        submeter = (Button) v.findViewById(R.id.submeter);
        submeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return v;
    }

}
