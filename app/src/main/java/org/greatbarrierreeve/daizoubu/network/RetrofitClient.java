package org.greatbarrierreeve.daizoubu.network;

import org.greatbarrierreeve.daizoubu.api.AuthService;
import org.greatbarrierreeve.daizoubu.api.ErrandService;
import org.greatbarrierreeve.daizoubu.repository.UserInfoRepository;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    // TODO: 7/4/26 REMOVE remove CleartextTRAFFIC in android manifest when deployed
    // ONLY NEEDED FOR TESTING DUE TO NO HTTPS ON LOCALHOST

    //for emulator testing virtual router
    private static final String BASE_URL = "http://10.0.2.2:8080";
//     private static final String BASE_URL = "http://167.71.216.176:8080/";

    private static ErrandService errandService;
    private static AuthService authService;
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request request = chain.request();
                        if(UserInfoRepository.getIdToken() == null){
                            return chain.proceed(request);
                        }
                        else{
                            Headers headers = request.headers().newBuilder().add("Authorization", UserInfoRepository.getIdToken()).build();
                            request = request.newBuilder().headers(headers).build();
                            return chain.proceed(request);
                        }
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ErrandService getErrandService() {
        if (errandService == null) {
            errandService = getRetrofitInstance().create(ErrandService.class);
        }
        return errandService;
    }

    public static AuthService getAuthService() {
        if (authService == null) {
            authService = getRetrofitInstance().create(AuthService.class);
        }
        return authService;
    }
}
