package com.example.m_chama.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class chat implements Parcelable
{
    String name;
    String message;
    String time;


    public chat(String message, String s)
    {

    }

    public chat()
    {//Empty constructor.
    }


    public chat(String name, String message, String time)
    {
        this.name = name;
        this.message = message;
        this.time = time;
    }


    protected chat(Parcel in)
    {
        name = in.readString();
        message = in.readString();
        time = in.readString();
    }

    public static final Creator<chat> CREATOR = new Creator<chat>()
    {
        @Override
        public chat createFromParcel(Parcel in) {
            return new chat(in);
        }

        @Override
        public chat[] newArray(int size) {
            return new chat[size];
        }
    };

    public String getName()
    {

        return name;
    }

    public String getMessage()
    {

        return message;

    }

    public String getTime()
    {

        return time;

    }

    public void setName(String name)
    {

        this.name = name;

    }

    public void setMessage(String message)
    {

        this.message = message;

    }

    public void setTime(String time) {

        this.time = time;

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
        dest.writeString(message);
        dest.writeString(time);
    }

    public void getValue(Class<chat> chatClass)
    {
    }
}


