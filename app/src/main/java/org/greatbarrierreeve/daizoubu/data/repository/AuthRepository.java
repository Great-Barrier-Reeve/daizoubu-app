package org.greatbarrierreeve.daizoubu.data.repository;

import org.greatbarrierreeve.daizoubu.api.AuthService;
import org.greatbarrierreeve.daizoubu.api.ErrandService;
import org.greatbarrierreeve.daizoubu.data.model.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class AuthRepository {
    private final AuthService authService;

    public AuthRepository(AuthService authService) {
        this.authService = authService;
    }

    public void sendLoginLink(String email, Callback<Map<String,String>> callback) {
        Call<Map<String, String>> call = authService.sendLink(email);
        call.enqueue(callback);
    }

    public void verifyUserToken(String idToken, Callback<User> callback) {
        Call<User> call = authService.verifyUser(idToken);
        call.enqueue(callback);
    }

    public void getUserProfile(String userId, Callback<User> callback) {
        Call<User> call = authService.getUserById(userId);
        call.enqueue(callback);
    }
}

