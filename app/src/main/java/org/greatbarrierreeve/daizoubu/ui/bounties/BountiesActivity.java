package org.greatbarrierreeve.daizoubu.ui.bounties;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.greatbarrierreeve.daizoubu.R;
import org.greatbarrierreeve.daizoubu.api.ErrandService;
import org.greatbarrierreeve.daizoubu.data.model.Errand;
import org.greatbarrierreeve.daizoubu.data.repository.ErrandRepository;
import org.greatbarrierreeve.daizoubu.data.repository.UserInfoRepository;
import org.greatbarrierreeve.daizoubu.network.RetrofitClient;

public class BountiesActivity extends AppCompatActivity {

    private ImageView iconBack;

    private ConstraintLayout sectionMyOrders;
    private ConstraintLayout sectionMyRuns;
    private ConstraintLayout sectionAvailableBounties;

    private RecyclerView recyclerViewMyOrders;
    private RecyclerView recyclerViewMyRuns;
    private RecyclerView recyclerViewBounties;

    private BountiesAdapter myOrdersAdapter;
    private BountiesAdapter myRunsAdapter;
    private BountiesAdapter availableBountiesAdapter;

    private TextView textViewEmptyMyOrders;
    private TextView textViewEmptyMyRuns;
    private TextView textViewEmptyAvailableBounties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bounties);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        iconBack = findViewById(R.id.iconBack);
        iconBack.setOnClickListener(v -> finish());

        sectionMyOrders          = findViewById(R.id.sectionMyOrders);
        sectionMyRuns            = findViewById(R.id.sectionMyDeliveries);
        sectionAvailableBounties = findViewById(R.id.sectionAvailableBounties);

        textViewEmptyMyOrders          = findViewById(R.id.textViewEmptyMyOrders);
        textViewEmptyMyRuns            = findViewById(R.id.textViewEmptyMyDeliveries);
        textViewEmptyAvailableBounties = findViewById(R.id.textViewEmptyAvailableBounties);

        recyclerViewMyOrders = findViewById(R.id.recyclerViewMyOrders);
        recyclerViewMyRuns   = findViewById(R.id.recyclerViewMyDeliveries);
        recyclerViewBounties = findViewById(R.id.recyclerViewBounties);

        String userId = "user123";

        myOrdersAdapter          = new BountiesAdapter(new ArrayList<>(), userId);
        myRunsAdapter            = new BountiesAdapter(new ArrayList<>(), userId);
        availableBountiesAdapter = new BountiesAdapter(new ArrayList<>(), userId);

        setupRecyclerView(recyclerViewMyOrders, myOrdersAdapter);
        setupRecyclerView(recyclerViewMyRuns, myRunsAdapter);
        setupRecyclerView(recyclerViewBounties, availableBountiesAdapter);

        ErrandService errandService = RetrofitClient.getErrandService();
        ErrandRepository errandRepository = new ErrandRepository(errandService);

        fetchMyOrders(errandRepository, userId);
        fetchMyRuns(errandRepository, userId);
        fetchAvailableBounties(errandRepository, userId);
    }

    private void setupRecyclerView(RecyclerView rv, BountiesAdapter adapter) {
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setNestedScrollingEnabled(false);
    }

    private void fetchMyOrders(ErrandRepository repo, String userId) {
        if (userId == null) return;

        repo.getErrandByBuyerId(userId, new retrofit2.Callback<>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<List<Errand>> call,
                                   @NonNull retrofit2.Response<List<Errand>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    showSection(sectionMyOrders, myOrdersAdapter,
                            textViewEmptyMyOrders, recyclerViewMyOrders,
                            response.body(), "No orders yet.");
                } else {
                    Toast.makeText(BountiesActivity.this,
                            "Failed to load My Orders: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<List<Errand>> call,
                                  @NonNull Throwable t) {
                Toast.makeText(BountiesActivity.this,
                        "Network error (My Orders): " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchMyRuns(ErrandRepository repo, String userId) {
        if (userId == null) return;

        repo.getErrandByRunnerId(userId, new retrofit2.Callback<>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<List<Errand>> call,
                                   @NonNull retrofit2.Response<List<Errand>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    showSection(sectionMyRuns, myRunsAdapter,
                            textViewEmptyMyRuns, recyclerViewMyRuns,
                            response.body(), "No runs yet.");
                } else {
                    Toast.makeText(BountiesActivity.this,
                            "Failed to load My Runs: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<List<Errand>> call,
                                  @NonNull Throwable t) {
                Toast.makeText(BountiesActivity.this,
                        "Network error (My Runs): " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchAvailableBounties(ErrandRepository repo, String userId) {
        repo.getAvailableErrands(new retrofit2.Callback<>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<List<Errand>> call,
                                   @NonNull retrofit2.Response<List<Errand>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Errand> filtered = filterAvailableBounties(response.body(), userId);
                    showSection(sectionAvailableBounties, availableBountiesAdapter,
                            textViewEmptyAvailableBounties, recyclerViewBounties,
                            filtered, "No bounties available.");
                } else {
                    Toast.makeText(BountiesActivity.this,
                            "Failed to load Available Bounties: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<List<Errand>> call,
                                  @NonNull Throwable t) {
                Toast.makeText(BountiesActivity.this,
                        "Network error (Available Bounties): " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Errand> filterAvailableBounties(List<Errand> all, String userId) {
        if (userId == null) return all;
        return all.stream()
                .filter(e -> !userId.equals(e.getBuyerId()) && !userId.equals(e.getRunnerId()))
                .collect(Collectors.toList());
    }

    private void showSection(ConstraintLayout section, BountiesAdapter adapter,
                             TextView emptyView, RecyclerView rv,
                             List<Errand> data, String emptyMessage) {
        section.setVisibility(View.VISIBLE);
        if (data.isEmpty()) {
            emptyView.setText(emptyMessage);
            emptyView.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
            adapter.setErrands(data);
        }
    }
}
