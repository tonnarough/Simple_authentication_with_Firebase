package com.example.testregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText ETEmail, ETPassword;
    private Button signedInBtn, registrationBtn;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ETEmail = (EditText) findViewById(R.id.email_et);
        ETPassword = (EditText) findViewById(R.id.password_et);
        signedInBtn = (Button) findViewById(R.id.signed_in);
        registrationBtn = (Button) findViewById(R.id.registration);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //User is signed in

                } else {
                    //User is signed out

                }
            }
        };

        signedInBtn.setOnClickListener(this);
        registrationBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signed_in:
                signedIn(ETEmail.getText().toString(), ETPassword.getText().toString());
                break;
            case R.id.registration:
                registration(ETEmail.getText().toString(), ETPassword.getText().toString());
                break;
            default:
                break;
        }
    }

    public void signedIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Авторизация успешна", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Авторизация провалена", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void registration(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Регистрация провалена", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}