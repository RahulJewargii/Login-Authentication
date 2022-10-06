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

public class Login extends AppCompatActivity {

    EditText Lemail, Lpassword;
    TextView goToRegister;
    Button login;
    ProgressBar progress;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Lemail = findViewById(R.id.email);
        Lpassword = findViewById(R.id.password);
        goToRegister = findViewById(R.id.textView4);
        login = findViewById(R.id.loginBtn);
        progress = findViewById(R.id.progressBar2);
        fAuth = FirebaseAuth.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Lemail.getText().toString().trim();
                String password = Lpassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    Lemail.setError("Email Is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Lpassword.setError("Password Is Required.");
                    return;
                }

                if (password.length() < 6){
                    Lpassword.setError("Password must be 6 character.");
                }

                progress.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Login.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Register.class));
                        }else {
                            Toast.makeText(Login.this, "Error!!!", Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}