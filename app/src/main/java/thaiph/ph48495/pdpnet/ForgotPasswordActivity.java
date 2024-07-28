package thaiph.ph48495.pdpnet;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Button btnGuiYC, btnQuayLai;
    private EditText edtEmail;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        btnGuiYC = findViewById(R.id.btnGuiYC);
        btnQuayLai = findViewById(R.id.btnQuayLai);
        edtEmail = findViewById(R.id.edtEmail);
        auth = FirebaseAuth.getInstance();

        btnGuiYC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                if(email.isEmpty()){
                    showDialog("Vui lòng nhập email");
                    return;
                }
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                edtEmail.setText("");
                                hideKeyboard(v);
                                showDialog("Chúng tôi đã gửi một email. Vui lòng kiểm tra email để đặt lại mật khẩu");
                            } else {
                                showDialog("Email không tồn tại");
                            }
                        });
            }
        });

        btnQuayLai.setOnClickListener(v -> {
            finish();
        });
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle("Thông báo");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.show();
    }
}