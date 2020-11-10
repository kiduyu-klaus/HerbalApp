package com.kiduyu.joshuaproject.HerbalApp.Consultants.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kiduyu.joshuaproject.HerbalApp.Models.Appointment;
import com.kiduyu.joshuaproject.HerbalApp.Session.Prevalent;
import com.kiduyu.joshuaproject.k_vet.R;

/**
 * Created by Kiduyu klaus
 * on 23/10/2020 10:18 2020
 */
public class Consultant_Approve_Fragment extends Fragment {
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase,rootref;
    ProgressDialog pDialog;
    private static final String TAG = "Doctor_Approve_Fragment";
       @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

           View view = inflater.inflate(R.layout.fragment_consultant_approve, container, false);

           mDatabase = FirebaseDatabase.getInstance().getReference().child("Appointments").child(Prevalent.currentOnlineUser.getFname());
           rootref = FirebaseDatabase.getInstance().getReference().child("My Appointments");
           Log.d(TAG, "my userrname: "+Prevalent.currentOnlineUser.getFname());
           mDatabase.keepSynced(true);
           recyclerView = view.findViewById(R.id.recyclerview_appointments_path);
           LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
           recyclerView.setLayoutManager(layoutManager);
           pDialog = new ProgressDialog(getActivity());
           getAppointments();
           return view;
       }

    private void getAppointments() {

        pDialog.setTitle("Fetching Appointments");
        pDialog.setMessage("Please wait, while we are checking the database.");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();


        FirebaseRecyclerOptions<Appointment> option = new FirebaseRecyclerOptions.Builder<Appointment>()
                .setQuery(mDatabase, Appointment.class)
                .build();

        FirebaseRecyclerAdapter<Appointment, MyViewHolderclasspath> adapter = new FirebaseRecyclerAdapter<Appointment, MyViewHolderclasspath>(option) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolderclasspath holder, int position, @NonNull final Appointment model) {

                pDialog.dismiss();
                holder.single_application_timeago.setText("less than a day ago");
                holder.single_application_county.setText("Dr. "+model.getCname());
                holder.single_application_username.setText(model.getUname());
                holder.single_application_school.setText("Tel : "+model.getCphone());
                holder.edt_myappications_descri11.setText("This Appointment  was set on "+model.getUdate()+"\nThe Appointment message was : -->\n"+model.getUdescri()+"\n\n\nThe Status is "+model.getStatus());

                if (model.getStatus().equals("Pending")){
                    holder.chat.setVisibility(View.VISIBLE);
                    holder.status.setVisibility(View.VISIBLE);
                } else {
                    holder.chat.setVisibility(View.GONE);
                    holder.status.setVisibility(View.GONE);
                }


                holder.chat.setText("Deny");
                holder.chat.setOnClickListener(v -> {

                    denyAppointment(model.getUname(),model.getUdate(),model.getCname());
                });

                holder.status.setText("Approve");
                holder.status.setOnClickListener(
                        v -> {
                            approveAppointment(model.getUname(),model.getUdate(),model.getCname());
                        }
                );
            }


            @NonNull
            @Override
            public MyViewHolderclasspath onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment, parent, false);
                return new MyViewHolderclasspath(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    private void approveAppointment(String cname, String udate, String uname) {
        pDialog.setTitle("please wait");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();



        mDatabase.child(uname).child("status").setValue("Aprroved");
       // rootref.child(cname).child(Prevalent.currentOnlineUser.getFname()).child(uname).child("status").setValue("Aprroved");
        pDialog.dismiss();
    }

    public void denyAppointment(String cname, String udate, String uname){
        pDialog.setTitle("please wait");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();



        mDatabase.child(uname).child("status").setValue("Denied");
       // rootref.child(cname).child(Prevalent.currentOnlineUser.getFname()).child(uname).child("status").setValue("Denied");
        pDialog.dismiss();

    }

    public class MyViewHolderclasspath extends RecyclerView.ViewHolder {
        TextView single_application_username, single_application_county, amountreeerequest, single_application_school, single_application_timeago, edt_myappications_descri11;
        ImageView single_application_image;
        Button chat,status;

        public MyViewHolderclasspath(@NonNull View itemView) {
            super(itemView);

            single_application_image = itemView.findViewById(R.id.single_application_user);
            single_application_username = itemView.findViewById(R.id.single_application_username);
            single_application_county = itemView.findViewById(R.id.single_application_county);
            single_application_school = itemView.findViewById(R.id.single_application_school);
            single_application_timeago = itemView.findViewById(R.id.single_application_timeago);
            edt_myappications_descri11 = itemView.findViewById(R.id.edt_myappications_descri11);
            status = itemView.findViewById(R.id.appointment_status);
            chat = itemView.findViewById(R.id.chat_appointment);
        }
    }
}
