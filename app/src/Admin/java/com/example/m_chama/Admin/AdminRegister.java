package com.example.m_chama.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.m_chama.Model.User;
import com.example.m_chama.R;
import com.example.m_chama.View.Login;
import com.example.m_chama.View.Home;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminRegister extends AppCompatActivity {
    private EditText mNameField;
    private AutoCompleteTextView mRegionField;
    private EditText mPhoneField;
    private EditText mEmailField;
    private EditText mPasswordField;

    private Button mRegisterBtn;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;


    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mProgress = new ProgressDialog(this);
        mNameField = (EditText) findViewById(R.id.mNameField);
        mPhoneField = (EditText) findViewById(R.id.mPhoneField);
        mEmailField = (EditText) findViewById(R.id.mEmailField);
        mRegionField = (AutoCompleteTextView) findViewById(R.id.mRegionField);
        mPasswordField = (EditText) findViewById(R.id.mPasswordField);
        mRegisterBtn = (Button) findViewById(R.id.mRegisterBtn);


        mRegisterBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startRegister();
            }
        });

        String [] Region ={
                "Kenya" ,"Tanzania" ,"Uganda"
        };

        ArrayAdapter<String> adapter = new  ArrayAdapter<String>(this , android.R.layout.select_dialog_singlechoice,Region);

        mRegionField.setThreshold(1);
        mRegionField.setAdapter(adapter);

        mRegionField.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                {
                    String myRegion=String.valueOf(parent.getItemAtPosition(position));
                    Log.d("Country selected" , myRegion);
                }
            }
        });



    }



    private void startRegister()
    {
        final String name = mNameField.getText().toString().trim();
        final String phone = mPhoneField.getText().toString().trim();
        final String region = mRegionField.getText().toString().trim();
        final String email = mEmailField.getText().toString().trim();
        String password = mPasswordField.getText().toString().trim();


        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
        {
            //progress dialog//
            mProgress.setMessage("Just give us a second....");
            mProgress.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if (task.isSuccessful())
                    {
                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = mDatabase.child(user_id);
                        current_user_db.child("name").setValue(name);
                        current_user_db.child("phone").setValue(phone);
                        current_user_db.child("region").setValue(region);
                        current_user_db.child("image").setValue("default");

                        mProgress.dismiss();
                        Intent mainIntent = new Intent(AdminRegister.this, Home.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        mainIntent.putExtra("aEmail",email);
                        mainIntent.putExtra("aname",name);
                        mainIntent.putExtra("anumber",phone);

                        Log.d("NameREgister","the users name " + name);
                        startActivity(mainIntent);
                    }

                }
            });

        }
    }

    public void gotologin(View view)
    {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

}
