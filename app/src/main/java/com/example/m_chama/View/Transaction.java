package com.example.m_chama.View;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.m_chama.Model.Adapter;
import com.example.m_chama.Model.FirebaseLiveData;
import com.example.m_chama.Model.TransactionViewModel;
import com.example.m_chama.Model.User;
import com.example.m_chama.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Transaction extends Fragment implements LoaderManager.LoaderCallbacks<List<com.example.m_chama.Model.User>> {

    private static final int OPERATION_SEARCH_LOADER = 1;
    private RecyclerView mRecycle;
    private Adapter mAdapter;
    private Object User;
    final List<User> userListArray = new ArrayList<>();
    public TransactionViewModel viewModel;
    private static FirebaseLiveData firebaseLiveData;
    private Two appWidget;

    private Button aButton;

    public Transaction() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
//Inflating fragment layout.

        View view = inflater.inflate(R.layout.fragment_transactiontwo, container, false);

        ((Home) getActivity()).getSupportActionBar().setTitle("Transactions");
        FloatingActionButton tButton;


        tButton = (FloatingActionButton) view.findViewById(R.id.next);

        tButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity().getApplicationContext(), Two.class);
                startActivity(intent);

            }
        });

        mRecycle = (RecyclerView) view.findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL, false);
        mRecycle.setLayoutManager(layoutManager);

        mAdapter = new Adapter(getActivity().getApplicationContext());

        mRecycle.setAdapter(mAdapter);

        //Create a Bundle called bundle

        Bundle bundle = new Bundle();


        LoaderManager loaderManager = getLoaderManager();

        // Get our Loader by calling getLoader and passing the ID we specified
        Loader<List> loader = loaderManager.getLoader(OPERATION_SEARCH_LOADER);

        // If the Loader was null, initialize it. Else, restart it.
        if (loader == null)
        {
            loaderManager.initLoader(OPERATION_SEARCH_LOADER, bundle, this).forceLoad();
            Log.d("Loader", "Loader has been initialized ");
        } else
        {
            loaderManager.restartLoader(OPERATION_SEARCH_LOADER, bundle, this).forceLoad();
            Log.d("Restart", "Loader has been restarted");
        }

//Creating ViewModel.
        viewModel = ViewModelProviders.of(getActivity()).get(TransactionViewModel.class);
        FirebaseLiveData firebaseLiveData= viewModel.getDataSnapshotLiveData();
        firebaseLiveData.observe(this, new Observer<DataSnapshot>()
        {
            @Override
            public void onChanged(DataSnapshot dataSnapshot)
            {

                DataSnapshot eventsDetails = dataSnapshot.child("database").child("events");
                Boolean exist = eventsDetails.exists();
                Log.d("Confirming", "This confirms that the datasnapshot exists " + exist);
                Iterable<DataSnapshot> eventsDatasnapshot = eventsDetails.getChildren();
                for (DataSnapshot eventsList : eventsDatasnapshot)
                {
                    Two two = new Two();
                    two = eventsList.getValue(Two.class);
                }

            }
        });


        Bundle appWidget;
        appWidget = new Bundle();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getActivity().getApplicationContext());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(getActivity().getApplicationContext(), Mchamawidget.class));
        Mchamawidget.wiringUpTheWidget(getActivity().getApplicationContext(),appWidgetManager,appWidgetIds,appWidget);

        Log.d("start", "starting list");

        return view;
    }

    @NonNull
    @Override
    public Loader<List<User>> onCreateLoader(int i, @Nullable Bundle bundle)
    {
        Log.d("Hi","just checking");
        return new AsyncTaskLoader<List<User>>(getActivity().getApplicationContext())

        {
            @Nullable
            @Override
            public List<User> loadInBackground()
            {

                final FirebaseDatabase database = FirebaseDatabase.getInstance();

                DatabaseReference ref = database.getReference().child("Database").child("transaction");

// Attach a listener to read the data at our posts reference

                ref.addValueEventListener(new ValueEventListener()

// Inflate the layout for this fragment

                {

                    @Override

                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                        Log.d("Reading", "Read all the records ");
                        Iterable<DataSnapshot> userlist = dataSnapshot.getChildren();

                        for (DataSnapshot onejob : userlist)
                        {
                            User myUser = onejob.getValue(User.class);
                            userListArray.add(myUser);
                        }



                        mAdapter.setUserList(userListArray);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {
                        Log.d("Error", "Database error " + databaseError.getDetails());
                    }
                });

                return userListArray;

            }
        };

    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<com.example.m_chama.Model.User>> loader, List<com.example.m_chama.Model.User> data) {

    }


    @Override
    public void onLoaderReset(@NonNull Loader<List<User>> loader) {

    }
}

