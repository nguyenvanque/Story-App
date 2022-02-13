package com.example.apptruyen.service;

import com.example.apptruyen.model.ListLike;
import com.example.apptruyen.httpconection.HttpAnotation.Form;
import com.example.apptruyen.httpconection.HttpAnotation.HttpMethod;
import com.example.apptruyen.httpconection.HttpAnotation.MethodType;

public interface LikeAPI {
    @HttpMethod(method = MethodType.POST,url ="AppTruyen/api/liketruyen.php")
    ListLike setLike(@Form(name ="idlike") String idlike,
                     @Form(name ="email") String email,
                     @Form(name ="time") String time,
                     @Form(name ="matruyen") int matruyen
    ) ;
    @HttpMethod(method = MethodType.POST,url ="AppTruyen/api/likeload.php")
    ListLike loadLike(
            @Form(name ="email") String email,
            @Form(name ="matruyen") int matruyen
    ) ;
    @HttpMethod(method = MethodType.POST,url ="AppTruyen/api/getLikeByMatruyen.php")
    ListLike getLikeByMaTruyen(
            @Form(name ="matruyen") int matruyen
    ) ;
}
