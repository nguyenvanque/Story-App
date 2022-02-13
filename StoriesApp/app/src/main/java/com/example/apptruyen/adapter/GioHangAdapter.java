package com.example.apptruyen.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;


import com.example.apptruyen.Constrain;
import com.example.apptruyen.R;
import com.example.apptruyen.activity.giohang.DanhSachGioHangActivity;
import com.example.apptruyen.activity.loaitruyen.DanhSachLoaiTruyenActivity;
import com.example.apptruyen.model.GioHang;
import com.example.apptruyen.service.GioHangAPI;
import com.example.apptruyen.httpconection.Http.HttpAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder> {
    Context context;
    public static ArrayList<GioHang> arrayGioHang;
    HttpAdapter httpAdapter;
    GioHangAPI gioHangAPI;
    String email=DanhSachLoaiTruyenActivity.email;

    public GioHangAdapter(Context context, ArrayList<GioHang> arrayGioHang) {
        this.context = context;
        this.arrayGioHang = arrayGioHang;
        httpAdapter=new HttpAdapter(context);
        httpAdapter.setBaseUrl(Constrain.BASE_URL);
        gioHangAPI=httpAdapter.create(GioHangAPI.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_giohang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GioHang gioHang = arrayGioHang.get(position);
        holder.txtName.setText(gioHang.getTentruyen());
        holder.txtGia.setText(gioHang.getGia() + " $");
        holder.txtSoluong.setText(gioHang.getSoluong() + "");
        if(gioHang.getSoluong()==1){
            holder.btnTru.setEnabled(false);
        }else {
            holder.btnTru.setEnabled(true);
        }
        if (gioHang.getHinhtruyen().equals("")) {
            holder.imagetruyen.setImageResource(R.drawable.ic_gallery_grey);
        } else {
            Picasso.with(context).load(gioHang.getHinhtruyen()).placeholder(R.drawable.ic_gallery_grey).into(holder.imagetruyen);
        }
        holder.btnCong.setOnClickListener(v ->  {
            int soluongmoi = (Integer.parseInt(holder.txtSoluong.getText().toString()) + 1);
            int soluonghientai = gioHang.getSoluong();
            long giahientai = gioHang.getGia();

            gioHangAPI.updateSoLuong(gioHang.getMatruyen(), email,soluongmoi);
            long giamoinhat = (giahientai * soluongmoi) / soluonghientai;
            gioHangAPI.updateGia(gioHang.getMatruyen(), email,giamoinhat);
            holder.txtGia.setText(giamoinhat + " $");
            holder.txtSoluong.setText(soluongmoi+"");

            arrayGioHang.clear();
            arrayGioHang.addAll(gioHangAPI.getAllGioHangByEmail(email).getGiohang());
            notifyDataSetChanged();

            DanhSachGioHangActivity.tongTien();
        });
        holder.btnTru.setOnClickListener(v ->  {
            int soluongmoi = (Integer.parseInt(holder.txtSoluong.getText().toString()) - 1);
            int soluonghientai =gioHang.getSoluong();
            long giahientai = gioHang.getGia();

            gioHangAPI.updateSoLuong(gioHang.getMatruyen(),email,soluongmoi);
            long giamoinhat = (giahientai * soluongmoi) / soluonghientai;
            gioHangAPI.updateGia(gioHang.getMatruyen(),email,giamoinhat);

            holder.txtGia.setText(giamoinhat + " $");
            holder.txtSoluong.setText(soluongmoi+"");

            arrayGioHang.clear();
            arrayGioHang.addAll(gioHangAPI.getAllGioHangByEmail(email).getGiohang());
            notifyDataSetChanged();

            DanhSachGioHangActivity.tongTien();
        });
        holder.itemView.setOnLongClickListener(v ->  {

            Dialog dialog=new Dialog(context);
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

            btnXoa.setOnClickListener(v1 -> {
                gioHangAPI.deleteItem(gioHang.getId(),email);
                arrayGioHang.clear();
                arrayGioHang.addAll(gioHangAPI.getAllGioHangByEmail(email).getGiohang());
                notifyDataSetChanged();
                DanhSachGioHangActivity.tongTien();
                dialog.dismiss();
            });
            btnHuy.setOnClickListener(v1 -> {
                dialog.dismiss();
            });
            dialog.show();

            return false;
        });
    }

    @Override
    public int getItemCount() {
        return arrayGioHang.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView imagetruyen;
        TextView txtName, txtGia, txtSoluong;
        TextView btnCong, btnTru;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagetruyen = itemView.findViewById(R.id.imagetruyen);
            txtName = itemView.findViewById(R.id.txtName);
            txtGia = itemView.findViewById(R.id.txtGia);
            txtSoluong = itemView.findViewById(R.id.txtSoluong);
            btnCong = itemView.findViewById(R.id.btnCong);
            btnTru = itemView.findViewById(R.id.btnTru);

        }
    }
}
