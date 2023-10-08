package premiumMode;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yusufmirza.theyksproject.databinding.ChatroomUsersBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import premiumMode.HelperClasses.Users;
import premiumMode.adapters.ChatRoomAdapter;
import premiumMode.adapters.ChatUsersAdapter;

public class ChatRoomUsers extends AppCompatActivity {

    ChatroomUsersBinding chatroomUsersBinding;

    ArrayList<Users> usersArrayList;

    String destination;
    String userRole;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatroomUsersBinding = ChatroomUsersBinding.inflate(getLayoutInflater());
        setContentView(chatroomUsersBinding.getRoot());

        usersArrayList = new ArrayList<>();

        destination = getIntent().getStringExtra("destination");

        firestore.collection("chatrooms").document(destination).collection("users")
                .document("userList").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        HashMap<String,Object> listOfUsers = (HashMap<String, Object>) documentSnapshot.get("users");

                        if (listOfUsers!=null){

                            for (String listKey : listOfUsers.keySet()){

                                HashMap<String,Object> user = (HashMap<String, Object>) listOfUsers.get(listKey);

                                if (user!=null){

                                    if (Objects.equals(user.get("ID"), firebaseUser.getUid())){
                                        String uID = (String) user.get("ID");
                                        String name = (String) user.get("name");
                                        String role = (String) user.get("role");

                                        name = name+" (siz)";

                                        userRole = role;

                                        usersArrayList.add(new Users(name,role,uID));

                                    } else {
                                        String uID = (String) user.get("ID");
                                        String name = (String) user.get("name");
                                        String role = (String) user.get("role");

                                        Log.d("User","ID" +  uID+"name"+name+"role"+role);

                                        usersArrayList.add(new Users(name,role,uID));
                                    }

                                }

                            }

                            if (usersArrayList.size()!=0){
                                chatroomUsersBinding.usersChatList.setAdapter(new ChatUsersAdapter(usersArrayList,ChatRoomUsers.this,userRole,destination));
                                chatroomUsersBinding.usersChatList.setLayoutManager(new LinearLayoutManager(ChatRoomUsers.this));
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChatRoomUsers.this, "Yüklenirken hata", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}
