package org.greatbarrierreeve.daizoubu.ui.order.menu;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import org.greatbarrierreeve.daizoubu.R;
import org.greatbarrierreeve.daizoubu.data.model.AddOnItem;
import org.greatbarrierreeve.daizoubu.data.model.MenuItem;
import org.greatbarrierreeve.daizoubu.ui.common.GridSpacingItemDecoration;
import org.greatbarrierreeve.daizoubu.ui.order.location.LocationActivity;

import java.util.ArrayList;
import java.util.List;


public class MenuActivity extends AppCompatActivity {

    ImageView iconBack;
    ImageView iconCart;
    MaterialCardView sectionDeliveryLocation;
    RecyclerView menuRecyclerView;


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

        // app bar click event handler
        sectionDeliveryLocation = findViewById(R.id.sectionDeliveryLocation);
        sectionDeliveryLocation.setOnClickListener(view -> startActivity(new Intent(MenuActivity.this, LocationActivity.class)));

        // back button click event handler
        iconBack = findViewById(R.id.iconBack);
        iconBack.setOnClickListener(view -> this.finish());

        // cart button click event handler
        iconCart = findViewById(R.id.iconCart);
        iconCart.setOnClickListener(view -> {});

        menuRecyclerView = findViewById(R.id.menuRecyclerView);
        List<AddOnItem> addOns = new ArrayList<>();
        addOns.add(new AddOnItem("1", "one", "desc of one", "2.79"));
        addOns.add(new AddOnItem("2", "two", "desc of two", "2.19"));
        List<MenuItem> dataSrc = new ArrayList<>() {{
            add(new MenuItem("borger", "Borger", "A delicious borger.", "6.79", addOns));
            add(new MenuItem("sporger", "Sporger", "A delicious sporger.", "5.31", new ArrayList<>()));
            add(new MenuItem("florger", "Florger", "A delicious florger.", "7.02", new ArrayList<>()));
        }};
        MenuAdapter menuAdapter = new MenuAdapter(dataSrc);
        menuRecyclerView.setAdapter(menuAdapter);
        menuRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // programmatically adjust grid spacing based on position
        int spacing = getResources().getDimensionPixelSize(R.dimen.grid_spacing);
        menuRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, spacing));

    }

}