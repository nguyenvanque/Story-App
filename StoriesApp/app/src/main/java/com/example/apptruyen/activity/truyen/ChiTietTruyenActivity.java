package com.example.apptruyen.activity.truyen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptruyen.Constrain;
import com.example.apptruyen.R;
import com.example.apptruyen.activity.giohang.DanhSachGioHangActivity;
import com.example.apptruyen.activity.loaitruyen.DanhSachLoaiTruyenActivity;
import com.example.apptruyen.adapter.ReviewAdapter;
import com.example.apptruyen.model.GioHang;
import com.example.apptruyen.model.ListGioHang;
import com.example.apptruyen.model.ListLike;
import com.example.apptruyen.model.ListReview;
import com.example.apptruyen.model.ListTruyen;
import com.example.apptruyen.model.ListUser;
import com.example.apptruyen.model.Review;
import com.example.apptruyen.service.AcountAPI;
import com.example.apptruyen.service.GioHangAPI;
import com.example.apptruyen.service.LikeAPI;
import com.example.apptruyen.service.ReviewAPI;
import com.example.apptruyen.service.TruyenAPI;
import com.example.apptruyen.statusbar.ColorStatusbar;
import com.example.apptruyen.httpconection.Http.HttpAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChiTietTruyenActivity extends AppCompatActivity {
    int matruyen, maloai;
    String tentruyen, hinhtruyen, motatruyen, gia;
    RoundedImageView imagetruyen;
    ImageView likeIv, imageCart;
    TextView txtTentruyen, txtMota, txtGia, txtCountReview,txtSoluong;
    LinearLayout btnLike, btnReview,btnShare, btnThemGioHang;
    String uemail = DanhSachLoaiTruyenActivity.email;
    String name, uimage;
    RecyclerView rcvReview;
    public static ReviewAPI reviewAPI;
    ArrayList<Review> listReview;
    HttpAdapter httpAdapter;
    ReviewAdapter reviewAdapter;
    String luottruycap;

    ArrayList<GioHang> listGioHang;
    public static GioHangAPI gioHangAPI;
    MediaPlayer player;
    int soluong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_truyen);
        ColorStatusbar.setColorStatusBar(ChiTietTruyenActivity.this);

        imagetruyen = findViewById(R.id.imagetruyen);
        txtTentruyen = findViewById(R.id.txtTentruyen);
        txtMota = findViewById(R.id.txtMota);
        txtSoluong = findViewById(R.id.txtSoluong);
        txtGia = findViewById(R.id.txtGia);
        btnLike = findViewById(R.id.btnLike);
        btnReview = findViewById(R.id.btnReview);
        likeIv = findViewById(R.id.likeIv);
        rcvReview = findViewById(R.id.rcvReview);
        txtCountReview = findViewById(R.id.txtCountReview);
        imageCart = findViewById(R.id.imageCart);
        btnShare = findViewById(R.id.btnShare);
        btnThemGioHang = findViewById(R.id.btnThemGioHang);
        listReview = new ArrayList<>();
        listGioHang = new ArrayList<>();


        httpAdapter = new HttpAdapter(this);
        httpAdapter.setBaseUrl(Constrain.BASE_URL);
        LikeAPI likeAPI = httpAdapter.create(LikeAPI.class);
        TruyenAPI truyenAPI = httpAdapter.create(TruyenAPI.class);
        AcountAPI acountAPI = httpAdapter.create(AcountAPI.class);
        reviewAPI = httpAdapter.create(ReviewAPI.class);
        gioHangAPI = httpAdapter.create(GioHangAPI.class);
        Intent intent = getIntent();
        matruyen = intent.getIntExtra("matruyen", 0);
        maloai = intent.getIntExtra("maloai", 0);
        tentruyen = intent.getStringExtra("tentruyen");
        hinhtruyen = intent.getStringExtra("hinhtruyen");
        motatruyen = intent.getStringExtra("motatruyen");
        gia = intent.getStringExtra("gia");





        ListTruyen listTruyen = truyenAPI.getAccess(matruyen);
        if (listTruyen.status == true) {
            for (int i = 0; i < listTruyen.getTruyen().size(); i++) {
                luottruycap = listTruyen.getTruyen().get(i).getLuottruycap();
            }
        }
        truyenAPI.updateAccess(matruyen, String.valueOf((Integer.parseInt(luottruycap) + 1)));
        loadMyInfo(acountAPI);

        loadReview();
        txtSoluong.setText(gioHangAPI.getAllGioHangByEmail(uemail).getGiohang().size()+"");

        if (hinhtruyen.equals("")) {
            imagetruyen.setImageResource(R.drawable.ic_gallery_grey);
        } else {
            Picasso.with(ChiTietTruyenActivity.this).load(hinhtruyen).placeholder(R.drawable.ic_gallery_grey).into(imagetruyen);
        }
        txtTentruyen.setText(tentruyen);
        txtGia.setText(gia + "$");


        if (likeAPI.loadLike(uemail, matruyen).status == true) {
            likeIv.setImageResource(R.drawable.ic_liked);
        } else {
            likeIv.setImageResource(R.drawable.ic_like);

        }

        btnLike.setOnClickListener(v -> {
            String time = String.valueOf(System.currentTimeMillis());
            ListLike listLike = likeAPI.setLike(time, uemail, time, matruyen);
            if (listLike.status == true) {
                player = MediaPlayer.create(ChiTietTruyenActivity.this, R.raw.facebook_sound);
                player.start();
                likeIv.setImageResource(R.drawable.ic_liked);
            } else {
                player = MediaPlayer.create(ChiTietTruyenActivity.this, R.raw.facebook_sound);
                player.stop();
                likeIv.setImageResource(R.drawable.ic_like);
            }
            player = MediaPlayer.create(ChiTietTruyenActivity.this, R.raw.facebook_sound);
            player.stop();
        });
        btnReview.setOnClickListener(v -> {
            final Dialog dialog = new Dialog(ChiTietTruyenActivity.this);
            dialog.setContentView(R.layout.dialog_review);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            Window window = dialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (dialog != null && dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            EditText edtDescripton = dialog.findViewById(R.id.edtMessage);
            RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
            AppCompatButton btnGui = dialog.findViewById(R.id.btnGui);
            AppCompatButton btnHuy = dialog.findViewById(R.id.btnHuy);
            btnGui.setOnClickListener(view -> {
                String message = edtDescripton.getText().toString();
                String rateNumber = String.valueOf(ratingBar.getRating());
                String time = String.valueOf(System.currentTimeMillis());
                if (message.equals("")) {
                    Toast.makeText(this, "Nhập đánh giá", Toast.LENGTH_SHORT).show();
                } else {
                    reviewMessage(time, uemail, name, uimage, time, message, rateNumber, matruyen);
                    Intent intent3 = getIntent();
                    startActivity(intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                    dialog.dismiss();
                }
            });
            btnHuy.setOnClickListener(v1 -> {
                dialog.dismiss();
            });
            dialog.show();
        });
        imageCart.setOnClickListener(v -> {
            Intent intent1 = new Intent(ChiTietTruyenActivity.this, DanhSachGioHangActivity.class);
            startActivity(intent1);
        });
        btnThemGioHang.setOnClickListener(v -> {
            themGioHang();
        });
        btnShare.setOnClickListener(v ->  {

        });
    }


    private void loadReview() {
        ListReview review = reviewAPI.getALL(matruyen);
        listReview = review.getReview();

        if (review.status == true) {
            if (listReview.size() == 0) {
                txtCountReview.setText("0 lượt đánh giá");
            } else {
                txtCountReview.setText(listReview.size() + " lượt đánh giá");
            }
            reviewAdapter = new ReviewAdapter(ChiTietTruyenActivity.this, listReview);
            rcvReview.setAdapter(reviewAdapter);
            reviewAdapter.notifyDataSetChanged();
        }
    }

    private void reviewMessage(String time, String uemail, String name, String uimage, String time1, String message, String rateNumber, int matruyen1) {
        ListReview listReview = reviewAPI.insert(time, uemail, name, uimage, time1, message, rateNumber, matruyen1);
        if (listReview.status == true) {
            Toast.makeText(this, "Thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Thaát bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadMyInfo(AcountAPI acountAPI) {
        ListUser listUser = acountAPI.getMyInfo(uemail);
        if (listUser.status == true) {
            name = listUser.getUsers().get(0).getName();
            uimage = listUser.getUsers().get(0).getHinh();
        }
    }

    private void themGioHang() {
        String time = String.valueOf(System.currentTimeMillis());
        soluong += 1;
        int soluongMoi = 0;
        ListGioHang arrGioHang = gioHangAPI.getAllGioHang(matruyen, uemail);
        if (arrGioHang.status == true) {
            listGioHang = arrGioHang.getGiohang();
            if (listGioHang.size() > 0) {
                soluongMoi += 1;
                boolean exist = false;
                for (int i = 0; i < listGioHang.size(); i++) {
                    if (listGioHang.get(i).getMatruyen() == matruyen) {
                        gioHangAPI.updateSoLuong(matruyen, uemail, listGioHang.get(i).getSoluong() + soluongMoi);
//                        if (listGioHang.size() > 10) {
//                            listGioHang.get(i).setSoluong(10);
//                        }
                        ListGioHang arr = gioHangAPI.getAllGioHang(matruyen, uemail);
                        ListGioHang updategia = gioHangAPI.updateGia(matruyen, uemail, Long.parseLong(gia) * arr.getGiohang().get(i).getSoluong());
                        if (updategia.status == true) {
                            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        }
                        exist = true;
                    }
                }
                if (exist == false) {
                    long giamoi = soluong * Long.parseLong(gia);
                    ListGioHang insert = gioHangAPI.insert(time, matruyen, tentruyen, hinhtruyen, giamoi, uemail, name, time, soluongMoi);
                    if (insert.status == true) {
                        Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                long giamoi = soluong * Long.parseLong(gia); // giá 1 sp là 1000
                ListGioHang result = gioHangAPI.insert(time, matruyen, tentruyen, hinhtruyen, giamoi, uemail, name, time, soluong);
                if (result.status == true) {
                    Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                }
            }
        }
        txtSoluong.setText(gioHangAPI.getAllGioHangByEmail(uemail).getGiohang().size()+"");
    }

    @Override
    protected void onResume() {
        listReview.clear();
        listReview.addAll(reviewAPI.getALL(matruyen).getReview());
        reviewAdapter.notifyDataSetChanged();
        txtSoluong.setText(gioHangAPI.getAllGioHangByEmail(uemail).getGiohang().size()+"");

        super.onResume();
    }
}