package org.greatbarrierreeve.daizoubu;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.greatbarrierreeve.daizoubu.data.model.Errand;
import org.greatbarrierreeve.daizoubu.data.model.User;
import org.greatbarrierreeve.daizoubu.network.RetrofitClient;
import org.greatbarrierreeve.daizoubu.repository.AuthRepository;
import org.greatbarrierreeve.daizoubu.repository.ErrandRepository;
import org.greatbarrierreeve.daizoubu.repository.UserInfoRepository;
import org.greatbarrierreeve.daizoubu.ui.order.menu.MenuActivity;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    CardView cardHighlightsBounties;
    CardView cardTabOrder;
    private AuthRepository authRepository;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        String uid = UserInfoRepository.getUserId();
        String token = UserInfoRepository.getIdToken();
        Log.d("DAIZOUBU_DEBUG", "UID: " + uid);
        Log.d("DAIZOUBU_DEBUG", "TOKEN: " + token);
        authRepository = new AuthRepository(RetrofitClient.getAuthService());



        SharedPreferences prefs = getSharedPreferences("auth_prefs", MODE_PRIVATE);
        Map<String, ?> allEntries = prefs.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("DAIZOUBU_DEBUG", "Found Key: " + entry.getKey() + " | Value: " + entry.getValue());
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        // orders card click event handler
        cardTabOrder = findViewById(R.id.cardTabOrder);
        cardTabOrder.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);

        });

        // bounties card click event handler
        cardHighlightsBounties = findViewById(R.id.cardHighlightsBounties);
        cardHighlightsBounties.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, BountiesActivity.class);
            startActivity(intent);

        });

        //dev purpose
//        ErrandRepository errandRepository = new ErrandRepository(RetrofitClient.getErrandService());
//        errandRepository.getAvailableErrands(new Callback<List<Errand>>() {
//            @Override
//            public void onResponse(Call<List<Errand>> call, Response<List<Errand>> response) {
//                if(response.isSuccessful()) {
//                    assert response.body() != null;
//                    if (!response.body().isEmpty()) {
//                        List<Errand> errands = response.body();
//                        for (Errand errand : errands) {
//                            System.out.println("async WORKS!" + errand.toString());
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Errand>> call, Throwable throwable) {
//                Log.d("error" , throwable.getMessage());
//            }
//        });


        // send user to login activity for dev purposes
//        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//        startActivity(intent);

    }


}