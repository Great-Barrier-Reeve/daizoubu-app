package org.greatbarrierreeve.daizoubu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.greatbarrierreeve.daizoubu.data.model.User;
import org.greatbarrierreeve.daizoubu.network.RetrofitClient;
import org.greatbarrierreeve.daizoubu.repository.AuthRepository;
import org.greatbarrierreeve.daizoubu.repository.UserInfoRepository;
import org.greatbarrierreeve.daizoubu.ui.homepage.MainActivity;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    EditText editTextUsername;
    EditText editTextPassword;
    Button buttonLogin;
    Button buttonGoToSignup;

    private AuthRepository authRepository;
    FirebaseAuth auth = FirebaseAuth.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        authRepository = new AuthRepository(RetrofitClient.getAuthService());


        // get references to widgets
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonGoToSignup = findViewById(R.id.buttonGoToSignup);


        // 2. Immediate Session Check (Skip login if already verified)
        if (UserInfoRepository.isLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });


        // login button click event handler
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextUsername.getText().toString().trim();

                if (email.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }

                // SAVE the email locally for the handshake later
                getSharedPreferences("auth_prefs", MODE_PRIVATE)
                        .edit()
                        .putString("pending_email", email)
                        .apply();

                // CALL the backend via repository
                authRepository.sendLoginLink(email, new Callback<Map<String, String>>() {
                    @Override
                    public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "link sent to " + email, Toast.LENGTH_LONG).show();
                        } else {
                            Log.e("DAIZOUBU_AUTH", "Server error code: " + response.code());
                            Toast.makeText(LoginActivity.this, "Server error. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Map<String, String>> call, Throwable t) {
                        Log.e("DAIZOUBU_AUTH", "Network error", t);
                        Toast.makeText(LoginActivity.this, "Connection failed. Check your Wi-Fi.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

//        handleIntent(getIntent());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        handleIntent(intent);
    }

private void handleIntent(Intent intent) {
    Uri data = intent.getData();
    if (data != null) {
        String link = data.toString();
        Log.d("DAIZOUBU_AUTH", "Caught the magic link: " + link);

        SharedPreferences prefs = getSharedPreferences("auth_prefs", MODE_PRIVATE);
        String email = prefs.getString("pending_email", "");

        if (!email.isEmpty()) {
            handleSignIn(email, link);
            if (UserInfoRepository.isLoggedIn()) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        } else {
            Toast.makeText(this, "Email missing. Please restart login.", Toast.LENGTH_LONG).show();
        }
    }
}


    // To this:
    private void handleSignIn(String email, String emailLink) {
        Log.d("DAIZOUBU_AUTH", "Attempting Firebase Sign-In with: " + email);

//        auth = FirebaseAuth.getInstance();
        if (auth.isSignInWithEmailLink(emailLink)) {
            auth.signInWithEmailLink(email, emailLink)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            Log.d("DAIZOUBU_AUTH", "FIREBASE SUCCESS! UID: " + user.getUid());

                            // Now get the Token to send to Spring Boot
                            user.getIdToken(true).addOnSuccessListener(result -> {
                                String token = result.getToken();
                                Log.d("DAIZOUBU_AUTH", "TOKEN ACQUIRED: " + token  + "...");
                                SharedPreferences prefs = getSharedPreferences("auth_prefs", MODE_PRIVATE);
                                prefs.edit()
                                        .putString("session_token", token)
                                        .putString("user_id", user.getUid())
                                        .apply();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            });
                        } else {
                            Log.e("DAIZOUBU_AUTH", "FIREBASE ERROR: " + task.getException().getMessage());
                        }
                    });
        } else {
            Log.e("DAIZOUBU_AUTH", "Link was not a valid Firebase Auth link.");
        }
    }
}
