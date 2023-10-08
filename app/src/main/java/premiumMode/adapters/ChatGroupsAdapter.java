package premiumMode.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.yusufmirza.theyksproject.R;

import java.util.ArrayList;

import premiumMode.ChatRoom;
import premiumMode.HelperClasses.GroupPreView;

public class ChatGroupsAdapter extends RecyclerView.Adapter<ChatGroupsAdapter.ChatGroupViewHolder>{

    ArrayList<GroupPreView> groupPreViews;

    public ChatGroupsAdapter(ArrayList<GroupPreView> groupPreViews){
        this.groupPreViews=groupPreViews;
    }


    @NonNull
    @Override
    public ChatGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_group_row,parent,false);
       return new ChatGroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatGroupViewHolder holder, int position) {
       holder.textTitle.setText(groupPreViews.get(position).getTitle());
       holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), ChatRoom.class);
                intent.putExtra("destination",groupPreViews.get(holder.getAdapterPosition()).getKey());
                holder.itemView.getContext().startActivity(intent);
           }
       });

    }

    @Override
    public int getItemCount() {
        return groupPreViews.size();
    }

    public static class ChatGroupViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle;
        ConstraintLayout constraintLayout;
        public ChatGroupViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle=  itemView.findViewById(R.id.chatGroupTitle);
            constraintLayout = itemView.findViewById(R.id.constraint_row);
        }
    }


}
