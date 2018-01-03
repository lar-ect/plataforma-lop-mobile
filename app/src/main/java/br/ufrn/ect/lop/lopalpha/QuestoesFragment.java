package br.ufrn.ect.lop.lopalpha;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestoesFragment extends Fragment {

    private List<Questao> questoesList = new ArrayList<Questao>();
    private RecyclerView recyclerView;
    private QuestoesAdapter qAdapter;


    public QuestoesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_questoes, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        qAdapter = new QuestoesAdapter(questoesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(qAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Questao questao = questoesList.get(position);

                FazerQuestaoFragment fazerQuestaoFragmentFragment = new FazerQuestaoFragment();
                getActivity().setTitle(questao.getTitulo());
                fazerQuestaoFragmentFragment.setTitulo(questao.getTitulo());
                fazerQuestaoFragmentFragment.setId(questao.getId());
                fazerQuestaoFragmentFragment.setEnunciado(questao.getEnunciado());
                android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentPai,fazerQuestaoFragmentFragment,"fragment1");
                fragmentTransaction.commit();
                //Toast.makeText(getActivity(), questao.getId() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        prepareQuestoesData();

        return v;
    }
    private void prepareQuestoesData() {
        String url = getString(R.string.host) + "/api/v1/questoes";
        String contentType = "application/x-www-form-urlencoded; charset=UTF-8";
        final ConexaoDAO conexaoDAO = new ConexaoDAO(getContext(),url);
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

}
