package com.qxy.movierank.adapter;

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


public class VarietyAdapter extends RecyclerView.Adapter<VarietyAdapter.InnerHolder> {

    private List<RankBean.DataDTO.ListDTO> mData = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView;

        itemView = layoutInflater.inflate(R.layout.variety_item, parent, false);
        return new InnerHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {

        holder.itemView.setTag(position);
        holder.setItemData(mData.get(position));

    }

    @Override
    public int getItemCount() {
        if(mData != null){
            return mData.size();
        }
        return 0;
    }


    public void setData(List<RankBean.DataDTO.ListDTO> varietyShowBeanList){
        if(mData != null){
            mData.clear();
        }
        mData.addAll(varietyShowBeanList);
        //更新UI
        notifyDataSetChanged();
    }

    //Viewholder
    public class InnerHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewVarietyItem;
        private TextView nameVarietyItem;
        private TextView directorVarietyItem;
        private TextView actorVarietyItem;
        private TextView popularityVarietyItem;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            imageViewVarietyItem = (ImageView) itemView.findViewById(R.id.imageView_variety_item);
            nameVarietyItem = (TextView) itemView.findViewById(R.id.name_variety_item);
            directorVarietyItem = (TextView) itemView.findViewById(R.id.director_variety_item);
            actorVarietyItem = (TextView) itemView.findViewById(R.id.actor_variety_item);
            popularityVarietyItem = (TextView) itemView.findViewById(R.id.popularity_variety_item);
        }

        public void setItemData(RankBean.DataDTO.ListDTO varietyShowBean){

            nameVarietyItem.setText(varietyShowBean.getName()+"");
            directorVarietyItem.setText(varietyShowBean.getDirectors().toString());
//            actorVarietyItem.setText(varietyShowBean.getActors().toString());
            popularityVarietyItem.setText(varietyShowBean.getDiscussion_hot()+"");
        }
    }

}
