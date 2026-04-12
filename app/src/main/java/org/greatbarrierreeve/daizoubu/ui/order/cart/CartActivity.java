package org.greatbarrierreeve.daizoubu.ui.order.cart;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.greatbarrierreeve.daizoubu.R;
import org.greatbarrierreeve.daizoubu.ui.order.location.LocationActivity;


public class CartActivity extends AppCompatActivity {

    ConstraintLayout sectionDeliveryLocation;
    ImageView iconBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        // back button click event handler
        iconBack = findViewById(R.id.iconBack);
        iconBack.setOnClickListener(view -> this.finish());

        // delivery location section event handler
        sectionDeliveryLocation = findViewById(R.id.sectionDeliveryLocation);
        sectionDeliveryLocation.setOnClickListener(view -> startActivity(new Intent(CartActivity.this, LocationActivity.class)));

    }

}