package org.greatbarrierreeve.daizoubu.ui.bounties;


import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greatbarrierreeve.daizoubu.R;


public class BountiesActivity extends AppCompatActivity {

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bounties);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bounties), (v, insets) -> {

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        recyclerView = findViewById(R.id.bountiesRecyclerView);
        String[] dataSrc = {"Bounty 1", "Bounty 2", "Bounty 3", "Bounty 4", "Bounty 5"};
        BountiesAdapter bountiesAdapter = new BountiesAdapter(dataSrc);
        recyclerView.setAdapter(bountiesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

}
