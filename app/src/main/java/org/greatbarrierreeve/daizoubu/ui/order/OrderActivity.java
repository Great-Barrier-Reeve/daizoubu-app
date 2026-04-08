package org.greatbarrierreeve.daizoubu.ui.order;


import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.greatbarrierreeve.daizoubu.R;


public class OrderActivity extends AppCompatActivity {

    EditText editTextOrder;
    EditText editTextOrderBounty;
    Button buttonSubmitOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.order), (v, insets) -> {

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        // get references to widgets
        editTextOrder = findViewById(R.id.editTextOrder);
        editTextOrderBounty = findViewById(R.id.editTextOrderBounty);
        buttonSubmitOrder = findViewById(R.id.buttonSubmitOrder);

        // submit order button click event handler
        buttonSubmitOrder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // TODO: validate order
                String order = editTextOrder.getText().toString();

                // TODO: validate bounty
                String bounty = editTextOrderBounty.getText().toString();

                // display user inputs for dev purposes
                Toast.makeText(getApplicationContext(), order + bounty, Toast.LENGTH_SHORT).show();

            }

        });

    }

}
