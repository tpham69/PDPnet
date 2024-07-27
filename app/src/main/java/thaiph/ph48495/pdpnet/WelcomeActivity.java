package thaiph.ph48495.pdpnet;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);


        new CountDownTimer(3000, 1000){
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Intent i = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(i);
            }
        }.start();
    }
}