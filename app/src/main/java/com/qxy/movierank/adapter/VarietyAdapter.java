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
        if (varietyShowBeanList == null) {
            return;
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
        private TextView releasedateVarietyItem;
        private TextView popularityVarietyItem;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            imageViewVarietyItem = (ImageView) itemView.findViewById(R.id.imageView_variety_item);
            nameVarietyItem = (TextView) itemView.findViewById(R.id.name_variety_item);
            nameEnVarietyItem = (TextView) itemView.findViewById(R.id.name_en_variety_item);
            directorVarietyItem = (TextView) itemView.findViewById(R.id.director_variety_item);
            //actorVarietyItem = (TextView) itemView.findViewById(R.id.actor_variety_item);综艺榜单不需要演员
            releasedateVarietyItem = (TextView) itemView.findViewById(R.id.release_date_variety_item);
            popularityVarietyItem = (TextView) itemView.findViewById(R.id.popularity_variety_item);
        }

        public void setItemData(RankBean.DataDTO.ListDTO varietyShowBean) {
            String actors = "";
            String dirctors = "";
            Glide.with(mContext)
                    .load(varietyShowBean.getPoster())
                    .error(R.mipmap.ic_launcher)
                    .into(imageViewVarietyItem);
            nameVarietyItem.setText(varietyShowBean.getName() == null ? "" : varietyShowBean.getName() + "");
            nameEnVarietyItem.setText(varietyShowBean.getName_en() == null ? "" : varietyShowBean.getName_en() + "");
            //导演
            if (varietyShowBean.getDirectors() != null) {
                List<String> dirctors_List = varietyShowBean.getDirectors();
                int count = 0;
                for (String dirctor : dirctors_List) {
                    dirctors += dirctor;
                    if (count != 2 && count != dirctors_List.size() - 1) {
                        dirctors += " / ";
                    }
                    count++;
                    if (count > 2) break;
                }
            }
            directorVarietyItem.setText(dirctors);
//            //演员列表，显示前3
//            if (varietyShowBean.getActors() != null) {
//                List<String> actors_List = varietyShowBean.getActors();
//                int count = 0;
//                for (String actor : actors_List) {
//                    actors += actor;
//                    if(count != 2 || count == actors_List.size()-1){
//                        actors +=" / ";
//                    }
//                    count++;
//                    if (count > 2) break;
//                }
//            }
//            actorVarietyItem.setText(actors);

            //播出日期
            releasedateVarietyItem.setText(varietyShowBean.getRelease_date() == null ? "" : varietyShowBean.getRelease_date() + " 播出");

            //热度（单位：万）
            double v = new BigDecimal(varietyShowBean.getHot()).divide(new BigDecimal(10000)).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            popularityVarietyItem.setText(v + "万");
        }
    }

}
