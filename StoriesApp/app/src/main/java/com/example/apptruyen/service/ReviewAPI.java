package com.example.apptruyen.service;

import com.example.apptruyen.model.ListReview;
import com.example.apptruyen.httpconection.HttpAnotation.Form;
import com.example.apptruyen.httpconection.HttpAnotation.HttpMethod;
import com.example.apptruyen.httpconection.HttpAnotation.MethodType;

public interface ReviewAPI {
    @HttpMethod(method = MethodType.POST,url ="AppTruyen/api/insert_review.php")
    ListReview insert(@Form(name ="idreview") String idreview,
                      @Form(name ="email") String email,
                      @Form(name ="name") String name,
                      @Form(name ="hinh") String hinh,
                      @Form(name ="time") String time,
                      @Form(name ="message") String message,
                      @Form(name ="ratenumber") String ratenumber,
                      @Form(name ="matruyen") int matruyen

    ) ;
    @HttpMethod(method = MethodType.POST,url ="AppTruyen/api/select_review.php")
    ListReview getALL(

            @Form(name ="matruyen") int matruyen
    ) ;


}
