package com.qxy.movierank.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qxy.movierank.MainActivity;
import com.qxy.movierank.R;
import com.qxy.movierank.bean.MovieBean;
import com.qxy.movierank.bean.RankBean;

import java.util.ArrayList;
import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewholder> {
    private Context context;
    private List<RankBean.DataDTO.ListDTO> beanArrayList = new ArrayList<>();

    public MovieAdapter(Context context, List<RankBean.DataDTO.ListDTO> beanArrayList) {
        this.context = context;
        this.beanArrayList = beanArrayList;
    }
    public MovieAdapter(Context context) {
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

        itemView = layoutInflater.inflate(R.layout.movie_item, parent, false);
        return new MovieAdapter.MyViewholder(itemView);
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

        // holder.itemView.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View v) {
        //     }
        // });
    }

    @Override
    public int getItemCount() {
        return beanArrayList.size();
    }

    //Viewholder
    static class MyViewholder extends RecyclerView.ViewHolder {
        TextView tvMovieName, tvDirector, tvActor, tvTime, tvPopularity, tvRegion;
        ImageView posterImageView;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            tvMovieName = itemView.findViewById(R.id.movie_name);
            tvDirector = itemView.findViewById(R.id.director);
            tvActor = itemView.findViewById(R.id.actor);
            tvTime = itemView.findViewById(R.id.time);
            tvPopularity = itemView.findViewById(R.id.popularity);
            tvRegion = itemView.findViewById(R.id.region);
            posterImageView = itemView.findViewById(R.id.poster);
        }
    }

}
