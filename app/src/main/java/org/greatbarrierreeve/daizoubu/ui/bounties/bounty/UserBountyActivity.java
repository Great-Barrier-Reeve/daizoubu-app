package org.greatbarrierreeve.daizoubu.ui.bounties.bounty;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;

import org.greatbarrierreeve.daizoubu.R;
import org.greatbarrierreeve.daizoubu.data.model.Errand;
import org.greatbarrierreeve.daizoubu.ui.bounties.BountiesAdapter;


public class UserBountyActivity extends AppCompatActivity {

    ImageView iconBack;
    MaterialCardView buttonPhoneDaizoubuer;
    private MotionLayout motionLayout;
    private Errand errand;

    public enum ProgressStep {
        ACCEPTED,
        COLLECTED,
        DELIVERED
    }


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

        // reset animation state
        motionLayout = findViewById(R.id.sectionOrderStatus);
        motionLayout.jumpToState(R.id.stateAccepted);

        // back button click event handler
        iconBack = findViewById(R.id.iconBack);
        iconBack.setOnClickListener(view -> finish());

        // phone button click event handler
        buttonPhoneDaizoubuer = findViewById(R.id.buttonPhoneDaizoubuer);
        buttonPhoneDaizoubuer.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+6567676767"))));

    }


    public void setProgressStep(ProgressStep step) {

        switch (step) {

            case ACCEPTED  -> motionLayout.transitionToState(R.id.stateAccepted);
            case COLLECTED -> motionLayout.transitionToState(R.id.stateCollected);
            case DELIVERED -> motionLayout.transitionToState(R.id.stateDelivered);

        }

    }

}