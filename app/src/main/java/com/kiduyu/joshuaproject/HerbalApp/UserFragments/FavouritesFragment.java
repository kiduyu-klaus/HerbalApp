package com.kiduyu.joshuaproject.HerbalApp.UserFragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.kiduyu.joshuaproject.HerbalApp.Activities.HerbsDetails;
import com.kiduyu.joshuaproject.HerbalApp.Adapters.HerbsAdapter;
import com.kiduyu.joshuaproject.HerbalApp.Constants.Constants;
import com.kiduyu.joshuaproject.HerbalApp.Models.Favourite;
import com.kiduyu.joshuaproject.HerbalApp.Models.Herb;
import com.kiduyu.joshuaproject.k_vet.R;

import java.util.ArrayList;

public class FavouritesFragment extends Fragment {
    RecyclerView recycler;
    EditText search;
    String currentDateTimeString;
    SwipeRefreshLayout swipeRefreshLayout;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;

    private ArrayList<Herb> herbArrayList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favourites_fragment, container, false);


        recycler = view.findViewById(R.id.recyclerview_favourites);
        String uniqueid = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Favourites").child(uniqueid);


        swipeRefreshLayout = view.findViewById(R.id.favourites_refresh);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        recycler.setLayoutManager(layoutManager);
        recycler.setFocusable(false);
        getFavourites();

        return view;
    }

    private void getFavourites() {

        final ProgressDialog p =new ProgressDialog(getActivity());
        p.setMessage("Please Wait...");
        p.show();
        FirebaseRecyclerOptions<Favourite> option = new FirebaseRecyclerOptions.Builder<Favourite>()
                .setQuery(mDatabase, Favourite.class)
                .build();

        FirebaseRecyclerAdapter<Favourite, MyViewHolder> adapter= new FirebaseRecyclerAdapter<Favourite, MyViewHolder>(option) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull final Favourite herb) {
                p.dismiss();
                Glide.with(getActivity()).load(Constants.Baseimageurl+herb.getImage()).into(holder.cover);
                holder.title.setText(herb.getName());
                holder.description.setText(herb.getDescription());
                holder.disease.setText(herb.getDisease());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent= new Intent(getActivity(), HerbsDetails.class);
                        intent.putExtra("name",herb.getName());
                        intent.putExtra("description",herb.getDescription());
                        intent.putExtra("disease",herb.getDisease());
                        intent.putExtra("image",Constants.Baseimageurl+herb.getImage());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.herb_item, parent, false));
            }
        };
        recycler.setAdapter(adapter);
        adapter.startListening();


    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {
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
