package com.example.apptruyen.service;

import com.example.apptruyen.model.ListGioHang;
import com.example.apptruyen.httpconection.HttpAnotation.Form;
import com.example.apptruyen.httpconection.HttpAnotation.HttpMethod;
import com.example.apptruyen.httpconection.HttpAnotation.MethodType;

public interface GioHangAPI {
    @HttpMethod(method = MethodType.POST,url ="AppTruyen/api/select_giohang.php")
    ListGioHang getAllGioHang(
            @Form(name ="matruyen") int matruyen,
            @Form(name ="email") String email
    ) ;
    @HttpMethod(method = MethodType.POST,url ="AppTruyen/api/getGioHangByEmail.php")
    ListGioHang getAllGioHangByEmail(
            @Form(name ="email") String email
    ) ;
    @HttpMethod(method = MethodType.POST,url ="AppTruyen/api/insert_giohang.php")
    ListGioHang insert(
            @Form(name ="id") String id,
            @Form(name ="matruyen") int matruyen,
                       @Form(name ="tentruyen") String tentruyen,
                      @Form(name ="hinhtruyen") String hinhtruyen,
                      @Form(name ="gia") long gia,
                      @Form(name ="email") String email,
                      @Form(name ="name") String name,
                      @Form(name ="timeoder") String timeoder,
                      @Form(name ="soluong") int soluong

    ) ;
    @HttpMethod(method = MethodType.POST,url ="AppTruyen/api/update_soluonggiohang.php")

    ListGioHang updateSoLuong(
            @Form(name ="matruyen") int matruyen,

            @Form(name ="email") String email,

            @Form(name ="soluong") int soluong

    ) ;
    @HttpMethod(method = MethodType.POST,url ="AppTruyen/api/update_giagiohang.php")

    ListGioHang updateGia(
            @Form(name ="matruyen") int matruyen,

            @Form(name ="email") String email,

            @Form(name ="gia") long  gia

    ) ;
    @HttpMethod(method = MethodType.POST,url ="AppTruyen/api/delete_itemgiohang.php")

    ListGioHang deleteItem(
            @Form(name ="id") String id,
            @Form(name ="email") String email

    ) ;
}
