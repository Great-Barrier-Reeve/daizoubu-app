package org.greatbarrierreeve.daizoubu.ui.order.cart;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.google.android.material.button.MaterialButton;

import org.greatbarrierreeve.daizoubu.R;
import org.greatbarrierreeve.daizoubu.api.ErrandService;
import org.greatbarrierreeve.daizoubu.data.model.Errand;
import org.greatbarrierreeve.daizoubu.data.model.ErrandStatus;
import org.greatbarrierreeve.daizoubu.data.model.Location;
import org.greatbarrierreeve.daizoubu.data.model.OrderItem;
import org.greatbarrierreeve.daizoubu.data.model.PriorityLevel;
import org.greatbarrierreeve.daizoubu.data.repository.ErrandRepository;
import org.greatbarrierreeve.daizoubu.data.repository.LocationRepository;
import org.greatbarrierreeve.daizoubu.network.RetrofitClient;
import org.greatbarrierreeve.daizoubu.ui.homepage.MainActivity;
import org.greatbarrierreeve.daizoubu.ui.order.location.LocationActivity;


public class CartActivity extends AppCompatActivity {

    CartViewModel cartViewModel;
    CartItemAdapter cartItemAdapter;
    RecyclerView recyclerViewCartItems;
    ConstraintLayout sectionDeliveryLocation;
    ConstraintLayout sectionBounty;
    ImageView iconBack;
    MaterialButton buttonPlaceOrder;
    TextView textViewBountyAmount;
    TextView textViewDeliveryLocationName;
    TextView textViewDeliveryLocationAddress;


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

        // link to cart view model
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        recyclerViewCartItems = findViewById(R.id.recyclerViewCartItems);
        cartItemAdapter = new CartItemAdapter(new ArrayList<>(), orderItem -> cartViewModel.remove(orderItem));
        recyclerViewCartItems.setAdapter(cartItemAdapter);
        recyclerViewCartItems.setLayoutManager(new LinearLayoutManager(this));
        cartViewModel.getItems().observe(this, cartItemAdapter::updateItems);

        // display bounty amount in money format
        textViewBountyAmount = findViewById(R.id.textViewBountyAmount);
        cartViewModel.getBountyAmount().observe(this, amount -> {

            String money = "$" + amount;
            textViewBountyAmount.setText(money);

        });

        // back button click event handler
        iconBack = findViewById(R.id.iconBack);
        iconBack.setOnClickListener(view -> this.finish());

        textViewDeliveryLocationName = findViewById(R.id.textViewDeliveryLocationName);
        textViewDeliveryLocationAddress = findViewById(R.id.textViewDeliveryLocationAddress);

        // delivery location section click event handler
        sectionDeliveryLocation = findViewById(R.id.sectionDeliveryLocation);
        sectionDeliveryLocation.setOnClickListener(view -> startActivity(new Intent(CartActivity.this, LocationActivity.class)));

        // bounty section click event handler
        sectionBounty = findViewById(R.id.sectionBounty);
        sectionBounty.setOnClickListener(view -> showBountyInputDialog());

        // place order button click event handler
        buttonPlaceOrder = findViewById(R.id.buttonPlaceOrder);
        buttonPlaceOrder.setOnClickListener(view -> {

            List<OrderItem> cartItems = cartViewModel.getItems().getValue();
            if (cartItems == null || cartItems.isEmpty()) {
                Toast.makeText(CartActivity.this, "Cart Empty", Toast.LENGTH_SHORT).show();
                return;
            }

            BigDecimal bounty = new BigDecimal(cartViewModel.getBountyAmount().getValue());
            BigDecimal subtotal = cartViewModel.getSubtotal();

            Errand errand = new Errand(
                    "user123",
                    "user234",
                    bounty,
                    cartItems,
                    ErrandStatus.REQUESTED,
                    PriorityLevel.NORMAL,
                    new Location("Albert Hong", "LT1"),
                    "Cai fan",
                    subtotal);

            ErrandService errandService = RetrofitClient.getErrandService();
            ErrandRepository errandRepository = new ErrandRepository(errandService);
            errandRepository.createErrand(errand, new retrofit2.Callback<>() {
                @Override
                public void onResponse(@NonNull retrofit2.Call<Errand> call, @NonNull retrofit2.Response<Errand> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Errand createdErrand = response.body();
                        cartViewModel.submitOrder();
                        Toast.makeText(CartActivity.this,
                                "Order placed! ID: " + createdErrand.getId(),
                                Toast.LENGTH_SHORT).show();
                        showOrderSuccessDialog();
                    } else {
                        Toast.makeText(CartActivity.this,
                                "Failed to place order: " + response.code(),
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull retrofit2.Call<Errand> call, @NonNull Throwable t) {
                    Toast.makeText(CartActivity.this,
                            "Network error: " + t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });

        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        Location location = LocationRepository.getLocation();
        textViewDeliveryLocationName.setText(location.getDisplayName());
        textViewDeliveryLocationAddress.setText(location.getAddress());
    }

    public void showBountyInputDialog() {

        TextView textViewDialogBountyAmount;

        // inflate dialog layout
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_bounty_input, null);

        // build dialog
        AlertDialog dialog = new AlertDialog.Builder(this, R.style.TransparentDialog)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        // display dialog
        dialog.show();

        // get bounty input and display in money format
        textViewDialogBountyAmount = dialogView.findViewById(R.id.textViewDialogBountyAmount);
        Runnable updateDisplay = () -> {

            // get bounty digits string from view model
            String digits = cartViewModel.getBountyInput();

            // pad with zeroes to ensure 2dp money format
            while (digits.length() < 3) { digits = "0" + digits; }

            // split dollars and cents place with dot
            String display = "$" + digits.substring(0, digits.length() - 2) + "." + digits.substring(digits.length() - 2);

            textViewDialogBountyAmount.setText(display);

        };

        // initial display
        updateDisplay.run();

        // get digit from the view tag and pass it to view model
        View.OnClickListener digitListener = view -> {

            String digit = (String) view.getTag();
            if (digit == null) return;

            cartViewModel.appendBountyInput(digit);
            updateDisplay.run();

        };

        // number buttons click event handlers
        dialogView.findViewById(R.id.buttonNumpadZero).setOnClickListener(digitListener);
        dialogView.findViewById(R.id.buttonNumpadOne).setOnClickListener(digitListener);
        dialogView.findViewById(R.id.buttonNumpadTwo).setOnClickListener(digitListener);
        dialogView.findViewById(R.id.buttonNumpadThree).setOnClickListener(digitListener);
        dialogView.findViewById(R.id.buttonNumpadFour).setOnClickListener(digitListener);
        dialogView.findViewById(R.id.buttonNumpadFive).setOnClickListener(digitListener);
        dialogView.findViewById(R.id.buttonNumpadSix).setOnClickListener(digitListener);
        dialogView.findViewById(R.id.buttonNumpadSeven).setOnClickListener(digitListener);
        dialogView.findViewById(R.id.buttonNumpadEight).setOnClickListener(digitListener);
        dialogView.findViewById(R.id.buttonNumpadNine).setOnClickListener(digitListener);

        // delete button click event handler
        dialogView.findViewById(R.id.buttonNumpadDelete).setOnClickListener(view -> {

            cartViewModel.deleteBountyDigit();
            updateDisplay.run();

        });

        // cancel button click event handler
        dialogView.findViewById(R.id.buttonNumpadCancel).setOnClickListener(view -> {

            cartViewModel.clearBountyInput();
            updateDisplay.run();

        });

        // confirm button click event handler
        dialogView.findViewById(R.id.buttonDialogSubmit).setOnClickListener(view -> {

            cartViewModel.confirmBountyInput();
            dialog.cancel();

        });

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