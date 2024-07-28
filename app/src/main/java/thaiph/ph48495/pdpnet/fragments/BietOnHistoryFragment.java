package thaiph.ph48495.pdpnet.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import thaiph.ph48495.pdpnet.DAO.BietOnDAO;
import thaiph.ph48495.pdpnet.R;
import thaiph.ph48495.pdpnet.adapter.BietOnAdapter;
import thaiph.ph48495.pdpnet.models.BietOn;


public class BietOnHistoryFragment extends Fragment {

    String TAG = "zzzzzzzzz";
    private BietOnAdapter bietOnAdapter;
    private BietOnDAO bietOnDAO;
    private List<BietOn> bietOnList;
    private RecyclerView recyclerViewBietOn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_biet_on_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Mappinng
        recyclerViewBietOn = view.findViewById(R.id.recyclerViewBietOn);
        recyclerViewBietOn.setLayoutManager(new LinearLayoutManager(getContext()));

        //get data
        bietOnDAO = new BietOnDAO(getContext());
        bietOnList = bietOnDAO.getAllBietOn();
        Log.d(TAG, "onViewCreated: size of list" + bietOnList.size());
        //set adapter
        bietOnAdapter = new BietOnAdapter(getContext(), bietOnList);
        recyclerViewBietOn.setAdapter(bietOnAdapter);

    }
}