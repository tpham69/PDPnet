package thaiph.ph48495.pdpnet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";

    // Firebase
    private FirebaseAuth auth;
    private Button btnDangKy, btnQuayLai;
    private TextInputEditText edtTaiKhoan, edtMatKhau, edtXacNhanMK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        // Mappingg
        auth = FirebaseAuth.getInstance();
        btnDangKy = findViewById(R.id.btnDangKy);
        btnQuayLai = findViewById(R.id.btnQuayLai);
        edtTaiKhoan = findViewById(R.id.tiedtTaiKhoan);
        edtMatKhau = findViewById(R.id.tiedtMatKhau);
        edtXacNhanMK = findViewById(R.id.tiedtXacNhanMK);


        btnDangKy.setOnClickListener(v -> {
            String email = edtTaiKhoan.getText().toString();
            String password = edtMatKhau.getText().toString();
            String confirmPassword = edtXacNhanMK.getText().toString();

            if (isValidEmail(email) && isValidPassword(password) && password.equals(confirmPassword)) {
                registerUser(email, password);
            } else {
                if (!isValidEmail(email)) {
                    Toast.makeText(SignUpActivity.this, "Email không hợp lệ!", Toast.LENGTH_SHORT).show();
                } else if (!isValidPassword(password)) {
                    Toast.makeText(SignUpActivity.this, "Mật khẩu phải có ít nhất 6 kí tự!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignUpActivity.this, "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnQuayLai.setOnClickListener(v -> finish());
    }

    private boolean isValidEmail(String email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = auth.getCurrentUser();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(SignUpActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                }
            });
    }


}