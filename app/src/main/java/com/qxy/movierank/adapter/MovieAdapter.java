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
    private ArrayList<RankBean> beanArrayList;

    public MovieAdapter(Context context, ArrayList<RankBean> beanArrayList) {
        this.context = context;
        this.beanArrayList = beanArrayList;
    }

    public void setData(ArrayList<RankBean> rankBeans) {
        beanArrayList = rankBeans;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //item_layout是Reclyerview每个格子的布局
        //获取recylerview格子的样式
        View itemview = View.inflate(context, R.layout.movie_item, null);
        //返回根据myViewHodler（）处理完的新View
        return new MyViewholder(itemview);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
        RankBean movie = beanArrayList.get(position);
        holder.tvMovieName.setText(movie.getData().getList().get(position).getName());
        holder.tvActor.setText((CharSequence) movie.getData().getList().get(position).getActors());
        //holder.tvDirector.setText((CharSequence) movie.getDirectors());
        //holder.tvTime.setText(movie.getRelease_date());
        //holder.tvPopularity.setText(movie.getSearch_hot());
        //holder.tvRegion.setText(movie.getRelease_date());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
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
