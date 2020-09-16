package com.kiduyu.joshuaproject.HerbalApp.UserFragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kiduyu.joshuaproject.HerbalApp.Adapters.ChatListAdapter;
import com.kiduyu.joshuaproject.HerbalApp.Models.ChatList;
import com.kiduyu.joshuaproject.HerbalApp.Models.User;
import com.kiduyu.joshuaproject.HerbalApp.Util.Netcheck;
import com.kiduyu.joshuaproject.k_vet.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatsFragment extends Fragment {
    private ChatListAdapter chatListAdapter;
    ArrayList<ChatList> chatListArrayList = new ArrayList<>();
    RecyclerView recycler;
    ProgressDialog pDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.chat_fragment, container, false);
        recycler = layout.findViewById(R.id.recyclerview_chat);
        pDialog = new ProgressDialog(getActivity());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);
        recycler.setFocusable(false);

        getChats();
        return layout;
    }

    private void getChats() {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference().child("Chats").child(Netcheck.getIMEI(getActivity()));

        FirebaseRecyclerOptions<ChatList> option = new FirebaseRecyclerOptions.Builder<ChatList>()
                .setQuery(RootRef, ChatList.class)
                .build();

        FirebaseRecyclerAdapter<ChatList, ChatViewHolder> adapter = new FirebaseRecyclerAdapter<ChatList, ChatViewHolder>(option) {
            @Override
            protected void onBindViewHolder(@NonNull final ChatViewHolder holder, int position, @NonNull ChatList chatList) {

                Glide.with(getActivity()).load(chatList.getPhoto()).into(holder.coverimage);
                holder.name.setText(chatList.getName());
                holder.message.setText(chatList.getLastMessage());
                holder.date.setText(chatList.getDate());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // Toast.makeText(mcontext, "Test", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_frame, new Messages1Fragment()).commit();

                    }
                });
            }


            @NonNull
            @Override
            public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_cell, parent, false);
                return new ChatViewHolder(view);
            }
        };
        recycler.setAdapter(adapter);
        adapter.startListening();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        CircleImageView coverimage;
        TextView name, message, date;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            coverimage = itemView.findViewById(R.id.photo_personcell);
            message = itemView.findViewById(R.id.lastmessage_personcell);
            name = itemView.findViewById(R.id.person_personcell);
            date = itemView.findViewById(R.id.date_personcell);
        }
    }
}

