package com.example.apptruyen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.apptruyen.activity.loaitruyen.DanhSachLoaiTruyenActivity;
import com.google.android.material.snackbar.Snackbar;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class ConnectivityReceiver extends BroadcastReceiver {
    Context context;
  ViewGroup parentLayout;

    public ConnectivityReceiver(Context context,ViewGroup viewGroup) {
        this.context=context;
        this.parentLayout=viewGroup;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
        } else {
            Snackbar snackbar = Snackbar
                    .make(parentLayout, "Chưa kết nối mạng", Snackbar.LENGTH_LONG)
                    .setAction("Đi cài đặt", v -> {
                        Intent intentset=new Intent(Settings.ACTION_WIFI_SETTINGS);
                        context.startActivity(intentset);
                    });
            // Changing action button text color
            snackbar.setActionTextColor(context.getResources().getColor(R.color.red));
            // Changing message text color
            View view = snackbar.getView();
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.blackNhat));
            TextView textView = view.findViewById(R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }
    }
}
