package premiumMode.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.yusufmirza.theyksproject.R;
import com.yusufmirza.theyksproject.databinding.FragmentGlobalChatRowBinding;
import com.yusufmirza.theyksproject.databinding.FragmentProfileRowBinding;

import java.util.ArrayList;
import java.util.HashMap;

import premiumMode.HelperClasses.SocialAccount;

public class ProfileElementsAdapter extends RecyclerView.Adapter<ProfileElementsAdapter.ProfileElementHolder> {

    ArrayList<SocialAccount> socialAccounts;
    AlertDialog.Builder builder,deleteBuilder,openLink;
    FragmentProfileRowBinding fragmentProfileRowBinding;
    Context context;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public ProfileElementsAdapter(ArrayList<SocialAccount> socialAccounts, Context context) {
        this.socialAccounts = socialAccounts;
        this.context = context;

    }


    @NonNull
    @Override
    public ProfileElementHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        fragmentProfileRowBinding = FragmentProfileRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProfileElementHolder(fragmentProfileRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileElementHolder holder, int position) {
        holder.fragmentProfileRowBinding.editLinkName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEditAccountAlertDialog(socialAccounts.get(holder.getAdapterPosition()),holder.getAdapterPosition());
                builder.create();
                builder.show();


            }
        });

        holder.fragmentProfileRowBinding.linkAccount.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                setDeletingAlertDialog(holder.getAdapterPosition());
                deleteBuilder.create();
                deleteBuilder.show();

                return true;
            }
        });




        holder.fragmentProfileRowBinding.goToLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOpenLinkALertDialog(holder.getAdapterPosition());
                openLink.create();
                openLink.show();




            }
        });

        holder.fragmentProfileRowBinding.goToLink.setImageResource(socialAccounts.get(holder.getAdapterPosition()).getImageID());


        holder.fragmentProfileRowBinding.linkAccount.setText(socialAccounts.get(holder.getAdapterPosition()).getName());

    }

    @Override
    public int getItemCount() {
        return socialAccounts.size();
    }

    public static class ProfileElementHolder extends RecyclerView.ViewHolder {

        FragmentProfileRowBinding fragmentProfileRowBinding;

        public ProfileElementHolder(@NonNull FragmentProfileRowBinding fragmentProfileRowBinding) {
            super(fragmentProfileRowBinding.getRoot());
            this.fragmentProfileRowBinding = fragmentProfileRowBinding;
        }
    }

    @SuppressLint("SetTextI18n")
    public void setOpenLinkALertDialog(int position){
        openLink = new AlertDialog.Builder(context);
        openLink.setTitle("Bağlantı Onayı");
        TextView textView = new TextView(context);
        textView.setText(socialAccounts.get(position).getLink() +" adresine ilermek istediğinize emin misiniz?");

        openLink.setView(textView);

        openLink.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String url = socialAccounts.get(position).getLink();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
            }
        }).setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "İptal edildi", Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void setDeletingAlertDialog(int position){
        deleteBuilder = new AlertDialog.Builder(context);
        deleteBuilder.setTitle("Bu hesabı silmek istediğinize emin misiniz");
        deleteBuilder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //silme işlemi

                int imageID = socialAccounts.get(position).getImageID();

                if (imageID==R.drawable.youtube){
                    firestore.collection("users").document(firebaseUser.getUid()).update("Accounts.Youtube", null);
                    socialAccounts.remove(position);
                    notifyDataSetChanged();

                } else if (imageID == R.drawable.instagram){
                firestore.collection("users").document(firebaseUser.getUid()).update("Accounts.Instagram", null);
                socialAccounts.remove(position);
                    notifyDataSetChanged();
                }
                 else if (imageID == R.drawable.linkedin){
                firestore.collection("users").document(firebaseUser.getUid()).update("Accounts.Linkedin", null);
                socialAccounts.remove(position);
                notifyDataSetChanged();
                }
                 else if (imageID == R.drawable.baseline_email_24){
                  firestore.collection("users").document(firebaseUser.getUid()).update("Accounts.Email", null);
                  socialAccounts.remove(position);
                    notifyDataSetChanged();
                }

                dialogInterface.dismiss();
            }
        }).setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
    }




    public void setEditAccountAlertDialog(SocialAccount socialAccount,int position) {
        builder = new AlertDialog.Builder(context);

        View view = LayoutInflater.from(context).inflate(R.layout.addaccount_alertdialog, null);

        EditText nameText = view.findViewById(R.id.nameText);
        EditText linkText = view.findViewById(R.id.linkText);
        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        RadioButton radioYoutube = view.findViewById(R.id.radioYoutube);
        RadioButton radioInstagram = view.findViewById(R.id.radioInstagram);
        RadioButton radioLinkedin = view.findViewById(R.id.radioLinkedin);
        RadioButton radioEmail = view.findViewById(R.id.radioEmail);

        radioYoutube.setEnabled(false);
        radioInstagram.setEnabled(false);
        radioLinkedin.setEnabled(false);
        radioEmail.setEnabled(false);



        nameText.setText(socialAccount.getName());
        linkText.setText(socialAccount.getLink());

        if (socialAccount.getImageID()==R.drawable.youtube){
            radioYoutube.setChecked(true);
        }else if (socialAccount.getImageID()==R.drawable.instagram){
            radioInstagram.setChecked(true);
        } else if (socialAccount.getImageID()==R.drawable.linkedin){
            radioLinkedin.setChecked(true);
        } else if (socialAccount.getImageID()==R.drawable.baseline_email_24){
            radioEmail.setChecked(true);
        }

        builder.setView(view);

        builder.setTitle("Hesabınıza bağlantı oluşturun");
        builder.setPositiveButton("Onayla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {




                String name = nameText.getText().toString().trim();
                String link = linkText.getText().toString().trim();
                int radioId = radioGroup.getCheckedRadioButtonId();

                RadioButton radioButton = view.findViewById(radioId);

                int drawable = 0;


                if (!name.equals("") && !link.equals("") && radioButton != null) {


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

                    if (drawable != 0) {
                        SocialAccount  socialAccount =  new SocialAccount(drawable, link, name);
                        //güncelleme

                        socialAccounts.set(position,socialAccount);


                        HashMap<String,Object> socialAccountsMap = new HashMap<>();
                        HashMap<String,Object> socialAccsMap = new HashMap<>();
                        HashMap<String,Object> socialAcc = new HashMap<>();
                        socialAcc.put("Link",link);
                        socialAcc.put("Name",name);
                        socialAcc.put("type",radioButton.getText().toString());

                        socialAccsMap.put(radioButton.getText().toString(),socialAcc);

                        socialAccountsMap.put("Accounts",socialAccsMap);

                        firestore.collection("users").document(firebaseUser.getUid()).set(socialAccountsMap, SetOptions.merge())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "Başarıyla eklendi", Toast.LENGTH_SHORT).show();
                                        notifyDataSetChanged();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Veri internete yüklenirken hata", Toast.LENGTH_SHORT).show();
                                    }
                                });







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
}
