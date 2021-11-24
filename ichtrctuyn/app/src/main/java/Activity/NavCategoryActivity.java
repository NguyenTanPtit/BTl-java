package Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.Activity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import Adapter.NavCategoryAdapter;
import Model.NavCategoryModel;
import Model.PopularModel;
import Model.ViewallModel;

public class NavCategoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<NavCategoryModel>navCategoryModelList;
    NavCategoryAdapter navCategoryAdapter;

    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_nav_category);
        String type= getIntent().getStringExtra("type");
        recyclerView=findViewById(R.id.nav_det_cat_rec);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        db=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        navCategoryModelList=new ArrayList<>();

        navCategoryAdapter=new NavCategoryAdapter(this,navCategoryModelList);

        recyclerView.setAdapter(navCategoryAdapter);
        if(type!=null&& type.equalsIgnoreCase("Sữa")){
            db.collection("NavCatDetailed").whereEqualTo("type","Sữa").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        NavCategoryModel navCategoryModel= documentSnapshot.toObject(NavCategoryModel.class);
                        navCategoryModelList.add(navCategoryModel);
                        navCategoryAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        if(type!=null&& type.equalsIgnoreCase("Thịt")){
            db.collection("NavCatDetailed").whereEqualTo("type","Thịt").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        NavCategoryModel navCategoryModel= documentSnapshot.toObject(NavCategoryModel.class);
                        navCategoryModelList.add(navCategoryModel);
                        navCategoryAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        if(type!=null&& type.equalsIgnoreCase("Rau củ")){
            db.collection("NavCatDetailed").whereEqualTo("type","Rau củ").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        NavCategoryModel navCategoryModel= documentSnapshot.toObject(NavCategoryModel.class);
                        navCategoryModelList.add(navCategoryModel);
                        navCategoryAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        if(type!=null&& type.equalsIgnoreCase("Cá")){
            db.collection("NavCatDetailed").whereEqualTo("type","Cá").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        NavCategoryModel navCategoryModel= documentSnapshot.toObject(NavCategoryModel.class);
                        navCategoryModelList.add(navCategoryModel);
                        navCategoryAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        if(type!=null&& type.equalsIgnoreCase("Hoa quả")){
            db.collection("NavCatDetailed").whereEqualTo("type","Hoa quả").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        NavCategoryModel navCategoryModel= documentSnapshot.toObject(NavCategoryModel.class);
                        navCategoryModelList.add(navCategoryModel);
                        navCategoryAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

    }

}