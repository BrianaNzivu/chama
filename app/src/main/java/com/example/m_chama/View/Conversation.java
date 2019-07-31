package com.example.m_chama.View;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_chama.Model.ChatAdapter;
import com.example.m_chama.Model.ConversationViewModel;
import com.example.m_chama.Model.FirebaseLiveData;
import com.example.m_chama.Model.chat;
import com.example.m_chama.Presenter.Background;
import com.example.m_chama.Presenter.MyIntentService;
import com.example.m_chama.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Conversation extends Fragment {


    private static final Object JOB_SCHEDULER_SERVICE= 0;
    public FirebaseDatabase mFirebaseDatabase;
    public DatabaseReference mref;
    public ChatAdapter chatAdapter;
    private RecyclerView chatRecyclerView;
    private  List<chat>chatList=new ArrayList<>();
    private Button nbutton;
    private EditText nmessage;
    private String value;
    public Context ncontext;
    public ConversationViewModel viewModel;
    public FirebaseLiveData firebaseLiveData;


    public Conversation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

// Set title bar
        ((home) getActivity())
                .getSupportActionBar().setTitle("Conversation");

//Getting name bundle
        Bundle b = getArguments();
        final String s = b.getString("username");

 // Inflate the layout for this fragment

        final View view =inflater.inflate(R.layout.fragment_conversation, container, false);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mref = mFirebaseDatabase.getReference().child("Database").child("chat");
        chatAdapter = new ChatAdapter(getActivity().getApplicationContext(),s);
        chatRecyclerView = view.findViewById(R.id.chatRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL, false);

//Attaching my Job Service.
        JobScheduler jobScheduler = (JobScheduler)getActivity().getApplicationContext()
                .getSystemService(String.valueOf(0));

        ComponentName componentName = new ComponentName(getActivity().getApplicationContext(),JobService.class);

        JobInfo jobInfoObj = new JobInfo.Builder(0, componentName)
                .setPeriodic(50000000)
                .setRequiresBatteryNotLow(true)
                .build();

        jobScheduler.schedule(jobInfoObj);


// Set the layoutManager of the recyclerView

        layoutManager.setStackFromEnd(true);
        chatRecyclerView.setLayoutManager(layoutManager);

//chatRecyclerView.setHasFixedSize(true);

        chatRecyclerView.setAdapter(chatAdapter);

//Setting onCLick listener for button for sending messgaes.
        nbutton=(Button) view.findViewById(R.id.send);
        nbutton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

               nmessage=(EditText)view.findViewById(R.id.convo);
               String message = nmessage.getText().toString();

               Log.d("Message","Message sent " + message);

//This returns the current time in string format
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    String stringTime = sdf.format(new Date());


                final chat myChat=new chat(message,s,stringTime);


//View Model for Conversation Fragment.
                viewModel = ViewModelProviders.of(getActivity()).get(ConversationViewModel.class);

                ConversationViewModel.FirebaseLiveData firebaseLiveData= viewModel.getDataSnapshotLiveData();
                firebaseLiveData.observe(this, new Observer<DataSnapshot>()
                {
                    @Override
                    public void onChanged(@Nullable DataSnapshot dataSnapshot)
                    {
                        DataSnapshot eventsDetails = dataSnapshot.child("Database").child("chat");
                        Boolean exist = eventsDetails.exists();
                        Log.d("Confirming", "This confirms that the datasnapshot exists " + exist);
                        Iterable<DataSnapshot> eventsDatasnapshot = eventsDetails.getChildren();
                        for (DataSnapshot eventsList : eventsDatasnapshot)
                        {
                            chat mychat = new chat();
                            mychat=eventsList.getValue(chat.class);
                        }
                    }
                 });


//Linking to my intent service.

                   Intent intent=new Intent(getActivity().getApplicationContext(), MyIntentService.class);

                   intent.setAction(Background.addChat);
                   intent.putExtra("chat",myChat);

                   Log.d("button","button clicked");

                    getActivity().startService(intent);

            }

        });

 // attaching the childEventListener

        mref.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {

//  This allows it to be realtime adding to the screen
                chat chat = dataSnapshot.getValue(chat.class);

                chatList.add(chat);
                if (chatList.size() != 0)
                {
                    chatAdapter.setChatList(chatList);
                    chatRecyclerView.scrollToPosition(chatAdapter.getItemCount() + 1);
                    Log.d("AddingChats", "Chats have been added to the UI");

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        Log.d("return","Value is being returned");

        return view;
    }

    public void onResume()
    {

        super.onResume();

    }

}





