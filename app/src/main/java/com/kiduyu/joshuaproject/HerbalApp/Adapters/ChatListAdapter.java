package com.kiduyu.joshuaproject.HerbalApp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kiduyu.joshuaproject.HerbalApp.Models.ChatList;
import com.kiduyu.joshuaproject.HerbalApp.Models.Herb;
import com.kiduyu.joshuaproject.k_vet.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Kiduyu klaus
 * on 13/09/2020 18:05 2020
 */
public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyViewHolder>  {

    Context mcontext;
    private ArrayList<ChatList> chatListArrayList;

    public ChatListAdapter(Context context, ArrayList<ChatList> items) {
        this.mcontext=context;
        this.chatListArrayList = items;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.person_cell, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChatList chatList= chatListArrayList.get(position);
        Glide.with(mcontext).load(chatList.getPhoto()).into(holder.coverimage);
        holder.name.setText(chatList.getName());
        holder.message.setText(chatList.getLastMessage());
        holder.date.setText(chatList.getDate());
       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext, "Test", Toast.LENGTH_SHORT).show();
                ((AppCompatActivity) mcontext).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.layoutid, new ChatsFragment()).commit();

            }
        });

        */
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext, "Test", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return chatListArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView coverimage;
        TextView name,message,date;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            coverimage=itemView.findViewById(R.id.photo_personcell);
            message=itemView.findViewById(R.id.lastmessage_personcell);
            name=itemView.findViewById(R.id.person_personcell);
            date=itemView.findViewById(R.id.date_personcell);
            linearLayout=itemView.findViewById(R.id.layoutpc);
        }
    }
}
