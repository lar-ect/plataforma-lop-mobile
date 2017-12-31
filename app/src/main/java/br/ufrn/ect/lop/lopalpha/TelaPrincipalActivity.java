package br.ufrn.ect.lop.lopalpha;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TelaPrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private List<Questao> questoesList = new ArrayList<Questao>();
    private RecyclerView recyclerView;
    private QuestoesAdapter qAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);
        Intent i = getIntent();
        User usuario = (User) i.getParcelableExtra("Usuario");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        qAdapter = new QuestoesAdapter(questoesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(qAdapter);
        prepareQuestoesData();
    }
    private void prepareQuestoesData() {
        String url = getString(R.string.host) + "/api/v1/questoes";
        String contentType = "application/x-www-form-urlencoded; charset=UTF-8";
        final ConexaoDAO conexaoDAO = new ConexaoDAO(this,url);
        conexaoDAO.setHeader("Content-Type",contentType);
        conexaoDAO.executeRequest(Request.Method.GET, new ConexaoDAO.VolleyCallback() {
            @Override
            public void getResponse(String response) {
                try{
                    Questao questao = new Questao();
                    JSONArray data = new JSONArray(response);
                    JSONObject qData;
                    for(int i=0; i<data.length();i++){

                        qData = data.getJSONObject(i);
                        Log.i("msg",qData.getString("titulo"));
                        questao = new Questao(qData.getString("_id"),qData.getString("titulo"),qData.getString("dificuldade")
                                ,qData.getString("enunciado"));
                        questoesList.add(questao);

                    }
                    qAdapter.notifyDataSetChanged();

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Intent i = getIntent();
        User usuario = (User) i.getParcelableExtra("Usuario");
        TextView tvNomePrincipal = (TextView) findViewById(R.id.nomePrincipal);
        TextView tvMatriculaPrincipal = (TextView) findViewById(R.id.matriculaPrincipal);

        tvNomePrincipal.setText(usuario.getNome());
        tvMatriculaPrincipal.setText(usuario.getMatricula());

        getMenuInflater().inflate(R.menu.tela_principal, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent i = getIntent();
        User usuario = (User) i.getParcelableExtra("Usuario");
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sair) {
            String url = getString(R.string.host) + "/api/v1/finalizar-session";
            String contentType = "application/x-www-form-urlencoded; charset=UTF-8";
            final ConexaoDAO conexaoDAO = new ConexaoDAO(this,url);
            conexaoDAO.setParams("id",usuario.getIdSessao());
            conexaoDAO.setHeader("Content-Type",contentType);
            conexaoDAO.executeRequest(Request.Method.POST, new ConexaoDAO.VolleyCallback() {
                @Override
                public void getResponse(String response) {
                    try{
                        JSONObject jObj = new JSONObject(response);
                        boolean status = jObj.getBoolean("status");
                        if(status){
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        }else{
                            Toast.makeText(TelaPrincipalActivity.this,jObj.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }catch (JSONException e){
                        Toast.makeText(TelaPrincipalActivity.this, "Problema ao finalizar sessão.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
