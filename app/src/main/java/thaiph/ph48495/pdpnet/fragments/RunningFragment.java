package thaiph.ph48495.pdpnet.fragments;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import thaiph.ph48495.pdpnet.DAO.RunningDAO;
import thaiph.ph48495.pdpnet.R;
import thaiph.ph48495.pdpnet.services.StepCounterService;

public class RunningFragment extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor stepCounterSensor;
    private int stepCount = 0;
    private TextView stepCountTextView;
    private RunningDAO runningDAO;
    private Button startButton, stopButton, setGoalButton;
    private EditText goalInput;
    private boolean isRunning = false;
    private float goalDistance = 0;

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

        // Disable start and stop
        startButton.setEnabled(false);
        stopButton.setEnabled(false);

        setGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goalText = goalInput.getText().toString();
                if (!goalText.isEmpty()) {
                    goalDistance = Float.parseFloat(goalText);
                    if (goalDistance >= 1) {
                        Toast.makeText(getContext(), "Mục tiêu đã được đặt: " + goalDistance + " km", Toast.LENGTH_SHORT).show();
                        // Enable start and stop buttons when a valid goal is set
                        startButton.setEnabled(true);
                        stopButton.setEnabled(true);
                        goalInput.setText("");
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
                isRunning = true;
                if (stepCounterSensor != null) {
                    sensorManager.registerListener(RunningFragment.this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
                }
                Intent serviceIntent = new Intent(getContext(), StepCounterService.class);
                getActivity().startService(serviceIntent);
                Toast.makeText(getContext(), "Hãy bắt đầu chạy", Toast.LENGTH_SHORT).show();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = false;
                if (stepCounterSensor != null) {
                    sensorManager.unregisterListener(RunningFragment.this, stepCounterSensor);
                }
                getActivity().stopService(new Intent(getContext(), StepCounterService.class));
            }
        });

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
            runningDAO.insertStepCount(stepCount);

            // Kiểm tra nếu đạt mục tiêu
            float distanceCovered = stepCount * 0.0008f;
            if (distanceCovered >= goalDistance) {
                Toast.makeText(getContext(), "Chúc mừng! Bạn đã đạt được mục tiêu.", Toast.LENGTH_SHORT).show();
                isRunning = false;
                if (stepCounterSensor != null) {
                    sensorManager.unregisterListener(RunningFragment.this, stepCounterSensor);
                }
                getActivity().stopService(new Intent(getContext(), StepCounterService.class));

                runningDAO.insertStepCount(stepCount);
                runningDAO.updateStreak();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}