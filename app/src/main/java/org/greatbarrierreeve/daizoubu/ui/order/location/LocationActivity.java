package org.greatbarrierreeve.daizoubu.ui.order.location;


import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

import org.greatbarrierreeve.daizoubu.R;


public class LocationActivity extends AppCompatActivity {

    EditText editTextSearchField;
    ImageView iconBack;
    MaterialCardView sectionSearchBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_location);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        // programatically adjust search field text size based on view width
        // because for some reason edit text does not support autosize
        editTextSearchField = findViewById(R.id.editTextSearchField);
        editTextSearchField.post(() -> {

            int viewHeight = editTextSearchField.getHeight();
            float textSize = viewHeight / 2.5f;
            editTextSearchField.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        });

        // sticky search bar click event handler
        sectionSearchBar = findViewById(R.id.sectionSearchBar);
        sectionSearchBar.setOnClickListener(view -> {

            // focus user on search field
            editTextSearchField.requestFocus();

            // show keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editTextSearchField, InputMethodManager.SHOW_IMPLICIT);

        });

        // back button click event handler
        iconBack = findViewById(R.id.iconBack);
        iconBack.setOnClickListener(view -> this.finish());

    }

}