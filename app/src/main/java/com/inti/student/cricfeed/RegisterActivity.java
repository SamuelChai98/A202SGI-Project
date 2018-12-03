package com.inti.student.cricfeed;

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

public class RegisterActivity extends AppCompatActivity {
    private EditText regName, regEmail, regPassword;
    private Button Register;
    private TextView regLogin;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeUI();

        firebaseAuth = FirebaseAuth.getInstance();

        //register button onclick event
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateEmpty()){
                    //Upload to database (trim whitespaces)
                    String user_email = regEmail.getText().toString().trim();
                    String user_password = regPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //Check if registration successful
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, R.string.toast_regSuccess, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                firebaseAuth.signOut(); //to prevent auto-login
                                finish();
                            }
                            else{
                                Toast.makeText(RegisterActivity.this, R.string.toast_regFailed, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        //loginactivity hyperlink onclick event
        regLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initializeUI(){
        regName = (EditText)findViewById(R.id.etRegName);
        regEmail = (EditText)findViewById(R.id.etRegEmail);
        regPassword = (EditText)findViewById(R.id.etRegPassword);
        Register = (Button)findViewById(R.id.btnRegister);
        regLogin = (TextView)findViewById(R.id.tvRegLogin);
    }

    //validate empty text fields
    private Boolean validateEmpty(){
        Boolean result = false;

        String name = regName.getText().toString();
        String email = regEmail.getText().toString();
        String password = regPassword.getText().toString();

        if(name.isEmpty() || password.isEmpty() || email.isEmpty()){
            Toast.makeText(this, R.string.toast_validEmpty, Toast.LENGTH_SHORT).show();
        }
        else{
            result = true;
        }
        return result;
    }
}
