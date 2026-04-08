package org.greatbarrierreeve.daizoubu.network;

import org.greatbarrierreeve.daizoubu.api.AuthService;
import org.greatbarrierreeve.daizoubu.api.ErrandService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class RetrofitClient {
    // TODO: 7/4/26 REMOVE remove CleartextTRAFFIC in android manifest when deployed
    // ONLY NEEDED FOR TESTING DUE TO NO HTTPS ON LOCALHOST

    //for emulator testing virtual router
//    private static final String BASE_URL = "http://10.0.2.2:8080";

    //for java unit test
    private static final String BASE_URL = "http://localhost:8080";

    private static ErrandService service;
    private static AuthService authService;
    private static Retrofit retrofit = null;

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static AuthService getAuthService() {
        if (authService == null) {
            authService = getRetrofit().create(AuthService.class);
        }
        return authService;
    }

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
