package premiumMode;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;
import com.google.firestore.v1.Write;
import com.yusufmirza.theyksproject.R;
import com.yusufmirza.theyksproject.databinding.ChatRoomLayoutBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import premiumMode.HelperClasses.RoomMessages;
import premiumMode.adapters.ChatRoomAdapter;
import premiumMode.database.DatabaseHelper;

public class ChatRoom extends AppCompatActivity {

    ChatRoomLayoutBinding chatRoomLayoutBinding;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    FirebaseUser firebaseUser;
    long lastMessageSecond;
    int lastMessageNanoSec;
    String destination;
    CollectionReference messagesReference;

    ArrayList<RoomMessages> roomMessagesArrayList;
    SharedPreferences sharedPreferences;
    AlertDialog.Builder addUserAlertDialog,leaveGroupDialog;
    long earlyMessageSecond = 0;

    DatabaseHelper database;



    public static String TAG = "ChatRoom";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Aktivite oluşturuldu");
        chatRoomLayoutBinding = ChatRoomLayoutBinding.inflate(getLayoutInflater());
        setContentView(chatRoomLayoutBinding.getRoot());

        Toolbar toolbar = chatRoomLayoutBinding.toolBar;
        setSupportActionBar(toolbar);

        database = new DatabaseHelper(ChatRoom.this);



        roomMessagesArrayList = new ArrayList<>();

        sharedPreferences = getSharedPreferences("lastMessage", MODE_PRIVATE);
        sharedPreferences = getSharedPreferences("lastMessageNano", MODE_PRIVATE);

        lastMessageSecond = sharedPreferences.getLong("lastMessage", 1);
        lastMessageNanoSec = sharedPreferences.getInt("lastMessageNano", 1);


        destination = getIntent().getStringExtra("destination");

        chatRoomLayoutBinding.toolBarTitle.setText(destination);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        messagesReference = firestore.collection("chatrooms").document(destination).collection("messages");

        //veri ekleme
        chatRoomLayoutBinding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> message = new HashMap<>();
                message.put("user", firebaseUser.getDisplayName());
                message.put("message", chatRoomLayoutBinding.chatText.getText().toString());
                FieldValue date = FieldValue.serverTimestamp();
                message.put("date", date);

                chatRoomLayoutBinding.chatText.getText().clear();




                firestore.collection("chatrooms").document(destination).collection("messages")
                        .add(message)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ChatRoom.this, "Failure", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


        firestore.collection("chatrooms").document(destination).collection("messages").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                Log.d(TAG, "onStart: Event başladı ");

                if (error != null) {
                    Toast.makeText(ChatRoom.this, "Error while loading!", Toast.LENGTH_SHORT).show();

                    return;
                }

                if (value != null && !value.getMetadata().hasPendingWrites()) {
                    loadData();
                    Toast.makeText(ChatRoom.this, "Snapshot Listener", Toast.LENGTH_SHORT).show();
                }

            }
        });

        chatRoomLayoutBinding.chatRoomRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                // Kullanıcı en üste ulaştıysa (0. pozisyon)

                assert layoutManager != null;
                if (layoutManager.findFirstVisibleItemPosition() == 0) {

                    ;

                    Timestamp messageTimeStamp =roomMessagesArrayList.get(0).getTimestamp();
                    earlyMessageSecond = messageTimeStamp.getSeconds();

                    Cursor cursor = database.searchAfterDataMessages(destination,earlyMessageSecond);

                    if (cursor!=null){
                        while (cursor.moveToNext()) {

                            String message = cursor.getString(0);
                            String user = cursor.getString(1);
                            long second = cursor.getLong(2);

                            Log.d("Adapt","Cursor ilerliyor.");

                            Timestamp timestamp = new Timestamp(second, 0);

                            RoomMessages roomMessages = new RoomMessages(message, user, timestamp);

                            roomMessagesArrayList.add(0,roomMessages);
                        }
                        chatRoomLayoutBinding.chatRoomRecyclerView.getAdapter().notifyItemRangeInserted(0,cursor.getCount());
                    }










                }
            }
        });




        enterMethod();


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: Aktivite oluşturuldu");

    }

    public void enterMethod() {
        firestore.collection("chatrooms").document(destination).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String name = (String) documentSnapshot.get("name");
                        Toast.makeText(ChatRoom.this, name + " giriş yapıldı", Toast.LENGTH_SHORT).show();

                        readDatabaseData();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChatRoom.this, "Giriş yapılırken hata", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void readDatabaseData() {


        Cursor cursor = database.searchDataMessages(destination);


        while (cursor.moveToNext()) {

            String message = cursor.getString(0);
            String user = cursor.getString(1);
            long second = cursor.getLong(2);

            Timestamp timestamp = new Timestamp(second, 0);

            RoomMessages roomMessages = new RoomMessages(message, user, timestamp);

            roomMessagesArrayList.add(0,roomMessages);



        }

        if (roomMessagesArrayList.size() != 0) {
            chatRoomLayoutBinding.chatRoomRecyclerView.setLayoutManager(new LinearLayoutManager(ChatRoom.this));
            chatRoomLayoutBinding.chatRoomRecyclerView.setAdapter(new ChatRoomAdapter(roomMessagesArrayList));
            chatRoomLayoutBinding.chatRoomRecyclerView.scrollToPosition(roomMessagesArrayList.size()-1);
        }

        loadData();
        Toast.makeText(this, "Read Database", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.chat_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() == R.id.addMember) {
            addUserAlertDialog();
            addUserAlertDialog.create();
            addUserAlertDialog.show();
        } else if (item.getItemId() ==R.id.members){
            Intent intent = new Intent(ChatRoom.this, ChatRoomUsers.class);
            intent.putExtra("destination",destination);
            startActivity(intent);
        } else if (item.getItemId() == R.id.leaveFromGroup){
            //Kişinin gruptan ayrılmasını sağla.

            leaveGroupAlertDialog();
            leaveGroupDialog.create();
            leaveGroupDialog.show();


        }



        return super.onOptionsItemSelected(item);
    }

    public void leaveGroupAlertDialog(){
        leaveGroupDialog = new AlertDialog.Builder(ChatRoom.this);

        leaveGroupDialog.setTitle("Gruptan Ayrılma");
        TextView textView = new TextView(ChatRoom.this);
        textView.setText("Gruptan ayrılmak istediğinize emin misiniz?");

        leaveGroupDialog.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Grup referansı
                DocumentReference groupReference = firestore.collection("chatrooms").document(destination)
                        .collection("users").document("userList");


                String id = firebaseUser.getUid();

                //Kullanıcı referansı
                DocumentReference userReference = firestore.collection("users").document(id);

                WriteBatch writeBatch = firestore.batch();

                writeBatch.update(userReference,"members."+destination,FieldValue.delete());
                writeBatch.update(groupReference,"users."+id,FieldValue.delete());


                writeBatch.commit().addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChatRoom.this, "Gruptan çıkarken hata oluştu.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Intent intent = new Intent(ChatRoom.this, PremiumActivity.class);
                        startActivity(intent);
                    }
                });

                dialogInterface.dismiss();

            }
        }).setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });


    }

    public void addUserAlertDialog() {
        addUserAlertDialog = new AlertDialog.Builder(ChatRoom.this);

        addUserAlertDialog.setTitle("Kullanıcı Ekle");
        EditText editText = new EditText(ChatRoom.this);
        editText.setHint("Kullanıcı UID sini giriniz");
        addUserAlertDialog.setView(editText);

        addUserAlertDialog.setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String userID = editText.getText().toString().trim();

                if (!userID.equals("")) {

                    //Kullanıcı ekleme işlemleri

                    firestore.collection("chatrooms").document(destination).get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {

                                    String ID = (String) documentSnapshot.get("documentID");
                                    String name = (String) documentSnapshot.get("name");

                                    DocumentReference userReference = firestore.collection("users").document(userID);
                                    assert ID != null;
                                    DocumentReference chatRoomReference = firestore.collection("chatrooms").document(ID).collection("users").document("userList");


                                    //kullanıcı dokumanı
                                    Map<String, Object> groupsData = new HashMap<>();
                                    Map<String, Object> chatsMap = new HashMap<>();
                                    Map<String, Object> chatMap = new HashMap<>();
                                    chatMap.put("Name", name);
                                    chatMap.put("Role", "user");
                                    chatsMap.put(ID, chatMap);
                                    groupsData.put("members", chatsMap);


                                    userReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                                            String userName = (String) documentSnapshot.get("name");


                                            //odanın dokümanı
                                            Map<String, Object> userData = new HashMap<>();
                                            Map<String, Object> newUser = new HashMap<>();
                                            Map<String, Object> userInfo = new HashMap<>();
                                            userInfo.put("name", userName);
                                            userInfo.put("ID", userID);
                                            userInfo.put("role", "user");
                                            newUser.put(userID, userInfo);
                                            userData.put("users", newUser);


                                            WriteBatch writeBatch = firestore.batch();

                                            writeBatch.set(chatRoomReference, userData, SetOptions.merge());
                                            writeBatch.set(userReference, groupsData, SetOptions.merge());

                                            writeBatch.commit().addOnFailureListener(new OnFailureListener() {
                                                                                         @Override
                                                                                         public void onFailure(@NonNull Exception e) {
                                                                                             Toast.makeText(ChatRoom.this, "Failure on Creating group", Toast.LENGTH_SHORT).show();
                                                                                         }
                                                                                     }
                                            );
                                        }


                                    });

                                    dialogInterface.dismiss();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ChatRoom.this, "Eklenirken hata oluştu", Toast.LENGTH_SHORT).show();
                                }
                            });


                }

            }
        }).setNegativeButton("İptal et", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });


    }

    public void loadData() {


        Timestamp timestamp = new Timestamp(lastMessageSecond, lastMessageNanoSec);

        CollectionReference collectionReference = firestore.collection("chatrooms").document(destination).collection("messages");
        collectionReference.orderBy("date", Query.Direction.ASCENDING)
                .whereGreaterThan("date", timestamp)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                            String user = (String) documentSnapshot.get("user");
                            String message = (String) documentSnapshot.get("message");
                            Timestamp timestamp = (Timestamp) documentSnapshot.get("date");
                            roomMessagesArrayList.add(new RoomMessages(message, user, timestamp));

                            long seconds = timestamp.getSeconds();


                            database.insertDataChats(message, user, seconds, destination);


                        }

                        if (queryDocumentSnapshots.size() > 0) {
                            DocumentSnapshot lastResult = queryDocumentSnapshots.getDocuments()
                                    .get(queryDocumentSnapshots.size() - 1);

                            Timestamp lastTimeStamp = (Timestamp) lastResult.get("date");

                            long timeSeconds = lastTimeStamp.getSeconds();
                            int nanoTimeSeconds = lastTimeStamp.getNanoseconds();

                            sharedPreferences.edit().putLong("lastMessage", timeSeconds).apply();
                            sharedPreferences.edit().putInt("lastMessageNano", nanoTimeSeconds).apply();

                            lastMessageSecond = timeSeconds;
                            lastMessageNanoSec = nanoTimeSeconds;
                        }


                        if (roomMessagesArrayList.size() != 0) {
                            chatRoomLayoutBinding.chatRoomRecyclerView.setLayoutManager(new LinearLayoutManager(ChatRoom.this));
                            chatRoomLayoutBinding.chatRoomRecyclerView.setAdapter(new ChatRoomAdapter(roomMessagesArrayList));
                            chatRoomLayoutBinding.chatRoomRecyclerView.scrollToPosition(roomMessagesArrayList.size()-1);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChatRoom.this, "Yüklenirken hata oluştu", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
