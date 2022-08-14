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

        Glide.with(holder.itemView.getContext())
                .load(movie.getPoster())
                .error(R.mipmap.ic_launcher)
                .into(holder.posterImageView);

        holder.tvMovieName.setText(movie.getName());

        String actors = "";
        if (movie.getActors() != null) {
            List actors_List = movie.getActors();
            int count = 0;
            for (Object o : actors_List) {
                count++;
                if (count > 3) break;
                actors += o.toString() + " / ";
            }
        }
        holder.tvActor.setText(actors);

        holder.tvDirector.setText(movie.getDirectors() == null ? "" : movie.getDirectors().toString() + "");
        holder.tvTime.setText(movie.getRelease_date());
        double v = new BigDecimal(movie.getHot()).divide(new BigDecimal(10000)).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        holder.tvPopularity.setText(v + "万");
        List<String> areas = movie.getAreas();
        if (null != areas) {
            holder.tvRegion.setText(areas.get(0));
        } else {
            holder.tvRegion.setText("");
        }

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
