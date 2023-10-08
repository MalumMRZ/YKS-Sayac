package premiumMode.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.yusufmirza.theyksproject.R;
import com.yusufmirza.theyksproject.databinding.FragmentGlobalchatBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import premiumMode.HelperClasses.Post;
import premiumMode.UploadActivity;
import premiumMode.adapters.PostAdapter;


public class GlobalChatFragment extends Fragment {

   FragmentGlobalchatBinding fragmentGlobalchatBinding;
    PostAdapter postAdapter;
    public ArrayList<Post> posts;
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;

    AlertDialog.Builder filterDialog;
    Context context;

    public GlobalChatFragment(Context context){
        this.context = context;
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentGlobalchatBinding = FragmentGlobalchatBinding.inflate(inflater);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(fragmentGlobalchatBinding.toolBar);


        fragmentGlobalchatBinding.toolBarTitle.setText("Soru Odaları");

        setHasOptionsMenu(true);

        return fragmentGlobalchatBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        posts = new ArrayList<Post>();
        getDatafromDb();

        fragmentGlobalchatBinding.addQuestionGobal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(context, UploadActivity.class);
               startActivity(intent);
            }
        });

    }

    private void getDatafromDb(){

        firebaseFirestore.collection("globalquestions").
                limit(10).
                orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error !=null){
                    Toast.makeText(context,error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
                if(value != null) { //Veriyi al

                    List<DocumentSnapshot> documentSnapshotsList = value.getDocuments();

                    posts = new ArrayList<>();

                    for(DocumentSnapshot document : documentSnapshotsList ){
                        Map<String,Object> gelenharita = document.getData();
                        String URL = (String) gelenharita.get("URL");
                        String comment =(String) gelenharita.get("comment");
                        String email = (String) gelenharita.get("email");
                        String id = (String) gelenharita.get("ID");
                        String name = (String) gelenharita.get("name");
                        String title = (String) gelenharita.get("title");
                        String postLesson = (String) gelenharita.get("lesson");
                        String postClass = (String) gelenharita.get("class");
                        String postExam = (String) gelenharita.get("exam");

                        Timestamp timestamp = (Timestamp) gelenharita.get("date");


                        Post postObject=new Post(name,id,email,timestamp,URL,title,comment,postLesson,postClass,postExam);
                        posts.add(postObject);



                    }
                    fragmentGlobalchatBinding.globalChatRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                    postAdapter= new PostAdapter(posts);
                    fragmentGlobalchatBinding.globalChatRecyclerView.setAdapter(postAdapter);

                }
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.globalchat_menu, menu); // Fragment'a özgü menüyü ekleyin
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() == R.id.addFilter) {

            setFilterAlertDialog();


        }

        return super.onContextItemSelected(item);
    }



    public void setFilterAlertDialog(){

        filterDialog = new AlertDialog.Builder(context);

        View view = View.inflate(context,R.layout.filter_alertdialog,null);

        EditText editText = view.findViewById(R.id.commentText);
        RadioGroup radioLessonGroup = view.findViewById(R.id.radioLessonGroup);
        RadioGroup radioClassGroup = view.findViewById(R.id.radioClassGroup);
        RadioGroup radioExamGroup = view.findViewById(R.id.radioExamGroup);


        filterDialog.setView(view);


        filterDialog.setPositiveButton("Filtrele", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                CollectionReference collectionReference = firebaseFirestore.collection("globalquestions");

                int radioLessonId  = radioLessonGroup.getCheckedRadioButtonId();
                int radioClassId = radioClassGroup.getCheckedRadioButtonId();
                int radioExamId = radioExamGroup.getCheckedRadioButtonId();


                RadioButton selectedRadioLesson = view.findViewById(radioLessonId);
                RadioButton selectedClassLesson = view.findViewById(radioClassId);
                RadioButton selectedExamLesson = view.findViewById(radioExamId);

                if(selectedRadioLesson!=null){
                    String selectedRadio = selectedRadioLesson.getText().toString();
                    Query queryLesson = collectionReference.whereEqualTo("lesson",selectedRadio);
                }else {
                    String selectedRadio ="";
                }

                if(selectedClassLesson!=null){
                    String selectedClass = selectedClassLesson.getText().toString();
                    Query queryClass = collectionReference.whereEqualTo("class",selectedClass);
                }else {
                    String selectedClass ="";
                }

                if(selectedExamLesson!=null){
                    String selectedExam = selectedExamLesson.getText().toString();
                    Query queryExam = collectionReference.whereEqualTo("exam",selectedExam);
                }else {
                    String selectedExam ="";
                }


                if (!editText.getText().toString().equals("")){
                    String name = editText.getText().toString();
                    Query queryName = collectionReference.whereEqualTo("name",name);
                }else {
                    String name = "";
                }






            }
        }).setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });



    }






}
