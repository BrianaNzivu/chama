package com.example.m_chama.View;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.m_chama.R;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    private Button mbutton;
    private TextView pname;
    private ImageView imgvw;
    private String value;
    private Context ncontext;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;


    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        ncontext = getActivity().getApplicationContext();
        pname=(TextView) view.findViewById(R.id.pname);

        Log.d("name","name being seen");


//Getting the username

        Bundle bundle=new Bundle();
        Bundle myBundle = getArguments();
        final String name =myBundle.getString("Name");
        pname.setText(name);

        SharedPreferences namePreference = ncontext.getSharedPreferences("prefs",0);
        final SharedPreferences.Editor editor= namePreference.edit();

//Setting Onclick Listener on the text view
        pname.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
//Creating an Alert Dialogbox.
                Log.d("alert", "alert being seen");
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                    alert.setTitle("Edit Name");
                    alert.setMessage("Enter your New Name ");

 // Set an EditText view to get user input
                    final EditText input = new EditText(getActivity().getApplicationContext());
                    alert.setView(input);

                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int whichButton)
                        {
                            value = input.getText().toString().trim();
                            // get user input and set it to result
                            // edit text
                            pname.setText(input.getText());
 // Creating the shared preferences for the name

                            Log.d("Pref", "Shared prefrence being captured");

                            editor.putString("value", value);
                            editor.commit();

                        }

                        private SharedPreferences getSharedPreferences(String prefs, int i)
                        {

                            return null;

                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int whichButton)
                        {
                            // Canceled.
                        }
                    });

                    alert.show();

            }

        });

        Log.d("ProfileFragment", "Fragment created");

        mbutton = (Button) view.findViewById(R.id.editPhoto);
        imgvw = (ImageView) view.findViewById(R.id.profilePhoto);


        mbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                requestRead();
                Log.d("Button", "Button has been clicked");
            }
        });

 // Set title bar

        ((home) getActivity())
                .getSupportActionBar().setTitle("Profile");


 // Inflate the layout for this fragment
        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        try
        {
            if (requestCode == 1)
            {

                if (resultCode == RESULT_OK)
                {
                    final Uri imageUri = data.getData();


                    imgvw.setImageURI(imageUri);

// Creating the shared preferences for the image Url

                    String toStroreString = imageUri.toString();
                    SharedPreferences profileImage = getActivity().getSharedPreferences("prefs", 0);
                    SharedPreferences.Editor editor = profileImage.edit();
                    editor.putString("profileImage", toStroreString);
                    editor.commit();
                }
            }
        }
        catch (Exception e)
        {

        }
    }


    public void startingUpTheCameraPicker()
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }

    public void requestRead()
    {
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
// In this code the user is requesting for permissions
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else
            {
// In this case the permissions have been offered already
            startingUpTheCameraPicker();
            }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
// In this case the permission has been granted and the user can start the camera picker
                startingUpTheCameraPicker();
            } else
                {
// Permission Denied
                Toast.makeText(getActivity().getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void onResume()
    {
        SharedPreferences settings=getActivity().getSharedPreferences("prefs",0);


        if (settings !=null)
        {
            String value = settings.getString("value"," ");
            pname.setText(value);


        }else{

        }

        super.onResume();

// Getting the data of the sharedPreferences


        SharedPreferences imageme = getActivity().getSharedPreferences("prefs", 0);
        String image = imageme.getString("profileImage", " ");


        if (!image.equals(" "))
        {
            Uri imageUri = Uri.parse(image);
            imgvw.setImageURI(imageUri);

            Toast.makeText(getActivity().getApplicationContext(), "", Toast.LENGTH_LONG).show();
        } else
            {
            imgvw.setImageResource(R.drawable.back);

            }
    }

}

