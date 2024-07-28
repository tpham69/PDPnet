package thaiph.ph48495.pdpnet.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import thaiph.ph48495.pdpnet.DAO.BietOnDAO;
import thaiph.ph48495.pdpnet.DAO.UserDAO;
import thaiph.ph48495.pdpnet.R;
import thaiph.ph48495.pdpnet.models.BietOn;

public class BietOnFragment extends Fragment {

    private EditText edtBietOn1, edtBietOn2, edtBietOn3, edtBietOn4, edtBietOn5;
    private Button btnSave, btnXoa;
    private TextView tvNgayThangNam;
    private BietOnDAO bietOnDAO;
    private UserDAO userDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_biet_on, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtBietOn1 = view.findViewById(R.id.edtBietOn1);
        edtBietOn2 = view.findViewById(R.id.edtBietOn2);
        edtBietOn3 = view.findViewById(R.id.edtBietOn3);
        edtBietOn4 = view.findViewById(R.id.edtBietOn4);
        edtBietOn5 = view.findViewById(R.id.edtBietOn5);
        tvNgayThangNam = view.findViewById(R.id.tvNgayThangNam);
        btnSave = view.findViewById(R.id.btnSave);
        btnXoa = view.findViewById(R.id.btnXoa);
        bietOnDAO = new BietOnDAO(getContext());
        userDAO = new UserDAO(getContext());

        btnSave.setOnClickListener(v -> saveBietOn());
        btnXoa.setOnClickListener(v -> clearFields());

        setFormattedDate();
    }

    private void setFormattedDate() {
        String currentDate = new SimpleDateFormat("dd 'Tháng' MM 'Năm' yyyy", Locale.getDefault()).format(new Date());
        tvNgayThangNam.setText("Ngày " + currentDate);
    }

    private void saveBietOn() {
        String content1 = edtBietOn1.getText().toString().trim();
        String content2 = edtBietOn2.getText().toString().trim();
        String content3 = edtBietOn3.getText().toString().trim();
        String content4 = edtBietOn4.getText().toString().trim();
        String content5 = edtBietOn5.getText().toString().trim();

        if (content1.isEmpty() || content2.isEmpty() || content3.isEmpty() || content4.isEmpty() || content5.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng điền đủ 5 điều biết ơn", Toast.LENGTH_SHORT).show();
            return;
        }

        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        List<String> contents = Arrays.asList(content1, content2, content3, content4, content5);
        BietOn bietOn = new BietOn(0, contents, currentDate, getUserID());

        bietOnDAO.insertBietOn(bietOn);

        Toast.makeText(getContext(), "Đã lưu 5 điều biết ơn", Toast.LENGTH_SHORT).show();
        clearFields();
    }

    private void clearFields() {
        edtBietOn1.setText("");
        edtBietOn2.setText("");
        edtBietOn3.setText("");
        edtBietOn4.setText("");
        edtBietOn5.setText("");
    }

    private int getUserID(){
        Intent i = getActivity().getIntent();
        return userDAO.getIDbyEmail(i.getStringExtra("email"));
    }
}