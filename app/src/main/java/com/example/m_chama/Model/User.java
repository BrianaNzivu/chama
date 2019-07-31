package com.example.m_chama.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User implements Parcelable
{
    String name;
    String email;
    String number;

    public User(){
//empty constructor
    }


    public User(String name ,String email ,String number)
    {
        this.name=name;
        this.email=email;
        this.number=number;
    }

    protected User(Parcel in)
    {
        name = in.readString();
        email = in.readString();
        number = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>()
    {
        @Override
        public User createFromParcel(Parcel in)
        {
            return new User(in);
        }

        @Override
        public User[] newArray(int size)
        {
            return new User[size];
        }
    };

    public String getName()
    {

        return name;
    }

    public String getEmail()
    {

        return email;

    }

    public String getNumber()
    {

        return number;

    }

    public void  setName(String name)
    {

        this.name=name;

    }

    public  void  setEmail(String email)
    {

        this.email=email;

    }

    public void setNumber(String number)
    {

        this.number = number;

    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(number);
    }
}
