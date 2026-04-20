package org.greatbarrierreeve.daizoubu.ui.order.menu;


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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import org.greatbarrierreeve.daizoubu.R;
import org.greatbarrierreeve.daizoubu.data.repository.CartRepository;
import org.greatbarrierreeve.daizoubu.data.repository.LocationRepository;
import org.greatbarrierreeve.daizoubu.ui.common.GridSpacingItemDecoration;
import org.greatbarrierreeve.daizoubu.ui.order.cart.CartActivity;
import org.greatbarrierreeve.daizoubu.ui.order.location.LocationActivity;


public class MenuActivity extends AppCompatActivity {

    ImageView iconBack;
    MaterialButton buttonPlaceOrder;
    MaterialCardView sectionAppBar;
    MenuViewModel menuViewModel;
    RecyclerView recyclerViewMenuItems;
    TextView textViewDeliverLocation;
    TextView textViewPlaceOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        // get store id from order activity intent
        String storeId = getIntent().getStringExtra("store_id");

        // set up recycler view
        recyclerViewMenuItems = findViewById(R.id.recyclerViewMenuItems);
        MenuItemAdapter menuItemAdapter = new MenuItemAdapter(new ArrayList<>(), menuItem -> {

            Intent intent = new Intent(MenuActivity.this, ItemDetailActivity.class);
            intent.putExtra("menu_item", menuItem);
            startActivity(intent);

        });
        recyclerViewMenuItems.setAdapter(menuItemAdapter);
        recyclerViewMenuItems.setLayoutManager(new GridLayoutManager(this, 2));

        // programmatically adjust grid spacing based on position
        int spacing = getResources().getDimensionPixelSize(R.dimen.grid_spacing);
        recyclerViewMenuItems.addItemDecoration(new GridSpacingItemDecoration(2, spacing));

        // set up view model
        menuViewModel = new ViewModelProvider(this).get(MenuViewModel.class);
        menuViewModel.fetchMenu(storeId);

        // update recycler view on data change
        menuViewModel.getMenu().observe(this, menuItemAdapter::updateMenu);

        // display error message
        menuViewModel.getError().observe(this, error -> Toast.makeText(this, error, Toast.LENGTH_SHORT).show());

        // app bar click event handler
        sectionAppBar = findViewById(R.id.sectionAppBar);
        sectionAppBar.setOnClickListener(view -> startActivity(new Intent(MenuActivity.this, LocationActivity.class)));

        // back button click event handler
        iconBack = findViewById(R.id.iconBack);
        iconBack.setOnClickListener(view -> this.finish());

        textViewDeliverLocation = findViewById(R.id.textViewDeliverLocation);

        // cart button click event handler
        buttonPlaceOrder = findViewById(R.id.buttonPlaceOrder);
        textViewPlaceOrder = findViewById(R.id.textViewPlaceOrder);
        buttonPlaceOrder.setOnClickListener(view -> startActivity(new Intent(MenuActivity.this, CartActivity.class)));

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