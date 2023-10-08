package premiumMode.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;
import com.yusufmirza.theyksproject.R;
import com.yusufmirza.theyksproject.databinding.FragmentChatBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import premiumMode.HelperClasses.GroupPreView;
import premiumMode.adapters.ChatGroupsAdapter;


public class ChatFragment extends Fragment {

    Context context;
    ArrayList<GroupPreView> groupPreViews = new ArrayList<>();
    FragmentChatBinding fragmentChatBinding;
    AlertDialog.Builder builder;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    FirebaseUser firebaseUser;

    public ChatFragment(Context context) {
        this.context = context;

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentChatBinding = FragmentChatBinding.inflate(inflater);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(fragmentChatBinding.toolBar);


        fragmentChatBinding.toolBarTitle.setText("Sohbet Odaları");

        setHasOptionsMenu(true);

        return fragmentChatBinding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        firestore.collection("users").document(firebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                Map<String,Object> membersMap = (Map<String, Object>) documentSnapshot.get("members");

                if (membersMap!=null){
                    for (String key : membersMap.keySet()){
                        Map<String,Object> chatMap = (Map<String, Object>) membersMap.get(key);

                        String title = (String) chatMap.get("Name");
                        String role = (String) chatMap.get("Role");

                        groupPreViews.add(new GroupPreView(title,key,role));
                    }

                    if (groupPreViews != null) { //Başlangıçta kontrol edilir.
                        fragmentChatBinding.chatGroup.setLayoutManager(new LinearLayoutManager(context));
                        fragmentChatBinding.chatGroup.setAdapter(new ChatGroupsAdapter(groupPreViews));
                    }
                }
                
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


        fragmentChatBinding.createNewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog();
                builder.create();
                builder.show();
            }
        });



    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.chat_menu, menu); // Fragment'a özgü menüyü ekleyin
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


            if (item.getItemId() == R.id.addMember) {
                Toast.makeText(context, "Deneme 1", Toast.LENGTH_SHORT).show();
            }

        return super.onContextItemSelected(item);
        }



    public void setAlertDialog() {
        builder = new AlertDialog.Builder(context);
        builder.setTitle("Grup Oluştur");
        EditText editText = new EditText(context);
        editText.setHint("Grup başlığı giriniz");
        builder.setView(editText);

        builder.setPositiveButton("Onayla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String groupName = editText.getText().toString();

                WriteBatch writeBatch = firestore.batch();

                DocumentReference documentReference = firestore.collection("chatrooms").document();
                DocumentReference usersDocument = documentReference.collection("users").document("userList");
                DocumentReference messagesDocument = documentReference.collection("messages").document();
                DocumentReference adminDocument = firestore.collection("users").document(firebaseUser.getUid());

                Map<String, Object> groupsData = new HashMap<>();
                Map<String,Object> chatsMap = new HashMap<>();
                Map<String, Object> chatMap = new HashMap<>();
                chatMap.put("Name",groupName);
                chatMap.put("Role","admin");
                chatsMap.put(documentReference.getId(),chatMap);
                groupsData.put("members",chatsMap);



                Map<String, Object> chatData = new HashMap<>();
                chatData.put("name", groupName);
                chatData.put("documentID", documentReference.getId());

                Map<String, Object> userData = new HashMap<>();
                Map<String,Object> newUser = new HashMap<>();
                Map<String,Object> userInfo = new HashMap<>();
                userInfo.put("name",firebaseUser.getDisplayName());
                userInfo.put("ID",firebaseUser.getUid());
                userInfo.put("role","owner");
                newUser.put(firebaseUser.getUid(),userInfo);
                userData.put("users",newUser);



                writeBatch.set(documentReference, chatData);
                writeBatch.set(messagesDocument, new HashMap<>());
                writeBatch.set(usersDocument, userData);
                writeBatch.set(adminDocument, groupsData, SetOptions.merge());

                writeBatch.commit().addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failure on Creating group", Toast.LENGTH_SHORT).show();
                    }
                });


                dialogInterface.dismiss();
            }
        }).setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "İptal et", Toast.LENGTH_SHORT).show();
                dialogInterface.cancel();
            }
        });
    }


}
