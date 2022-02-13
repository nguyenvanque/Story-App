package com.example.apptruyen.activity.truyen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.apptruyen.Constrain;
import com.example.apptruyen.R;
import com.example.apptruyen.model.ListTruyen;
import com.example.apptruyen.service.TruyenAPI;
import com.example.apptruyen.httpconection.Http.HttpAdapter;

public class InsertTruyenActivity extends AppCompatActivity {
    EditText edttentruyen,edthinhtruyen,edtmota,edtGia;
    Spinner spinerLoaitruyen;
    Button btnThem,btnDanhhsach;


    private ProgressDialog pDialog;
    int maloai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_truyen);

        Intent intent=getIntent();
        maloai=intent.getIntExtra("maloai",0);

        edthinhtruyen=findViewById(R.id.editHinhtruyen);
        edttentruyen=findViewById(R.id.editTentruyen);
        edtmota=findViewById(R.id.editMota);
        edtGia=findViewById(R.id.edtGia);
        spinerLoaitruyen=findViewById(R.id.spinerloaitruyen);
        btnThem=findViewById(R.id.btnThem);
        btnDanhhsach=findViewById(R.id.btnDanhSach);


        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Đang thêm...");
        pDialog.setCanceledOnTouchOutside(false);

        HttpAdapter httpAdapter=new HttpAdapter(this);
        httpAdapter.setBaseUrl(Constrain.BASE_URL);
        TruyenAPI truyenAPI=httpAdapter.create(TruyenAPI.class);

        btnThem.setOnClickListener(v ->  {
            String tentruyen=edttentruyen.getText().toString();
            String hinhtruyen=edthinhtruyen.getText().toString();
            String mota=edtmota.getText().toString();
            String gia=edtGia.getText().toString();

            Toast.makeText(this, ""+maloai, Toast.LENGTH_SHORT).show();
            ListTruyen listTruyen=truyenAPI.insert(tentruyen,hinhtruyen,mota,gia,maloai);
            if(listTruyen.status==true){
                Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();

            }
        });

    }
}