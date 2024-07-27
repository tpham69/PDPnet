package thaiph.ph48495.pdpnet.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import thaiph.ph48495.pdpnet.DAO.BietOnDAO;
import thaiph.ph48495.pdpnet.R;
import thaiph.ph48495.pdpnet.adapter.BietOnAdapter;
import thaiph.ph48495.pdpnet.models.Gratitude;


public class BietOnFragment extends Fragment {
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ImageView add;
    private Context context;
    private BietOnAdapter bietOnAdapter;
    private ArrayList<Gratitude> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_biet_on, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rcv_Bieton);
        BietOnDAO bietOnDAO = new BietOnDAO(getContext());
        list = bietOnDAO.getAllGratitude();
        bietOnAdapter= new BietOnAdapter(getContext(),list);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(bietOnAdapter);
        toolbar = view.findViewById(R.id.tb_menu);
        add = view.findViewById(R.id.btn_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
                View view = inflater.inflate(R.layout.layout_insert,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(view);
                TextView tv_date = view.findViewById(R.id.tv_dateis);
                EditText ed_entry = view.findViewById(R.id.ip_entry);
                tv_date.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
                builder.setTitle("Biết Ơn!");
                builder.setIcon(R.drawable.baseline_article_24);
                builder.setPositiveButton("Post", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Gratitude gratitude = new Gratitude();
                        gratitude.setEntry(ed_entry.getText().toString().trim());
                        gratitude.setDate(tv_date.getText().toString().trim());
                        long check = bietOnDAO.insertGratitude(gratitude);
                        if (check<0){
                            Toast.makeText(getContext().getApplicationContext(),"Post lỗi!",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getContext().getApplicationContext(),"Đã Post!",Toast.LENGTH_SHORT).show();
                        }
                        list = bietOnDAO.getAllGratitude();
                        bietOnAdapter = new BietOnAdapter(getContext(),list);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(bietOnAdapter);
                    }
                });
                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Oke không add!", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }

        });
    }
}