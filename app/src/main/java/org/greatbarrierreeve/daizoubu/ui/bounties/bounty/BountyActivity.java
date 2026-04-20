package org.greatbarrierreeve.daizoubu.ui.bounties.bounty;


import android.content.Intent;
import android.net.Uri;
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

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;

import java.math.BigDecimal;

import org.greatbarrierreeve.daizoubu.R;
import org.greatbarrierreeve.daizoubu.api.ErrandService;
import org.greatbarrierreeve.daizoubu.data.model.AcceptRequest;
import org.greatbarrierreeve.daizoubu.data.model.Errand;
import org.greatbarrierreeve.daizoubu.data.repository.UserInfoRepository;
import org.greatbarrierreeve.daizoubu.network.RetrofitClient;
import org.greatbarrierreeve.daizoubu.ui.bounties.BountiesAdapter;
import org.greatbarrierreeve.daizoubu.ui.homepage.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BountyActivity extends AppCompatActivity {

    ImageView iconBack;
    MaterialButton buttonPlaceOrder;
    MaterialCardView buttonPhoneDaizouer;
    TextView textViewPlaceOrder;
    private Errand errand;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bounty);

        String errandJson = getIntent().getStringExtra(BountiesAdapter.EXTRA_ERRAND);
        if (errandJson != null) {
            errand = new Gson().fromJson(errandJson, Errand.class);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // back button click event handler
        iconBack = findViewById(R.id.iconBack);
        iconBack.setOnClickListener(view -> finish());

        // phone button click event handler
        buttonPhoneDaizouer = findViewById(R.id.buttonPhoneDaizouer);
        buttonPhoneDaizouer.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+6567676767"))));

        // accept bounty button click event handler
        buttonPlaceOrder = findViewById(R.id.buttonPlaceOrder);
        buttonPlaceOrder.setOnClickListener(view -> {
            ErrandService errandService = RetrofitClient.getErrandService();
            AcceptRequest acceptRequest = new AcceptRequest();
            acceptRequest.setRunnerId(UserInfoRepository.getUserId());

            errandService.acceptErrand(errand.getId(), acceptRequest).enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<Errand> call, @NonNull Response<Errand> response) {
                    if (response.isSuccessful()) {
                        showBountyAcceptDialog();
                    } else {
                        Toast.makeText(BountyActivity.this, "Failed to accept bounty: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Errand> call, @NonNull Throwable t) {
                    Toast.makeText(BountyActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });


        if (errand != null) {
            bindErrand();
        }
    }

    private void bindErrand() {
        // timestamp: "Posted X mins ago"
        TextView textViewOrderTimestamp = findViewById(R.id.textViewOrderTimestamp);
        if (errand.getCreatedAt() != null) {
            long nowSeconds = System.currentTimeMillis() / 1000;
            long minsAgo = (nowSeconds - errand.getCreatedAt().getSeconds()) / 60;
            textViewOrderTimestamp.setText("Posted " + minsAgo + " mins ago");
        }

        // large bounty amount header
        TextView textViewBountyAmount = findViewById(R.id.textViewBountyAmount);
        textViewBountyAmount.setText("$" + formatAmount(errand.getBounty()));

        // collect from — fixed store name
        TextView textViewStoreLocationName = findViewById(R.id.textViewStoreLocationName);
        textViewStoreLocationName.setText("SUTD Canteen");

        // deliver to — errand delivery location
        TextView textViewDeliverLocationName = findViewById(R.id.textViewDeliverLocationName);
        if (errand.getDeliveryLocation() != null) {
            textViewDeliverLocationName.setText(errand.getDeliveryLocation().getDisplayName());
        }

        // you pay = order items subtotal (excludes bounty)
        TextView textViewPayAmount = findViewById(R.id.textViewPayAmount);
        textViewPayAmount.setText("$" + formatAmount(errand.getSubtotal()));

        // you collect = subtotal + bounty
        TextView textViewCollectAmount = findViewById(R.id.textViewCollectAmount);
        BigDecimal collectTotal = safeAdd(errand.getSubtotal(), errand.getBounty());
        textViewCollectAmount.setText("$" + formatAmount(collectTotal));

        // you earn = bounty only
        TextView textViewEarnAmount = findViewById(R.id.textViewEarnAmount);
        textViewEarnAmount.setText("$" + formatAmount(errand.getBounty()));

        // buyer id
        TextView textViewHeaderDaizouer = findViewById(R.id.textViewHeaderDaizouer);
        textViewHeaderDaizouer.setText(errand.getBuyerId());
    }

    private String formatAmount(BigDecimal value) {
        if (value == null) return "0.00";
        return String.format("%.2f", value);
    }

    private BigDecimal safeAdd(BigDecimal a, BigDecimal b) {
        BigDecimal result = BigDecimal.ZERO;
        if (a != null) result = result.add(a);
        if (b != null) result = result.add(b);
        return result;
    }


    public void showBountyAcceptDialog() {

        ConstraintLayout sectionDialogOk;

        // inflate dialog layout
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_bounty_accept, null);

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
            Intent intent = new Intent(BountyActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

        });

    }

}
