package thaiph.ph48495.pdpnet.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import thaiph.ph48495.pdpnet.DAO.BietOnDAO;
import thaiph.ph48495.pdpnet.R;
import thaiph.ph48495.pdpnet.models.BietOn;

public class BietOnAdapter extends RecyclerView.Adapter<BietOnAdapter.BietOnViewHolder> {

    private Context context;
    private List<BietOn> bietOnList;

    public BietOnAdapter(Context context, List<BietOn> bietOnList) {
        this.context = context;
        this.bietOnList = bietOnList;
    }

    @NonNull
    @Override
    public BietOnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bieton, parent, false);
        return new BietOnViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BietOnViewHolder holder, int position) {
        BietOn bietOn = bietOnList.get(position);
        holder.tvDate.setText(bietOn.getDate());

        //Sửa looix lặp lại dữ liệu
        holder.tvContents.setText("");

        int i = 1;
        for (String content : bietOn.getContents()) {
            holder.tvContents.append(i+". " + content + "\n");
            i++;
        }

        //BtnSua
        holder.btnSua.setOnClickListener(v -> {
            showEditDialog(bietOn, position);
        });
    }

    @Override
    public int getItemCount() {
        return bietOnList.size();
    }

    public static class BietOnViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserId, tvContents, tvDate;
        ImageView btnSua;

        public BietOnViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContents = itemView.findViewById(R.id.tvContents);
            tvDate = itemView.findViewById(R.id.tvDate);
            btnSua = itemView.findViewById(R.id.btnSua);
        }
    }

    private void showEditDialog(BietOn bietOn, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog_bieton, null);
        builder.setView(dialogView);

        BietOnDAO bietOnDAO = new BietOnDAO(context);

        EditText edtBietOn1 = dialogView.findViewById(R.id.edtBietOn1);
        EditText edtBietOn2 = dialogView.findViewById(R.id.edtBietOn2);
        EditText edtBietOn3 = dialogView.findViewById(R.id.edtBietOn3);
        EditText edtBietOn4 = dialogView.findViewById(R.id.edtBietOn4);
        EditText edtBietOn5 = dialogView.findViewById(R.id.edtBietOn5);
        Button btnXacNhan = dialogView.findViewById(R.id.btnXacNhanBietOn);
        Button btnHuy = dialogView.findViewById(R.id.btnHuy);

        List<String> contents = bietOn.getContents();
        edtBietOn1.setText(contents.get(0));
        edtBietOn2.setText(contents.get(1));
        edtBietOn3.setText(contents.get(2));
        edtBietOn4.setText(contents.get(3));
        edtBietOn5.setText(contents.get(4));

        AlertDialog dialog = builder.create();

        btnXacNhan.setOnClickListener(v -> {
            String content1 = edtBietOn1.getText().toString();
            String content2 = edtBietOn2.getText().toString();
            String content3 = edtBietOn3.getText().toString();
            String content4 = edtBietOn4.getText().toString();
            String content5 = edtBietOn5.getText().toString();

            if (content1.isEmpty() || content2.isEmpty() || content3.isEmpty() || content4.isEmpty() || content5.isEmpty()) {
                Toast.makeText(context, "Vui lòng điền đủ 5 điều biết ơn", Toast.LENGTH_SHORT).show();
                return;
            }

            List<String> updatedContents = Arrays.asList(content1, content2, content3, content4, content5);
            bietOn.setContents(updatedContents);
            bietOnDAO.updateBietOn(bietOn);

            bietOnList.set(position, bietOn);
            notifyItemChanged(position);
            dialog.dismiss();
            Toast.makeText(context, "Đã cập nhật 5 điều biết ơn", Toast.LENGTH_SHORT).show();
        });

        btnHuy.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }
}