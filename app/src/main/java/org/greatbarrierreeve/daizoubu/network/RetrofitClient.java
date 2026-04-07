package org.greatbarrierreeve.daizoubu.network;

import org.greatbarrierreeve.daizoubu.api.ErrandService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class RetrofitClient {
    //for emulator testing virtual router
//    private static final String BASE_URL = "http://10.0.2.2:8080";

    //for java unit test
    private static final String BASE_URL = "http://localhost:8080";

    private static ErrandService service;

    public static ErrandService getService(){
        if (service == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = retrofit.create(ErrandService.class);
        }
        return service;
    }
}
