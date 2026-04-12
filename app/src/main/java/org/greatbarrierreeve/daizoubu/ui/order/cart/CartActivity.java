package org.greatbarrierreeve.daizoubu.ui.order.cart;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import org.greatbarrierreeve.daizoubu.R;
import org.greatbarrierreeve.daizoubu.ui.homepage.MainActivity;
import org.greatbarrierreeve.daizoubu.ui.order.location.LocationActivity;


public class CartActivity extends AppCompatActivity {

    ConstraintLayout sectionDeliveryLocation;
    ImageView iconBack;
    MaterialButton buttonPlaceOrder;


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

        // delivery location section click event handler
        sectionDeliveryLocation = findViewById(R.id.sectionDeliveryLocation);
        sectionDeliveryLocation.setOnClickListener(view -> startActivity(new Intent(CartActivity.this, LocationActivity.class)));

        // place order button click event handler
        buttonPlaceOrder = findViewById(R.id.buttonPlaceOrder);
        buttonPlaceOrder.setOnClickListener(view -> showOrderSuccessDialog());


    }


    public void showOrderSuccessDialog() {

        ConstraintLayout sectionDialogOk;

        // inflate dialog layout
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_order_success, null);

        // build dialog
        AlertDialog dialog = new AlertDialog.Builder(this, R.style.TransparentDialog)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        // display dialog
        dialog.show();

        // dialog ok section click handler
        sectionDialogOk = dialog.findViewById(R.id.sectionDialogOk);
        sectionDialogOk.setOnClickListener(view -> dialog.cancel());

        // dialog cancel event handler
        dialog.setOnCancelListener(dialogInterface -> {

            // murder all previous activities on top of main activity
            Intent intent = new Intent(CartActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

        });

    }

}