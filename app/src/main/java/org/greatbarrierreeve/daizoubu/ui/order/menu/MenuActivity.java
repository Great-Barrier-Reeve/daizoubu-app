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
import org.greatbarrierreeve.daizoubu.data.model.MenuItem;

import java.util.ArrayList;
import java.util.List;


public class MenuActivity extends AppCompatActivity {

    RecyclerView recyclerView;


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

        recyclerView = findViewById(R.id.menuRecyclerView);
        List<MenuItem> dataSrc = new ArrayList<>() {{
//            add(new MenuItem("borger", "Borger", "A delicious borger.", "6.79", new ArrayList<>()));
//            add(new MenuItem("sporger", "Sporger", "A delicious sporger.", "5.31", new ArrayList<>()));
//            add(new MenuItem("florger", "Florger", "A delicious florger.", "7.02", new ArrayList<>()));
        }};
        MenuAdapter menuAdapter = new MenuAdapter(dataSrc);
        recyclerView.setAdapter(menuAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    }

}