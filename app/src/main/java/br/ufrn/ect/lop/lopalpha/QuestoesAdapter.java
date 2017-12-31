package br.ufrn.ect.lop.lopalpha;

import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by marcus on 31/12/17.
 */

public class QuestoesAdapter extends RecyclerView.Adapter<QuestoesAdapter.MyViewHolder> {

    private List<Questao> questoesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
        }
    }


    public QuestoesAdapter(List<Questao> questoesList) {
        this.questoesList = questoesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.questoes_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Questao questao = questoesList.get(position);
        holder.title.setText(questao.getTitulo());
        holder.genre.setText(questao.getEnunciado());
        holder.year.setText(questao.getDificuldade());
    }

    @Override
    public int getItemCount() {
        return questoesList.size();
    }
}