package premiumMode;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.yusufmirza.theyksproject.R;
import com.yusufmirza.theyksproject.databinding.PremiumActivityBinding;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import premiumMode.fragments.ChatFragment;
import premiumMode.fragments.GlobalChatFragment;
import premiumMode.fragments.ProfileFragment;

public class PremiumActivity extends AppCompatActivity {

    PremiumActivityBinding premiumActivityBinding;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        premiumActivityBinding = PremiumActivityBinding.inflate(getLayoutInflater());
        setContentView(premiumActivityBinding.getRoot());

        SharedPreferences sharedPreferences = getSharedPreferences("file",MODE_PRIVATE);

        boolean state = sharedPreferences.getBoolean("file",false);



        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


       if (!state){
           DocumentReference documentReference = firestore.collection("users").document(firebaseUser.getUid());




           Map<String,Object> userFile = new HashMap<>();
           userFile.put("userID",firebaseUser.getUid());
           userFile.put("name",firebaseUser.getDisplayName());
           userFile.put("email",firebaseUser.getEmail());


           documentReference.set(userFile, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
               @Override
               public void onSuccess(Void unused) {
                   Toast.makeText(PremiumActivity.this, "Başarıyla sisteme bilgileriniz aktarıldı.", Toast.LENGTH_SHORT).show();
                   sharedPreferences.edit().putBoolean("file",true).apply();
               }
           }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   Toast.makeText(PremiumActivity.this, "Hata oluştu", Toast.LENGTH_SHORT).show();
               }
           });
       }









        getSupportFragmentManager().beginTransaction().replace(premiumActivityBinding.frameLayout.getId(),new ProfileFragment(PremiumActivity.this)).commit();




        premiumActivityBinding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()== R.id.profile){
                    getSupportFragmentManager().beginTransaction().replace(premiumActivityBinding.frameLayout.getId(),new ProfileFragment(PremiumActivity.this)).commit();
                } if(item.getItemId() == R.id.globalchat){
                    getSupportFragmentManager().beginTransaction().replace(premiumActivityBinding.frameLayout.getId(),new GlobalChatFragment(PremiumActivity.this)).commit();
                } if (item.getItemId() == R.id.chat){
                    getSupportFragmentManager().beginTransaction().replace(premiumActivityBinding.frameLayout.getId(),new ChatFragment(PremiumActivity.this)).commit();
                }

                return true;
            }
        });






    }
}
