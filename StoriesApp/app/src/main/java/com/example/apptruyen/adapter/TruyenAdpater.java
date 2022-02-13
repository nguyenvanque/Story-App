package com.example.apptruyen.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;


import com.example.apptruyen.Constrain;
import com.example.apptruyen.R;
import com.example.apptruyen.activity.truyen.ChiTietTruyenActivity;
import com.example.apptruyen.activity.truyen.DanhSachTruyenActivity;
import com.example.apptruyen.model.ListLike;
import com.example.apptruyen.model.Truyen;
import com.example.apptruyen.service.LikeAPI;
import com.example.apptruyen.service.TruyenAPI;
import com.example.apptruyen.httpconection.Http.HttpAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TruyenAdpater extends RecyclerView.Adapter<TruyenAdpater.ViewHolder> {

    Context context;
    ArrayList<Truyen> list;
    int layout;
    HttpAdapter httpAdapter;

    public TruyenAdpater(Context context, int layout, ArrayList<Truyen> list) {
        this.context = context;
        this.list = list;
        this.layout = layout;
        httpAdapter = new HttpAdapter(context);
        httpAdapter.setBaseUrl(Constrain.BASE_URL);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Truyen truyen = list.get(position);
        holder.txtTentruyen.setText(truyen.getTentruyen());
        holder.txtGia.setText("$" + truyen.getGia());
        holder.txtLuotruyCap.setText(truyen.getLuottruycap());

        LikeAPI likeAPI = httpAdapter.create(LikeAPI.class);

        ListLike countLike = likeAPI.getLikeByMaTruyen(truyen.getMatruyen());
        holder.txtLuotthich.setText(countLike.getLiketruyen().size() + "");

        if (truyen.getHinhtruyen().equals("")) {
            holder.imageTruyen.setImageResource(R.drawable.ic_gallery_grey);
        } else {
            Picasso.with(context).load(truyen.getHinhtruyen()).placeholder(R.drawable.ic_gallery_grey).into(holder.imageTruyen);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChiTietTruyenActivity.class);
            intent.putExtra("matruyen", truyen.getMatruyen());
            intent.putExtra("tentruyen", truyen.getTentruyen());
            intent.putExtra("hinhtruyen", truyen.getHinhtruyen());
            intent.putExtra("motatruyen", truyen.getMota());
            intent.putExtra("gia", truyen.getGia());
            intent.putExtra("maloai", truyen.getMaloai());
            context.startActivity(intent);

        });

        holder.itemView.setOnLongClickListener(v -> {

            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                    context, R.style.BottomSheetDialogTheme
            );

            View bottomSheetView = LayoutInflater.from(context).inflate(
                    R.layout.bottom_sheet,
                    (LinearLayout) bottomSheetDialog.findViewById(R.id.bottomSheetContainer)
            );

            bottomSheetView.findViewById(R.id.txt_Sua).setOnClickListener(v1 -> {
                showdialogUpdate(truyen.getMatruyen(), truyen.getTentruyen(), truyen.getHinhtruyen(), truyen.getMota(), truyen.getGia(), truyen.getMaloai());
                bottomSheetDialog.dismiss();
            });
            bottomSheetView.findViewById(R.id.txt_Xoa).setOnClickListener(v1 -> {
                deleteData(truyen.getMatruyen(), truyen.getMaloai());
                bottomSheetDialog.dismiss();
            });
            bottomSheetView.findViewById(R.id.txt_Huy).setOnClickListener(v1 -> {
                bottomSheetDialog.dismiss();
            });

            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
            return false;
        });

    }

    private void showdialogUpdate(int matruyen, String tentruyen, String hinhtruyen, String motatruyen, String gia, int maloai) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_suatruyen);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        EditText edtName = dialog.findViewById(R.id.editTentruyen);
        EditText edtImage = dialog.findViewById(R.id.editHinhtruyen);
        EditText edtMota = dialog.findViewById(R.id.editMota);
        EditText edtGia = dialog.findViewById(R.id.edtGia);
        Spinner spinnerMaloai = dialog.findViewById(R.id.spinerloaitruyen);
        edtName.setText(tentruyen);
        edtImage.setText(hinhtruyen);
        edtMota.setText(motatruyen);
        edtGia.setText(gia);
        AppCompatButton btnHuy = dialog.findViewById(R.id.btnHuy);
        AppCompatButton btnSua = dialog.findViewById(R.id.btnSua);
        btnSua.setOnClickListener(view -> {
            String name = edtName.getText().toString();
            String image = edtImage.getText().toString();
            String description = edtMota.getText().toString();
            String price = edtGia.getText().toString();
            if (edtName.equals("")) {
                Toast.makeText(context, "Vui lòng nhập tên loại", Toast.LENGTH_SHORT).show();
            } else if (edtName.equals("")) {
                Toast.makeText(context, "Vui lòng nhập hình", Toast.LENGTH_SHORT).show();
            } else if (edtMota.equals("")) {
                Toast.makeText(context, "Vui lòng nhập mô tả", Toast.LENGTH_SHORT).show();
            } else if (edtGia.equals("")) {
                Toast.makeText(context, "Vui lòng nhập giá", Toast.LENGTH_SHORT).show();
            } else {

                httpAdapter.setBaseUrl(Constrain.BASE_URL);
                TruyenAPI apiTruyen = httpAdapter.create(TruyenAPI.class);
                boolean status = apiTruyen.update(matruyen, name, image, description, price, maloai).status;

                list.clear();
                list.addAll(DanhSachTruyenActivity.truyenAPI.getByMaLoai(maloai).getTruyen());
                notifyDataSetChanged();
                if (status == true) {
                    Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Update thất bại", Toast.LENGTH_SHORT).show();

                }

                dialog.dismiss();
            }
        });
        btnHuy.setOnClickListener(v1 -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    private void deleteData(final int id, int maloai) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_option);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }


        TextView txtMessage = dialog.findViewById(R.id.txtMessage);
        txtMessage.setText("Bạn có muốn xóa hay không ?");

        AppCompatButton btnXoa = dialog.findViewById(R.id.btnXoa);
        AppCompatButton btnHuy = dialog.findViewById(R.id.btnHuy);

        btnXoa.setOnClickListener(v -> {
            httpAdapter.setBaseUrl(Constrain.BASE_URL);
            TruyenAPI apiTruyen = httpAdapter.create(TruyenAPI.class);
            boolean status = apiTruyen.delete(id, maloai).status;
            list.clear();
            list.addAll(DanhSachTruyenActivity.truyenAPI.getByMaLoai(maloai).getTruyen());
            notifyDataSetChanged();
            if (status == true) {
                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();

            }
            dialog.dismiss();

        });
        btnHuy.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView imageTruyen;
        TextView txtTentruyen, txtGia, txtLuotruyCap, txtLuotthich;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageTruyen = itemView.findViewById(R.id.imagetruyen);
            txtTentruyen = itemView.findViewById(R.id.txtTentruyen);
            txtGia = itemView.findViewById(R.id.txtGia);
            txtLuotruyCap = itemView.findViewById(R.id.txtLuotruyCap);
            txtLuotthich = itemView.findViewById(R.id.txtLuotthich);

        }
    }
}
