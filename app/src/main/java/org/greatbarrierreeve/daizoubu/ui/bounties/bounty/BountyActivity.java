package org.greatbarrierreeve.daizoubu.ui.bounties.bounty;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

import org.greatbarrierreeve.daizoubu.R;


public class BountyActivity extends AppCompatActivity {

    ImageView iconBack;
    MaterialCardView buttonPhoneDaizouer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bounty);

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

    }

}