package com.qxy.movierank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.qxy.movierank.R;
import com.qxy.movierank.bean.RankBean;

import java.math.BigDecimal;
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
        Glide.with(context)
                .load(movie.getPoster())
                .error(R.mipmap.ic_launcher)
                .into(holder.posterImageView);
        holder.tvMovieName.setText(movie.getName());

        //演员列表，显示前3
        String actors = "";
        if (movie.getActors() != null) {
            List<String> actors_List = movie.getActors();
            int count = 0;
            for (String actor : actors_List) {
                actors += actor;
                if(count != 2 || count == actors_List.size()-1){
                    actors +=" / ";
                }
                count++;
                if (count > 2) break;
            }
        }
        holder.tvActor.setText(actors);
        //导演
        String dirctors = "";
        if(movie.getDirectors() != null){
            List<String> dirctors_List = movie.getDirectors();
            int count = 0;
            for (String dirctor : dirctors_List) {
                dirctors += dirctor;
                if(count != 2 && count!=dirctors_List.size()-1){
                    dirctors += " / ";
                }
                count++;
                if(count > 2)break;
            }
        }
        holder.tvDirector.setText(dirctors);
        holder.tvTime.setText(movie.getRelease_date() == null ? "" : movie.getRelease_date() + " 上映");
        double v = new BigDecimal(movie.getHot()).divide(new BigDecimal(10000)).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        holder.tvPopularity.setText(v + "万");
        holder.tvRegion.setText(movie.getAreas() == null ? "" : movie.getAreas().get(0) + "");

        // holder.itemView.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View v) {
        //     }
        // });
    }

    @Override
    public int getItemCount() {
        if (beanArrayList != null) {
            return beanArrayList.size();
        }
        return 0;
    }

    //Viewholder
    static class MyViewholder extends RecyclerView.ViewHolder {
        TextView tvMovieName, tvDirector, tvActor, tvTime, tvPopularity, tvRegion;
        ImageView posterImageView;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            tvMovieName = itemView.findViewById(R.id.movie_name);
            tvDirector = itemView.findViewById(R.id.director_tv_item);
            tvActor = itemView.findViewById(R.id.actor_tv_item);
            tvTime = itemView.findViewById(R.id.time_tv_item);
            tvPopularity = itemView.findViewById(R.id.popularity_tv_item);
            tvRegion = itemView.findViewById(R.id.region_tv_item);
            posterImageView = itemView.findViewById(R.id.poster_tv_item);
        }
    }

}
