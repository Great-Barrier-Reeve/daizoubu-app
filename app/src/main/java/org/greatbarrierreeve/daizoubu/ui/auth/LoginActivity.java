package org.greatbarrierreeve.daizoubu.ui.auth;


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
import org.greatbarrierreeve.daizoubu.api.AuthService;
import org.greatbarrierreeve.daizoubu.network.RetrofitClient;
import org.greatbarrierreeve.daizoubu.repository.AuthRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    EditText editTextUsername;
    EditText editTextPassword;
    Button buttonLogin;
    Button buttonGoToSignup;
    AuthRepository authRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        // get references to widgets
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonGoToSignup = findViewById(R.id.buttonGoToSignup);

        AuthService service = RetrofitClient.getAuthService();
        authRepository = new AuthRepository(service);

        // login button click event handler
        buttonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//              TODO: validate username
                String username = editTextUsername.getText().toString();

                // TODO: validate password
                String password = editTextPassword.getText().toString();

                // display user inputs for dev purposes
                Toast.makeText(getApplicationContext(), username + password, Toast.LENGTH_SHORT).show();
//                String email = editTextUsername.getText().toString().trim();
//
//                if (email.isEmpty()) {
//                    Toast.makeText(LoginActivity.this, "Please enter an email", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                // 1. Show a Toast so the user knows something is happening
//                Toast.makeText(getApplicationContext(), "Sending magic link...", Toast.LENGTH_SHORT).show();
//
//                // 2. Call  the Repository
//                authRepository.sendLoginLink(email, new Callback<Map<String, String>>() {
//                    @Override
//                    public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
//                        if (response.isSuccessful()) {
//                            // Success! Spring Boot sent the email.
//                            Toast.makeText(getApplicationContext(), "Check your SUTD inbox!", Toast.LENGTH_LONG).show();
//                        } else {
//                            // Backend rejected it (e.g. not an SUTD email)
//                            Toast.makeText(getApplicationContext(), "Use a valid SUTD email", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                    @Override
//                    public void onFailure(Call<Map<String, String>> call, Throwable t) {
//                        // MANDATORY: Retrofit requires this block!
//                        Toast.makeText(getApplicationContext(), "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//
//                });
            }
        });
    }
}