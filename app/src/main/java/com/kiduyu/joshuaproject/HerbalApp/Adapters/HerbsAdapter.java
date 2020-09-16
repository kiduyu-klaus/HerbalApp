package com.kiduyu.joshuaproject.HerbalApp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kiduyu.joshuaproject.HerbalApp.Activities.HerbsDetails;
import com.kiduyu.joshuaproject.HerbalApp.Constants.Constants;
import com.kiduyu.joshuaproject.HerbalApp.Models.Consultant;
import com.kiduyu.joshuaproject.HerbalApp.Models.Herb;
import com.kiduyu.joshuaproject.k_vet.R;

import java.util.ArrayList;
import java.util.List;

public class HerbsAdapter extends RecyclerView.Adapter<HerbsAdapter.MyViewHolder> {

    Context mcontext;
    private  ArrayList<Herb> consultantList;


    public HerbsAdapter(Context context, ArrayList<Herb> cList) {
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
        final Herb herb = consultantList.get(position);

        Glide.with(mcontext).load(Constants.Baseimageurl+herb.getImage()).into(holder.cover);
        holder.title.setText(herb.getName());
        holder.description.setText(herb.getDescription());
        holder.disease.setText(herb.getDisease());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mcontext, HerbsDetails.class);
                intent.putExtra("name",herb.getName());
                intent.putExtra("description",herb.getDescription());
                intent.putExtra("disease",herb.getDisease());
                intent.putExtra("image",Constants.Baseimageurl+herb.getImage());
                mcontext.startActivity(intent);
            }
        });

    }
    public void filterList(ArrayList<Herb> filteredList) {
        consultantList = filteredList;
        notifyDataSetChanged();
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
