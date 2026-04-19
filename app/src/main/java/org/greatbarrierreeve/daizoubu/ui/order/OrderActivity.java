package org.greatbarrierreeve.daizoubu.ui.order;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import org.greatbarrierreeve.daizoubu.R;
import org.greatbarrierreeve.daizoubu.data.repository.CartRepository;
import org.greatbarrierreeve.daizoubu.data.repository.LocationRepository;
import org.greatbarrierreeve.daizoubu.ui.order.cart.CartActivity;
import org.greatbarrierreeve.daizoubu.ui.order.location.LocationActivity;
import org.greatbarrierreeve.daizoubu.ui.order.menu.MenuActivity;


public class OrderActivity extends AppCompatActivity {

    ImageView iconBack;
    MaterialButton buttonPlaceOrder;
    MaterialCardView sectionAppBar;
    OrderViewModel orderViewModel;
    RecyclerView recyclerViewStores;
    TextView textViewDeliverLocation;
    TextView textViewPlaceOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        // set up recycler view
        recyclerViewStores = findViewById(R.id.recyclerViewStores);
        StoreAdapter storeAdapter = new StoreAdapter(new ArrayList<>(), store -> {

            Intent intent = new Intent(OrderActivity.this, MenuActivity.class);
            intent.putExtra("store_id", store.getId());
            startActivity(intent);

        });
        recyclerViewStores.setAdapter(storeAdapter);
        recyclerViewStores.setLayoutManager(new LinearLayoutManager(this));

        // set up view model
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);

        // update recycler view on data change
        orderViewModel.getStores().observe(this, storeAdapter::updateStores);

        // display error message
        orderViewModel.getError().observe(this, error -> Toast.makeText(this, error, Toast.LENGTH_SHORT).show());

        // app bar click event handler
        sectionAppBar = findViewById(R.id.sectionAppBar);
        sectionAppBar.setOnClickListener(view -> startActivity(new Intent(OrderActivity.this, LocationActivity.class)));

        // back button click event handler
        iconBack = findViewById(R.id.iconBack);
        iconBack.setOnClickListener(view -> this.finish());

        textViewDeliverLocation = findViewById(R.id.textViewDeliverLocation);

        // cart button click event handler
        buttonPlaceOrder = findViewById(R.id.buttonPlaceOrder);
        textViewPlaceOrder = findViewById(R.id.textViewPlaceOrder);
        buttonPlaceOrder.setOnClickListener(view -> startActivity(new Intent(OrderActivity.this, CartActivity.class)));

        CartRepository.getInstance().getItems().observe(this, items -> {
            int visibility = (items != null && !items.isEmpty()) ? View.VISIBLE : View.GONE;
            buttonPlaceOrder.setVisibility(visibility);
            textViewPlaceOrder.setVisibility(visibility);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        textViewDeliverLocation.setText(LocationRepository.getLocation().getDisplayName());
    }

}