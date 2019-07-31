package com.example.m_chama.Model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TransactionViewModel extends ViewModel
{

//Getting reference from my Firebase Databasse for Fragment Transaction.
    private static final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    private final FirebaseLiveData liveData = new FirebaseLiveData(ref);

    @NonNull
    public FirebaseLiveData getDataSnapshotLiveData()
    {
        return liveData;
    }

    public FirebaseLiveData getLiveData()
    {
        return liveData;
    }
}



