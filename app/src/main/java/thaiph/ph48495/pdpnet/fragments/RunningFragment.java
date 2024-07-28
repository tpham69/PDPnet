package thaiph.ph48495.pdpnet.fragments;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import thaiph.ph48495.pdpnet.DAO.RunningDAO;
import thaiph.ph48495.pdpnet.DAO.UserDAO;
import thaiph.ph48495.pdpnet.R;
import thaiph.ph48495.pdpnet.models.Running;
import thaiph.ph48495.pdpnet.services.StepCounterService;

public class RunningFragment extends Fragment implements SensorEventListener {

    String TAG = "zzzzzzzzzzzz";
    private static final int PERMISSION_REQUEST_CODE = 100 ;
    private SensorManager sensorManager;
    private Sensor stepCounterSensor;
    private int stepCount = 1000;
    private TextView stepCountTextView;
    private RunningDAO runningDAO;
    private UserDAO userDAO;
    private Button startButton, stopButton, setGoalButton;
    private EditText goalInput;
    private boolean isRunning = false;
    private float goalDistance = 0;
    private TextView tvKilometer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_running, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stepCountTextView = view.findViewById(R.id.tvSoBuoc);
        startButton = view.findViewById(R.id.start_button);
        stopButton = view.findViewById(R.id.stop_button);
        setGoalButton = view.findViewById(R.id.set_goal_button);
        goalInput = view.findViewById(R.id.goal_input);
        sensorManager = (SensorManager) getActivity().getSystemService(getContext().SENSOR_SERVICE);
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        runningDAO = new RunningDAO(getContext());
        userDAO = new UserDAO(getContext());
        tvKilometer = view.findViewById(R.id.tvKilometer);

        // Disable start and stop
        startButton.setEnabled(false);
        stopButton.setEnabled(false);

        setGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goalText = goalInput.getText().toString();

                // Reset UI
                stepCount = 0;
                stepCountTextView.setText(String.valueOf(stepCount));
                tvKilometer.setText("Tương đương đã đi: 0 km");
                
                // Reset sensor
                if (stepCounterSensor != null) {
                    sensorManager.unregisterListener(RunningFragment.this, stepCounterSensor);
                    sensorManager.registerListener(RunningFragment.this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
                }

                if (!goalText.isEmpty()) {
                    goalDistance = Float.parseFloat(goalText);
                    if (goalDistance >= 1) {
                        Toast.makeText(getContext(), "Mục tiêu đã được đặt: " + goalDistance + " km", Toast.LENGTH_SHORT).show();

                        startButton.setEnabled(true);
                        stopButton.setEnabled(true);
                        goalInput.setText("");
                        hideKeyboard(v);
                    } else {
                        Toast.makeText(getContext(), "Mục tiêu phải lớn hơn hoặc tối thiểu 1 km", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Vui lòng nhập mục tiêu", Toast.LENGTH_SHORT).show();
                }
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissions()) {
//                    simulateStepCount(true); //test inscrement step count, nhớ xóa khi hoàn thành
                    startRunningService();
                    //disable all other buttons

                    setGoalButton.setEnabled(false);
                    startButton.setEnabled(false);
                    goalInput.setEnabled(false);
                } else {
                    requestPermissions();
                }
            }
        });


        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = false;
                if (stepCounterSensor != null) {
//                    simulateStepCount(false); //test inscrement step count, nhớ xóa khi hoàn thành
                    tvKilometer.setText("Tương đương đã đi: 0 km");
                    stepCountTextView.setText("0");
                    sensorManager.unregisterListener(RunningFragment.this, stepCounterSensor);
                }
                getActivity().stopService(new Intent(getContext(), StepCounterService.class));
                // re-enable all buttons
                setGoalButton.setEnabled(true);
                startButton.setEnabled(true);
                goalInput.setEnabled(true);
            }
        });

    }

    private boolean checkPermissions() {
        return ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startRunningService();
            } else {
                Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startRunningService() {
        isRunning = true;
        if (stepCounterSensor != null) {

            sensorManager.registerListener(RunningFragment.this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        Intent serviceIntent = new Intent(getContext(), StepCounterService.class);
        getActivity().startService(serviceIntent);
        Toast.makeText(getContext(), "Hãy bắt đầu chạy", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isRunning && stepCounterSensor != null) {
            sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (stepCounterSensor != null) {
            sensorManager.unregisterListener(this, stepCounterSensor);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            stepCount = (int) event.values[0];
            stepCountTextView.setText(String.valueOf(stepCount));

            // Calculate distance in kilometers and update the new TextView
            float distanceCovered = stepCount * 0.0008f;
            tvKilometer.setText(String.format("Tương đương đã đi: %.2f km", distanceCovered));

            Log.d(TAG, "onSensorChanged: stepCount: " + stepCount);
            Log.d(TAG, "onSensorChanged: distanceCovered: " + distanceCovered +" ----- "+ " goalDistance: " + goalDistance);
            // Kiểm tra nếu đạt mục tiêu
            if (distanceCovered >= goalDistance) {
                Toast.makeText(getContext(), "Chúc mừng! Bạn đã đạt được mục tiêu.", Toast.LENGTH_SHORT).show();
                isRunning = false;
                if (stepCounterSensor != null) {
                    sensorManager.unregisterListener(RunningFragment.this, stepCounterSensor);
                }
                getActivity().stopService(new Intent(getContext(), StepCounterService.class));
                int userId = getUserID();
                String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                runningDAO.insertRunning(new Running(0, userId, currentDate, stepCount));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private int getUserID(){
        Intent i = getActivity().getIntent();
        return userDAO.getIDbyEmail(i.getStringExtra("email"));
    }
}