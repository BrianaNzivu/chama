package com.example.m_chama.Model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_chama.R;

import java.util.ArrayList;
import java.util.List;


public class ChatAdapter extends RecyclerView.Adapter {


    List<chat> chatList = new ArrayList<>();
    Context mContext;
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private String username;


    public ChatAdapter(Context context,String username)
    {
        this.mContext = context;
        this.username = username;
    }


    @Override
    public int getItemViewType(int position)
    {
        chat currentChat = chatList.get(position);

//Establishing received and sent chats.
        if (currentChat.getName() != null && !currentChat.getName().isEmpty())
        {
            if (currentChat.getName().equals(username))
            {
                Log.d("SentMessages", "Sent messages retrieved");
                return 1;

            } else
                {

                Log.d("ReceivedMessages", "Received messages received");
                return 2;

                }
        }

        return 0;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        if (viewType == 1)

        {
            Context context = parent.getContext();
// This is for getting the id of the layout with the data of received messages.
            int id = R.layout.received;
//This is for inflating of the layout
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            boolean shouldAttachToParentImmediately = false;

            View view = layoutInflater.inflate(id, parent, shouldAttachToParentImmediately);
            return new ChatViewHolderMessageReceived(view);
        }

        if (viewType == 2)

        {
            Context context = parent.getContext();
// This is for getting the id of the layout with the data of sent messages.
            int id = R.layout.sent;
// This is for inflating of the layout
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            boolean shouldAttachToParentImmediately = false;

            View view = layoutInflater.inflate(id, parent, shouldAttachToParentImmediately);
            return new ChatViewHolderMessageSent(view);
        }

        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i)
    {
//This is for showing the current chat sent.
        chat currentChat = chatList.get(i);
        int holderType = viewHolder.getItemViewType();

        if (holderType == VIEW_TYPE_MESSAGE_RECEIVED)
        {
            ((ChatViewHolderMessageSent) viewHolder).bind(currentChat);
        }

        if (holderType == VIEW_TYPE_MESSAGE_SENT)
        {
            ((ChatViewHolderMessageReceived) viewHolder).bind(currentChat);
        }
    }


    @Override
    public int getItemCount()
    {
        return chatList.size();
    }

    public void setChatList(List<chat> chats)
    {
        chatList = chats;
        notifyDataSetChanged();
    }


//ViewHolder for Received messages.
    class ChatViewHolderMessageReceived extends RecyclerView.ViewHolder
    {
        TextView userMessage;
        TextView textMessageTime;

//Fetching strings.
        public ChatViewHolderMessageReceived(View view)
        {
            super(view);

            userMessage = view.findViewById(R.id.textsent);
            textMessageTime = view.findViewById(R.id.rtime);
        }

        void bind(chat chat)
        {
            userMessage.setText(chat.getMessage());
            textMessageTime.setText(chat.getTime());
        }
    }
//ViewHolder for Sent messages.
    class ChatViewHolderMessageSent extends RecyclerView.ViewHolder
    {
        TextView Message, Name, Time;

        public ChatViewHolderMessageSent(View view)
        {
            super(view);
            Message = view.findViewById(R.id.rmessage);
            Name = view.findViewById(R.id.mname);
            Time = view.findViewById(R.id.mtime);
        }

        void bind(chat chat)
        {
            Message.setText(chat.getMessage());
            Name.setText(chat.getName());
            Time.setText(chat.getTime());
        }
    }
}


