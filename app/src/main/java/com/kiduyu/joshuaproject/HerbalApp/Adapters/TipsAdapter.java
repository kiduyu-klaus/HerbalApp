package com.kiduyu.joshuaproject.HerbalApp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kiduyu.joshuaproject.HerbalApp.Constants.Constants;
import com.kiduyu.joshuaproject.HerbalApp.Models.Herb;
import com.kiduyu.joshuaproject.HerbalApp.Models.Tip;
import com.kiduyu.joshuaproject.k_vet.R;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.MyViewHolder> {

    Context mcontext;
    Activity activity;
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
        final Tip tip = tipList.get(position);

        Glide.with(mcontext).load(Constants.Baseimageurl+tip.getImage()).into(holder.cover);
        holder.title.setText(tip.getTitle());
        holder.description.setText(tip.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FancyAlertDialog.Builder((Activity) mcontext)
                        .setTitle(tip.getTitle())
                        .setBackgroundColor(Color.parseColor("#303F9F"))  //Don't pass R.color.colorvalue
                        .setMessage("In order to achieve this, you should "+tip.getDescription())
                        .setNegativeBtnText("Dismiss")
                        .setPositiveBtnBackground(Color.parseColor("#FF4081"))  //Don't pass R.color.colorvalue
                        .setPositiveBtnText("Save")
                        .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))  //Don't pass R.color.colorvalue
                        .setAnimation(Animation.POP)
                        .isCancellable(true)
                        .setIcon(R.drawable.ic_info, Icon.Visible)
                        .OnPositiveClicked(new FancyAlertDialogListener() {
                            @Override
                            public void OnClick() {
                                SaveTip(tip.getTitle(),tip.getDescription(),tip.getImage());
                            }
                        })
                        .OnNegativeClicked(new FancyAlertDialogListener() {
                            @Override
                            public void OnClick() {
                                FancyToast.makeText(mcontext,"Cancel",FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
                            }
                        })
                        .build();
            }
        });

    }

    private void SaveTip(String title, String description, String image) {

        FancyToast.makeText(mcontext,"Saved",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
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
