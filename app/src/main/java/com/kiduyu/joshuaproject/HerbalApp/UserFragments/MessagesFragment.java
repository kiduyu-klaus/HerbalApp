package com.kiduyu.joshuaproject.HerbalApp.UserFragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kiduyu.joshuaproject.HerbalApp.Models.ChatList;
import com.kiduyu.joshuaproject.HerbalApp.Models.Message;
import com.kiduyu.joshuaproject.HerbalApp.Session.Prevalent;
import com.kiduyu.joshuaproject.HerbalApp.Util.Netcheck;
import com.kiduyu.joshuaproject.k_vet.R;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class MessagesFragment extends Fragment {

    private ListView messageList;
    private LinearLayout attachmentGrid, locationtv, gallerytv, cameratv;
    private AppCompatEditText messageTextView;
    ImageButton attach, send;
    public static final int CAMERA = 2;
    public static final int GALLERY = 3;

    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.message_fragment, container, false);

        imageView = (ImageView) layout.findViewById(R.id.image_view);
        attachmentGrid = layout.findViewById(R.id.attachment_grid);
        attach = layout.findViewById(R.id.attach_Button);
        gallerytv = layout.findViewById(R.id.galley_id);
        locationtv = layout.findViewById(R.id.location_id);
        cameratv = layout.findViewById(R.id.camera_id);
        send = layout.findViewById(R.id.button_send);

        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attachButton(v);
            }
        });
        gallerytv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryButton(v);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendButton(v);
            }
        });
        locationtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationButton(v);
            }
        });
        cameratv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraButton(v);
            }
        });

        messageTextView = layout.findViewById(R.id.message_text);
        messageTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attachmentGrid.setVisibility(View.GONE);
            }
        });

        return layout;
    }

    public void attachButton(View view) {
        if (attachmentGrid.getVisibility() == View.VISIBLE) {
            attachmentGrid.setVisibility(View.GONE);
        } else {
            attachmentGrid.setVisibility(View.VISIBLE);
        }
    }

    public void cameraButton(View view) {
        attachmentGrid.setVisibility(View.GONE);
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA);
    }

    public void sendButton(View view) {
        attachmentGrid.setVisibility(View.GONE);
        String message = messageTextView.getText().toString();
        if (!message.equals("")) {
            String time = Netcheck.getTime();
            String trimmedmessage = message.trim();
            String sender = Prevalent.currentOnlineUser.getFname();
            String receiver = "Josh";

            SaveMEssage(time, trimmedmessage, sender, receiver);

        }
    }

    private void SaveMEssage(final String time, final String trimmedmessage, final String sender, final String receiver) {


        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                Message message = new Message(time, trimmedmessage, sender, receiver);

                RootRef.child("Messages").child(Netcheck.getIMEI(getActivity())).child(time).setValue(message)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    messageTextView.setText("");

                                } else {
                                    // pDialog.dismiss();
                                    FancyToast.makeText(getActivity(), "Network Error: Please try again after some time...", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                                }
                            }
                        });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                FancyToast.makeText(getActivity(), String.valueOf(databaseError.getMessage()), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                // pDialog.dismiss();

            }
        });

    }

    public void galleryButton(View view) {
        attachmentGrid.setVisibility(View.GONE);
        Intent gallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, GALLERY);
    }

    public void locationButton(View view) {
        attachmentGrid.setVisibility(View.GONE);
        //Toast.makeText(this, "Location", Toast.LENGTH_SHORT).show();
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
        } else shareLocation();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm.getActiveNetworkInfo() != null) {
                if (requestCode == CAMERA) {
                    //sendImage((Bitmap) data.getExtras().get("data"));
                } else if (requestCode == GALLERY) {
                    Uri imageUri = data.getData();
                    imageView.setImageURI(imageUri);
                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    //sendImage(bitmap);
                }
            } else {
                Toast.makeText(getActivity(), "No connection available", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void shareLocation() {
    }
}
