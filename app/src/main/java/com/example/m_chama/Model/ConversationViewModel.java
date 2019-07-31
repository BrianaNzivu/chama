package com.example.m_chama.Model;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ConversationViewModel extends ViewModel {


//Getting reference from my Firebase Databasse for Conversation.
    private static final DatabaseReference mref =FirebaseDatabase.getInstance().getReference();

    private final FirebaseLiveData liveData = new FirebaseLiveData(mref);

    @NonNull
    public FirebaseLiveData getDataSnapshotLiveData()
    {
        return liveData;
    }

    public FirebaseLiveData getLiveData()

    {
        return liveData;
    }

    public class FirebaseLiveData
    {

        public FirebaseLiveData(DatabaseReference mref)
        {
            //reading from Firebase.
        }

        public void observe(View.OnClickListener onClickListener, Observer<DataSnapshot> dataSnapshotObserver)
        {
        }
    }
}


