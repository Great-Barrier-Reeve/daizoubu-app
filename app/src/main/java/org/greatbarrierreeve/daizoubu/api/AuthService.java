package org.greatbarrierreeve.daizoubu.api;
import org.greatbarrierreeve.daizoubu.data.model.User;

import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface AuthService {
    @GET("api/authenticate/send-link")
    Call<Map<String, String>> sendLink(@Query("email") String email);

    @POST("api/user/verification")
    Call<User> verifyUser(@Header("Authorization") String token);

    @GET("api/user/{id}")
    Call<User> getUserById(@Path("id") String id);
}
