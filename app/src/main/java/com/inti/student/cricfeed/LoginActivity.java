package com.inti.student.cricfeed;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class LoginActivity extends AppCompatActivity {
    private EditText logUsername;
    private EditText logPassword;
    private Button Login;
    private TextView logRegister;
    private FirebaseAuth firebaseAuth;
    private int count = 3;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeUI();

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        //if user already logged in, redirect to Home
        if(user != null){
            finish();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        //login onclick events
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateEmpty()){
                    validate(logUsername.getText().toString(), logPassword.getText().toString());
                }
            }
        });

        //registeractivity hyperlink onclick event
        logRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initializeUI(){
        logUsername = (EditText)findViewById(R.id.etLogUsername);
        logPassword = (EditText)findViewById(R.id.etLogPassword);
        Login = (Button)findViewById(R.id.btnLogin);
        logRegister = (TextView)findViewById(R.id.tvLogRegister);
    }

    //validate empty text fields
    private boolean validateEmpty(){
        Boolean result = false;

        String username = logUsername.getText().toString();
        String password = logPassword.getText().toString();

        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, R.string.toast_validEmpty, Toast.LENGTH_SHORT).show();
        }
        else{
            result = true;
        }
        return result;
    }

    //validate with database record
    private void validate(String username, String password){
        progressDialog.setMessage("Validation in progress");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, R.string.toast_loginSuccess, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); //ensure won't "back" to login page
                }
                else{
                    count--;
                    //store string in variable
                    String attemptCount = getResources().getString(R.string.toast_attemptNo);
                    if (count > 0){
                        Toast.makeText(LoginActivity.this, attemptCount + String.valueOf(count), Toast.LENGTH_SHORT).show();
                    }
                    else if (count == 0) {
                        Toast.makeText(LoginActivity.this, R.string.toast_loginFailed, Toast.LENGTH_SHORT).show();
                        Login.setEnabled(false);
                    }
                }
            }
        });
    }
}
