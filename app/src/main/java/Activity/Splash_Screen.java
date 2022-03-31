package Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Activity.R;

import java.util.Objects;

public class Splash_Screen extends AppCompatActivity {

    Animation topAni, botAni;
    ImageView logo;
    TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        init();

    }

    private void init() {
        topAni = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        topAni.setDuration(2000);
        botAni = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        botAni.setDuration(2000);
        logo = findViewById(R.id.splash_logo);
        welcome = findViewById(R.id.welcome);
        logo.setAnimation(topAni);
        welcome.setAnimation(botAni);
        nextActivity();
    }

    private void nextActivity() {
        int SPLASH_TIME = 4000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash_Screen.this, Welcome.class));
                finish();
            }
        }, SPLASH_TIME);
    }
}