package com.example.apptruyen.service;

import com.example.apptruyen.model.ListUser;
import com.example.apptruyen.httpconection.HttpAnotation.Form;
import com.example.apptruyen.httpconection.HttpAnotation.HttpMethod;
import com.example.apptruyen.httpconection.HttpAnotation.MethodType;

public interface AcountAPI {
    @HttpMethod(method = MethodType.POST,url ="AppTruyen/api/register.php")
    ListUser setAccout(@Form(name ="name") String name,
                       @Form(name ="email") String email,
                       @Form(name ="password") String password,
                       @Form(name ="hinh") String hinhh
    ) ;
    @HttpMethod(method = MethodType.POST,url ="AppTruyen/api/login.php")
    ListUser loginAccout(
            @Form(name ="email") String email,
            @Form(name ="password") String password
    ) ;
    @HttpMethod(method = MethodType.POST,url ="AppTruyen/api/getMyinfo.php")
    ListUser getMyInfo(
            @Form(name ="email") String email

    ) ;
    @HttpMethod(method = MethodType.POST,url ="AppTruyen/api/update_password.php")
    ListUser update(
            @Form(name ="oldpassword") String oldpassword,
            @Form(name ="email") String email,
            @Form(name ="newpassword") String newpassword

    ) ;
}
