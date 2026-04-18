package org.greatbarrierreeve.daizoubu.ui.order.menu;


import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
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


public class ItemDetailActivity extends AppCompatActivity {

    Button buttonOrderAdd;
    ImageView iconX;
    RecyclerView addOnRecyclerView;
    TextView textViewDesc;
    TextView textViewOrderAdd;
    TextView textViewTitle;


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
        MenuItem menuItem = getIntent().getParcelableExtra("menu_item");

        // link to item detail view model
        ItemDetailViewModel itemDetailViewModel = new ViewModelProvider(this).get(ItemDetailViewModel.class);
        itemDetailViewModel.setMenuItem(menuItem);

        // cancel button click event handler
        iconX = findViewById(R.id.iconX);
        iconX.setOnClickListener(view -> finish());

        // update title text view
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewTitle.setText(menuItem.getName());

        // update desc text view
        textViewDesc = findViewById(R.id.textViewDesc);
        textViewDesc.setText(menuItem.getDesc());

        // update add to order button
        buttonOrderAdd = findViewById(R.id.buttonOrderAdd);
        textViewOrderAdd = findViewById(R.id.textViewOrderAdd);
        itemDetailViewModel.getTotalPrice().observe(this, total -> {

            String orderItemPrice = "Add to Order • $" + total;
            textViewOrderAdd.setText(orderItemPrice);

        });

        // add to order button click event handler
        buttonOrderAdd.setOnClickListener(view -> {

            itemDetailViewModel.addToCart();
            finish();

        });




//        buttonSubmitOrder.setOnClickListener(view -> {
//
//            List<AddOnItem> selectedAddOns = getSelectedAddOns(menuItem);
//            String addOns = "";
//            for (AddOnItem addOnItem : selectedAddOns) { addOns += ", " + addOnItem.getName(); }
//            String message = String.format("Ordered %s%s", menuItem.getName(), addOns);
//            // Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//
//            Errand errand = new Errand(
//                    "user123",
//                    "user234",
//                    new BigDecimal(6.70),
//                    new OrderItem.Builder(menuItem,5).build(),
//                    ErrandStatus.REQUESTED,
//                    PriorityLevel.NORMAL,
//                    new Location("Albert Hong", "LT1"),
//                    "Cai fan",
//                    new BigDecimal(20.0));
//
//            ErrandService errandService = RetrofitClient.getErrandService();
//            ErrandRepository errandRepository = new ErrandRepository(errandService);
//            errandRepository.createErrand(errand, new retrofit2.Callback<>() {
//                @Override
//                public void onResponse(@NonNull retrofit2.Call<Errand> call, @NonNull retrofit2.Response<Errand> response) {
//
//                    if (response.isSuccessful() && response.body() != null) {
//
//                        Errand createdErrand = response.body();
//                        Toast.makeText(ItemDetailActivity.this,
//                                "Order placed! ID: " + createdErrand.getId(),
//                                Toast.LENGTH_SHORT).show();
//
//                    } else {
//
//                        Toast.makeText(ItemDetailActivity.this,
//                                "Failed to place order: " + response.code(),
//                                Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//
//                @Override
//                public void onFailure(@NonNull retrofit2.Call<Errand> call, @NonNull Throwable t) {
//                    Toast.makeText(ItemDetailActivity.this,
//                            "Network error: " + t.getMessage(),
//                            Toast.LENGTH_SHORT).show();
//                }
//            });
//
//        });

        // create addon recycler view
        addOnRecyclerView = findViewById(R.id.addOnRecyclerView);
        ItemDetailAdapter itemDetailAdapter =  new ItemDetailAdapter(
                menuItem.getOptionAddOns(),
                new ItemDetailAdapter.AddOnSelectionController() {
                    @Override public boolean isAddOnSelected(AddOnItem a) {
                        return itemDetailViewModel.isAddOnSelected(a);
                    }
                    @Override public void setAddOnSelected(AddOnItem a, boolean checked) {
                        itemDetailViewModel.setAddOnSelected(a, checked);
                    }
                });
        addOnRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        addOnRecyclerView.setAdapter(itemDetailAdapter);

    }

}