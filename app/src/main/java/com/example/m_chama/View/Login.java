package com.example.m_chama.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.m_chama.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener{


    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp;
    private  View view;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//getting firebase auth object
        firebaseAuth= FirebaseAuth.getInstance();
 //if the objects start the profile activitytcurrentuser method is not null
 //means user is already logged in
        if(firebaseAuth.getCurrentUser() != null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(), Home.class));
        }
//initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        textViewSignUp=(TextView) findViewById(R.id.textViewSignUp);
        progressDialog = new ProgressDialog(this);

        buttonSignIn.setOnClickListener(this);
    }

//method for user login
    private void userLogin() {
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

//checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }


        //if the email and password are not empty
//displaying a progress dialog
        progressDialog.setMessage("Just give us a second...");
        progressDialog.show();

//logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

//if the task is successfull.
                        if (task.isSuccessful()) {
                            finish();
                            Intent intent = new Intent(Login.this, Home.class);
                            intent.putExtra("aEmail", email);
                            startActivity(intent);
                        }
                    }
                });
    }


    @Override
    public void onClick(View view) {

    }
    public void gotoregister(View view)
     {
        startActivity(new Intent(this, Register.class));
    }
}
