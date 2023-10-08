package premiumMode.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.yusufmirza.theyksproject.R;
import com.yusufmirza.theyksproject.databinding.ChatroomUsersRowBinding;

import java.nio.channels.SelectableChannel;
import java.util.ArrayList;

import premiumMode.ChatRoom;
import premiumMode.HelperClasses.Users;
import premiumMode.PremiumActivity;

public class ChatUsersAdapter extends RecyclerView.Adapter<ChatUsersAdapter.ChatUsesViewHolder> {

    ArrayList<Users> usersArrayList;
    AlertDialog.Builder userOptionsBuilder, kickAlertDialog, roleAlertDialog,userInfoAlertDialog;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    Context context;
    String userRole;
    String destination;

    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    public ChatUsersAdapter(ArrayList<Users> usersArrayList, Context context, String userRole, String destination) {
        this.usersArrayList = usersArrayList;
        this.context = context;
        this.userRole = userRole;
        this.destination = destination;
    }


    @NonNull
    @Override
    public ChatUsesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatroom_users_row, parent, false);
        return new ChatUsesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatUsesViewHolder holder, int position) {
        holder.chatUserRole.setText(usersArrayList.get(position).getRole());
        holder.chatUserName.setText(usersArrayList.get(position).getName());


        holder.userLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if (userRole.equals("user")){
                    Toast.makeText(context, "Yetkiniz yok", Toast.LENGTH_SHORT).show();
                }
                else if(userRole.equals("admin") && usersArrayList.get(position).getRole().equals("admin")) {
                    Toast.makeText(context, "Bir admin olarak bir adminin ayarlarını değiştiremessiniz.", Toast.LENGTH_SHORT).show();
                }
                else if(userRole.equals("admin") && usersArrayList.get(position).getRole().equals("owner")) {
                    Toast.makeText(context, "Bir admin olarak oda sahibini değiştiremessiniz.", Toast.LENGTH_SHORT).show();
                }
                else if(userRole.equals("admin") && usersArrayList.get(position).getuID().equals(firebaseUser.getUid())) {
                    Toast.makeText(context, "Bir admin olarak kendinizi değiştiremessiniz.", Toast.LENGTH_SHORT).show();
                }
                else if(userRole.equals("owner") && usersArrayList.get(position).getuID().equals(firebaseUser.getUid())) {
                    Toast.makeText(context, "Bir sahip olarak kendinizi değiştiremessiniz.", Toast.LENGTH_SHORT).show();
                }
                else {
                    setUserOptionsAdapter(holder.getAdapterPosition());
                    userOptionsBuilder.create();
                    userOptionsBuilder.show();
                }

                return true;
            }
        });

        holder.userLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Kullanıcı bilgilerini içeren layout

                //user Document
                DocumentReference userDoc = firestore.collection("users").document(usersArrayList.get(position).getuID());

                userDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        String description = (String) documentSnapshot.get("description");


                        showUsersInfos(usersArrayList.get(holder.getAdapterPosition()).getName(),description);
                        userInfoAlertDialog.create();
                        userInfoAlertDialog.show();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Bir sorun oluştu", Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });

    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public static class ChatUsesViewHolder extends RecyclerView.ViewHolder {

        LinearLayout userLinearLayout;
        TextView chatUserName;
        TextView chatUserRole;

        public ChatUsesViewHolder(@NonNull View itemView) {
            super(itemView);
            userLinearLayout = itemView.findViewById(R.id.userLinearLayout);
            chatUserName = itemView.findViewById(R.id.chatUserName);
            chatUserRole = itemView.findViewById(R.id.chatUserRole);


        }
    }


    public void showUsersInfos(String name,String description){

        userInfoAlertDialog = new AlertDialog.Builder(context);
        View view = View.inflate(context,R.layout.userprofile_alertdialog,null);

        TextView userName = view.findViewById(R.id.showUserName);
        TextView userDescription = view.findViewById(R.id.showDescription);

        userName.setText(name);
        userDescription.setText(description);

        userInfoAlertDialog.setView(view);


        userInfoAlertDialog.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });





    }
    public void setUserOptionsAdapter(int position) {
        userOptionsBuilder = new AlertDialog.Builder(context);

        if (userRole.equals("admin")) {

            userOptionsBuilder.setTitle("Kullanıcı Seçenekleri");

            View view = View.inflate(context,R.layout.useroptions_alertdialog,null);

            TextView kickUser = view.findViewById(R.id.kickUser);
            TextView changeRole = view.findViewById(R.id.changeRole);
            kickUser.setText("Kullanıcıyı gruptan at");
            changeRole.setText("Kullanıcının gruptaki rolunu değiştir");

            userOptionsBuilder.setView(view);

            kickUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    setKickAlertDialog(position);
                    kickAlertDialog.create();
                    kickAlertDialog.show();

                }
            });

            changeRole.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeRoleAlertDialogAdmin(position);
                    roleAlertDialog.create();
                    roleAlertDialog.show();

                }
            });

            userOptionsBuilder.setPositiveButton("Menüden Çık", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });


        } else if (userRole.equals("owner")) {

            userOptionsBuilder.setTitle("Kullanıcı Seçenekleri");

            View view = View.inflate(context,R.layout.useroptions_alertdialog,null);

            TextView kickUser = view.findViewById(R.id.kickUser);
            TextView changeRole = view.findViewById(R.id.changeRole);
            kickUser.setText("Kullanıcıyı gruptan at");
            changeRole.setText("Kullanıcının gruptaki rolunu değiştir");

            userOptionsBuilder.setView(view);


            kickUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    setKickAlertDialog(position);
                    kickAlertDialog.create();
                    kickAlertDialog.show();

                }
            });

            changeRole.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeRoleAlertDialogOwner(position);
                    roleAlertDialog.create();
                    roleAlertDialog.show();
                }
            });

            userOptionsBuilder.setPositiveButton("Menüden Çık", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

        }
    }

    public void setKickAlertDialog(int position) {
        kickAlertDialog = new AlertDialog.Builder(context);
        kickAlertDialog.setTitle("Kullanıcı atma");
        TextView textView = new TextView(context);
        textView.setText(" "+usersArrayList.get(position).getName()+"  adlı kullanıcıyı atmak istediğinize emin misiniz ?");

        kickAlertDialog.setView(textView);
        kickAlertDialog.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Grup referansı
                DocumentReference groupReference = firestore.collection("chatrooms").document(destination)
                        .collection("users").document("userList");


                String id = usersArrayList.get(position).getuID();

                //Kullanıcı referansı
                DocumentReference userReference = firestore.collection("users").document(id);

                WriteBatch writeBatch = firestore.batch();

                writeBatch.update(userReference,"members."+destination, FieldValue.delete());
                writeBatch.update(groupReference,"users."+id,FieldValue.delete());


                writeBatch.commit().addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Gruptan atarken hata oluştu.", Toast.LENGTH_SHORT).show();
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

    public void changeRoleAlertDialogOwner(int position) {

        roleAlertDialog = new AlertDialog.Builder(context);
        roleAlertDialog.setTitle("Kullanıcı rolünü düzenle");
        RadioGroup radioGroup = new RadioGroup(context);
        RadioButton radioButtonUser = new RadioButton(context);
        radioButtonUser.setText("Kullanıcı");
        RadioButton radioButtonAdmin = new RadioButton(context);
        radioButtonAdmin.setText("Admin");
        RadioButton radioButtonOwner = new RadioButton(context);
        radioButtonOwner.setText("Owner");

        radioGroup.addView(radioButtonUser);
        radioGroup.addView(radioButtonAdmin);
        radioGroup.addView(radioButtonOwner);

        roleAlertDialog.setView(radioGroup);


        roleAlertDialog.setPositiveButton("Ayarla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                int selected = radioGroup.getCheckedRadioButtonId();

                if (selected == -1) {
                    Toast.makeText(context, "Lütfen bir seçenek seçiniz", Toast.LENGTH_SHORT).show();
                } else if (selected == radioButtonUser.getId()) {

                    AlertDialog.Builder controlAlert = new AlertDialog.Builder(context);
                    controlAlert.setTitle("Kullanıcının rolunu user yapmaya emin misiniz ?");

                    controlAlert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface innerDialogInterface, int i) {
                            //Kullanıcıyı user yap.
                            String userID = usersArrayList.get(position).getuID();


                            DocumentReference groupReference = firestore.collection("chatrooms").document(destination)
                                    .collection("users").document("userList");

                            DocumentReference userReference = firestore.collection("users").document(userID);

                            WriteBatch writeBatch = firestore.batch();

                            writeBatch.update(userReference, "members." + destination + ".Role", "user");
                            writeBatch.update(groupReference, "users." + userID + ".role", "user");

                            writeBatch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(context, "Kullancının rolü değiştirildi", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "İşlem yapılırken hata oluştu", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).setNegativeButton("İptal et ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface innerDialogInterface, int i) {
                            innerDialogInterface.cancel();
                            dialogInterface.cancel();
                        }
                    });

                    controlAlert.create();
                    controlAlert.show();


                } else if (selected == radioButtonAdmin.getId()) {

                    AlertDialog.Builder controlAlert = new AlertDialog.Builder(context);
                    controlAlert.setTitle("Kullanıcının rolunu admin yapmaya emin misiniz ?");

                    controlAlert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface innerDialogInterface, int i) {
                            //Kullanıcıyı admin yap.

                            String userID = usersArrayList.get(position).getuID();


                            DocumentReference groupReference = firestore.collection("chatrooms").document(destination)
                                    .collection("users").document("userList");

                            DocumentReference userReference = firestore.collection("users").document(userID);

                            WriteBatch writeBatch = firestore.batch();

                            writeBatch.update(userReference, "members." + destination + ".Role", "admin");
                            writeBatch.update(groupReference, "users." + userID + ".role", "admin");

                            writeBatch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(context, "Kullancının rolü değiştirildi", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "İşlem yapılırken hata oluştu", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).setNegativeButton("İptal et ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface innerDialogInterface, int i) {
                            innerDialogInterface.cancel();
                            dialogInterface.cancel();
                        }
                    });

                    controlAlert.create();
                    controlAlert.show();

                } else if (selected == radioButtonOwner.getId()) {

                    AlertDialog.Builder controlAlert = new AlertDialog.Builder(context);
                    controlAlert.setTitle("Kullanıcının rolunu owner yapmaya emin misiniz ?");
                    TextView textView = new TextView(context);
                    textView.setText("Bunu yaparsanız artık odanın sahibi olamazsınız");
                    controlAlert.setView(textView);

                    controlAlert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface innerDialogInterface, int i) {
                            //Kullanıcıyı owner yap.
                            String userID = usersArrayList.get(position).getuID();


                            DocumentReference groupReference = firestore.collection("chatrooms").document(destination)
                                    .collection("users").document("userList");

                            DocumentReference userReference = firestore.collection("users").document(userID);

                            DocumentReference ownerReference = firestore.collection("users").document(firebaseUser.getUid());
                            DocumentReference groupOwnerReference = firestore.collection("chatrooms").document(destination)
                                    .collection("users").document("userList");


                            WriteBatch writeBatch = firestore.batch();

                            writeBatch.update(userReference, "members." + destination + ".Role", "owner");
                            writeBatch.update(groupReference, "users." + userID + ".role", "owner");
                            writeBatch.update(ownerReference, "members." + destination + ".Role", "admin");
                            writeBatch.update(groupOwnerReference, "users." + firebaseUser.getUid() + ".role", "admin");

                            writeBatch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(context, "Kullancının rolü değiştirildi", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "İşlem yapılırken hata oluştu", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).setNegativeButton("İptal et ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface innerDialogInterface, int i) {
                            innerDialogInterface.cancel();

                        }
                    });

                    controlAlert.create();
                    controlAlert.show();


                }
            }
        }).setNegativeButton("İptal et", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

    }

    public void changeRoleAlertDialogAdmin(int position) {
        roleAlertDialog = new AlertDialog.Builder(context);
        roleAlertDialog.setTitle("Kullanıcı rolünü düzenle");
        RadioGroup radioGroup = new RadioGroup(context);
        RadioButton radioButtonUser = new RadioButton(context);
        radioButtonUser.setText("User");
        RadioButton radioButtonAdmin = new RadioButton(context);
        radioButtonAdmin.setText("Admin");


        radioGroup.addView(radioButtonUser);
        radioGroup.addView(radioButtonAdmin);

        roleAlertDialog.setView(radioGroup);


        roleAlertDialog.setPositiveButton("Ayarla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                int selected = radioGroup.getCheckedRadioButtonId();

                if (selected == -1) {
                    Toast.makeText(context, "Lütfen bir seçenek seçiniz", Toast.LENGTH_SHORT).show();
                }
                else if (selected == radioButtonUser.getId()) {

                    AlertDialog.Builder controlAlert = new AlertDialog.Builder(context);
                    controlAlert.setTitle("Kullanıcının rolunu user yapmaya emin misiniz ?");

                    controlAlert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface innerDialogInterface, int i) {
                            //Kullanıcıyı user yap.
                            String userID = usersArrayList.get(position).getuID();


                            DocumentReference groupReference = firestore.collection("chatrooms").document(destination)
                                    .collection("users").document("userList");

                            DocumentReference userReference = firestore.collection("users").document(userID);

                            WriteBatch writeBatch = firestore.batch();

                            writeBatch.update(userReference, "members." + destination + ".Role", "user");
                            writeBatch.update(groupReference, "users." + userID + ".role", "user");

                            writeBatch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(context, "İşlem başarılı", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "İşlem başarısız", Toast.LENGTH_SHORT).show();

                                }
                            });


                        }
                    }).setNegativeButton("İptal et ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface innerDialogInterface, int i) {
                            innerDialogInterface.cancel();
                            dialogInterface.cancel();
                        }
                    });




                    controlAlert.create();
                    controlAlert.show();


                } else if (selected == radioButtonAdmin.getId()) {

                    AlertDialog.Builder controlAlert = new AlertDialog.Builder(context);
                    controlAlert.setTitle("Kullanıcının rolunu admin yapmaya emin misiniz ?");

                    controlAlert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface innerDialogInterface, int i) {
                            //Kullanıcıyı admin yap.

                            String userID = usersArrayList.get(position).getuID();


                            DocumentReference groupReference = firestore.collection("chatrooms").document(destination)
                                    .collection("users").document("userList");

                            DocumentReference userReference = firestore.collection("users").document(userID);

                            WriteBatch writeBatch = firestore.batch();

                            writeBatch.update(userReference, "members." + destination + ".Role", "admin");
                            writeBatch.update(groupReference, "users." + userID + ".role", "admin");

                            writeBatch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(context, "İşlem başarılı", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "İşlem başarısız", Toast.LENGTH_SHORT).show();

                                }
                            });


                        }
                    }).setNegativeButton("İptal et ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface innerDialogInterface, int i) {
                            innerDialogInterface.cancel();

                        }
                    });

                    controlAlert.create();
                    controlAlert.show();

                }
            }
        }).setNegativeButton("İptal et", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

    }

}
