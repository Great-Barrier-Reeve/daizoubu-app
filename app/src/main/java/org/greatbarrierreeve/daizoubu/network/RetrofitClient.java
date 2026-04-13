package org.greatbarrierreeve.daizoubu.network;

import org.greatbarrierreeve.daizoubu.api.AuthService;
import org.greatbarrierreeve.daizoubu.api.ErrandService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class RetrofitClient {
    // TODO: 7/4/26 REMOVE remove CleartextTRAFFIC in android manifest when deployed
    // ONLY NEEDED FOR TESTING DUE TO NO HTTPS ON LOCALHOST

    //for emulator testing virtual router
   //private static final String BASE_URL = "http://10.0.2.2:8080";

    //for java unit test
    private static final String BASE_URL = "http://192.168.10.90:8080/";

    private static ErrandService service;

    public static ErrandService getService(){

        OkHttpClient httpClient;

        Interceptor headAuthInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Headers header = request.headers().newBuilder().add("Authorization", "as").build();
                request = request.newBuilder().headers(header).build();
                return chain.proceed(request);
            }
        };
        httpClient = new OkHttpClient.Builder().addInterceptor(headAuthInterceptor).build();

        if (service == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = retrofit.create(ErrandService.class);
        }
        return service;
    }
}
