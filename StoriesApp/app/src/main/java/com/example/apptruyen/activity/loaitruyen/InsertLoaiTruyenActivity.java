package com.example.apptruyen.activity.loaitruyen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.apptruyen.Constrain;
import com.example.apptruyen.R;
import com.example.apptruyen.model.ListLoaiTruyen;
import com.example.apptruyen.service.LoaiTruyenAPI;
import com.example.apptruyen.statusbar.ColorStatusbar;
import com.example.apptruyen.httpconection.Http.HttpAdapter;


public class InsertLoaiTruyenActivity extends AppCompatActivity {
   EditText edtName,edtImage;
   AppCompatButton btnAdd,btnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_loai_truyen);
        ColorStatusbar.setColorStatusBar(InsertLoaiTruyenActivity.this);

        edtName=findViewById(R.id.editName);
        edtImage=findViewById(R.id.editImage);
        btnAdd=findViewById(R.id.btnAdd);
        btnList=findViewById(R.id.btnList);
        HttpAdapter adapter = new HttpAdapter(InsertLoaiTruyenActivity.this);
        adapter.setBaseUrl(Constrain.BASE_URL);
        LoaiTruyenAPI loaiTruyenAPI = adapter.create(LoaiTruyenAPI.class);
        btnAdd.setOnClickListener(v -> {
            String name=edtName.getText().toString();
            String image=edtImage.getText().toString();
            ListLoaiTruyen listLoaiTruyen=loaiTruyenAPI.insert(name,image);
            if(listLoaiTruyen.status==true){
                Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        btnList.setOnClickListener(v->{
           startActivity(new Intent(InsertLoaiTruyenActivity.this,DanhSachLoaiTruyenActivity.class));
           finish();
        });
    }
}