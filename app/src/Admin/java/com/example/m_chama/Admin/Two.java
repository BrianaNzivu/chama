package com.example.m_chama.Admin;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.m_chama.Model.User;
import com.example.m_chama.Presenter.Background;
import com.example.m_chama.Presenter.MyIntentService;
import com.example.m_chama.R;
import com.example.m_chama.View.Mchamawidget;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Two extends AppCompatActivity {

    EditText mname;
    EditText mdate;
    EditText mnumber;
    Button mbutton;
    String name;
    String email;
    String number;
    private FirebaseDatabase mFirebase;
    private DatabaseReference mref;
    private ProgressBar nprogress;
    private Bundle appWidget;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        mname=(EditText)  findViewById(R.id.Name);
        mdate=(EditText)  findViewById(R.id.Email);
        mnumber=(EditText)  findViewById(R.id.Phone);
        mbutton=(Button)  findViewById(R.id.submit);
        nprogress = (ProgressBar) findViewById(R.id.myprogressbar);


//Setting OnClick Listener to button.
        mbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("dialog","Strating my progrees dialog");
                nprogress=new ProgressBar(Two.this);



                name = mname.getText().toString();
                email=mdate.getText().toString();
                number=mnumber.getText().toString();



                appWidget = new Bundle();

                appWidget.putString("Name",name);
                appWidget.putString("Date",email);
                appWidget.putString("Number",number);



                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplication().getApplicationContext());
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(getApplication().getApplicationContext(), Mchamawidget.class));
                Mchamawidget.wiringUpTheWidget(getApplication().getApplicationContext(),appWidgetManager,appWidgetIds,appWidget);


                final User myUser=new User(name,email,number);

                nprogress.setVisibility(View.VISIBLE);

                Intent intent=new Intent(Two.this, MyIntentService.class);
                intent.setAction(Background.register);
                intent.putExtra("Users",myUser);

                nprogress.setVisibility(View.INVISIBLE);

                Log.d("button","button clicked");

                startService(intent);

            }
        });
    }
}
