package com.example.apptruyen.service;

import com.example.apptruyen.model.ListTruyen;
import com.example.apptruyen.httpconection.HttpAnotation.Form;
import com.example.apptruyen.httpconection.HttpAnotation.HttpMethod;
import com.example.apptruyen.httpconection.HttpAnotation.MethodType;

public interface TruyenAPI {

        @HttpMethod(method = MethodType.POST,url ="AppTruyen/api/insert_truyen.php")
        ListTruyen insert(@Form(name ="tentruyen") String name,
                          @Form(name ="hinhtruyen") String hinhtruyen,
                          @Form(name ="mota") String mota,
                          @Form(name ="gia") String gia,
                          @Form(name ="maloai") int maloai

        ) ;

        @HttpMethod(method = MethodType.POST,url ="AppTruyen/api/select_truyen.php")
        ListTruyen getByMaLoai(
                @Form(name ="maloai") int maloai
        ) ;

        @HttpMethod(method = MethodType.POST,url ="AppTruyen/api/update_truyen.php")
        ListTruyen update(
                @Form(name ="matruyen") int matruyen,
                @Form(name ="tentruyen") String name,
                @Form(name ="hinhtruyen") String hinhtruyen,
                @Form(name ="mota") String mota,
                @Form(name ="gia") String gia,
                @Form(name ="maloai") int maloai

        ) ;
        @HttpMethod(method = MethodType.POST,url ="AppTruyen/api/delete_truyen.php")

        ListTruyen delete(
                @Form(name ="matruyen") int matruyen,
                @Form(name ="maloai") int maloai


        ) ;

        @HttpMethod(method = MethodType.POST,url ="AppTruyen/api/selectAccess.php")

        ListTruyen getAccess(
                @Form(name ="matruyen") int matruyen
        ) ;
        @HttpMethod(method = MethodType.POST,url ="AppTruyen/api/updateAccess.php")

        ListTruyen updateAccess(
                @Form(name ="matruyen") int matruyen,
                @Form(name = "luottruycap") String luottruycap
        ) ;


}
