package org.greatbarrierreeve.daizoubu.ui.order;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import org.greatbarrierreeve.daizoubu.R;
import org.greatbarrierreeve.daizoubu.ui.order.cart.CartActivity;
import org.greatbarrierreeve.daizoubu.ui.order.location.LocationActivity;


public class OrderActivity extends AppCompatActivity {

    ImageView iconBack;
    MaterialButton buttonPlaceOrder;
    MaterialCardView sectionAppBar;


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

        // app bar click event handler
        sectionAppBar = findViewById(R.id.sectionAppBar);
        sectionAppBar.setOnClickListener(view -> startActivity(new Intent(OrderActivity.this, LocationActivity.class)));

        // back button click event handler
        iconBack = findViewById(R.id.iconBack);
        iconBack.setOnClickListener(view -> this.finish());

        // cart button click event handler
        buttonPlaceOrder = findViewById(R.id.buttonPlaceOrder);
        buttonPlaceOrder.setOnClickListener(view -> startActivity(new Intent(OrderActivity.this, CartActivity.class)));

    }

}