package org.greatbarrierreeve.daizoubu.ui.order.location;


import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import org.greatbarrierreeve.daizoubu.R;
import org.greatbarrierreeve.daizoubu.data.repository.LocationRepository;

import java.util.ArrayList;


public class LocationActivity extends AppCompatActivity {

    EditText editTextSearchField;
    ImageView iconBack;
    MaterialCardView sectionSearchBar;

    private LocationAdapter adapter;

    private LocationViewModel locationViewModel;


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

        RecyclerView recyclerView = findViewById(R.id.recycler_view_locations);
        adapter = new LocationAdapter(new ArrayList<>(), location -> {
            LocationRepository.saveLocation(location);
            finish();
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);

        editTextSearchField.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        locationViewModel = new ViewModelProvider(this).get(LocationViewModel.class);

        locationViewModel.getLocations().observe(this, locationList -> {
            if (locationList != null) {
                adapter.updateData(locationList);
            }
        });

    }

}
