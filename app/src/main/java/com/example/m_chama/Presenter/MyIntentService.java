package com.example.m_chama.Presenter;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.example.m_chama.Model.User;
import com.example.m_chama.Model.chat;

public class MyIntentService extends IntentService {


    public MyIntentService()
    {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent)

    {
//Connecting to Background class.
        Log.d("On handler","Its starting");

        Background background=new Background(this);
        String action=intent.getAction();
//Writing to Firebase Database for Transaction Fragment.
        if(Background.register.equals(action))
        {
            User myuser = new User();
             myuser = intent.getParcelableExtra("Users");
            background.writeToFirebase(myuser);
        }
//Writing to Firebase Database for Conversation Fragment.
        if(Background.addChat.equals(action))
        {
            chat mychat = new chat();
            mychat = intent.getParcelableExtra("chat");
            background.writeChatFIrebase(mychat);
        }
    }
}
