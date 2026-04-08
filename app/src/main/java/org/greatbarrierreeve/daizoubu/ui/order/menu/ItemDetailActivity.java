package org.greatbarrierreeve.daizoubu.ui.order.menu;


import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greatbarrierreeve.daizoubu.R;
import org.greatbarrierreeve.daizoubu.data.model.AddOnItem;
import org.greatbarrierreeve.daizoubu.data.model.MenuItem;

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
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            // TODO: submit errand to API

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