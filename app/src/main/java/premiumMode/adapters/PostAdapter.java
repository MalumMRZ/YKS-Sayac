package premiumMode.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.yusufmirza.theyksproject.databinding.FragmentGlobalChatRowBinding;

import java.util.ArrayList;

import premiumMode.HelperClasses.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {
      private FragmentGlobalChatRowBinding fragmentGlobalChatRowBinding;
      private ArrayList<Post> posts;


      public PostAdapter(ArrayList<Post> posts) {
          this.posts=posts;
      }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        fragmentGlobalChatRowBinding = FragmentGlobalChatRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);


        return new PostHolder(fragmentGlobalChatRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
         holder.fragmentGlobalChatRowBinding.expressionText.setText(posts.get(position).getComment());
         holder.fragmentGlobalChatRowBinding.textTimeDate.setText(posts.get(position).getTimestamp().toString());
         holder.fragmentGlobalChatRowBinding.textUserName.setText(posts.get(position).getName());
         holder.fragmentGlobalChatRowBinding.titleText.setText(posts.get(position).getTitle());
         Picasso.get().load(posts.get(position).getURL())
                .fit().
                into(fragmentGlobalChatRowBinding.imageView);

    }



    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder{

        FragmentGlobalChatRowBinding fragmentGlobalChatRowBinding;

        public PostHolder(@NonNull FragmentGlobalChatRowBinding fragmentGlobalChatRowBinding ) {
            super(fragmentGlobalChatRowBinding.getRoot());
            this.fragmentGlobalChatRowBinding=fragmentGlobalChatRowBinding;
        }
    }



}
