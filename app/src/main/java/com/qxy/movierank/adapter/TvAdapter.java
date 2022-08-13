package com.qxy.movierank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qxy.movierank.R;
import com.qxy.movierank.bean.RankBean;

import java.util.ArrayList;
import java.util.List;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.MyViewholder> {
    private Context context;
    private List<RankBean.DataDTO.ListDTO> beanArrayList = new ArrayList<>();

    public TvAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<RankBean.DataDTO.ListDTO> rankBeans) {
        beanArrayList = rankBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView;

        itemView = layoutInflater.inflate(R.layout.tv_item, parent, false);
        return new MyViewholder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {

        RankBean.DataDTO.ListDTO movie = beanArrayList.get(position);
        holder.tvMovieName.setText(movie.getName());
        List<String> actors = movie.getActors();
        if (null != actors) {
            holder.tvActor.setText(actors.toString());

        } else {
            holder.tvActor.setText("");
        }
        List<String> directors = movie.getDirectors();
        if (null != directors) {
            holder.tvDirector.setText(directors.get(0));

        } else {
            holder.tvDirector.setText("");
        }

        holder.tvTime.setText(movie.getRelease_date());
        if (0 != movie.getInfluence_hot()) {

        }
        holder.tvPopularity.setText(movie.getInfluence_hot()+"");
        List<String> areas = movie.getAreas();
        if (null != areas) {
            holder.tvRegion.setText(areas.get(0));
        } else {
            holder.tvRegion.setText("");
        }

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    //Viewholder
    static class MyViewholder extends RecyclerView.ViewHolder {
        TextView tvMovieName, tvDirector, tvActor, tvTime, tvPopularity, tvRegion;
        ImageView posterImageView;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            tvMovieName = itemView.findViewById(R.id.name);
            tvDirector = itemView.findViewById(R.id.director);
            tvActor = itemView.findViewById(R.id.actor);
            tvTime = itemView.findViewById(R.id.time);
            tvPopularity = itemView.findViewById(R.id.popularity);
            tvRegion = itemView.findViewById(R.id.region);
            posterImageView = itemView.findViewById(R.id.poster);
        }
    }

}
