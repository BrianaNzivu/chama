package com.example.m_chama.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_chama.R;

import java.util.ArrayList;
import java.util.List;



public class Adapter extends RecyclerView.Adapter<Adapter.DetailsViewHolder>
{
    List<User> userList=new ArrayList<>();
    Context mContext;

    public Adapter(Context context)
    {
        mContext=context;
    }


//Attaching Adapter.
    @NonNull
    @Override
    public Adapter.DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        Context context=viewGroup.getContext();
        int id= R.layout.adapter;

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately=false;

        View view=layoutInflater.inflate(id,viewGroup,shouldAttachToParentImmediately);

        return new DetailsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.DetailsViewHolder detailsViewHolder, int position)
    {
        User user = userList.get(position);
        detailsViewHolder.email.setText(user.getEmail());
        detailsViewHolder.name.setText(user.getName());
        detailsViewHolder.phone.setText(user.getNumber());
    }


    @Override
    public int getItemCount()

    {
        return userList.size();
    }

    public void setUserList(List<User>ourUser)
    {
        this.userList=ourUser;
        notifyDataSetChanged();
    }

//Displaying Values.
    class DetailsViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        TextView email;
        TextView phone;


        public DetailsViewHolder(@NonNull View itemView)
        {
            super(itemView);

            name=itemView.findViewById(R.id.jname);
            email=itemView.findViewById(R.id.jemail);
            phone=itemView.findViewById(R.id.jphone);
        }
    }
}
