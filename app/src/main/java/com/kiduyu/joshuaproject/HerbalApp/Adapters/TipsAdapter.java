package com.kiduyu.joshuaproject.HerbalApp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kiduyu.joshuaproject.HerbalApp.Constants.Constants;
import com.kiduyu.joshuaproject.HerbalApp.Models.Herb;
import com.kiduyu.joshuaproject.HerbalApp.Models.Tip;
import com.kiduyu.joshuaproject.k_vet.R;

import java.util.List;

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.MyViewHolder> {

    Context mcontext;
    private List<Tip> tipList;


    public TipsAdapter(Context context, List<Tip> tList) {
        this.tipList = tList;
        this.mcontext = context;
    }
    @NonNull
    @Override
    public TipsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TipsAdapter.MyViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.tip_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TipsAdapter.MyViewHolder holder, int position) {
        Tip tip = tipList.get(position);

        Glide.with(mcontext).load(Constants.Baseimageurl+tip.getImage()).into(holder.cover);
        holder.title.setText(tip.getTitle());
        holder.description.setText(tip.getDescription());

    }

    @Override
    public int getItemCount() {
        return tipList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView cover;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tip_title);
            description = itemView.findViewById(R.id.tip_description);

            cover = itemView.findViewById(R.id.tip_image);
        }
    }
}
