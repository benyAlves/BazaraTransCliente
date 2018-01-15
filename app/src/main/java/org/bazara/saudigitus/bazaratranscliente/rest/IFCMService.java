package org.bazara.saudigitus.bazaratranscliente.rest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by SD on 1/12/2018.
 */

public interface IFCMService {

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAcGxAyWQ:APA91bEnDdUPzLbXsysafchOV7uHocaIT2mEt4tip9bmJHrmJhhHRyfcnb_NahY6k6CfqFH6gpv2nFaEYIc6a3O6WAMIhayIvGJlDx1BXOAEdcBgpNKYYow32HtiSpggLt12oWZ6GIGx"
    })

    @POST("fcm/send")
    Call<String> sendMessage(@Body String body);
}
