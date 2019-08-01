package com.example.m_chama.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.example.m_chama.R;
import com.google.android.material.navigation.NavigationView;


public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private View mCoordinate;
    private ImageView mImage, nImage;
    private TextView nText, nEmail;
    private String Email, Name,Amount,Date;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
//Setting Name and EMail on Toolbar.
        Email = intent.getStringExtra("aEmail");
        Name = intent.getStringExtra("aname");
        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);

        Log.d("HomeName","Name of user " + Name);
//New Bundle for FragmentHome Java Class.
        Bundle args = new Bundle();
        args.putString("email", Email);
        args.putString("name",Name);

        FragmentHome fragment = new FragmentHome();
        fragment.setArguments(args);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_layout, fragment);
        fragmentTransaction.commit();

         //inflate header layout


        NavigationView Navigationview = (NavigationView) findViewById(R.id.nav_view);

        View navView = Navigationview.inflateHeaderView(R.layout.nav_header_home);


//        mImage = (ImageView) navView.findViewById(R.id.navpic);

        nText = (TextView) navView.findViewById(R.id.name);
        nEmail = (TextView) navView.findViewById(R.id.email);
        nEmail.setText(Email);
        nText.setText(Name);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        mCoordinate = findViewById(R.id.Layout);


    }



    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        } else
            {
            super.onBackPressed();
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the FragmentHome/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

            if (id == R.id.nav_home)
            {


                Bundle args = new Bundle();
                args.putString("email", Email);
                args.putString("name",Name);

                FragmentHome fragment = new FragmentHome();
                fragment.setArguments(args);
                FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_layout, fragment);
                fragmentTransaction.commit();


            } else if (id == R.id.nav_conversation)
            {

                Bundle args = new Bundle();
                args.putString("username", Name);

                Log.d("COnversationUsername","the user name passed" + Name);
                Conversation newFragment = new Conversation();
                newFragment.setArguments(args);

                FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_layout, newFragment);
                fragmentTransaction.commit();


            } else if (id == R.id.nav_profile)
            {

                Bundle args = new Bundle();
                args.putString("Name",Name);
                Profile fragment = new Profile();
                fragment.setArguments(args);
                FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_layout, fragment);
                fragmentTransaction.commit();


            } else if (id == R.id.nav_transactiontwo)
            {

                Bundle args=new Bundle();
                args.putString("Name",Name);
                args.putString("Date",Date);
                args.putString("Amount",Amount);

                Transaction fragment = new Transaction();
                FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_layout, fragment);
                fragmentTransaction.commit();


            } else if (id == R.id.nav_send)
            {

            }

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;



    }


}




