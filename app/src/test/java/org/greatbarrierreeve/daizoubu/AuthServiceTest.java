package org.greatbarrierreeve.daizoubu;

import org.greatbarrierreeve.daizoubu.network.RetrofitClient;
import org.greatbarrierreeve.daizoubu.api.AuthService;
import org.greatbarrierreeve.daizoubu.data.model.User;
import org.greatbarrierreeve.daizoubu.network.RetrofitClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class AuthServiceTest {
    private AuthService authService;
    @Before
    public void setUp() {
        // Use your existing singleton client
        authService = RetrofitClient.getAuthService();
    }

        @Test
    public void testSendLink() {
        String testEmail = "lebin_lee@mymail.sutd.edu.sg";
        Call<Map<String, String>> call = authService.sendLink(testEmail);

        try {
            Response<Map<String, String>> response = call.execute();

            if (response.isSuccessful() && response.body() != null) {
                System.out.println("Success! Message: " + response.body().get("message"));
            } else {
                System.out.println("Failed! Code: " + response.code());
                if (response.errorBody() != null) {
                    System.out.println("Error Body: " + response.errorBody().string());
                }
            }
        } catch (IOException e) {
            System.out.println("📡 Network Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @Test
    public void testGetUserById() {
        // 1. Arrange: Pick an ID that you know exists in your database/Firebase
        String testId = "QWrsyfnOv6JRvWqcppPq";

        // 2. Act: Prepare the call
        Call<User> call = authService.getUserById(testId);

        try {
            // 3. Execute: Send the request
            Response<User> response = call.execute();

            if (response.isSuccessful() && response.body() != null) {
                User user = response.body();
                System.out.println(" Found User: " + user.getUserId());
            } else {
                System.out.println("User not found. Code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void testVerifyUser() {
//        // 1. Arrange: You need a real Firebase ID Token here.
//        // For testing, you usually get this from the Firebase Console or a successful login.
//        String mockToken = "Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6...";
//
//        // 2. Act: Pass the token into the Header
//        Call<User> call = authService.verifyUser(mockToken);
//
//        try {
//            Response<User> response = call.execute();
//
//            if (response.isSuccessful()) {
//                System.out.println(" Token is valid! Welcome, " + response.body().getUserId());
//            } else {
//                // If the token is expired or fake, your backend should return 401 Unauthorized
//                System.out.println(" Verification Failed. Code: " + response.code());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
