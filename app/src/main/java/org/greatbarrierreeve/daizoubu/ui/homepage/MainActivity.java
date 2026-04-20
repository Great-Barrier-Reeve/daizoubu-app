package org.greatbarrierreeve.daizoubu.ui.homepage;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greatbarrierreeve.daizoubu.network.RetrofitClient;
import org.greatbarrierreeve.daizoubu.data.model.Errand;
import org.greatbarrierreeve.daizoubu.data.repository.AuthRepository;
import org.greatbarrierreeve.daizoubu.data.repository.ErrandRepository;
import org.greatbarrierreeve.daizoubu.data.repository.UserInfoRepository;
import org.greatbarrierreeve.daizoubu.ui.bounties.BountiesActivity;
import org.greatbarrierreeve.daizoubu.ui.bounties.BountiesAdapter;
import org.greatbarrierreeve.daizoubu.R;
import org.greatbarrierreeve.daizoubu.ui.order.OrderActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class MainActivity extends AppCompatActivity {

    CardView cardPlaceOrder;
    CardView cardTabOrder;
    ConstraintLayout sectionHeaderOrders;
    private AuthRepository authRepository;

    private RecyclerView recyclerViewMyOrders;
    private RecyclerView recyclerViewMyRuns;
    private RecyclerView recyclerViewBounties;

    private BountiesAdapter myOrdersAdapter;
    private BountiesAdapter myRunsAdapter;
    private BountiesAdapter availableBountiesAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        String uid = "user123";
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

        recyclerViewMyOrders  = findViewById(R.id.recyclerViewMyOrders);
        recyclerViewMyRuns    = findViewById(R.id.recyclerViewMyRuns);
        recyclerViewBounties  = findViewById(R.id.recyclerViewBounties);

        myOrdersAdapter          = new BountiesAdapter(new ArrayList<>(), uid);
        myRunsAdapter            = new BountiesAdapter(new ArrayList<>(), uid);
        availableBountiesAdapter = new BountiesAdapter(new ArrayList<>(), uid);

        setupRecyclerView(recyclerViewMyOrders, myOrdersAdapter);
        setupRecyclerView(recyclerViewMyRuns, myRunsAdapter);
        setupRecyclerView(recyclerViewBounties, availableBountiesAdapter);

        ErrandRepository errandRepository = new ErrandRepository(RetrofitClient.getErrandService());
        String userId = "user123";

        fetchMyOrders(errandRepository, userId);
        fetchMyRuns(errandRepository, userId);
        fetchAvailableBounties(errandRepository, userId);

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

    private void setupRecyclerView(RecyclerView rv, BountiesAdapter adapter) {
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv.setNestedScrollingEnabled(false);
    }

    private void fetchMyOrders(ErrandRepository repo, String userId) {
        if (userId == null) return;
        repo.getErrandByBuyerId(userId, new retrofit2.Callback<>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<List<Errand>> call,
                                   @NonNull retrofit2.Response<List<Errand>> response) {
                if (response.isSuccessful() && response.body() != null)
                    myOrdersAdapter.setErrands(response.body());
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<List<Errand>> call,
                                  @NonNull Throwable t) { }
        });
    }

    private void fetchMyRuns(ErrandRepository repo, String userId) {
        if (userId == null) return;
        repo.getErrandByRunnerId(userId, new retrofit2.Callback<>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<List<Errand>> call,
                                   @NonNull retrofit2.Response<List<Errand>> response) {
                if (response.isSuccessful() && response.body() != null)
                    myRunsAdapter.setErrands(response.body());
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<List<Errand>> call,
                                  @NonNull Throwable t) { }
        });
    }

    private void fetchAvailableBounties(ErrandRepository repo, String userId) {
        repo.getAvailableErrands(new retrofit2.Callback<>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<List<Errand>> call,
                                   @NonNull retrofit2.Response<List<Errand>> response) {
                if (response.isSuccessful() && response.body() != null)
                    availableBountiesAdapter.setErrands(filterAvailableBounties(response.body(), userId));
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<List<Errand>> call,
                                  @NonNull Throwable t) { }
        });
    }

    private List<Errand> filterAvailableBounties(List<Errand> all, String userId) {
        java.util.stream.Stream<Errand> stream = all.stream();
        if (userId != null)
            stream = stream.filter(e -> !userId.equals(e.getBuyerId()) && !userId.equals(e.getRunnerId()));
        return stream.limit(10).collect(Collectors.toList());
    }

}