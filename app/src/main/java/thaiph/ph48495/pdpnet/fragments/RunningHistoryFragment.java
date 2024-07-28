package thaiph.ph48495.pdpnet.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import thaiph.ph48495.pdpnet.DAO.RunningDAO;
import thaiph.ph48495.pdpnet.R;
import thaiph.ph48495.pdpnet.adapter.RunningAdapter;
import thaiph.ph48495.pdpnet.models.Running;

public class RunningHistoryFragment extends Fragment {

    private RunningAdapter runningAdapter;
    private RunningDAO runningDAO;
    private List<Running> runningList;
    private RecyclerView recyclerViewRunning;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_running_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewRunning = view.findViewById(R.id.recycler_view_running);
        recyclerViewRunning.setLayoutManager(new LinearLayoutManager(getContext()));

        runningDAO = new RunningDAO(getContext());
        runningList = runningDAO.getAllRunning();

        // Set Adapter
        runningAdapter = new RunningAdapter(getContext(), runningList);
        recyclerViewRunning.setAdapter(runningAdapter);
    }
}