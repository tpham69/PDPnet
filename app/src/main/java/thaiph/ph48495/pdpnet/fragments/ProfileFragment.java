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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import thaiph.ph48495.pdpnet.DAO.BietOnDAO;
import thaiph.ph48495.pdpnet.DAO.RunningDAO;
import thaiph.ph48495.pdpnet.LoginActivity;
import thaiph.ph48495.pdpnet.R;
import thaiph.ph48495.pdpnet.models.Running;
import thaiph.ph48495.pdpnet.models.User;

public class ProfileFragment extends Fragment {

    private TextView todayDistanceTextView, totalDistanceTextView, emailTextView, streakBietOn, streakChayBo;
    private RunningDAO runningDAO;
    private Button btnDoiMatKhau, btnDangXuat;
    private FirebaseAuth mAuth;
    private SharedPreferences pref;
    private BietOnDAO bietOnDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        emailTextView = view.findViewById(R.id.emailTextView);
        streakChayBo = view.findViewById(R.id.streakTextView);
        streakBietOn = view.findViewById(R.id.streakGratitudeTextView);
        todayDistanceTextView = view.findViewById(R.id.todayDistanceTextView);
        totalDistanceTextView = view.findViewById(R.id.totalDistanceTextView);
        btnDoiMatKhau = view.findViewById(R.id.btnDoiMatKhau);
        btnDangXuat = view.findViewById(R.id.btnDangXuat);
        mAuth = FirebaseAuth.getInstance();
        pref = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);

        runningDAO = new RunningDAO(getContext());
        bietOnDAO = new BietOnDAO(getContext());


        String userEmail = getCurrentUserEmail();
        float todayDistance = getTodayDistance();
        float totalDistance = getTotalDistance();
        int chuoiBietOn = bietOnDAO.getStreakBietOn();
        int chuoiChayBo = runningDAO.getStreakChayBo();

        User user = new User(userEmail, chuoiBietOn, chuoiChayBo, todayDistance, totalDistance);

        emailTextView.setText(user.getEmail());
        streakBietOn.setText(String.valueOf(user.getStreakBietOn()));
        streakChayBo.setText(String.valueOf(user.getStreakChayBo()));
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
                signOutUser();
            }
        });

        return view;
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

    public float getTodayDistance() {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        List<Running> todayRunningList = runningDAO.getRunningByDate(currentDate);

        int totalSteps = 0;
        for (Running running : todayRunningList) {
            totalSteps += running.getSteps();
        }

        return totalSteps * 0.0008f;
    }

    public float getTotalDistance() {
        List<Running> runningList = runningDAO.getAllRunning();

        int totalSteps = 0;
        for (Running running : runningList) {
            totalSteps += running.getSteps();
        }
        return totalSteps * 0.0008f;
    }

    public String getCurrentUserEmail() {
        return mAuth.getCurrentUser().getEmail();
    }
}