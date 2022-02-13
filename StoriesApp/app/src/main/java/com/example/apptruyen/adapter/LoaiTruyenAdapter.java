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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptruyen.Constrain;
import com.example.apptruyen.R;
import com.example.apptruyen.activity.loaitruyen.DanhSachLoaiTruyenActivity;
import com.example.apptruyen.activity.truyen.DanhSachTruyenActivity;
import com.example.apptruyen.model.LoaiTruyen;
import com.example.apptruyen.service.LoaiTruyenAPI;
import com.example.apptruyen.httpconection.Http.HttpAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaiTruyenAdapter extends RecyclerView.Adapter<LoaiTruyenAdapter.Viewholder>{

    Context context;
    ArrayList<LoaiTruyen> list;
    HttpAdapter adapter ;


    public LoaiTruyenAdapter(Context context, ArrayList<LoaiTruyen> list) {
        this.context = context;
        this.list = list;
        adapter = new HttpAdapter(context);
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_loaitryuen,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
       LoaiTruyen loaiTruyen=list.get(position);
       holder.txtLoaiTruyen.setText(loaiTruyen.getTenloai());
       if(!loaiTruyen.getHinhloai().equals("")){
           Picasso.with(context).load(loaiTruyen.getHinhloai()).placeholder(R.drawable.ic_gallery_grey).into(holder.imageLoaitruyen);

       }else {
           holder.imageLoaitruyen.setImageResource(R.drawable.ic_gallery_grey);
       }
       holder.itemView.setOnClickListener(v -> {
           Intent intent=new Intent(context, DanhSachTruyenActivity.class);
           intent.putExtra("maloai",loaiTruyen.getMaloai());
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

           bottomSheetView.findViewById(R.id.txt_Sua).setOnClickListener(v1 ->  {
                           bottomSheetDialog.dismiss();
               showdialogUpdate(loaiTruyen.getMaloai(),loaiTruyen.getTenloai(),loaiTruyen.getHinhloai());
           });
           bottomSheetView.findViewById(R.id.txt_Xoa).setOnClickListener(v1 -> {
               deleteData(loaiTruyen.getMaloai());
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

    private void showdialogUpdate(int maloai, String tenloai, String hinhloai) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_sualoai);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        EditText edtName=dialog.findViewById(R.id.editName);
        EditText edtImage=dialog.findViewById(R.id.editImage);
        edtName.setText(tenloai);
        edtImage.setText(hinhloai);
        AppCompatButton btnHuy=dialog.findViewById(R.id.btnHuy);
        AppCompatButton btnSua=dialog.findViewById(R.id.btnSua);
        btnSua.setOnClickListener(view -> {
            String name=edtName.getText().toString();
            String image=edtImage.getText().toString();
            String time= String.valueOf(System.currentTimeMillis());
            if(edtName.equals("")){
                Toast.makeText(context, "Vui lòng nhập tên loại", Toast.LENGTH_SHORT).show();
            }
               else     if(edtName.equals("")){
                Toast.makeText(context, "Vui lòng nhập hình", Toast.LENGTH_SHORT).show();
            }else {
                adapter.setBaseUrl(Constrain.BASE_URL);
                LoaiTruyenAPI apiLoaitruyen=adapter.create(LoaiTruyenAPI.class);
                boolean success=apiLoaitruyen.update(maloai,name,image).status;
                list.clear();
                list.addAll(DanhSachLoaiTruyenActivity.loaiTruyenAPI.getAll().getLoaitruyen());
                notifyDataSetChanged();
                if(success==true){
                    Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                }else {
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

    private void deleteData(final int id) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_option);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }


        TextView txtMessage=dialog.findViewById(R.id.txtMessage);
        txtMessage.setText("Bạn có muốn xóa hay không ?");

        AppCompatButton btnXoa=dialog.findViewById(R.id.btnXoa);
        AppCompatButton btnHuy=dialog.findViewById(R.id.btnHuy);

        btnXoa.setOnClickListener(v -> {
            adapter.setBaseUrl(Constrain.BASE_URL);
            LoaiTruyenAPI apiLoaitruyen=adapter.create(LoaiTruyenAPI.class);
            boolean success=apiLoaitruyen.delete(id).status;
            list.clear();
            list.addAll(DanhSachLoaiTruyenActivity.loaiTruyenAPI.getAll().getLoaitruyen());
            notifyDataSetChanged();
            if(success==true){
                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
            }else {
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

    class Viewholder extends RecyclerView.ViewHolder{
ImageView imageLoaitruyen;
TextView txtLoaiTruyen;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageLoaitruyen=itemView.findViewById(R.id.imageLoaitruyen);
            txtLoaiTruyen=itemView.findViewById(R.id.txtTenLoai);
        }
    }
}
