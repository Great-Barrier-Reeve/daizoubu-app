package org.greatbarrierreeve.daizoubu.ui.homepage;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.greatbarrierreeve.daizoubu.network.RetrofitClient;
import org.greatbarrierreeve.daizoubu.data.repository.AuthRepository;
import org.greatbarrierreeve.daizoubu.data.repository.UserInfoRepository;
import org.greatbarrierreeve.daizoubu.ui.bounties.BountiesActivity;
import org.greatbarrierreeve.daizoubu.R;
import org.greatbarrierreeve.daizoubu.ui.order.OrderActivity;

import java.util.Map;


public class MainActivity extends AppCompatActivity {

    CardView cardPlaceOrder;
    CardView cardTabOrder;
    ConstraintLayout sectionHeaderOrders;
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

        // orders tab button card click event handler
        cardTabOrder = findViewById(R.id.cardTabOrder);
        cardTabOrder.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, OrderActivity.class)));

        // bounties section header click event handler
        sectionHeaderOrders = findViewById(R.id.sectionHeaderBounties);
        sectionHeaderOrders.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, BountiesActivity.class)));

        // place order button card click event handler
        cardPlaceOrder = findViewById(R.id.cardPlaceOrder);
        cardPlaceOrder.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, OrderActivity.class)));

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