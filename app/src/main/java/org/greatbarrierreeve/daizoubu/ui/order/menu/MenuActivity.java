package org.greatbarrierreeve.daizoubu.ui.order.menu;


import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greatbarrierreeve.daizoubu.R;
import org.greatbarrierreeve.daizoubu.data.model.AddOnItem;
import org.greatbarrierreeve.daizoubu.data.model.MenuItem;
import org.greatbarrierreeve.daizoubu.ui.common.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class MenuActivity extends AppCompatActivity {

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