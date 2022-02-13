package com.example.apptruyen.activity.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.apptruyen.Constrain;
import com.example.apptruyen.R;
import com.example.apptruyen.activity.loaitruyen.DanhSachLoaiTruyenActivity;
import com.example.apptruyen.model.ListUser;
import com.example.apptruyen.service.AcountAPI;
import com.example.apptruyen.statusbar.ColorStatusbar;
import com.example.apptruyen.httpconection.Http.HttpAdapter;

public class ProfileActivity extends AppCompatActivity {
    ProgressDialog pDialog;
    Dialog dialog;
    String email= DanhSachLoaiTruyenActivity.email;
    RelativeLayout changepasswordLayout,storyLikeLayout;
    AcountAPI acountAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ColorStatusbar.setColorStatusBar(ProfileActivity.this);
        HttpAdapter adapter = new HttpAdapter(ProfileActivity.this);
        adapter.setBaseUrl(Constrain.BASE_URL);
        acountAPI= adapter.create(AcountAPI.class);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Đang đăng ký...");
        pDialog.setCanceledOnTouchOutside(false);

        changepasswordLayout=findViewById(R.id.changepasswordLayout);
        storyLikeLayout=findViewById(R.id.storyLikeLayout);
        dialog = new Dialog(ProfileActivity.this);
        Intent intent=getIntent();
        changepasswordLayout.setOnClickListener(v -> {
            showChangePasswordDialog();
        });
        storyLikeLayout.setOnClickListener(v -> {

        });
    }

    private void showChangePasswordDialog() {
        dialog.setContentView(R.layout.dialog_changes_password);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        final EditText oldPasswordEt = dialog.findViewById(R.id.oldpasswordEt);
        final EditText newPasswordEt = dialog.findViewById(R.id.newpasswordEt);
        final EditText cfnewPasswordEt = dialog.findViewById(R.id.cfnewpasswordEt);
        AppCompatButton updateBtn = dialog.findViewById(R.id.updateBtn);
        AppCompatButton cancelBtn = dialog.findViewById(R.id.cancelBtn);
        updateBtn.setOnClickListener(v -> {
            final String oldPassWord = oldPasswordEt.getText().toString();
            final String newPassword = newPasswordEt.getText().toString();
            final String cfnewPassword = cfnewPasswordEt.getText().toString();
            if (TextUtils.isEmpty(oldPassWord) && TextUtils.isEmpty(newPassword)) {
                Toast.makeText(ProfileActivity.this, "Mật khẩu không được bỏ trống", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!newPassword.equals(cfnewPassword)){
                Toast.makeText(ProfileActivity.this, "Mật khẩu xác nhận không đúng", Toast.LENGTH_SHORT).show();
                return;
            }
            if (newPassword.length() < 6) {
                Toast.makeText(ProfileActivity.this, "Mật khẩu từ 6 kí tự", Toast.LENGTH_SHORT).show();
                return;
            } else {
                updatePassword(oldPassWord, newPassword);
                dialog.dismiss();
                pDialog.dismiss();
            }
        });
        cancelBtn.setOnClickListener(v ->  {
            pDialog.dismiss();
            dialog.dismiss();

        });
        dialog.show();
    }

    private void updatePassword(String oldPassWord, String newPassword) {

        Toast.makeText(this, ""+oldPassWord+newPassword+email, Toast.LENGTH_SHORT).show();

        ListUser listUser=acountAPI.update(oldPassWord,email,newPassword);
        if(listUser.status==true){
            Toast.makeText(this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}