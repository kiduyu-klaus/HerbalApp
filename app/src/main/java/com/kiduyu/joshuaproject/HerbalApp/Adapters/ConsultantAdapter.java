package com.kiduyu.joshuaproject.HerbalApp.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kiduyu.joshuaproject.HerbalApp.Account.RegisterActivity;
import com.kiduyu.joshuaproject.HerbalApp.Activities.BookAppointment;
import com.kiduyu.joshuaproject.HerbalApp.Constants.Constants;
import com.kiduyu.joshuaproject.HerbalApp.Models.ChatList;
import com.kiduyu.joshuaproject.HerbalApp.Models.Consultant;
import com.kiduyu.joshuaproject.HerbalApp.UserFragments.ChatsFragment;
import com.kiduyu.joshuaproject.HerbalApp.Util.Netcheck;
import com.kiduyu.joshuaproject.k_vet.R;
import com.shashank.sony.fancytoastlib.FancyToast;

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
    public void onBindViewHolder(@NonNull final ConsultantAdapter.MyViewHolder holder, int position) {
        final Consultant consultant = consultantList.get(position);

        Glide.with(mcontext).load(Constants.Baseimageurl + consultant.getImage()).into(holder.cover);
        holder.title.setText(consultant.getName());
        holder.location.setText("Located in: " + consultant.getLocation());
        holder.phone.setText("Consultant Cell: " + consultant.getPhone());
        holder.date.setText("Joined our System on: " + consultant.getDate());

        holder.bookAp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, BookAppointment.class);
                intent.putExtra("consultant", consultant.getName());
                intent.putExtra("consultant_phone", consultant.getPhone());
                intent.putExtra("consultant_image", Constants.Baseimageurl +consultant.getImage());
                mcontext.startActivity(intent);
            }
        });
        holder.chat.setVisibility(View.GONE);

        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String lastMessage = "Hello " + consultant.getName() + " i would like to start a chat abou a few things please.";
               // holder.chat.setVisibility(View.INVISIBLE);
                SendFirstMessage(Constants.Baseimageurl + consultant.getImage(), consultant.getName(), lastMessage, Netcheck.getDate(), consultant.getPhone());
            }
        });

    }

    private void SendFirstMessage(final String s, final String name, final String lastMessage, final String date, final String phone) {
        final ProgressDialog pDialog = new ProgressDialog(mcontext);
        pDialog.setTitle("Getting things ready");
        pDialog.setMessage("Please wait, while we are checking the credentials.");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!(dataSnapshot.child("Chats").child(Netcheck.getIMEI(mcontext)).exists())) {

                    ChatList chatList = new ChatList(s, name, lastMessage, date, phone);

                    RootRef.child("Chats").child(Netcheck.getIMEI(mcontext)).child(name).setValue(chatList)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        pDialog.dismiss();
                                        ((AppCompatActivity) mcontext).getSupportFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.fragment_frame, new ChatsFragment()).commit();

                                    } else {
                                        pDialog.dismiss();
                                        FancyToast.makeText(mcontext, "Network Error: Please try again after some time...", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                                    }
                                }
                            });

                } else {

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                FancyToast.makeText(mcontext, String.valueOf(databaseError.getMessage()), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                pDialog.dismiss();

            }
        });

    }

    @Override
    public int getItemCount() {
        return consultantList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, location, phone, date;
        Button bookAp, chat;
        ImageView cover;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.consultant_title);
            location = itemView.findViewById(R.id.consultant_location);
            phone = itemView.findViewById(R.id.consultant_phone);
            date = itemView.findViewById(R.id.consultant_date);

            bookAp = itemView.findViewById(R.id.book_appointment);
            chat = itemView.findViewById(R.id.chat_consultant);

            cover = itemView.findViewById(R.id.consultant_image);
        }
    }
}
