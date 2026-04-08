package org.greatbarrierreeve.daizoubu.ui.bounties;


import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import org.greatbarrierreeve.daizoubu.R;
import org.greatbarrierreeve.daizoubu.api.ErrandService;
import org.greatbarrierreeve.daizoubu.data.model.Errand;
import org.greatbarrierreeve.daizoubu.network.RetrofitClient;
import org.greatbarrierreeve.daizoubu.repository.ErrandRepository;


public class BountiesActivity extends AppCompatActivity {

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bounties);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bounties), (v, insets) -> {

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        recyclerView = findViewById(R.id.bountiesRecyclerView);
        BountiesAdapter bountiesAdapter = new BountiesAdapter(new ArrayList<>());
        recyclerView.setAdapter(bountiesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ErrandService errandService = RetrofitClient.getService();
        ErrandRepository errandRepository = new ErrandRepository(errandService);

        errandRepository.getAvailableErrands(new retrofit2.Callback<>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<List<Errand>> call, @NonNull retrofit2.Response<List<Errand>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    bountiesAdapter.setErrands(response.body());

                } else {

                    Toast.makeText(BountiesActivity.this,
                            "Failed to fetch errands: " + response.code(),
                            Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<List<Errand>> call, @NonNull Throwable t) {

                Toast.makeText(BountiesActivity.this,
                        "Network error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();

            }

        });

    }

}
