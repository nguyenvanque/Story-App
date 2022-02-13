package com.example.apptruyen.activity.truyen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apptruyen.ConnectivityReceiver;
import com.example.apptruyen.Constrain;
import com.example.apptruyen.R;
import com.example.apptruyen.activity.giohang.DanhSachGioHangActivity;
import com.example.apptruyen.activity.loaitruyen.DanhSachLoaiTruyenActivity;
import com.example.apptruyen.adapter.TruyenAdpater;
import com.example.apptruyen.model.ListGioHang;
import com.example.apptruyen.model.ListTruyen;
import com.example.apptruyen.model.Truyen;
import com.example.apptruyen.service.GioHangAPI;
import com.example.apptruyen.service.TruyenAPI;
import com.example.apptruyen.statusbar.ColorStatusbar;
import com.example.apptruyen.httpconection.Http.HttpAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DanhSachTruyenActivity extends AppCompatActivity {
    int maloai;
    FloatingActionButton btnThem;
    TruyenAdpater truyenAdpater;
    RecyclerView recyclerView, rcvTruyenDexuat;
    ArrayList<Truyen> list, listDexuat;
    public static TruyenAPI truyenAPI;
    TextView txtData, txtSoluong;
    ImageView imageCart;
    String email = DanhSachLoaiTruyenActivity.email;
    GioHangAPI gioHangAPI;
    public static ListGioHang listGioHang;
    ConnectivityReceiver connectivityReceiver;
    NestedScrollView homeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_truyen);
        ColorStatusbar.setColorStatusBar(DanhSachTruyenActivity.this);
        list = new ArrayList<>();
        listDexuat = new ArrayList<>();

        btnThem = findViewById(R.id.btnThem);
        txtData = findViewById(R.id.txtData);
        txtSoluong = findViewById(R.id.txtSoluong);
        imageCart = findViewById(R.id.imageCart);
        recyclerView = findViewById(R.id.rcvTruyen);
        rcvTruyenDexuat = findViewById(R.id.rcvTruyenDexuat);
        homeLayout = findViewById(R.id.homeLayout);

        Intent intent = getIntent();
        maloai = intent.getIntExtra("maloai", 0);

        HttpAdapter adapter = new HttpAdapter(DanhSachTruyenActivity.this);
        adapter.setBaseUrl(Constrain.BASE_URL);
        truyenAPI = adapter.create(TruyenAPI.class);
        gioHangAPI = adapter.create(GioHangAPI.class);
        ListTruyen listTruyen = truyenAPI.getByMaLoai(maloai);
        listGioHang = gioHangAPI.getAllGioHangByEmail(email);
        txtSoluong.setText(listGioHang.getGiohang().size() + "");

        imageCart.setOnClickListener(v -> {
            startActivity(new Intent(DanhSachTruyenActivity.this, DanhSachGioHangActivity.class));
        });

        btnThem.setOnClickListener(v -> {
            Intent intent1 = new Intent(DanhSachTruyenActivity.this, InsertTruyenActivity.class);
            intent1.putExtra("maloai", maloai);
            startActivity(intent1);
        });

        list = listTruyen.getTruyen();
        if (list.size() == 0) {
            txtData.setVisibility(View.VISIBLE);
        } else {
            txtData.setVisibility(View.GONE);
        }

        for (int i = 0; i < listTruyen.getTruyen().size(); i++) {
            if (Integer.parseInt(listTruyen.getTruyen().get(i).getLuottruycap()) > 10) {
                Truyen truyen = new Truyen();
                truyen.setTentruyen(listTruyen.getTruyen().get(i).getTentruyen());
                truyen.setGia(listTruyen.getTruyen().get(i).getGia());
                truyen.setHinhtruyen(listTruyen.getTruyen().get(i).getHinhtruyen());
                truyen.setMota(listTruyen.getTruyen().get(i).getMota());
                truyen.setMatruyen(listTruyen.getTruyen().get(i).getMatruyen());
                truyen.setMaloai(listTruyen.getTruyen().get(i).getMaloai());
                truyen.setLuottruycap(listTruyen.getTruyen().get(i).getLuottruycap());
                listDexuat.add(truyen);
            }
        }
        truyenAdpater = new TruyenAdpater(DanhSachTruyenActivity.this, R.layout.item_truyen_dexuat, listDexuat);
        rcvTruyenDexuat.setAdapter(truyenAdpater);
        truyenAdpater = new TruyenAdpater(DanhSachTruyenActivity.this, R.layout.item_truyen, list);
        recyclerView.setAdapter(truyenAdpater);

    }

    @Override
    protected void onResume() {
        connectivityReceiver = new ConnectivityReceiver(DanhSachTruyenActivity.this, homeLayout);
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectivityReceiver, intentFilter);

        list.clear();
        list.addAll(truyenAPI.getByMaLoai(maloai).getTruyen());
        truyenAdpater.notifyDataSetChanged();

        listGioHang = gioHangAPI.getAllGioHangByEmail(email);
        txtSoluong.setText(listGioHang.getGiohang().size() + "");
        super.onResume();
    }
}