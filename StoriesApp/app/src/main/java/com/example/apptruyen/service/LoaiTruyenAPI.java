package com.example.apptruyen.service;

import com.example.apptruyen.model.ListLoaiTruyen;
import com.example.apptruyen.httpconection.HttpAnotation.Form;
import com.example.apptruyen.httpconection.HttpAnotation.HttpMethod;
import com.example.apptruyen.httpconection.HttpAnotation.MethodType;

public interface LoaiTruyenAPI {

    @HttpMethod(method = MethodType.POST,url ="AppTruyen/api/insert_loaitruyen.php")
    ListLoaiTruyen insert(@Form(name ="tenloai") String name,
                             @Form(name ="hinhloai") String image

    ) ;
    @HttpMethod(method = MethodType.POST,url ="AppTruyen/api/select_loaitruyen.php")
    ListLoaiTruyen getAll() ;

    @HttpMethod(method = MethodType.POST,url ="AppTruyen/api/update_loaitruyen.php")
    ListLoaiTruyen update(
            @Form(name ="maloai") int maloai,
            @Form(name ="tenloai") String name,
            @Form(name ="hinhloai") String image

    ) ;
    @HttpMethod(method = MethodType.POST,url ="AppTruyen/api/delete_loaitruyen.php")

    ListLoaiTruyen delete(
            @Form(name ="maloai") int maloai


    ) ;
}
