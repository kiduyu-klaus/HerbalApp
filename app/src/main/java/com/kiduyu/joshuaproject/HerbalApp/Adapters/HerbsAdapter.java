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
import com.kiduyu.joshuaproject.HerbalApp.Models.Consultant;
import com.kiduyu.joshuaproject.HerbalApp.Models.Herb;
import com.kiduyu.joshuaproject.k_vet.R;

import java.util.List;

public class HerbsAdapter extends RecyclerView.Adapter<HerbsAdapter.MyViewHolder> {

    Context mcontext;
    private List<Herb> consultantList;


    public HerbsAdapter(Context context, List<Herb> cList) {
        this.consultantList = cList;
        this.mcontext = context;
    }
    @NonNull
    @Override
    public HerbsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HerbsAdapter.MyViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.herb_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HerbsAdapter.MyViewHolder holder, int position) {
        Herb herb = consultantList.get(position);

        Glide.with(mcontext).load(Constants.Baseimageurl+herb.getImage()).into(holder.cover);
        holder.title.setText(herb.getName());
        holder.description.setText(herb.getDescription());
        holder.disease.setText(herb.getDisease());

    }

    @Override
    public int getItemCount() {
        return consultantList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, disease, description;
        ImageView cover;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.consultant_title);
            disease = itemView.findViewById(R.id.consultant_disease);
            description = itemView.findViewById(R.id.consultant_description);

            cover = itemView.findViewById(R.id.consultant_image);
        }
    }
}
