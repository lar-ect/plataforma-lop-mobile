package br.ufrn.ect.lop.lopalpha;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;

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

/**
 * Created by marcus on 30/12/17.
 */

public class ConexaoDAO{
    String url;
    Context context;
    RequestQueue requestQueue;
    private Map<String, String> header;
    private Map<String, String> params;
    public ConexaoDAO(Context context, String url) {
        this.context = context;
        this.url = url;
        requestQueue = Volley.newRequestQueue(context);
        header = new HashMap<>();
        params = new HashMap<>();
    }


    public void setParams(String key, String value) {
        header.put(key, value);
    }
    public void setHeader(String key, String value) {
        header.put(key, value);
    }
    public void executeRequest(int method, final VolleyCallback callback) {
        final StringRequest jsonObjRequest = new StringRequest(method,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.getResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("MSG",error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return header;
            }
        };
        requestQueue.add(jsonObjRequest);
    }
    public interface VolleyCallback
    {
        public void getResponse(String response);
    }
}
