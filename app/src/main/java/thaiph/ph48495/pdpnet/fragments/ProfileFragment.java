package thaiph.ph48495.pdpnet.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Locale;

import thaiph.ph48495.pdpnet.DAO.RunningDAO;
import thaiph.ph48495.pdpnet.R;
import thaiph.ph48495.pdpnet.models.User;

public class ProfileFragment extends Fragment {

    private TextView emailTextView;
    private TextView streakTextView;
    private TextView todayDistanceTextView;
    private TextView totalDistanceTextView;
    private RunningDAO runningDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        emailTextView = view.findViewById(R.id.emailTextView);
        streakTextView = view.findViewById(R.id.streakTextView);
        todayDistanceTextView = view.findViewById(R.id.todayDistanceTextView);
        totalDistanceTextView = view.findViewById(R.id.totalDistanceTextView);

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

        return view;
    }

    private String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", "");
    }
}