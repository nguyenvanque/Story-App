package com.example.apptruyen.activity.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apptruyen.Constrain;
import com.example.apptruyen.R;
import com.example.apptruyen.model.ListUser;
import com.example.apptruyen.service.AcountAPI;
import com.example.apptruyen.httpconection.Http.HttpAdapter;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtUserName,editHinh,edtPassWord,edtEmail;
    private Button btnRegister;
    private Button btnLogin;
    private ProgressDialog pDialog;
    String str_name,str_email,str_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtUserName = findViewById(R.id.editUsername);
        edtPassWord = findViewById(R.id.editPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        edtEmail = findViewById(R.id.editEmail);
        editHinh = findViewById(R.id.editHinh);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Đang đăng ký...");
        pDialog.setCanceledOnTouchOutside(false);


        HttpAdapter adapter = new HttpAdapter(RegisterActivity.this);
        adapter.setBaseUrl(Constrain.BASE_URL);
        AcountAPI register = adapter.create(AcountAPI.class);

        btnRegister.setOnClickListener(v ->  {
            String name=edtUserName.getText().toString();
            String email=edtEmail.getText().toString();
            String password=edtPassWord.getText().toString();
            String hinh=edtPassWord.getText().toString();
            ListUser users=register.setAccout(name,email,password,hinh);
             if(users.status==true){
                 Toast.makeText(this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
             }else {
                 Toast.makeText(this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();

             }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });






    }
}