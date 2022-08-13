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


public class VarietyAdapter extends RecyclerView.Adapter<VarietyAdapter.InnerHolder> {
    private Context mContext;
    private List<RankBean.DataDTO.ListDTO> mData = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView;
        mContext = parent.getContext();
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
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }


    public void setData(List<RankBean.DataDTO.ListDTO> varietyShowBeanList) {
        if (mData != null) {
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
        private TextView nameEnVarietyItem;
        private TextView directorVarietyItem;
        private TextView actorVarietyItem;
        private TextView popularityVarietyItem;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            imageViewVarietyItem = (ImageView) itemView.findViewById(R.id.imageView_variety_item);
            nameVarietyItem = (TextView) itemView.findViewById(R.id.name_variety_item);
            nameEnVarietyItem = (TextView) itemView.findViewById(R.id.name_en_variety_item);
            directorVarietyItem = (TextView) itemView.findViewById(R.id.director_variety_item);
            actorVarietyItem = (TextView) itemView.findViewById(R.id.actor_variety_item);
            popularityVarietyItem = (TextView) itemView.findViewById(R.id.popularity_variety_item);
        }

        public void setItemData(RankBean.DataDTO.ListDTO varietyShowBean) {
            Glide.with(mContext)
                    .load(varietyShowBean.getPoster())
                    .error(R.mipmap.ic_launcher)
                    .into(imageViewVarietyItem);
            nameVarietyItem.setText(varietyShowBean.getName() == null ? "" : varietyShowBean.getName() + "");
            nameEnVarietyItem.setText(varietyShowBean.getName_en() == null ? "" : varietyShowBean.getName_en() + "");
            directorVarietyItem.setText(varietyShowBean.getDirectors() == null ? "" : varietyShowBean.getDirectors().toString() + "");
            String actors = "";
            if (varietyShowBean.getActors() != null) {
                List actors_List = varietyShowBean.getActors();
                int count = 0;
                for (Object o : actors_List) {
                    count++;
                    if (count > 3) break;
                    actors += o.toString() + " / ";
                }
            }
//            actorVarietyItem.setText(varietyShowBean.getActors()==null?"":varietyShowBean.getActors().toString());
            actorVarietyItem.setText(actors);
//            if (null!=varietyShowBean.getActors()){
//                actorVarietyItem.setText(varietyShowBean.getActors().toString() + "");
//
//            }else {
//                actorVarietyItem.setText("");
//
//            }
            double v = new BigDecimal(varietyShowBean.getHot()).divide(new BigDecimal(10000)).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            popularityVarietyItem.setText(v + "万");
        }
    }

}
