package ir.shariaty.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {

    private static int timeout=5000;

    //lines
    View first,second,third,fourth,fifth,sixth;
    TextView note,sLogon;


    //Animation
    Animation topAnimation;
    Animation middleAnimation;
    Animation bottomAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);

        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        middleAnimation = AnimationUtils.loadAnimation(this,R.anim.middle_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        first = findViewById(R.id.first_line);
        second = findViewById(R.id.second_line);
        third = findViewById(R.id.third_line);
        fourth = findViewById(R.id.fourth_line);
        fifth = findViewById(R.id.fifth_line);
        sixth = findViewById(R.id.sixth_line);

        note = findViewById(R.id.note);
        sLogon = findViewById(R.id.tagLine);

        first.setAnimation(topAnimation);
        second.setAnimation(topAnimation);
        third.setAnimation(topAnimation);
        fourth.setAnimation(topAnimation);
        fifth.setAnimation(topAnimation);
        sixth.setAnimation(topAnimation);

        note.setAnimation(middleAnimation);
        sLogon.setAnimation(bottomAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        },timeout);

    }

    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();
        }
    }
}
