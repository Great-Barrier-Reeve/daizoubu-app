package org.greatbarrierreeve.daizoubu;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {

    Button buttonGoToOrder;
    Button buttonGoToBounties;


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

        // get references to widgets
        buttonGoToOrder = findViewById(R.id.buttonGoToOrder);
        buttonGoToBounties = findViewById(R.id.buttonGoToBounties);

        // orders button click event handler
        buttonGoToOrder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                startActivity(intent);

            }
        });

        // send user to login activity for dev purposes
//        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//        startActivity(intent);

    }

}