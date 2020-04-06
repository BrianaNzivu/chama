package com.example.m_chama.View;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.androidstudy.daraja.Daraja;
import com.androidstudy.daraja.DarajaListener;
import com.androidstudy.daraja.model.AccessToken;
import com.androidstudy.daraja.model.LNMExpress;
import com.androidstudy.daraja.model.LNMResult;
import com.androidstudy.daraja.util.TransactionType;
import com.example.m_chama.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {

    public static final String YOUTUBE_API_KEY = "AIzaSyBsYbtK5OHRiXYq3KlISGeK6HFCnb_x3I4";
    public TextView nemail;
    public TextView mname;
    private String value;
    public Context ncontext;
    Daraja daraja;
    public String anumber;
    private Button mybutton;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
//Setting Title bar.
        ((Home) getActivity())
                .getSupportActionBar().setTitle("Home");


        nemail=(TextView) view.findViewById(R.id.myemail);
        mname=(TextView)view.findViewById(R.id.myname);

        Log.d("Name","getting name");

//Getting Bundle.
       Bundle myBundle = getArguments();
       String email = myBundle.getString("email");
       nemail.setText(email);
       String name =myBundle.getString("name");
       mname.setText(name);

// Getting the data of the sharedPreferences

        ncontext = getActivity().getApplicationContext();

        SharedPreferences settings=getActivity().getSharedPreferences("prefs",0);

//Using Daraja API in order to set M-Pesa.
        mybutton=(Button)view.findViewById(R.id.mpesa);
        daraja = Daraja.with("Zc98sT35Czsl5QgC0E6L8BrEcvGycR46", "Vq6BE83VoMbbojNs", new DarajaListener<AccessToken>()
        {
            @Override
            public void onResult(@NonNull AccessToken accessToken)
            {
                Log.d("DarajaCreation", "The daraja class has beeen created");
                Log.i("AccessToken", accessToken.getAccess_token());
            }

            @Override
            public void onError(String error)
            {
                Log.d("DarajaCreation", "The daraja class has not been created,an error has been encountered" + error);
                Log.e("AccessToken", error);
            }

        });

        mybutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LNMExpress lnmExpress = new LNMExpress(
                        "174379",
                        "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",  //https://developer.safaricom.co.ke/test_credentials
                        TransactionType.CustomerPayBillOnline,
                        "100",
                        "254798436887",
                        "174379",
                        anumber,
                        "http://mycallbackurl.com/checkout.php",
                        "001ABC",
                        "Goods Payment"
                );

                daraja.requestMPESAExpress(lnmExpress,
                    new DarajaListener<LNMResult>()
                        {
                            @Override
                            public void onResult(@NonNull LNMResult lnmResult)
                            {
                                Log.d("SendingMoney", "Money has been sent");
                            }

                            @Override
                            public void onError(String error)
                            {
                                Log.d("SendingMoney", "Money has not been sent " + error);
                            }
                        });
            }
        });


        if (settings !=null)
        {
            String value = settings.getString("value"," ");
            mname.setText(value);


        }else
            {

        }
// Inflate the layout for this fragment
        return view;
    }

}
