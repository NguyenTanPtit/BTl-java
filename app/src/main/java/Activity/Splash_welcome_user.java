package Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Activity.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

import user.User;

public class Splash_welcome_user extends AppCompatActivity {

    Animation welcome_user;
    TextView welcome;
    FirebaseUser user;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_welcome_user);
        init();
    }
    private void init(){

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        final ArrayList<User> users= new ArrayList<>();
        reference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user1 = snapshot.getValue(User.class);
                users.add(user1);
                String userName = users.get(0).getName();
                welcome_user = AnimationUtils.loadAnimation(Splash_welcome_user.this,R.anim.welcome_user_animation);
                welcome = findViewById(R.id.welcome_user);
                welcome.setText("Xin chào "+ userName);
                welcome.setAnimation(welcome_user);
                nextActivity();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Splash_welcome_user.this, "Lỗi"+error.getMessage()+"!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void nextActivity() {
        int SPLASH_TIME = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash_welcome_user.this, Normal_User.class));
                finish();
            }
        }, SPLASH_TIME);
    }
}