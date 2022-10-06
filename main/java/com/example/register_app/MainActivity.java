package com.example.register_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextView gotToLogin;
    EditText Rname,Remail,Rpassword,Rphone;
    Button register;
    ProgressBar progress;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gotToLogin = findViewById(R.id.textView5);
        Rname = findViewById(R.id.name);
        Remail = findViewById(R.id.email);
        Rpassword = findViewById(R.id.password);
        Rphone = findViewById(R.id.phone);
        register = findViewById(R.id.loginBtn);
        progress = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),Register.class));
            finish();
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Remail.getText().toString().trim();
                String password = Rpassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    Remail.setError("Email Is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Rpassword.setError("Password Is Required.");
                    return;
                }

                if (password.length() < 6){
                    Rpassword.setError("Password must be more than 6 Character");
                    return;
                }
                progress.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Register.class));
                        }else {
                            Toast.makeText(MainActivity.this, "Error!!!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);
                        }

                    }
                });

            }
        });

        gotToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

    }
}