package thaiph.ph48495.pdpnet.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

import thaiph.ph48495.pdpnet.DAO.RunningDAO;
import thaiph.ph48495.pdpnet.LoginActivity;
import thaiph.ph48495.pdpnet.R;
import thaiph.ph48495.pdpnet.models.User;

public class ProfileFragment extends Fragment {

    private TextView emailTextView;
    private TextView streakTextView;
    private TextView todayDistanceTextView;
    private TextView totalDistanceTextView;
    private RunningDAO runningDAO;
    private Button btnDoiMatKhau, btnDangXuat;
    private FirebaseAuth mAuth;
    private SharedPreferences pref;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        emailTextView = view.findViewById(R.id.emailTextView);
        streakTextView = view.findViewById(R.id.streakTextView);
        todayDistanceTextView = view.findViewById(R.id.todayDistanceTextView);
        totalDistanceTextView = view.findViewById(R.id.totalDistanceTextView);
        btnDoiMatKhau = view.findViewById(R.id.btnDoiMatKhau);
        btnDangXuat = view.findViewById(R.id.btnDangXuat);
        mAuth = FirebaseAuth.getInstance();
        pref = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);

        runningDAO = new RunningDAO(getContext());

        String userEmail = getCurrentUserEmail();
        int userStreak = runningDAO.getStreak();
        float todayDistance = runningDAO.getTodayDistance();
        float totalDistance = runningDAO.getTotalDistance();

        User user = new User(userEmail, userStreak, todayDistance, totalDistance);

        emailTextView.setText(user.getEmail());
        streakTextView.setText(String.valueOf(user.getStreak()));
        todayDistanceTextView.setText(String.format(Locale.getDefault(), "%.2f km", user.getTodayDistance()));
        totalDistanceTextView.setText(String.format(Locale.getDefault(), "%.2f km", user.getTotalDistance()));

        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePasswordFragment fm = new ChangePasswordFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fm).commit();
            }
        });

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Hello", Toast.LENGTH_SHORT).show();
                signOutUser();
            }
        });

        return view;
    }

    private String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", "");
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
}