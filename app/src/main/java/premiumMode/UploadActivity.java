package premiumMode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.yusufmirza.theyksproject.R;
import com.yusufmirza.theyksproject.databinding.ActivityUploadBinding;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.UUID;

import id.zelory.compressor.Compressor;

public class UploadActivity extends AppCompatActivity {

    Uri imageUri;
    Bitmap selectedImage;

    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    ActivityResultLauncher<Intent> toGallery;
    ActivityResultLauncher<String> toPermission;
    private ActivityUploadBinding activityUploadBinding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUploadBinding = ActivityUploadBinding.inflate(getLayoutInflater());
        View view = activityUploadBinding.getRoot();
        setContentView(view);

        auth= FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();


        set_Launchers();
    }
    public void upload(View view) throws IOException {
        if(imageUri !=null){
            UUID uuid = UUID.randomUUID();
            String urlcode = "images"+uuid+".jpg";

            activityUploadBinding.buttonUpload.setEnabled(false);



            int radioLessonId  = activityUploadBinding.radioLessonGroup.getCheckedRadioButtonId();
            int radioClassId = activityUploadBinding.radioClassGroup.getCheckedRadioButtonId();
            int radioExamId = activityUploadBinding.radioExamGroup.getCheckedRadioButtonId();


            RadioButton selectedRadioLesson = findViewById(radioLessonId);
            RadioButton selectedClassLesson = findViewById(radioClassId);
            RadioButton selectedExamLesson = findViewById(radioExamId);

            String lessonRadioText = selectedRadioLesson.getText().toString();
            String classRadioText = selectedClassLesson.getText().toString();
            String examRadioText = selectedExamLesson.getText().toString();







            storageReference.child(urlcode).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //Download URl

                    StorageReference getreference = firebaseStorage.getReference(urlcode);
                    getreference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String dowloadUri = uri.toString();
                            String comment = activityUploadBinding.commentText.getText().toString();
                            String title = activityUploadBinding.titleText.getText().toString();
                            FieldValue date = FieldValue.serverTimestamp();

                            FirebaseUser currentUser = auth.getCurrentUser();
                            String email = currentUser.getEmail();
                            String id = currentUser.getUid();
                            String name = currentUser.getDisplayName();


                            //Filtreleme güncellemesi
                            HashMap<String,Object> hashPost = new HashMap<String,Object>();
                            hashPost.put("comment",comment);
                            hashPost.put("email", email);
                            hashPost.put("date",date);
                            hashPost.put("URL",dowloadUri);
                            hashPost.put("ID",id);
                            hashPost.put("name",name);
                            hashPost.put("lesson",lessonRadioText);
                            hashPost.put("class",classRadioText);
                            hashPost.put("exam",examRadioText);
                            hashPost.put("title",title);

                            firebaseFirestore.collection("globalquestions").add(hashPost).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                               Intent intent = new Intent(UploadActivity.this,PremiumActivity.class);
                               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                               startActivity(intent);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                  Toast.makeText(UploadActivity.this,"Yüklenemedi",Toast.LENGTH_LONG).show();
                                    activityUploadBinding.buttonUpload.setEnabled(false);
                                }
                            });




                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UploadActivity.this,"Cant download URl of image",Toast.LENGTH_LONG).show();
                            activityUploadBinding.buttonUpload.setEnabled(false);
                        }
                    });



                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadActivity.this, "Dosya yüklenmedi",Toast.LENGTH_SHORT).show();
                    activityUploadBinding.buttonUpload.setEnabled(false);
                }
            });



        }


    }
    public void select(View view) {


        if (Build.VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(UploadActivity.this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_MEDIA_IMAGES)) {
                    Snackbar.make(view, "You have to give permission for this", Snackbar.LENGTH_INDEFINITE).setAction("Give permission", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //ask permission
                            toPermission.launch(Manifest.permission.READ_MEDIA_IMAGES);
                        }
                    }).show();
                } else {
                    //ask permission
                    toPermission.launch(Manifest.permission.READ_MEDIA_IMAGES);
                }
            } else {
                //gallery
                Intent intentToGallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                toGallery.launch(intentToGallery);
            }
        }
        else {
            if (ContextCompat.checkSelfPermission(UploadActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Snackbar.make(view, "You have to give permission for this", Snackbar.LENGTH_INDEFINITE).setAction("Give permission", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //ask permission
                            toPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                        }
                    }).show();
                } else {
                    //ask permission
                    toPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                }
            } else {
                //gallery
                Intent intentToGallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                toGallery.launch(intentToGallery);
            }

        }
    }

    public void set_Launchers() {
        toGallery = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                  if(result.getResultCode()==RESULT_OK){
                      Intent galleryintent = result.getData();
                      if(galleryintent!= null){ //gallery den resim alınınca yapılacaklar
                          imageUri= galleryintent.getData();

                         try {
                             if (Build.VERSION.SDK_INT >= 28) {

                                 ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), imageUri);
                                 selectedImage = ImageDecoder.decodeBitmap(source);
                                 activityUploadBinding.selectImage.setImageBitmap(selectedImage);


                             } else {
                                 selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                                 activityUploadBinding.selectImage.setImageBitmap(selectedImage);
                             }

                         }catch(Exception e) {
                             e.printStackTrace();
                         }

                      }
                  }
            }
        });

        toPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(result){ //Gallery
                   Intent intentToGallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                   toGallery.launch(intentToGallery);
                } else {
                    Toast.makeText(UploadActivity.this,"İzin Vermesseniz Yardımcı Olamayız",Toast.LENGTH_LONG).show();
                }
            }
        });
    }





}