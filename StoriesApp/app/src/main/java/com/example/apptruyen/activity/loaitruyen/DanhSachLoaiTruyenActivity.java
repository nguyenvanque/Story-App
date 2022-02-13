package com.example.apptruyen.activity.loaitruyen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.apptruyen.ConnectivityReceiver;
import com.example.apptruyen.Constrain;
import com.example.apptruyen.R;
import com.example.apptruyen.activity.auth.ProfileActivity;
import com.example.apptruyen.adapter.LoaiTruyenAdapter;
import com.example.apptruyen.model.LoaiTruyen;
import com.example.apptruyen.service.LoaiTruyenAPI;
import com.example.apptruyen.statusbar.ColorStatusbar;
import com.example.apptruyen.httpconection.Http.HttpAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class DanhSachLoaiTruyenActivity extends AppCompatActivity {
    FloatingActionButton btnThem;
    RecyclerView recyclerView;
    LoaiTruyenAdapter loaiTruyenAdapter;
    ArrayList<LoaiTruyen> list;
    public static LoaiTruyenAPI loaiTruyenAPI;
    SearchView edtSearch;
    HttpAdapter adapter;
    ImageView userProfile;
    RelativeLayout homeLayout;
   public static String email;
    private ConnectivityReceiver connectivityReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_loai_truyen);

        Intent intentEmail=getIntent();
        email=intentEmail.getStringExtra("email");
        ColorStatusbar.setColorStatusBar(this);
        btnThem = findViewById(R.id.btnThem);
        edtSearch = findViewById(R.id.edtSearch);
        recyclerView = findViewById(R.id.rcvLoaitruyen);
        userProfile = findViewById(R.id.userProfile);
        homeLayout = findViewById(R.id.homeLayout);
        list = new ArrayList<>();

        adapter = new HttpAdapter(DanhSachLoaiTruyenActivity.this);
        adapter.setBaseUrl(Constrain.BASE_URL);
        loaiTruyenAPI = adapter.create(LoaiTruyenAPI.class);

        loadLoaiTruyen();

        btnThem.setOnClickListener(v -> {
            startActivity(new Intent(DanhSachLoaiTruyenActivity.this, InsertLoaiTruyenActivity.class));
        });
        userProfile.setOnClickListener(v -> {
            Intent intent=new Intent(DanhSachLoaiTruyenActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
        edtSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!query.equals("")){
                    searchLoaitruyen(query);
                }else {
                    loadLoaiTruyen();
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                if(!newText.equals("")){
                    searchLoaitruyen(newText);
                }else {
                    loadLoaiTruyen();
                }
                return false;
            }
        });
    }

    private void searchLoaitruyen(String query) {
        list = loaiTruyenAPI.getAll().getLoaitruyen();
        ArrayList<LoaiTruyen> listsearch=new ArrayList<>();
        for (int i=0;i<list.size();i++){
            LoaiTruyen loaiTruyen=new LoaiTruyen();
            loaiTruyen.setTenloai(list.get(i).getTenloai());
            loaiTruyen.setMaloai(list.get(i).getMaloai());
            loaiTruyen.setHinhloai(list.get(i).getHinhloai());
            if((loaiTruyen.getTenloai().contains(query))){
                listsearch.add(loaiTruyen);
            }
            loaiTruyenAdapter = new LoaiTruyenAdapter(DanhSachLoaiTruyenActivity.this, listsearch);
            recyclerView.setAdapter(loaiTruyenAdapter);
            loaiTruyenAdapter.notifyDataSetChanged();
        }
    }

    private void loadLoaiTruyen() {
        list = loaiTruyenAPI.getAll().getLoaitruyen();
        loaiTruyenAdapter = new LoaiTruyenAdapter(DanhSachLoaiTruyenActivity.this, list);
        recyclerView.setAdapter(loaiTruyenAdapter);
        loaiTruyenAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onResume() {
        connectivityReceiver = new ConnectivityReceiver(DanhSachLoaiTruyenActivity.this,homeLayout);
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectivityReceiver, intentFilter);

        list.clear();
        list.addAll(loaiTruyenAPI.getAll().getLoaitruyen());
        loaiTruyenAdapter.notifyDataSetChanged();
        super.onResume();
    }


}