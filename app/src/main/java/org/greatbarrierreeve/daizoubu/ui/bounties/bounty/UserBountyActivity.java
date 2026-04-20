package org.greatbarrierreeve.daizoubu.ui.bounties.bounty;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;

import org.greatbarrierreeve.daizoubu.R;
import org.greatbarrierreeve.daizoubu.data.model.Errand;
import org.greatbarrierreeve.daizoubu.data.model.ErrandStatus;
import org.greatbarrierreeve.daizoubu.data.model.Timestamp;
import org.greatbarrierreeve.daizoubu.ui.bounties.BountiesAdapter;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class UserBountyActivity extends AppCompatActivity {

    ImageView iconBack;
    MaterialCardView buttonPhoneDaizoubuer;
    private MotionLayout motionLayout;
    private Errand errand;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_bounty);

        String errandJson = getIntent().getStringExtra(BountiesAdapter.EXTRA_ERRAND);
        if (errandJson != null) {
            errand = new Gson().fromJson(errandJson, Errand.class);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        motionLayout = findViewById(R.id.sectionOrderStatus);

        // back button click event handler
        iconBack = findViewById(R.id.iconBack);
        iconBack.setOnClickListener(view -> finish());

        // phone button click event handler
        buttonPhoneDaizoubuer = findViewById(R.id.buttonPhoneDaizoubuer);
        buttonPhoneDaizoubuer.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+6567676767"))));

        if (errand != null) {
            bindErrand();
        } else {
            motionLayout.jumpToState(R.id.stateAccepted);
        }
    }

    private void bindErrand() {
        ErrandStatus status = errand.getStatus();

        ConstraintLayout sectionDaizoubuer = findViewById(R.id.sectionDaizoubuer);

        // store details header
        TextView textViewStoreDetails = findViewById(R.id.textViewStoreDetails);
        textViewStoreDetails.setText("SUTD Canteen");

        // status name and description
        TextView textViewStatusName = findViewById(R.id.textViewStatusName);
        TextView textViewStatusDesc = findViewById(R.id.textViewStatusDesc);
        TextView textViewCollectedTimestamp = findViewById(R.id.textViewCollectedTimestamp);

        // icon visibility
        MaterialCardView cardAccepted  = findViewById(R.id.cardAccepted);
        MaterialCardView cardCollected = findViewById(R.id.cardCollected);
        MaterialCardView cardDelivered = findViewById(R.id.cardDelivered);
        cardAccepted.setVisibility(View.INVISIBLE);
        cardCollected.setVisibility(View.INVISIBLE);
        cardDelivered.setVisibility(View.INVISIBLE);

        // motion layout state + status text + icons
        if (status == ErrandStatus.ACCEPTED) {
            motionLayout.jumpToState(R.id.stateCollected);
            textViewStatusName.setText("COLLECTING");
            textViewStatusDesc.setText("Daizoubuer on the way to the store!");
            textViewCollectedTimestamp.setText("");
            cardAccepted.setVisibility(View.INVISIBLE);
            cardCollected.setVisibility(View.VISIBLE);
            cardDelivered.setVisibility(View.INVISIBLE);
        } else if (status == ErrandStatus.COLLECTED) {
            motionLayout.jumpToState(R.id.stateDelivered);
            textViewStatusName.setText("DELIVERING");
            textViewStatusDesc.setText("Daizoubuer on the way to you!");
            textViewCollectedTimestamp.setText("1809H 20/04/26");
            cardAccepted.setVisibility(View.INVISIBLE);
            cardCollected.setVisibility(View.INVISIBLE);
            cardDelivered.setVisibility(View.VISIBLE);
        } else if (status == ErrandStatus.DELIVERED) {
            motionLayout.jumpToState(R.id.stateDelivered);
            textViewStatusName.setText("DELIVERED");
            textViewStatusDesc.setText("Enjoy your meal!");
            textViewCollectedTimestamp.setText("1809H 20/04/26");
            cardAccepted.setVisibility(View.INVISIBLE);
            cardCollected.setVisibility(View.INVISIBLE);
            cardDelivered.setVisibility(View.INVISIBLE);
        } else {
            // REQUESTED (and any other status) → requesting state
            sectionDaizoubuer.setVisibility(View.INVISIBLE);
            motionLayout.jumpToState(R.id.stateAccepted);
            textViewStatusName.setText("REQUESTING");
            textViewStatusDesc.setText("Looking for a daizoubuer!");
            textViewCollectedTimestamp.setText("");
            cardAccepted.setVisibility(View.VISIBLE);
        }

        // timestamps
        TextView textViewAcceptedTimestamp  = findViewById(R.id.textViewAcceptedTimestamp);
        TextView textViewDeliveredTimestamp = findViewById(R.id.textViewDeliveredTimestamp);

        textViewAcceptedTimestamp.setText(formatTimestamp(errand.getAcceptedAt()));
        textViewDeliveredTimestamp.setText(formatTimestamp(errand.getDeliveredAt()));

        // daizoubuer (runner)
        TextView textViewHeaderDaizoubuer = findViewById(R.id.textViewHeaderDaizoubuer);
        textViewHeaderDaizoubuer.setText(errand.getRunnerId());

        // bounty amount
        TextView textViewBountyAmount = findViewById(R.id.textViewBountyAmount);
        textViewBountyAmount.setText("$" + formatAmount(errand.getBounty()));

        // total = subtotal + bounty
        TextView textViewTotalAmount = findViewById(R.id.textViewTotalAmount);
        textViewTotalAmount.setText("$" + formatAmount(safeAdd(errand.getSubtotal(), errand.getBounty())));
    }

    private String formatTimestamp(Timestamp ts) {
        if (ts == null) return "";
        Date date = new Date(ts.getSeconds() * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HHmm'H' dd/MM/yy", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sdf.format(date);
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

}
