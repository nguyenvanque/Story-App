package com.example.apptruyen.activity.giohang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.apptruyen.Constrain;
import com.example.apptruyen.R;
import com.example.apptruyen.activity.loaitruyen.DanhSachLoaiTruyenActivity;
import com.example.apptruyen.adapter.GioHangAdapter;
import com.example.apptruyen.model.GioHang;
import com.example.apptruyen.model.ListGioHang;
import com.example.apptruyen.service.GioHangAPI;
import com.example.apptruyen.statusbar.ColorStatusbar;
import com.example.apptruyen.httpconection.Http.HttpAdapter;

import java.util.ArrayList;

public class DanhSachGioHangActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    public static ArrayList<GioHang> listGioHang;
    GioHangAdapter gioHangAdapter;
    HttpAdapter httpAdapter;
    public static GioHangAPI gioHangAPI;
    static TextView txtTongcong;
    String email = DanhSachLoaiTruyenActivity.email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_gio_hang);
        ColorStatusbar.setColorStatusBar(DanhSachGioHangActivity.this);
        httpAdapter = new HttpAdapter(DanhSachGioHangActivity.this);
        httpAdapter.setBaseUrl(Constrain.BASE_URL);
        gioHangAPI = httpAdapter.create(GioHangAPI.class);

        recyclerView = findViewById(R.id.rcvGioHang);
        txtTongcong = findViewById(R.id.txtTongcong);
        listGioHang = new ArrayList<>();

        ListGioHang arrdata = gioHangAPI.getAllGioHangByEmail(email);
        listGioHang = arrdata.getGiohang();
        gioHangAdapter = new GioHangAdapter(DanhSachGioHangActivity.this, listGioHang);
        recyclerView.setAdapter(gioHangAdapter);

        tongTien();


    }

    public static void tongTien() {
        long tongGia = 0;
        for (int i = 0; i < listGioHang.size(); i++) {
            tongGia += listGioHang.get(i).getGia();
        }
        txtTongcong.setText("Tổng cộng: " + tongGia + "$");

    }

    @Override
    protected void onResume() {
        listGioHang.clear();
        listGioHang.addAll(gioHangAPI.getAllGioHangByEmail(email).getGiohang());
        gioHangAdapter.notifyDataSetChanged();
        super.onResume();
    }
}