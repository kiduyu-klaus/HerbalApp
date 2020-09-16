package com.kiduyu.joshuaproject.HerbalApp.UserFragments;

import android.content.res.AssetManager;
import android.os.Environment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kiduyu.joshuaproject.HerbalApp.Adapters.ChatMessageAdapter;
import com.kiduyu.joshuaproject.HerbalApp.Models.ChatMessage;
import com.kiduyu.joshuaproject.HerbalApp.Session.Prevalent;
import com.kiduyu.joshuaproject.k_vet.R;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.Graphmaster;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.PCAIMLProcessorExtension;
import org.alicebot.ab.Timer;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class Messages1Fragment extends Fragment {

    private ListView mListView;
    private FloatingActionButton mButtonSend;
    private EditText mEditTextMessage;
    private ImageView mImageView;
    public Bot bot;
    public static Chat chat;
    private ChatMessageAdapter mAdapter;


    ImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.message_fragment1, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Chats");
        mListView = (ListView) layout.findViewById(R.id.listView);
        mButtonSend = (FloatingActionButton) layout.findViewById(R.id.btn_send);
        mEditTextMessage = (EditText) layout.findViewById(R.id.et_message);
        mImageView = (ImageView) layout.findViewById(R.id.iv_image);
        mAdapter = new ChatMessageAdapter(getActivity(), new ArrayList<ChatMessage>());
        mListView.setAdapter(mAdapter);
SendFirstMessage();
        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = mEditTextMessage.getText().toString().trim();
                //bot
                String response = chat.multisentenceRespond(mEditTextMessage.getText().toString());
                if (TextUtils.isEmpty(message)) {
                    return;
                }
                sendMessage(message);
                CompareBot(message);

                mEditTextMessage.setText("");
                mListView.setSelection(mAdapter.getCount() - 1);
            }
        });
        //checking SD card availablility
        boolean a = isSDCARDAvailable();
        //receiving the assets from the app directory
        AssetManager assets = getResources().getAssets();
        File jayDir = new File(Environment.getExternalStorageDirectory().toString() + "/hari/bots/Hari");
        boolean b = jayDir.mkdirs();

        if (jayDir.exists()) {
            //Reading the file
            try {
                for (String dir : assets.list("Hari")) {
                    File subdir = new File(jayDir.getPath() + "/" + dir);
                    boolean subdir_check = subdir.mkdirs();
                    for (String file : assets.list("Hari/" + dir)) {
                        File f = new File(jayDir.getPath() + "/" + dir + "/" + file);
                        if (f.exists()) {
                            continue;
                        }
                        InputStream in = null;
                        OutputStream out = null;
                        in = assets.open("Hari/" + dir + "/" + file);
                        out = new FileOutputStream(jayDir.getPath() + "/" + dir + "/" + file);
                        //copy file from assets to the mobile's SD card or any secondary memory
                        copyFile(in, out);
                        in.close();
                        in = null;
                        out.flush();
                        out.close();
                        out = null;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //get the working directory
        MagicStrings.root_path = Environment.getExternalStorageDirectory().toString() + "/hari";
        System.out.println("Working Directory = " + MagicStrings.root_path);
        AIMLProcessor.extension =  new PCAIMLProcessorExtension();
        //Assign the AIML files to bot for processing
        bot = new Bot("Hari", MagicStrings.root_path, "chat");
        chat = new Chat(bot);
        String[] args = null;
        mainFunction(args);

        return layout;
    }

    private void CompareBot(String message) {
        if (message.contains("hello") || message.contains("Hello")){
            String answer ="Hello too "+ Prevalent.currentOnlineUser.getFname();
            mimicOtherMessage(answer);
        } else if(message.contains("when will he be back") || message.contains("When will he be back")){
            String answer ="Am not sure , Your Appointment is being processed and once Dr.Josh is Available, i will notify you Immediately.";
            mimicOtherMessage(answer);
        }else if(message.contains("Thankyou") || message.contains("thankyou")){
            String answer ="It's my pleasure and you are welcome.";
            mimicOtherMessage(answer);

        }else if(message.contains("My appointment") || message.contains("my appointment")){
            String answer ="Sadly, Your Appointment did not go through as the doctor has a meeting on the same day as your appointment.\nwould you like to set a new Appointment?";
            mimicOtherMessage(answer);

        }else if(message.contains("hello") || message.contains("Hello")){

        }else if(message.contains("hello") || message.contains("Hello")){

        }else if(message.contains("hello") || message.contains("Hello")){

        }else if(message.contains("hello") || message.contains("Hello")){

        }else if(message.contains("hello") || message.contains("Hello")){

        }else if(message.contains("hello") || message.contains("Hello")){

        }else if(message.contains("hello") || message.contains("Hello")){

        }else if(message.contains("hello") || message.contains("Hello")){

        }else if(message.contains("hello") || message.contains("Hello")){

        }else if(message.contains("hello") || message.contains("Hello")){

        }else if(message.contains("hello") || message.contains("Hello")){

        }else if(message.contains("hello") || message.contains("Hello")){

        }
    }

    private void SendFirstMessage() {
        String message= "Hello Mr/Mrs "+Prevalent.currentOnlineUser.getFname()+", My name is Gideon, am Dr. Josh's assistant, the doctor is not around but i can help you in any way i can.";
        mimicOtherMessage(message);
    }


    private void sendMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, true, false);
        mAdapter.add(chatMessage);

        //mimicOtherMessage(message);
    }
    private void mimicOtherMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, false, false);
        mAdapter.add(chatMessage);
    }
    private void sendMessage() {
        ChatMessage chatMessage = new ChatMessage(null, true, true);
        mAdapter.add(chatMessage);

        mimicOtherMessage();
    }

    private void mimicOtherMessage() {
        ChatMessage chatMessage = new ChatMessage(null, false, true);
        mAdapter.add(chatMessage);
    }

    //check SD card availability
    public static boolean isSDCARDAvailable(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)? true :false;
    }
    //copying the file
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

    //Request and response of user and the bot
    public static void mainFunction (String[] args) {
        MagicBooleans.trace_mode = false;
        System.out.println("trace mode = " + MagicBooleans.trace_mode);
        Graphmaster.enableShortCuts = true;
        Timer timer = new Timer();
        String request = "Hello.";
        String response = chat.multisentenceRespond(request);

        System.out.println("Human: "+request);
        System.out.println("Robot: " + response);
    }
}
