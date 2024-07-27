package thaiph.ph48495.pdpnet.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import thaiph.ph48495.pdpnet.DAO.BietOnDAO;
import thaiph.ph48495.pdpnet.R;
import thaiph.ph48495.pdpnet.models.Gratitude;

public class BietOnAdapter extends RecyclerView.Adapter<BietOnAdapter.BietOnViewHolder> {
    private ArrayList<Gratitude> bietOnList;
    private Context context;
    BietOnDAO bietOnDAO;

    public BietOnAdapter(Context context, ArrayList<Gratitude> bietOnList) {
        this.context = context;
        this.bietOnList = bietOnList;
        bietOnDAO = new BietOnDAO(context);
    }
    @NonNull
    @Override
    public BietOnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_bieton, parent, false);
        return new BietOnViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BietOnViewHolder holder, int position) {
        Gratitude gratitude = bietOnList.get(position);
        holder.tvDate.setText(gratitude.getDate());
        holder.tvGratitude.setText(gratitude.getEntry());
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo");
                builder.setIcon(R.drawable.baseline_clear_24);
                builder.setMessage("Bạn có chắc chắn muốn xoá không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            long check = bietOnDAO.delete(bietOnList.get(position).getID());
                        if (check == 1){
                            bietOnList.clear();
                            bietOnList = bietOnDAO.getAllGratitude();
                            Toast.makeText(context, "Xoá Thành Công", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(context, "Xoá thất bại rồi nhé!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Oke không xoá!", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return bietOnList.size();
    }
    public static class BietOnViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvGratitude;
        ImageView btn_delete;

        public BietOnViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvGratitude = itemView.findViewById(R.id.tv_entry);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
