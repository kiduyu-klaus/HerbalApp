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
import com.kiduyu.joshuaproject.k_vet.R;

import java.util.List;

public class ConsultantAdapter extends RecyclerView.Adapter<ConsultantAdapter.MyViewHolder> {
    Context mcontext;
    private List<Consultant> consultantList;


    public ConsultantAdapter(Context context, List<Consultant> cList) {
        this.consultantList = cList;
        this.mcontext = context;
    }

    @NonNull
    @Override
    public ConsultantAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.consultant_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ConsultantAdapter.MyViewHolder holder, int position) {
        Consultant consultant = consultantList.get(position);

        Glide.with(mcontext).load(Constants.Baseimageurl+consultant.getImage()).into(holder.cover);
        holder.title.setText(consultant.getName());
        holder.location.setText(consultant.getLocation());
        holder.phone.setText(consultant.getPhone());
        holder.date.setText(consultant.getDate());

    }

    @Override
    public int getItemCount() {
        return consultantList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, location, phone, date;
        ImageView cover;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.consultant_title);
            location = itemView.findViewById(R.id.consultant_location);
            phone = itemView.findViewById(R.id.consultant_phone);
            date = itemView.findViewById(R.id.consultant_date);

            cover = itemView.findViewById(R.id.consultant_image);
        }
    }
}