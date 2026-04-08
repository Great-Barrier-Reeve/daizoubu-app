package org.greatbarrierreeve.daizoubu.ui.homepage;


import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.greatbarrierreeve.daizoubu.ui.bounties.BountiesActivity;
import org.greatbarrierreeve.daizoubu.R;
import org.greatbarrierreeve.daizoubu.ui.order.menu.MenuActivity;


public class MainActivity extends AppCompatActivity {

    CardView cardHighlightsBounties;
    CardView cardTabOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        // orders card click event handler
        cardTabOrder = findViewById(R.id.cardTabOrder);
        cardTabOrder.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);

        });

        // bounties card click event handler
        cardHighlightsBounties = findViewById(R.id.cardHighlightsBounties);
        cardHighlightsBounties.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, BountiesActivity.class);
            startActivity(intent);

        });

        // send user to login activity for dev purposes
//        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//        startActivity(intent);

    }

}