package org.greatbarrierreeve.daizoubu.ui.order.menu;


import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greatbarrierreeve.daizoubu.R;
import org.greatbarrierreeve.daizoubu.api.ErrandService;
import org.greatbarrierreeve.daizoubu.data.model.AddOnItem;
import org.greatbarrierreeve.daizoubu.data.model.Errand;
import org.greatbarrierreeve.daizoubu.data.model.ErrandStatus;
import org.greatbarrierreeve.daizoubu.data.model.Location;
import org.greatbarrierreeve.daizoubu.data.model.MenuItem;
import org.greatbarrierreeve.daizoubu.data.model.OrderItem;
import org.greatbarrierreeve.daizoubu.data.model.PriorityLevel;
import org.greatbarrierreeve.daizoubu.network.RetrofitClient;
import org.greatbarrierreeve.daizoubu.data.repository.ErrandRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class ItemDetailActivity extends AppCompatActivity {

    Button buttonSubmitOrder;
    RecyclerView addOnRecyclerView;
    TextView textViewTitle;
    TextView textViewDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        // get menu item from intent
        MenuItem menuItem = getIntent().getParcelableExtra("menuItem");

        // update title text view
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewTitle.setText(menuItem.getName());

        // update desc text view
        textViewDesc = findViewById(R.id.textViewDesc);
        textViewDesc.setText(menuItem.getDesc());

        // update submit order button
        buttonSubmitOrder = findViewById(R.id.buttonSubmitOrder);
        buttonSubmitOrder.setText("$" + menuItem.getPrice());
        buttonSubmitOrder.setOnClickListener(view -> {

            List<AddOnItem> selectedAddOns = getSelectedAddOns(menuItem);
            String addOns = "";
            for (AddOnItem addOnItem : selectedAddOns) { addOns += ", " + addOnItem.getName(); }
            String message = String.format("Ordered %s%s", menuItem.getName(), addOns);
            // Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

            Errand errand = new Errand(
                    "user123",
                    "user234",
                    new BigDecimal(6.70),
                    new OrderItem.Builder(menuItem,5).build(),
                    ErrandStatus.REQUESTED,
                    PriorityLevel.NORMAL,
                    new Location("Albert Hong", "LT1"),
                    "Cai fan",
                    new BigDecimal(20.0));

            ErrandService errandService = RetrofitClient.getErrandService();
            ErrandRepository errandRepository = new ErrandRepository(errandService);
            errandRepository.createErrand(errand, new retrofit2.Callback<>() {
                @Override
                public void onResponse(@NonNull retrofit2.Call<Errand> call, @NonNull retrofit2.Response<Errand> response) {

                    if (response.isSuccessful() && response.body() != null) {

                        Errand createdErrand = response.body();
                        Toast.makeText(ItemDetailActivity.this,
                                "Order placed! ID: " + createdErrand.getId(),
                                Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(ItemDetailActivity.this,
                                "Failed to place order: " + response.code(),
                                Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(@NonNull retrofit2.Call<Errand> call, @NonNull Throwable t) {
                    Toast.makeText(ItemDetailActivity.this,
                            "Network error: " + t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });

        });

        // create addon recycler view
        addOnRecyclerView = findViewById(R.id.addOnRecyclerView);
        ItemDetailAdapter itemDetailAdapter = new ItemDetailAdapter(menuItem.getOptionAddOns());
        addOnRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        addOnRecyclerView.setAdapter(itemDetailAdapter);

    }


    private List<AddOnItem> getSelectedAddOns(MenuItem menuItem) {

        List<AddOnItem> selected = new ArrayList<>();

        for (AddOnItem addOnItem : menuItem.getOptionAddOns()) {

            if (addOnItem.getIsSelected()) { selected.add(addOnItem); }

        }

        return selected;
    }

}