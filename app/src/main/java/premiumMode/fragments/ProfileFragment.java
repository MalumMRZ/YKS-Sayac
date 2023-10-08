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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.yusufmirza.theyksproject.R;
import com.yusufmirza.theyksproject.databinding.FragmentProfileBinding;

import java.util.ArrayList;
import java.util.HashMap;

import premiumMode.HelperClasses.SocialAccount;
import premiumMode.adapters.ProfileElementsAdapter;


public class ProfileFragment extends Fragment {


   FragmentProfileBinding fragmentProfileBinding;
   Context context;
    AlertDialog.Builder builder,builderUser;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

   ArrayList<SocialAccount> socialAccounts;

    public ProfileFragment(Context context) {
       this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentProfileBinding = FragmentProfileBinding.inflate(inflater);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(fragmentProfileBinding.toolBar);


        fragmentProfileBinding.toolBarTitle.setText("Profil");

        setHasOptionsMenu(true);

        return fragmentProfileBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        socialAccounts = new ArrayList<>();


        fragmentProfileBinding.textToastInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Sadece birer sosyal medya hesabı ekleyebilirsiniz veriler üstüne yazar.", Toast.LENGTH_LONG).show();
            }
        });


        firebaseFirestore.collection("users").document(firebaseUser.getUid()).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                String description = (String) documentSnapshot.get("description");

                                fragmentProfileBinding.descriptionEditText.setText(description);



                              HashMap<String,Object> accountsOuterMap = (HashMap<String, Object>) documentSnapshot.get("Accounts");

                                if (accountsOuterMap!=null){
                                    for (String accountOuterKey : accountsOuterMap.keySet()){

                                        HashMap<String,Object> accountsInnerMap = (HashMap<String, Object>) accountsOuterMap.get(accountOuterKey);

                                if (accountsInnerMap!=null){
                                    String name = (String) accountsInnerMap.get("Name");
                                    String link = (String) accountsInnerMap.get("Link");
                                    String type = (String) accountsInnerMap.get("type");
                                    int drawable =0;

                                    switch (type){

                                        case "Youtube":
                                            drawable = R.drawable.youtube;
                                            break;
                                        case "Instagram":
                                            drawable = R.drawable.instagram;
                                            break;
                                        case "Linkedin":
                                            drawable = R.drawable.linkedin;
                                            break;
                                        case "Email":
                                            drawable = R.drawable.baseline_email_24;
                                            break;

                                    }


                                    if (drawable!=0){
                                        socialAccounts.add(new SocialAccount(drawable,link,name));
                                    }



                                    if (socialAccounts.size()!=0){
                                        fragmentProfileBinding.accountsView.setLayoutManager(new LinearLayoutManager(context));
                                        fragmentProfileBinding.accountsView.setAdapter(new ProfileElementsAdapter(socialAccounts,context));
                                    }
                                }



                                    }
                                }





                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Veriler çekilemedi", Toast.LENGTH_SHORT).show();
                    }
                });




          fragmentProfileBinding.addAccountLink.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  setAddAccountAlertDialog();
                  builder.create();
                  builder.show();
              }
          });

          fragmentProfileBinding.imageEditUserButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  setEditUserNameDialog();
                  builderUser.create();
                  builderUser.show();
              }
          });

          fragmentProfileBinding.descriptonTextButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  if (fragmentProfileBinding.descriptionEditText.isEnabled()){
                      //Ayrıca eğer bu kapatlıyorsa otomatik bir şekilde yeni gelen veri gğncellenmeli.
                      fragmentProfileBinding.descriptionEditText.setEnabled(false);

                      String description = fragmentProfileBinding.descriptionEditText.getText().toString().trim();

                      HashMap<String,Object> userDescription = new HashMap<>();
                      userDescription.put("description",description);

                      firebaseFirestore.collection("users").document(firebaseUser.getUid()).set(userDescription, SetOptions.merge())
                              .addOnSuccessListener(new OnSuccessListener<Void>() {
                                  @Override
                                  public void onSuccess(Void unused) {
                                      Toast.makeText(context, "Güncellendi", Toast.LENGTH_SHORT).show();
                                  }
                              }).addOnFailureListener(new OnFailureListener() {
                                  @Override
                                  public void onFailure(@NonNull Exception e) {
                                      Toast.makeText(context, "Güncellenirken hata oluştu", Toast.LENGTH_SHORT).show();
                                  }
                              });



                  } else {
                      fragmentProfileBinding.descriptionEditText.setEnabled(true);
                  }
              }
          });


          fragmentProfileBinding.textUserName.setText(firebaseUser.getDisplayName());


    }


    public void setAddAccountAlertDialog(){
        builder = new AlertDialog.Builder(context);

        View view = LayoutInflater.from(context).inflate(R.layout.addaccount_alertdialog,null);

        builder.setView(view);

        builder.setTitle("Hesabınıza bağlantı oluşturun");
        builder.setPositiveButton("Onayla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                EditText nameText = view.findViewById(R.id.nameText);
                EditText linkText = view.findViewById(R.id.linkText);
                RadioGroup radioGroup = view.findViewById(R.id.radioGroup);

                String name = nameText.getText().toString().trim();
                String link = linkText.getText().toString().trim();
                int radioId = radioGroup.getCheckedRadioButtonId();

                RadioButton radioButton = view.findViewById(radioId);

                int drawable = 0;


                if (!name.equals("") && !link.equals("") && radioButton!=null){



                    switch (radioButton.getText().toString()) {
                        case "Youtube":
                            drawable = R.drawable.youtube;
                            break;
                        case "Instagram":
                            drawable = R.drawable.instagram;
                            break;
                        case "Linkedin":
                            drawable = R.drawable.linkedin;
                            break;
                        case "Email":
                            drawable = R.drawable.baseline_email_24;

                    }

                      if (drawable!=0){
                          socialAccounts.add(new SocialAccount(drawable,link,name));

                          HashMap<String,Object> socialAccountsMap = new HashMap<>();
                          HashMap<String,Object> socialAccsMap = new HashMap<>();
                          HashMap<String,Object> socialAccount = new HashMap<>();
                          socialAccount.put("Link",link);
                          socialAccount.put("Name",name);
                          socialAccount.put("type",radioButton.getText().toString());

                          socialAccsMap.put(radioButton.getText().toString(),socialAccount);

                          socialAccountsMap.put("Accounts",socialAccsMap);

                          firebaseFirestore.collection("users").document(firebaseUser.getUid()).set(socialAccountsMap,SetOptions.merge())
                                          .addOnSuccessListener(new OnSuccessListener<Void>() {
                                              @Override
                                              public void onSuccess(Void unused) {
                                                  Toast.makeText(context, "Başarıyla eklendi", Toast.LENGTH_SHORT).show();
                                              }
                                          }).addOnFailureListener(new OnFailureListener() {
                                      @Override
                                      public void onFailure(@NonNull Exception e) {
                                          Toast.makeText(context, "Veri internete yüklenirken hata", Toast.LENGTH_SHORT).show();
                                      }
                                  });



                          fragmentProfileBinding.accountsView.setLayoutManager(new LinearLayoutManager(context));
                          fragmentProfileBinding.accountsView.setAdapter(new ProfileElementsAdapter(socialAccounts,context));


                      } else {
                          Toast.makeText(context, "Beklenmedik hata oluştu bildirin !", Toast.LENGTH_SHORT).show();
                      }


                    dialogInterface.dismiss();


                } else {
                    Toast.makeText(context, "Lütfen boşlukları doldurun", Toast.LENGTH_SHORT).show();
                }




            }
        }).setNegativeButton("İptal Et", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });



    }

    public void setEditUserNameDialog(){
        builderUser = new AlertDialog.Builder(context);
        builderUser.setTitle("Kullanıcıyı Düzenle");
        EditText editText = new EditText(context);
        editText.setHint("Yeni Kullanıcı Adınızı Giriniz");
        builderUser.setView(editText);


        builderUser.setPositiveButton("Onayla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String newName = editText.getText().toString();

                if (!newName.equals("")){

                    UserProfileChangeRequest.Builder userProfileChangeRequest = new UserProfileChangeRequest.Builder();
                    userProfileChangeRequest.setDisplayName(newName);

                    firebaseUser.updateProfile(userProfileChangeRequest.build()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context, "Yeni bilgileriniz kaydedildi", Toast.LENGTH_SHORT).show();
                            fragmentProfileBinding.textUserName.setText(firebaseUser.getDisplayName());

                            String id = firebaseUser.getUid();

                            HashMap<String,Object> userName = new HashMap<>();
                            userName.put("name",newName);


                            firebaseFirestore.collection("users").document(id).set(userName, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(context, "Kullanıcı adı güncellendi", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Kullanıcı adı güncellenirken hata", Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Bir hata oluştu", Toast.LENGTH_SHORT).show();
                        }
                    });

                    dialogInterface.dismiss();

                } else {
                    Toast.makeText(context, "Lütfen boşluğu doldurunuz", Toast.LENGTH_SHORT).show();
                }



            }
        }).setNegativeButton("İptal et", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
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




}
