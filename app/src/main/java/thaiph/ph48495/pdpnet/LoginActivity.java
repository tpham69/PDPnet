package thaiph.ph48495.pdpnet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    String TAG = "zzzzzzzz";

    //FireBase
    FirebaseAuth auth;
    Button btnDangNhap, btnDangKy;
    TextView btnQuenMatKhau;
    TextInputEditText edtTaiKhoan, edtMatKhau;
    CheckBox chkGhiNho;
    //SharedPreferences
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(this);
        //Mapping
        auth = FirebaseAuth.getInstance();
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangKy = findViewById(R.id.btnDangKy);
        btnQuenMatKhau = findViewById(R.id.btnQuenMatKhau);
        edtTaiKhoan = findViewById(R.id.edtTaiKhoan);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        chkGhiNho = findViewById(R.id.chkGhiNho);
        chkGhiNho = findViewById(R.id.chkGhiNho);
        pref = getSharedPreferences("user_info", MODE_PRIVATE);
        editor = pref.edit();

        // Load saved email, password
        loadSavedCredentials();

        //Xử lý các sự kiện
        btnDangNhap.setOnClickListener(v -> {
            String email = edtTaiKhoan.getText().toString();
            String password = edtMatKhau.getText().toString();

            if(!email.equals("") && !password.equals("")){
                loginUser(email, password);
            } else {
                Toast.makeText(LoginActivity.this, "Không để trống email hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
            }
        });

        //Chuyển sang màn hình đăng ký
        btnDangKy.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });

        btnQuenMatKhau.setOnClickListener(v -> {
            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }


    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = auth.getCurrentUser();

                        if (chkGhiNho.isChecked()) {
                            editor.putString("email", email);
                            editor.putString("password", password);
                            editor.putBoolean("remember", true);
                        } else {
                            editor.clear();
                        }
                        editor.apply();

                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadSavedCredentials() {
        String savedEmail = pref.getString("email", "");
        String savedPassword = pref.getString("password", "");
        boolean isRemembered = pref.getBoolean("remember", false);

        if(isRemembered){
            edtTaiKhoan.setText(savedEmail);
            edtMatKhau.setText(savedPassword);
            chkGhiNho.setChecked(isRemembered);
        }
    }
}