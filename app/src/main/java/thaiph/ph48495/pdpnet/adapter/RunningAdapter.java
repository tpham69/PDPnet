package thaiph.ph48495.pdpnet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import thaiph.ph48495.pdpnet.R;
import thaiph.ph48495.pdpnet.models.Running;

public class RunningAdapter extends RecyclerView.Adapter<RunningAdapter.RunningViewHolder> {

    private Context context;
    private List<Running> runningList;

    public RunningAdapter(Context context, List<Running> runningList) {
        this.context = context;
        this.runningList = runningList;
    }

    @NonNull
    @Override
    public RunningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_running, parent, false);
        return new RunningViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RunningViewHolder holder, int position) {
        Running running = runningList.get(position);
        holder.tvDate.setText(running.getDate());
        holder.tvStep.setText("Số bước chân: " + running.getSteps());
        holder.tvKilometer.setText(String.format("Số Km: %.2f", running.getSteps() * 0.0008));
    }

    @Override
    public int getItemCount() {
        return runningList.size();
    }


    public static class RunningViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvStep, tvKilometer;

        public RunningViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvStep = itemView.findViewById(R.id.tvStep);
            tvKilometer = itemView.findViewById(R.id.tvKilometer);
        }
    }
}