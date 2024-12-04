package com.example.test5.test;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.test5.R;
import com.example.test5.database.UserDAO;

public class SignUpActivity extends AppCompatActivity {
    TextView tvSignIn;
    EditText edtUser, edtPassword, edtEmail, edtConfirmPassword;
    Button btnSignUp;
    UserDAO userDB;
    CheckBox checkBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        tvSignIn = findViewById(R.id.tvSignIn);
        edtUser = findViewById(R.id.edtName);
        edtPassword = findViewById(R.id.edtPass);
        edtConfirmPassword = findViewById(R.id.edtPassconfirm);
        edtEmail = findViewById(R.id.edtEmail);
        btnSignUp = findViewById(R.id.btnSignUp);
        checkBox = findViewById(R.id.cb);
        userDB = new UserDAO(SignUpActivity.this);

        tvSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
            startActivity(intent);
        });

        btnSignUp.setOnClickListener(v -> {
            String user = edtUser.getText().toString().trim();
            String pass = edtPassword.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String confirmpassword = edtConfirmPassword.getText().toString().trim();

            if (TextUtils.isEmpty(user)) {
                edtUser.setError("Username can not be empty");
                return;
            }
            if (TextUtils.isEmpty(pass)) {
                edtPassword.setError("Password can not be empty");
                return;
            }
            if (TextUtils.isEmpty(email)) {
                edtEmail.setError("Email can not be empty");
                return;
            }
            if (TextUtils.isEmpty(confirmpassword)) {
                edtConfirmPassword.setError("Confirm Password can not be empty");
                return;
            }

            // Check if passwords match
            if (!pass.equals(confirmpassword)) {
                edtConfirmPassword.setError("Passwords do not match");
                return;
            }

            boolean checkUsername = userDB.checkUsernameEmail(user, 1);
            boolean checkEmail = userDB.checkUsernameEmail(email, 2);

            if (checkUsername) {
                Toast.makeText(SignUpActivity.this, "Username exists", Toast.LENGTH_SHORT).show();
                return;
            }
            if (checkEmail) {
                Toast.makeText(SignUpActivity.this, "Email exists", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!checkBox.isChecked()) {
                Toast.makeText(SignUpActivity.this, "You must accept the terms and conditions to sign up", Toast.LENGTH_SHORT).show();
                return;
            }

            // Add user to the database
            long insert = userDB.addNewUser(user, pass, email);
            if (insert == -1) {
                Toast.makeText(SignUpActivity.this, "Signup Failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SignUpActivity.this, "Signup Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

