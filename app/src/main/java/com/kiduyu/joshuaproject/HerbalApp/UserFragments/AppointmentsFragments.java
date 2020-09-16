package com.kiduyu.joshuaproject.HerbalApp.UserFragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kiduyu.joshuaproject.HerbalApp.Constants.Constants;
import com.kiduyu.joshuaproject.HerbalApp.Models.Appointment;
import com.kiduyu.joshuaproject.HerbalApp.Models.ChatList;
import com.kiduyu.joshuaproject.HerbalApp.Session.Prevalent;
import com.kiduyu.joshuaproject.HerbalApp.Util.Netcheck;
import com.kiduyu.joshuaproject.k_vet.R;
import com.shashank.sony.fancytoastlib.FancyToast;

/**
 * Created by Kiduyu klaus
 * on 13/09/2020 15:40 2020
 */
public class AppointmentsFragments extends Fragment {
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    ProgressDialog pDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.appointments_fragment, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Appointments").child(Prevalent.currentOnlineUser.getFname());
        mDatabase.keepSynced(true);
        recyclerView = layout.findViewById(R.id.recyclerview_appointments);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        pDialog = new ProgressDialog(getActivity());
        getAppointments();


        return layout;
    }

    private void getAppointments() {
        pDialog.setTitle("Fetching Appointments");
        pDialog.setMessage("Please wait, while we are checking the database.");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();


        FirebaseRecyclerOptions<Appointment> option = new FirebaseRecyclerOptions.Builder<Appointment>()
                .setQuery(mDatabase, Appointment.class)
                .build();

        FirebaseRecyclerAdapter<Appointment, MyViewHolderclass> adapter = new FirebaseRecyclerAdapter<Appointment, MyViewHolderclass>(option) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolderclass holder, int position, @NonNull final Appointment model) {

                pDialog.dismiss();

                holder.single_application_timeago.setText("less than a day ago");
                holder.single_application_county.setText("Dr. "+model.getCname());
                holder.single_application_username.setText(model.getUname());
                holder.single_application_school.setText("Tel : "+model.getCphone());
                holder.edt_myappications_descri11.setText("An Appointment to Dr."+model.getCname()+" was set on "+model.getUdate()+"\nThe Appointment message was"+model.getUdescri());

                holder.chat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String lastMessage = "Hello " + "Dr. "+model.getCname() + " i would like to start a chat about a few things please.";
                        // holder.chat.setVisibility(View.INVISIBLE);
                        SendFirstMessage(Constants.Baseimageurl + model.getImage(), model.getCname(), lastMessage, Netcheck.getDate(), model.getCphone());

                    }
                });
            }
            private void SendFirstMessage(final String s, final String name, final String lastMessage, final String date, final String phone) {
                final ProgressDialog pDialog = new ProgressDialog(getActivity());
                pDialog.setTitle("Getting things ready");
                pDialog.setMessage("Please wait, while we are checking the credentials.");
                pDialog.setCanceledOnTouchOutside(false);
                pDialog.show();

                final DatabaseReference RootRef;
                RootRef = FirebaseDatabase.getInstance().getReference();

                RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (!(dataSnapshot.child("Chats").child(Netcheck.getIMEI(getActivity())).exists())) {

                            ChatList chatList = new ChatList(s, name, lastMessage, date, phone);

                            RootRef.child("Chats").child(Netcheck.getIMEI(getActivity())).child(name).setValue(chatList)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                pDialog.dismiss();
                                                getActivity().getSupportFragmentManager()
                                                        .beginTransaction()
                                                        .replace(R.id.fragment_frame, new ChatsFragment()).commit();

                                            } else {
                                                pDialog.dismiss();
                                                FancyToast.makeText(getActivity(), "Network Error: Please try again after some time...", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                                            }
                                        }
                                    });

                        } else {

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        FancyToast.makeText(getActivity(), String.valueOf(databaseError.getMessage()), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                        pDialog.dismiss();

                    }
                });

            }

            @NonNull
            @Override
            public MyViewHolderclass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment, parent, false);
                return new MyViewHolderclass(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    public class MyViewHolderclass extends RecyclerView.ViewHolder {
        TextView single_application_username, single_application_county, amountreeerequest, single_application_school, single_application_timeago, edt_myappications_descri11;
        ImageView single_application_image;
        Button chat;

        public MyViewHolderclass(@NonNull View itemView) {
            super(itemView);
            single_application_image = itemView.findViewById(R.id.single_application_user);
            single_application_username = itemView.findViewById(R.id.single_application_username);
            single_application_county = itemView.findViewById(R.id.single_application_county);
            single_application_school = itemView.findViewById(R.id.single_application_school);
            single_application_timeago = itemView.findViewById(R.id.single_application_timeago);
            edt_myappications_descri11 = itemView.findViewById(R.id.edt_myappications_descri11);
            chat = itemView.findViewById(R.id.chat_appointment);
        }
    }
}
