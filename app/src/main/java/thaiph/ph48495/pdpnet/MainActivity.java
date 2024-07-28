package thaiph.ph48495.pdpnet;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import thaiph.ph48495.pdpnet.fragments.BietOnFragment;
import thaiph.ph48495.pdpnet.fragments.ProfileFragment;
import thaiph.ph48495.pdpnet.fragments.RunningFragment;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_navigation);

        // Set default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BietOnFragment()).commit();
        }

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_biet_on) {
                    BietOnFragment fm = new BietOnFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fm).commit();
                } else if (item.getItemId() == R.id.nav_running) {
                    RunningFragment fm = new RunningFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fm).commit();
                } else if (item.getItemId() == R.id.nav_profile){
                    ProfileFragment fm = new ProfileFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fm).commit();
                } else if (item.getItemId() == R.id.nav_history) {
                    Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                    startActivity(intent);
                }

                return true;
            }
        });
    }
}