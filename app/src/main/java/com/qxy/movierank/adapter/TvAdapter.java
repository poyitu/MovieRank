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

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.InnerHolder> {
    private Context mContext;
    private List<RankBean.DataDTO.ListDTO> mData = new ArrayList<>();


    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView;
        mContext = parent.getContext();
        itemView = layoutInflater.inflate(R.layout.tv_item, parent, false);
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

    private void initView(View itemView) {

    }

    //Viewholder
    public class InnerHolder extends RecyclerView.ViewHolder {
        private ImageView posterTvItem;
        private TextView nameTvItem;
        private TextView nameEnTvItem;
        private TextView directorTvItem;
        private TextView actorTvItem;
        private TextView timeTvItem;
        private TextView popularityTvItem;
        private TextView regionTvItem;
        private TextView tagsTvItem;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);


            posterTvItem = (ImageView) itemView.findViewById(R.id.poster_tv_item);
            nameTvItem = (TextView) itemView.findViewById(R.id.name_tv_item);
            nameEnTvItem = (TextView) itemView.findViewById(R.id.name_en_tv_item);
            directorTvItem = (TextView) itemView.findViewById(R.id.director_tv_item);
            actorTvItem = (TextView) itemView.findViewById(R.id.actor_tv_item);
            timeTvItem = (TextView) itemView.findViewById(R.id.time_tv_item);
            popularityTvItem = (TextView) itemView.findViewById(R.id.popularity_tv_item);
            regionTvItem = (TextView) itemView.findViewById(R.id.region_tv_item);
            tagsTvItem = (TextView) itemView.findViewById(R.id.tags_tv_item);
        }

        public void setItemData(RankBean.DataDTO.ListDTO varietyShowBean) {
            String tags = "";
            String dirctors = "";
            String actors = "";
            String areas = "";
            Glide.with(mContext)
                    .load(varietyShowBean.getPoster())
                    .error(R.mipmap.ic_launcher)
                    .into(posterTvItem);
            nameTvItem.setText(varietyShowBean.getName() == null ? "" : varietyShowBean.getName() + "");
            nameEnTvItem.setText(varietyShowBean.getName_en() == null ? "" : varietyShowBean.getName_en() + "");

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
            directorTvItem.setText(dirctors);

            //演员列表，显示前3
            if (varietyShowBean.getActors() != null) {
                List<String> actors_List = varietyShowBean.getActors();
                int count = 0;
                for (String actor : actors_List) {
                    actors += actor;
                    if (count != 2 || count == actors_List.size() - 1) {
                        actors += " / ";
                    }
                    count++;
                    if (count > 2) break;
                }
            }
            actorTvItem.setText(actors);

            if (varietyShowBean.getTags() != null) {
                List<String> tags_List = varietyShowBean.getTags();
                int count = 0;
                for (String tag : tags_List) {
                    tags += tag;
                    if (count != 2 && count != tags_List.size() - 1) {
                        tags += " / ";
                    }
                    count++;
                    if (count > 2) break;
                }

            }
            tagsTvItem.setText(tags);

            //上映区域
            if (varietyShowBean.getAreas() != null) {
                List<String> areas_List = varietyShowBean.getAreas();
                int count = 0;
                for (String area : areas_List) {
                    areas += area;
                    if (count != 2 && count != areas_List.size() - 1) {
                        areas += " / ";
                    }
                    count++;
                    if (count > 2) break;
                }

                regionTvItem.setText(areas);

            }


            //播出日期
            timeTvItem.setText(varietyShowBean.getRelease_date() == null ? "" : varietyShowBean.getRelease_date() + " 播出");

            //热度（单位：万）
            double v = new BigDecimal(varietyShowBean.getHot()).divide(new BigDecimal(10000)).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            popularityTvItem.setText(v + "万");
        }
    }

}
