package com.example.apptruyen.activity.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptruyen.Constrain;
import com.example.apptruyen.R;
import com.example.apptruyen.activity.loaitruyen.DanhSachLoaiTruyenActivity;
import com.example.apptruyen.model.ListUser;
import com.example.apptruyen.service.AcountAPI;
import com.example.apptruyen.httpconection.Http.HttpAdapter;

public class LoginActivity extends AppCompatActivity {
    private EditText edtPassWord;
    private EditText edtEmail;
    private AppCompatButton btnLogin;
    private AppCompatButton btnRegister;
    private ProgressDialog pDialog;
    TextView btnReset,btnReset2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtPassWord = findViewById(R.id.editPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        edtEmail = findViewById(R.id.editEmail);
        btnReset = findViewById(R.id.btnResetpassword);
        btnReset2 = findViewById(R.id.btnResetpassword2);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Đang đăng ký...");
        pDialog.setCanceledOnTouchOutside(false);
        HttpAdapter adapter = new HttpAdapter(LoginActivity.this);
        adapter.setBaseUrl(Constrain.BASE_URL);
        AcountAPI register = adapter.create(AcountAPI.class);
        btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));

        });
        btnLogin.setOnClickListener(v -> {
            String email=edtEmail.getText().toString();
            String password=edtPassWord.getText().toString();
            ListUser user=register.loginAccout(email,password);

                if(user.status==true){
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LoginActivity.this, DanhSachLoaiTruyenActivity.class);
                    intent.putExtra("email",email);
                    startActivity(intent);

                }else {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }

        });


    }
}