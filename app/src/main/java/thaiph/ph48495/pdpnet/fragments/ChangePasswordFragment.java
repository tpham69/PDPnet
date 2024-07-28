package thaiph.ph48495.pdpnet.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import thaiph.ph48495.pdpnet.LoginActivity;
import thaiph.ph48495.pdpnet.R;


public class ChangePasswordFragment extends Fragment {
    private EditText edtMatKhauCu, edtMatKhau, edtXacNhanMK, edtEmail;
    private Button btnXacNhan;
    private FirebaseAuth mAuth;
    private SharedPreferences pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtEmail = view.findViewById(R.id.edtEmail);
        edtMatKhauCu = view.findViewById(R.id.edtMatKhauCu);
        edtMatKhau = view.findViewById(R.id.edtMatKhau);
        edtXacNhanMK = view.findViewById(R.id.edtXacNhanMK);
        btnXacNhan = view.findViewById(R.id.btnXacNhan);
        mAuth = FirebaseAuth.getInstance();
        pref = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);


        fillEmail(); //fill email to edit text

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

    }


    private void changePassword() {
        String mkCu = edtMatKhauCu.getText().toString().trim();
        String mkMoi = edtMatKhau.getText().toString().trim();
        String xacNhanMK = edtXacNhanMK.getText().toString().trim();

        if (mkCu.equals("") || mkMoi.equals("") || xacNhanMK.equals("") ){
            Toast.makeText(getContext(), "Không để trống!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!mkMoi.equals(xacNhanMK)) {
            Toast.makeText(getContext(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        if(mkMoi.length() < 6){
            Toast.makeText(getContext(), "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), mkCu);
            user.reauthenticate(credential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    user.updatePassword(mkMoi).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            updateSharedPreferences(mkMoi);
                            Toast.makeText(getContext(), "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                            signOutUser(); //Nếu đổi mk thành công thì đăng xuất người dùng
                        } else {
                            Toast.makeText(getContext(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Mật khẩu cũ không đúng!", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void updateSharedPreferences(String newPassword) {
        SharedPreferences.Editor editor = pref.edit();
        if (pref.getBoolean("remember", false)) {
            editor.putString("password", newPassword);
        }
        editor.apply();
    }
    private void signOutUser() {
        mAuth.signOut();
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void fillEmail() {
        String email = pref.getString("email", "");
        edtEmail.setText(email);
    }
}