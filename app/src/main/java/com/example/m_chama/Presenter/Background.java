package com.example.m_chama.Presenter;


import android.content.Context;

import com.example.m_chama.Model.User;
import com.example.m_chama.Model.chat;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//Background class
public class Background {

    private FirebaseDatabase mFirebase;
    private DatabaseReference mref;
    private Context mContext;
    public static final String register = "Add to database";
    public static final String addChat = "Add chat";


    public Background(Context context)
    {

    this.mContext=context;

    }
//Writing to Firebase a Transaction made from Transaction Fragment .
    public void writeToFirebase(User myUser)
    {
        mFirebase=FirebaseDatabase.getInstance();
        mref=mFirebase.getReference();
        mref.child("Database").child("transaction").push().setValue(myUser);
    }
//Writing to Firebase chats from Converation Fragment.
    public void writeChatFIrebase(chat myChat)
    {
        mFirebase=FirebaseDatabase.getInstance();
        mref=mFirebase.getReference();
        mref.child("Database").child("chat").push().setValue(myChat);
    }
}
