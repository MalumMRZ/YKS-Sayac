package premiumMode.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.yusufmirza.theyksproject.R;

import java.util.ArrayList;

import premiumMode.HelperClasses.RoomMessages;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ChatRoomViewHolder>{

    ArrayList<RoomMessages> roomMessages;

    public ChatRoomAdapter(ArrayList<RoomMessages> roomMessages){
        this.roomMessages=roomMessages;
    }


    @NonNull
    @Override
    public ChatRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_room_row,parent,false);
       return new ChatRoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomViewHolder holder, int position) {
       holder.textSender.setText(roomMessages.get(position).getSender());
       holder.textMessage.setText(roomMessages.get(position).getMessage());
       holder.textTime.setText(roomMessages.get(position).getTimestamp().toString());


    }

    @Override
    public int getItemCount() {
        return roomMessages.size();
    }

    public static class ChatRoomViewHolder extends RecyclerView.ViewHolder{

        TextView textSender,textMessage,textTime;

        public ChatRoomViewHolder(@NonNull View itemView) {
            super(itemView);
            textSender = itemView.findViewById(R.id.textSender);
            textMessage = itemView.findViewById(R.id.textMessage);
            textTime = itemView.findViewById(R.id.textTime);


        }
    }
    }



